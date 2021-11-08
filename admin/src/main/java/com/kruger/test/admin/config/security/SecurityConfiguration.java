package com.kruger.test.admin.config.security;

import java.util.Collections;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import com.kruger.test.admin.rest.service.UserDetailImplService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.ModelAndView;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailImplService userDetailImplService;

    @Autowired
    private OAuthEntryPoint entryPoint;

    @Bean
    public ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
        return new ErrorViewResolver() {
            @Override
            public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status,
                    Map<String, Object> model) {
                return status == HttpStatus.NOT_FOUND
                        ? new ModelAndView("/index.html", Collections.<String, Object>emptyMap(), HttpStatus.OK)
                        : null;
            }
        };
    }

    @Bean
    public Filter jwtTokenFilter() {
        return new OAuthJWTFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                // http.cors().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers("/*")
                .permitAll()
                .antMatchers("/employee", "/employee/**", "/role", "/role/**", "/user", "/user/**",
                        "/vaccination-stock", "/vaccination-stock/**")
                .authenticated().antMatchers(HttpMethod.POST, "/oauth/token").permitAll();

        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailImplService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui.html").antMatchers("/").antMatchers("/resources/***")
                .antMatchers("/swagger-ui/index.html").antMatchers("/index.html")
                .antMatchers("/swagger-ui.html/swagger-resources/**").antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-ui/").antMatchers("/swagger-ui").antMatchers("/swagger-resources/**")
                .antMatchers("/v2/api-docs").antMatchers("/actuator/**")
                .antMatchers("/webjars/springfox-swagger-ui/**");
    }

}
