package edu.pnu.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.DbLog;
import edu.pnu.domain.Member;
import edu.pnu.persistence.LogRepository;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private LogRepository logRepo;
	

	
//	@Autowired
//	private LogDao logdao;
	
	
	public List<Member> getAllMember() {
//		Map<String,Object> map = memberdao.getAllMember();
//		logdao.addLog(map);
//		
//		return (List<Member>) map.get("result"); 
		
		DbLog log = new DbLog();
		
		log.setMethod("get");
		log.setSqlString("");
		log.setRegiddate(new Date());
		log.setSuccess(1);
		
		logRepo.save(log);
		
		return memberRepo.findAll();
	}
	
	public Member getMemberById(Integer id) {
//		Map<String, Object> map = memberdao.getMemberById(id); 
//		logdao.addLog(map);
//		
//		return  (Member) map.get("result");
		DbLog log = new DbLog();
		
		log.setMethod("get");
		log.setSqlString("");
		log.setRegiddate(new Date());
		log.setSuccess(1);
		
		logRepo.save(log);
		
		
		
		return memberRepo.findById(id).get(); 
	}
	
	public Member postMember(Member Member) {
//		Map<String, Object> map = memberdao.postMember(Member); 
//		logdao.addLog(map);
//		
//		if (map.get("success").equals(true)) {
//			return "Post 성공";
//		} else {
//			return "Post 실패";
//		}
		
		DbLog log = new DbLog();
		
		log.setMethod("Post");
		log.setSqlString("");
		log.setRegiddate(new Date());
		log.setSuccess(1);
		
		logRepo.save(log);
		
		
		return memberRepo.save(Member);
	}
	
	public Member putMember(Integer id, Member Member) {
//		Map<String, Object> map = memberdao.putMember(id, Member);
//		logdao.addLog(map);
//		
//		if (map.get("success").equals(true)) {
//			return "Put 성공";
//		} else {
//			return "Put 실패";
//		}
		DbLog log = new DbLog();
		
		log.setMethod("Put");
		log.setSqlString("");
		log.setRegiddate(new Date());
		log.setSuccess(1);
		logRepo.save(log);
		
	
		Member.setId(id);
		return memberRepo.save(Member); 
	}
	
	public Member patchMember(Integer id, Member Member) {
//		Map<String, Object> map = memberdao.patchMember(id, Member);
//		logdao.addLog(map);
//		
//		if (map.get("success").equals(true)) {
//			return "Patch 성공";
//		} else {
//			return "Patch 실패";
//		}
		Optional<Member> opt =  memberRepo.findById(id);
		
		DbLog log = new DbLog();
		
		log.setMethod("Patch");
		log.setSqlString("");
		log.setRegiddate(new Date());
		log.setSuccess(1);
		
		if (!opt.isPresent()) {
			log.setSuccess(0);
			logRepo.save(log);
			return null;
		}
		
		logRepo.save(log);
		
		Member memberTemp = memberRepo.findById(id).get();
		
		if (Member.getPass()==null) 
			Member.setPass(memberTemp.getPass());
		if (Member.getName()==null)
			Member.setName(memberTemp.getName());
		
		
		return memberRepo.save(Member);
	}
	
	public void deleteMember(Integer id) {
//		Map<String, Object> map = memberdao.deleteMember(id);
//		logdao.addLog(map);
//		
//		if (map.get("success").equals(true)) {
//			return "Delete 성공";
//		} else {
//			return "Delete 실패";
//		}	 
		DbLog log = new DbLog();
		
		log.setMethod("Delete");
		log.setSqlString("");
		log.setRegiddate(new Date());
		log.setSuccess(1);
		
		logRepo.save(log);
		
		memberRepo.deleteById(id);
		
	}

}
