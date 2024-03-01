package ru.aston.libertybankjwtstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aston.libertybankjwtstarter.jwt.JwtProvider;

@Configuration
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {


  @Bean
  public JwtProvider jwtProvider(JwtProperties properties) {

    return new JwtProvider(properties);
  }


}
