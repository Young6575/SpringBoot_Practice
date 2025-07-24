package com.rubypaper.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@AllArgsConstructor // 모든 멤버 변수를 포함하는 생성자 생성
@NoArgsConstructor  // 기본 생성자를 생성
@Builder  // 객체 생성시 Builder Pattern 제공
public class BoardVO {
	private int seq;
	private String title;
	private String writer;
	private String content;
	@Builder.Default
	private Date createDate = new Date();
	private int cnt;
}
