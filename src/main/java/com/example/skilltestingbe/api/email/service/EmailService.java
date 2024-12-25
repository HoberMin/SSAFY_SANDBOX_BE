package com.example.skilltestingbe.api.email.service;

import com.example.skilltestingbe.api.email.repository.EmailRepository;
import com.example.skilltestingbe.api.email.repository.domain.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final SpringTemplateEngine templateEngine;

    private static final int EXPIRATION_MINUTES = 5;
    private static final String MAIL_TITLE = "[SSAFY SANDBOX] 이메일 검증 요청 메일입니다";

    public void sendEmail(String email) throws MessagingException {
        emailRepository.findByEmail(email).ifPresent((emailRepository::delete));

        String code = UUID.randomUUID().toString().substring(0, 6);
        Email emailEntity = Email.builder()
                .email(email)
                .code(code)
                .build();

        emailRepository.saveAndFlush(emailEntity);
        send(email, code);
    }

    private void send(String email, String code) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariable("code", code);
        String htmlContent = templateEngine.process("email/verify-email-templates", context);

        helper.setTo(email);
        helper.setSubject(MAIL_TITLE);
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }

    public boolean verify(String email, String code) throws NoSuchElementException {
        Email emailEntity = emailRepository.findByEmailAndCode(email, code).orElseThrow(NoSuchElementException::new);
        LocalDateTime expirationTime = emailEntity.getCreatedAt().plusMinutes(EXPIRATION_MINUTES);
        emailRepository.delete(emailEntity);

        return expirationTime.isAfter(LocalDateTime.now());
    }
}
