package edu.pnu.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persisitence.MemberRepository;
import edu.pnu.utill.JWTUtill;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component("OAuth2SuccessHandlerWithDB")
@RequiredArgsConstructor
public class OAuth2SuccessHandlerWithDB extends OAuth2SuccessHandler {
	private final MemberRepository memberRepo;
	private final PasswordEncoder encoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Map<String, String> map = getUserInfo(authentication);
		
		String username = map.get("provider") + "_" + map.get("email");
		memberRepo.save(Member.builder().username(username)
										.password(encoder.encode("1a2s3d4f"))
										.role(Role.ROLE_MEMBER)
										.enabled(true).build());
		
		String token = JWTUtill.getJWT(username);
		
		sendJWTtoClient(response, token);
	}
	
}
