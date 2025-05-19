package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.constant.TokenType;
import dev.sideproject.ndx2.dto.AccountDto;
import dev.sideproject.ndx2.service.MailService;
import dev.sideproject.ndx2.service.TokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailServiceImpl implements MailService {
    final JavaMailSender mailSender;
    final TemplateEngine templateEngine;
    final TokenService tokenService;
    @Value("${spring.mail.username}")
    String mailSupport;
    @Value("${spring.mail.verification-url}")
    String verificationUrl;

    @Override
    public void sendHtmlVerificationMail(AccountDto accountDto) throws MessagingException {
        String verificationToken = tokenService.createToken(accountDto.getId(), accountDto.getEmail(),
                TokenType.EMAIL_VERIFICATION,24);
        String verificationLink= String.format("%s?token=%s", verificationUrl, verificationToken);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("audience", accountDto.getFullName());
        context.setVariable("email_to", accountDto.getEmail());
        context.setVariable("created_at", accountDto.getCreatedAt());
        context.setVariable("verification_link", verificationLink);
        context.setVariable("support_email", mailSupport);

        String htmlContent = templateEngine.process("VerifyNotification.html", context);
        helper.setFrom(mailSupport);
        helper.setTo(accountDto.getEmail());
        helper.setSubject("Account Verification Required");
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendHtmlVerifySuccess(AccountDto accountDto) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("audience", accountDto.getFullName());
        context.setVariable("support_email", mailSupport);

        String htmlContent = templateEngine.process("VerifySuccessNotification.html", context);
        helper.setFrom(mailSupport);
        helper.setTo(accountDto.getEmail());
        helper.setSubject("Account Verification Required");
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}
