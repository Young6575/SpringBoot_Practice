package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persisitence.MemberRepository;
import edu.pnu.utill.JWTUtill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final MemberRepository memberRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 요청 헤더에서 JWT를 얻어온다.
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (jwtToken == null || !jwtToken.startsWith(JWTUtill.prefix)) {
			// 토근이 없거나, "Bearer "로 시작하지 않는다면
			filterChain.doFilter(request, response); // 필터를 그냥 통과
			return;
		}
		String username = JWTUtill.getClaim(jwtToken, JWTUtill.usernameClaim);
		User user = null;
		if (username != null) {
			Optional<Member> opt = memberRepo.findById(username);
			if (!opt.isPresent()) {
				System.out.println("[JWTAuthorizationFilter]not found user!");
				filterChain.doFilter(request, response);
				return;
			}
			Member member = opt.get();
			System.out.println("[JWTAuthorizationFilter]" + member);
		
			user = new User(member.getUsername(), member.getPassword(),
							AuthorityUtils.createAuthorityList(member.getRole().toString()));
		}
		else {
			String provider = JWTUtill.getClaim(jwtToken, JWTUtill.providerClaim);
			String email = JWTUtill.getClaim(jwtToken, JWTUtill.emailClaim);
			System.out.println("[JWTAuthorizationFilter]username:"
													+ provider + "_" + email);
			user = new User(provider + "_" + email, "****",
							AuthorityUtils.createAuthorityList(Role.ROLE_MEMBER.toString()));		
		}        
		// 인증 객체 생성
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// 시큐리티 세션에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(request, response);
		
		
	}
}
