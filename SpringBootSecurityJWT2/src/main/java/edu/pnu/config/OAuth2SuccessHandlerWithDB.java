package edu.pnu.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("OAuth2SuccessHandlerWithDB")
public class OAuth2SuccessHandlerWithDB extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired private MemberRepository memberRepo;
	@Autowired private PasswordEncoder encoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		OAuth2AuthenticationToken OAuth2Token = (OAuth2AuthenticationToken) authentication;
		String provider = OAuth2Token.getAuthorizedClientRegistrationId();
		System.out.println("로그인 Provider: " + provider);
		
		OAuth2User user = OAuth2Token.getPrincipal();
		String email = (String) user.getAttributes().get("email");
		System.out.println("사용자 이메일: " + email);
		
		
		String username = provider + "-" + email;
		memberRepo.save(Member.builder().username(username)
										.password(encoder.encode("1a2s3d4f"))
										.role(Role.ROLE_MEMBER)
										.enabled(true).build());
		
		
		String jwtToken = JWTUtil.getJWT(provider, email);		
		response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
		
	}
}
