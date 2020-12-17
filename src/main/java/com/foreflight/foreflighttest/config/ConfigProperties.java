package com.foreflight.foreflighttest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigProperties {
    @Getter
    @Setter
    @Value("${api.username}")
    private static String username;

    @Getter
    @Setter
    @Value("${api.password}")
    private static String password;
}
