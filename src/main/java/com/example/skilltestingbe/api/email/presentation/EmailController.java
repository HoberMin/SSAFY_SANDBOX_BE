package com.example.skilltestingbe.api.email.presentation;

import com.example.skilltestingbe.api.email.dto.EmailRequestDto;
import com.example.skilltestingbe.api.email.dto.SendEmailDto;
import com.example.skilltestingbe.api.email.dto.VerifyEmailDto;
import com.example.skilltestingbe.api.email.service.EmailService;
import com.example.skilltestingbe.api.paging.dto.ArticlesByOffsetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Tag(name = "EMAIL API", description = "Email 페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping()
    @Operation(summary = "Email 전송", description = "입력한 Email로 인증 코드가 포함된 메일 전송")
    @ApiResponse(responseCode = "200", description = "Email 전송 성공",
            content = @Content(schema = @Schema(implementation = SendEmailDto.class)))
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequestDto requestDto) {

        try {
            emailService.sendEmail(requestDto.getEmail());
            SendEmailDto dto = SendEmailDto.builder()
                    .isOk(true)
                    .build();
            return ResponseEntity.ok().body(dto);
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/authentication")
    @Operation(summary = "인증 코드 검증", description = "Email로 전송된 인증 코드 검증")
    @ApiResponse(responseCode = "200", description = "인증 코드 검증 결과 반환",
            content = @Content(schema = @Schema(implementation = SendEmailDto.class),
            examples = {
                    @ExampleObject(
                            name = "인증 성공",
                            description = "인증 코드가 유효한 경우",
                            value = "{\"isOk\": true}"
                    ),
                    @ExampleObject(
                            name = "인증 실패",
                            description = "인증 코드 또는 Email이 유효하지 않은 경우",
                            value = "{\"isOk\": false}"
                    )
            }))
    public ResponseEntity<?> verifyEmail(@RequestBody EmailRequestDto requestDto) {
        try {
            boolean result = emailService.verify(requestDto.getEmail(), requestDto.getAuthentication());
            VerifyEmailDto dto = VerifyEmailDto.builder()
                    .isSuccess(result)
                    .build();
            return ResponseEntity.ok().body(dto);
        } catch (NoSuchElementException e) {
            VerifyEmailDto dto = VerifyEmailDto.builder()
                    .isSuccess(false)
                    .build();
            return ResponseEntity.ok().body(dto);
        }
    }
}
