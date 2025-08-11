package com.rubypaper.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL) // One-나 / Many-상대방
	private List<Board> boardList = new ArrayList<>();
}
