package com.example.library_project.service;



import com.example.library_project.enums.AppLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceBundleService {
@Autowired
    private  ResourceBundleMessageSource resourceBundleMessageSource;

    public String getMessage(String code, AppLanguage appLanguage) {
        return resourceBundleMessageSource.getMessage(code, null, new Locale(appLanguage.name()));
    }

}