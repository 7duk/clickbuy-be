package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.dto.request.AccountRequest;
import dev.sideproject.ndx2.dto.response.AccountResponse;

public interface AccountService {
    void verify(String token);
    AccountResponse details(Integer id);
    void changePassword(Integer accountId, AccountRequest accountRequest);
    void editProfile(Integer accountId, AccountRequest accountRequest);
}
