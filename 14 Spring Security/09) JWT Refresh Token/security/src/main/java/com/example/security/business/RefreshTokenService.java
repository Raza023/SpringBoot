package com.example.security.business;

import com.example.security.dto.RefreshTokenRequest;
import com.example.security.model.RefreshToken;
import com.example.security.model.User;
import com.example.security.repository.RefreshTokenDataService;
import com.example.security.repository.UserDataService;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenDataService refreshTokenDataService;
    private final UserDataService userDataService;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(1000 * 60 * 30));  //30 minutes.
        User user = userDataService.findByName(username).orElse(null);
        refreshToken.setUser(user);
        Optional<RefreshToken> refreshTokenOptional = refreshTokenDataService.findByUserId(user.getId());
        return refreshTokenOptional.orElseGet(() -> refreshTokenDataService.saveAndFlush(refreshToken));
    }

    public Optional<RefreshToken> findByToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenDataService.findByToken(refreshTokenRequest.getRefreshToken());
    }

    public RefreshToken verifyExpirationOfRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {  //current time is ahead of expiry time
            refreshTokenDataService.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + ": Refresh token is already expired."
                    + " Your token can't be refreshed now."
                    + " Please log back in again to access this application.");
        }
        return refreshToken;
    }

}
