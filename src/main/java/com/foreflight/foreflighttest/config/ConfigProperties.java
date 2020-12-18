package com.foreflight.foreflighttest.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigProperties {

    @Value("${api.username}")
    private String username;

    @Value("${api.password}")
    private String password;

}
