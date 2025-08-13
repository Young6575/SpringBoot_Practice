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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	private Long seq;
	private String title;
	private String writer;
	private String content;
	@Builder.Default
	private Date createDate = new Date();
	@Builder.Default
	private Long cnt = 0L;

}
