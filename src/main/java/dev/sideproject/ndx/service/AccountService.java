package dev.sideproject.ndx.service;

import dev.sideproject.ndx.dto.response.AccountResponse;
import dev.sideproject.ndx.dto.request.AccountRequest;

public interface AccountService {
    void verify(String token);

    AccountResponse details(Integer id);

    void changePassword(Integer accountId, AccountRequest accountRequest);

    void editProfile(Integer accountId, AccountRequest accountRequest);
}
