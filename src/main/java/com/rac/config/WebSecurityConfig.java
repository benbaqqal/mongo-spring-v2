package com.rac.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	// http.authorizeRequests().antMatchers("/",
	// "/home").permitAll().anyRequest().authenticated().and().formLogin()
	// .loginPage("/login").permitAll().and().logout().permitAll();

	http.httpBasic().and().authorizeRequests()
		.antMatchers("/index.html", "/views/home.html", "/views/post.html", "/views/login.html", "/css/*",
			"/user", "/js/*", "/lib/*", "/fonts/*", "/*")
		.permitAll().anyRequest().authenticated().and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
		.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

    private Filter csrfHeaderFilter() {
	return new OncePerRequestFilter() {
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		    FilterChain filterChain) throws ServletException, IOException {
		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if (csrf != null) {
		    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
		    String token = csrf.getToken();
		    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin")
		.password("password").roles("ADMIN");
    }
}
