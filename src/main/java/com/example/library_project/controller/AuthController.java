package com.example.library_project.controller;

import com.example.library_project.dto.ProfileDTO;
import com.example.library_project.dto.request.AuthRegistrationRequest;
import com.example.library_project.dto.request.ProfileLoginRequestDTO;
import com.example.library_project.enums.AppLanguage;
import com.example.library_project.service.AuthRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthRegistrationService authService;

    @PostMapping("/loge")
    @Operation(summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO>loge(@RequestBody ProfileLoginRequestDTO dto,
                                          @RequestHeader(value = "Accept-Language",
                                                  defaultValue = "uz")AppLanguage appLanguage){
        return ResponseEntity.ok( authService.loge(dto,appLanguage));
    }

    @Operation(summary = "Api for email  registration",description = "this api used for registration email")
    @PostMapping("/registration/email")
    public ResponseEntity<String> registrationEmail(@RequestBody AuthRegistrationRequest dto,
                                                        @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage appLanguage) {
        log.info("registration Email", dto.getEmail());
        return ResponseEntity.ok(authService.registrationEmail(dto, appLanguage));
    }

    @Operation(summary = "Api for email code Verification",description = "this api used for verification email")
    @GetMapping("/verification/email/{id}")
    public ResponseEntity<Boolean> emailVerification(@PathVariable("id") String id) {
        return ResponseEntity.ok(authService.emailVerification(id));
    }
    @PutMapping("/verification/email/permission/{id}")
   @Operation(summary = "API for granting email verification permission",
              description = "This API is used for confirming email verification permission by user Token.")
    public ResponseEntity<Boolean> emailVerificationPermission(@PathVariable("id") String id) {
        return ResponseEntity.ok(authService.emailVerificationPermission(id));
    }
    @PutMapping("/verification/email/rejection/{id}")
    @Operation(summary = "API for rejecting email verification",
            description = "This API is used for rejecting email verification by user Token.")
    public ResponseEntity<Boolean> emailVerificationRejection(@PathVariable("id") String id) {
        return ResponseEntity.ok(authService.emailVerificationRejection(id));
    }
}
