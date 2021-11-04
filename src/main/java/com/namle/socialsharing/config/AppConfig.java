package com.namle.socialsharing.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@Configuration
@Data
@NoArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppConfig {
    private String env;
    private String secretKey;
}
