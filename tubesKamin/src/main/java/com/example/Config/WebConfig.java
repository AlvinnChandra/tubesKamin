package com.example.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/QRCode/**")
                .addResourceLocations("file:/C:/Users/Renggana/OneDrive/Documents/GitHub/tubesKamin/tubesKamin/QRCode/"); //GANTI INI
    }
}
