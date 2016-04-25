/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author emirm
 */
@SpringBootApplication
@RestController
public class emirApplication {
    
    @RequestMapping("/user")
    public Principal user(Principal user) {
            return user;
    }
    
    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                    http.formLogin().and().logout().and().authorizeRequests()
                                    .antMatchers("/index.html", "/views/home.html", "/views/login.html", "/", 
                                            "/views/liga.html", "/views/liga_ispis.html", "/views/grad.html", "/views/grad_lista.html", "/views/klub.html", 
                                            "/views/klub_lista.html", "/views/sezona.html", "/views/sezona_edit.html").permitAll().anyRequest()
                                    .authenticated().and().csrf()
                                    .csrfTokenRepository(csrfTokenRepository()).and()
                                    .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
            }

            private Filter csrfHeaderFilter() {
                    return new OncePerRequestFilter() {
                            @Override
                            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                                            throws ServletException, IOException {
                                    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                                                    .getName());
                                    if (csrf != null) {
                                            Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                                            String token = csrf.getToken();
                                            if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
                                                    cookie = new Cookie("XSRF-TOKEN", token);
                                                    cookie.setPath("/");
                                                    response.addCookie(cookie);
                                            }
                                    }
                                    filterChain.doFilter(request, response);
                            }
                    };
            }

            private CsrfTokenRepository csrfTokenRepository() {
                    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
                    repository.setHeaderName("X-XSRF-TOKEN");
                    return repository;
            }
    }
    
    
    public static void main(String[] args) {
        SpringApplication.run(emirApplication.class, args);
    }
}
