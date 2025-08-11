package com.rubypaper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Member {
	@Id
	@Column(name = "MERBER_ID")
	private String id;
	private String password;
	private String name;
	private String role;
}
