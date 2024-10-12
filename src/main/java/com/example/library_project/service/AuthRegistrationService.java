package com.example.library_project.service;

import com.example.library_project.dto.JwtDTO;
import com.example.library_project.dto.ProfileDTO;
import com.example.library_project.dto.request.AuthRegistrationRequest;
import com.example.library_project.dto.request.ProfileLoginRequestDTO;
import com.example.library_project.entity.EmailHistoryEntity;
import com.example.library_project.entity.ProfileEntity;
import com.example.library_project.enums.AppLanguage;
import com.example.library_project.enums.ProfileRole;
import com.example.library_project.enums.ProfileStatus;
import com.example.library_project.exp.AppBadException;
import com.example.library_project.repository.EmailHistoryRepository;
import com.example.library_project.repository.ProfileRepository;
import com.example.library_project.utl.JWTUtil;
import com.example.library_project.utl.MDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.library_project.utl.JWTUtil.decode;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthRegistrationService {
    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;
    private final ResourceBundleService resourceBundleService;
    private final EmailHistoryRepository emailHistoryRepository;

    public ProfileDTO loge(ProfileLoginRequestDTO dto, AppLanguage appLanguage) {
        ProfileEntity entity =getByEmail(dto.getEmail(),appLanguage);

        if (entity.getStatus().equals(ProfileStatus.ACTIVE)
                && entity.getPassword().
                equals(MDUtil.encode(dto.getPassword()))) {

            String jwt=JWTUtil.encode(entity.getEmail(),entity.getRole(),appLanguage);

             profileRepository.updateByIsLoggendIn(true,entity.getEmail());
            String txt = sendEmailVerification(entity,jwt);

            mailSenderService.sendEmail(entity.getEmail(), "Check login", txt);
            return toDto(entity,jwt);
        }
        throw new AppBadException(resourceBundleService.getMessage("email.password.wrong", appLanguage));

    }
    public String registrationEmail(AuthRegistrationRequest dto, AppLanguage appLanguage) {
        if(!validateUserRegistrationAttempt(dto,appLanguage)){

        }
        ProfileEntity entity=entityDto(dto);
        String jwt=JWTUtil.encode(entity.getEmail(),entity.getRole(),appLanguage);
        String text=sendVerificationEmail(entity,jwt);
        mailSenderService.sendEmail(entity.getEmail(), "Complete registration", text);

        var emailHistoryEntity=new EmailHistoryEntity();
        emailHistoryEntity.setEmail(dto.getEmail());
        emailHistoryEntity.setMessage(text);
        emailHistoryRepository.save(emailHistoryEntity);

       String message=resourceBundleService.getMessage("please.confirm.email",appLanguage);
        return message;

    }

    private boolean validateUserRegistrationAttempt(AuthRegistrationRequest dto, AppLanguage language) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.deleteByEmail(optional.get().getEmail());

                LocalDateTime from = LocalDateTime.now().minusMinutes(1);
                LocalDateTime to = LocalDateTime.now();


                if (emailHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 3) {
                    throw new AppBadException(resourceBundleService.getMessage("To.many.attempt.Please.try.after.one.minute", language));
                }
                return true;
            }
            throw new AppBadException(resourceBundleService.getMessage("This.email.has.been.registered", language));
        }
        return true;
    }

public Boolean emailVerification(String token) {
JwtDTO jwtDTO=decode(token);
ProfileEntity profileEntity=getByEmail(jwtDTO.getEmail(), jwtDTO.getAppLanguage());
    if (!profileEntity.getStatus().equals(ProfileStatus.ACTIVE)) {
        profileRepository.updateByEmail(ProfileStatus.ACTIVE, jwtDTO.getEmail());
        return true;
    }
    throw new AppBadException(resourceBundleService.getMessage("This.email.has.been.registered",jwtDTO.getAppLanguage()));
}

private  String sendVerificationEmail(ProfileEntity entity, String jwt){
    String text = "<h1 style=\"=text-align: center\">Hello %s</h1>\n" +
            "<p style=\"background-color: indianred; color: white; padding:30px\"> To complete registration please link to the following link </p>\n" +
            "<a style=\"background-color: #f44336;\n" +
            "  color: white;\n" +
            "  padding: 14px 25px;\n" +
            "  text-align: center;\n" +
            "  text-decoration: none;\n" +
            "  display: inline-block;\" href=\"http://localhost:8081/auth/verification/email/%s\n" +
            "\">Click</a>\n" +
            "<br>\n";

    text = String.format(text, entity.getName(), jwt);
    return text;
}
    private String sendEmailVerification(ProfileEntity entity, String jwt) {
        String text = "<h1 style=\"text-align: center\">Salom</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding:30px\">Siz bizning book saytimizga ushbu email orqali kirishga harakat qildingiz. Agar bu siz bo'lsangiz, iltimos tasdiqlang yoki rad eting!</p>\n" +
                "<a style=\"background-color: #4CAF50;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8081/auth/verification/email/permission/%s\">\n" +
                "Tasdiqlash</a>\n" +
                "<br><br>\n" +
                "<a style=\"background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8081/auth/verification/email/rejection/%s\">\n" +
                "Rad etish</a>\n";

        text = String.format(text,jwt,jwt);
        return text;
    }
    private ProfileEntity getByEmail(String email,AppLanguage appLanguage){
    Optional<ProfileEntity> optional = Optional.ofNullable(profileRepository.findByEmail(email)
            .orElseThrow(() -> new AppBadException(resourceBundleService.getMessage("item.not.found", appLanguage))));
    return optional.get();

}
    public Boolean emailVerificationPermission(String token) {

        ProfileEntity profileEntity=getProfileToken(token);
        if(!profileEntity.isLoggedIn()){
          profileRepository.updateByIsLoggendIn(true,profileEntity.getEmail());
        }
        return null;
    }
    public Boolean emailVerificationRejection(String token) {
        ProfileEntity profileEntity=getProfileToken(token);
        if(profileEntity.isLoggedIn()){
            profileRepository.updateByIsLoggendIn(false,profileEntity.getEmail());
        }
        return null;
    }

    private  ProfileEntity entityDto(AuthRegistrationRequest dto){
        ProfileEntity entity = new ProfileEntity();
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setName(dto.getUsername());

        profileRepository.save(entity);
        return entity;
    }
    private  ProfileEntity getProfileToken(String token){
        JwtDTO jwtDTO=decode(token);
        ProfileEntity profileEntity=getByEmail(jwtDTO.getEmail(), jwtDTO.getAppLanguage());
        return profileEntity;
    }

    public ProfileDTO toDto(ProfileEntity entity, String jwt){
        ProfileDTO dto=new ProfileDTO();
        dto.setEmail(entity.getEmail());
        dto.setJwtToken(jwt);

        return dto;
    }
}