package com.example.skilltestingbe.qa.presentation;

import com.example.skilltestingbe.qa.dto.QaRegisterDto;
import com.example.skilltestingbe.qa.service.QaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qa")
public class QaController {

	private final QaService qaService;

	@PostMapping()
	public ResponseEntity<?> register(@RequestBody QaRegisterDto qaRegisterDto) {
		qaService.save(qaRegisterDto);
		return ResponseEntity.ok().build();
	}
}
