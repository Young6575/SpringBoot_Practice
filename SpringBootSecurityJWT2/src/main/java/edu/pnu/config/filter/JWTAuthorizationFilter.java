package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
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
		
		String jwtToken = request.getHeader("Authorization");
		System.out.println("jwtToken:" + jwtToken);
		if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		User user = null;
		String username = JWTUtil.getClaim(jwtToken, JWTUtil.usernameClaim);
		if (username != null) {
			System.out.println("username:" + username);
			Optional<Member> opt = memberRepo.findById(username);
			if (!opt.isPresent()) {
				filterChain.doFilter(request, response);
				return;
		}
			Member member = opt.get();
			System.out.println("===>member:" + member);
		
			user = new User(member.getUsername(), user.getPassword(),AuthorityUtils.createAuthorityList(member.getRole().toString()));
					
		} else {
			String provider = JWTUtil.getClaim(jwtToken, JWTUtil.providerClaim);
			String email = JWTUtil.getClaim(jwtToken, JWTUtil.emailClaim);
			System.out.println("username:" + provider + "-" + email);
			user = new User(provider + "-" + email, "abcd", AuthorityUtils.createAuthorityList("ROLE_USER"));
		}
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		// 시큐리티 세션에 등록한다.
		SecurityContextHolder.getContext().setAuthentication(auth);
		// 다음 필터로 이동
		filterChain.doFilter(request, response);
		
	}

}
