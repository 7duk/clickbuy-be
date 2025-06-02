package dev.sideproject.ndx2.service;

 import dev.sideproject.ndx2.dto.AccountResponse;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlVerificationMail(AccountResponse accountDto) throws MessagingException;
    void sendHtmlVerifySuccess(AccountResponse accountDto) throws MessagingException;
}
