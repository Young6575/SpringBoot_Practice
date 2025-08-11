package com.rubypaper.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	private String title;
//	private String writer;
	private String content;
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createDate; // 테이블 생성 시 CamelNotation일 경우 create_date로 변경됨.
	private Long cnt;
	
	
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne() // Many-나 / one-상대방
	@JoinColumn(name = "mid")
	Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getBoardList().add(this);
	}
	
}
