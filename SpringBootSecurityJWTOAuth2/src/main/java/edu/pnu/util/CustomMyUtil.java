package edu.pnu.util;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomMyUtil {

	@SuppressWarnings("unchecked")
	public static String getUsernameFromOAuth2User(OAuth2User user) {
		
		Map<String, Object> attmap = user.getAttributes();
		String userString = user.toString();
		
		String ret = null;
		
		if (userString.contains("googleapis.com")) {
			ret = "Google_" + attmap.get("name") + "_" + attmap.get("sub");
		} else if (userString.contains("github.com")) {
			ret = "Github_" + attmap.get("login") + "_" + attmap.get("id");
		}  else if (userString.contains("facebook.com")) {
			ret = "Facebook_" + attmap.get("name") + "_" + attmap.get("id");
		}  else if (userString.contains("response=")) {
			Map<String, Object> map = (Map<String, Object>)attmap.get("response");
			ret = "Naver_" + attmap.get("name") + "_" + attmap.get("id");
		}  else if (userString.contains("googleapis.com")) {
			Map<String, Object> map = (Map<String, Object>)attmap.get("properties");
			ret = "Kakao_" + attmap.get("nickname") + "_" + attmap.get("id");
		}
		
		if (ret != null) {
			ret = ret.replaceAll(",", "_").replaceAll(" ", "_");
		}
		return ret;
		
	}
	
}
