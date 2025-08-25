package edu.pnu.config;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletResponse;

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@SuppressWarnings("unchecked")
	Map<String, String> getUserInfo(Authentication authentication) {
		OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;
		String provider = oAuth2Token.getAuthorizedClientRegistrationId();
		System.out.println("[OAuth2SuccessHandler]Provider:" + provider);
		
		OAuth2User user = oAuth2Token.getPrincipal();
		String email = null;
		if (provider.equalsIgnoreCase("naver")) {
			email = (String)((Map<String, Object>)user.getAttribute("response")).get(email);
		} else {
			email = (String) user.getAttributes().get(email);
		}
		System.out.println("[OAuth2SuccessHandlet]email:" + email);
		return Map.of("provider",provider,"email",email);
	}
	
	void sendJWTtoClient(HttpServletResponse response, String token) {
		System.out.println("[OAuth2SuccessHandler]token:" + token);
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
	}
	
	
}
