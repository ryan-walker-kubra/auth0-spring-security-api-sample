package com.auth0.example;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity(debug = true)
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @PostConstruct
    public void setup() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                .antMatchers(GET, "/login").permitAll()
                .antMatchers(GET, "/photos/**").hasAuthority("read:photos")
                .antMatchers(POST, "/photos/**").hasAuthority("create:photos")
                .antMatchers(PUT, "/photos/**").hasAuthority("update:photos")
                .antMatchers(DELETE, "/photos/**").hasAuthority("delete:photos")
                .antMatchers(GET, "/permissions/**").anonymous()
                .antMatchers(GET, "/users/**").anonymous()
                .antMatchers(GET, "/rules/**").anonymous()
                .antMatchers(POST, "/rules/**").anonymous()
                .antMatchers(PATCH, "/rules/**").anonymous()
                .anyRequest().authenticated();
    }

}
