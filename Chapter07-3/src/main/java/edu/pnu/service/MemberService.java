package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void save(Member member) {
		member.setPass(encoder.encode(member.getPass()));
		member.setRole(Role.ROLE_MEMBER);
		member.setEnabled(true);
		memberRepo.save(member);
	}
	
	
}
