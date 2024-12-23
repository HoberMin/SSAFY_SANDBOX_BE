package com.example.skilltestingbe.qa.service;

import com.example.skilltestingbe.qa.dto.QaRegisterDto;
import com.example.skilltestingbe.qa.repository.QaRepository;
import com.example.skilltestingbe.qa.repository.domain.Qa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QaService {

    private final QaRepository qaRepository;

    public void save(QaRegisterDto qaRegisterDto) {
        Qa qa = Qa.builder()
                .name(qaRegisterDto.getName())
                .region(qaRegisterDto.getRegion())
                .content(qaRegisterDto.getContent())
                .classNumber(qaRegisterDto.getClassNumber())
                .build();

        qaRepository.save(qa);
    }
}
