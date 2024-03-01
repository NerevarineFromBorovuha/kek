package ru.aston.libertybankjwtstarter.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Slf4j
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtProperties {

  //@Value("${application.security.jwt.secret}")
  private String secret;

@PostConstruct
  void init(){
    log.info("initialisation jwt successful");
  }

}
