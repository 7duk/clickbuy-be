package dev.sideproject.ndx.service.impl;

import dev.sideproject.ndx.dto.response.AccountResponse;
import dev.sideproject.ndx.common.TokenType;
import dev.sideproject.ndx.service.MailService;
import dev.sideproject.ndx.service.TokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    public void sendHtmlVerificationMail(AccountResponse accountResponse) throws MessagingException {
        String verificationToken = tokenService.createToken(accountResponse.getUsername(),
                TokenType.EMAIL_VERIFICATION, 24);
        String verificationLink = String.format("%s?token=%s", verificationUrl, verificationToken);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("audience", accountResponse.getFullName());
        context.setVariable("email_to", accountResponse.getEmail());
        context.setVariable("created_at", accountResponse.getCreatedAt());
        context.setVariable("verification_link", verificationLink);
        context.setVariable("support_email", mailSupport);

        String htmlContent = templateEngine.process("VerifyNotification.html", context);
        helper.setFrom(mailSupport);
        helper.setTo(accountResponse.getEmail());
        helper.setSubject("Account Verification Required");
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendHtmlVerifySuccess(AccountResponse accountResponse) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("audience", accountResponse.getFullName());
        context.setVariable("support_email", mailSupport);

        String htmlContent = templateEngine.process("VerifySuccessNotification.html", context);
        helper.setFrom(mailSupport);
        helper.setTo(accountResponse.getEmail());
        helper.setSubject("Account Verification Required");
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}
