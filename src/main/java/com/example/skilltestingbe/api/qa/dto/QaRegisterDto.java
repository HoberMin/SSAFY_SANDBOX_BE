package com.example.skilltestingbe.api.qa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QaRegisterDto {
	private String classNumber;
	private String region;
	private String name;
	private String content;
}
