package edu.pnu.config.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import edu.pnu.utill.JWTUtill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	

	private final AuthenticationManager authenticationManager;
	
	// /Login Method={POST} 요청 시
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		// Json/Object Mapping 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			// request에서 json 타입의 [username/password]를 읽어서 Member 객체를 생성
			Member member = mapper.readValue(request.getInputStream(), Member.class);
			
			// Security에서 자격 증명 요청에 필요한 객체 생성
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
			
			// 인증 진행 -> UserDatailsService의 loadUserByUsername에서 DB로부터 사용자 정보를 읽어온 뒤
			// 사용자 입력 정보와 비교한 뒤 자격 증명에 성공하면 Authentication객체를 만들어서 리턴
			return authenticationManager.authenticate(authToken);
			
		} catch (Exception e) {
			System.out.println("[JWTAuthenticationFilter]" + e.getMessage());// 예외 발생 로그 출력
			response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 자격 증명에 실패하면 응답코드 리턴
		}
		return null;
	}
	
	// 인증 시
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
	
		// 자격 증명이 성공하면 loadUserByUsername에거 만든 객체가 authResult에 담겨져 있다.
		User user = (User) authResult.getPrincipal();
		
		// user 객체를 콘솔에 출력해서 확인
		System.out.println("[JWTAuthenticationFilter]auth:" + user);
		
		// username으로 JWT를 생성한다.
		String token = JWTUtill.getJWT(user.getUsername());
		
		// Response Header[Authorization]에 JWT를 저장해서 응답.
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		response.setStatus(HttpStatus.OK.value());
	}

	
	
}

