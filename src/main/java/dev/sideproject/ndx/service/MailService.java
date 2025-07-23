package dev.sideproject.ndx.service;

 import dev.sideproject.ndx.dto.response.AccountResponse;
 import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlVerificationMail(AccountResponse accountDto) throws MessagingException;

    void sendHtmlVerifySuccess(AccountResponse accountDto) throws MessagingException;
}
