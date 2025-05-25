package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.dto.AccountDto;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlVerificationMail(AccountDto accountDto) throws MessagingException;
    void sendHtmlVerifySuccess(AccountDto accountDto) throws MessagingException;
}
