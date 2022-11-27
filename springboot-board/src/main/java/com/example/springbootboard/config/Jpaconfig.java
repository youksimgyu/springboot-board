package com.example.springbootboard.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configurable
@EnableJpaAuditing
public class Jpaconfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("youk"); // TODO : 스프링 시큐리티로 인증 기능을 붙이게 될 떄, 수정필요함.
    }
}
