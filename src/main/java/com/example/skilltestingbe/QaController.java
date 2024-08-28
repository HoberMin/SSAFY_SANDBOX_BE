package com.example.skilltestingbe;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QaController {
	private final QaRepository qaRepository;

	@PostMapping("/qa")
	public void register(@RequestBody QaRegisterDto qaRegisterDto) {
		Qa qa = Qa.builder()
			.classNumber(qaRegisterDto.getClassNumber())
			.region(qaRegisterDto.getRegion())
			.name(qaRegisterDto.getName())
			.content(qaRegisterDto.getContent())
			.build();

		qaRepository.save(qa);
	}
}
