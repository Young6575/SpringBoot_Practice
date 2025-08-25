package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.config.filter.JWTAuthorizationFilter;
import edu.pnu.persisitence.MemberRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	
	private final MemberRepository memberRepo;
	
	@Resource(name = "${project.oauth2login.qualifier.name}")
	private AuthenticationSuccessHandler oauth2SuccessHandler;
	

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(cf->cf.disable());
		
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		http.formLogin(frmLogin->frmLogin.disable());
		
		http.httpBasic(basic->basic.disable());
		
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// 스피링 시큐리티 필터체인에 작성한 필터를 추가한다. UsernamePasswordAuthenticationFilter를 상속한 필터이므로
		// 원래 UsernamePassowrdAuthenticationFilter가 위치한 곳에 대신 추가된다. 
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
	
		
		// 스피링 시큐리티가 등록한 필터들 중에서 AuthorizationFilter 앞에 JWTAuthorizationFilter를 삽입한다.
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepo), AuthorizationFilter.class);
		
		http.oauth2Login(oauth2->oauth2.successHandler(oauth2SuccessHandler));
		
		return http.build();
	}
	
}
