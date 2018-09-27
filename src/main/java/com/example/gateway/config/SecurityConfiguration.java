package com.example.gateway.config;

import com.example.gateway.security.CustomBasicAuthenticationFilter;
import com.example.gateway.security.CustomBasicAuthenticationProvider;
import com.example.gateway.security.TokenAuthenticationFitler;
import com.example.gateway.security.TokenAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomBasicAuthenticationProvider customBasicAuthenticationProvider;
//
//    @Autowired
//    private TokenAuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customBasicAuthenticationProvider());
        auth.authenticationProvider(tokenAuthenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .httpBasic()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  // stateless site
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/token").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .addFilterBefore(new CustomBasicAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFitler(authenticationManager()), CustomBasicAuthenticationFilter.class);
    }


    @Bean
    public CustomBasicAuthenticationProvider customBasicAuthenticationProvider() {
        return new CustomBasicAuthenticationProvider();
    }

    @Bean
    public TokenAuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
