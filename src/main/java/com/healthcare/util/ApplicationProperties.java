package com.healthcare.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
    
    @Value("${doctor.api.url}")
    public String doctorApiUrl;
    
    @Value("${doctor.api.key}")
    public String doctorApiKey;

}
