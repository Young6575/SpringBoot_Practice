package edu.pnu.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
import edu.pnu.persistence.MemberRepository;
import jakarta.annotation.Resource;

@Configuration
public class SecurityConfig {
	
	@Resource(name = "${project.oauth2login.qualifier.name}")
	private AuthenticationSuccessHandler oauth2SuccessHandler;

	@Autowired
    private final MemberRepository memberRepository;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

    SecurityConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// csrf 비활성화
		http.csrf(cf->cf.disable());
		
		// 인가
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		// LoginFrom 미사용
		http.formLogin(frmlogin->frmlogin.disable());
		
		// HttpBasic 미사용
		http.httpBasic(basic->basic.disable());
		
		// Session 설정
		// Url 호출 뒤 응답할 때까지는 유지되지만 응답 후 삭제
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		//http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		AuthenticationManager manager = authenticationConfiguration.getAuthenticationManager();
		JWTAuthenticationFilter filter = new JWTAuthenticationFilter(manager);
		http.addFilter(filter);
		
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepository), AuthorizationFilter.class);
		
		http.oauth2Login(oauth2->oauth2.successHandler(oauth2SuccessHandler));
		
		return http.build();
	}
	
}
