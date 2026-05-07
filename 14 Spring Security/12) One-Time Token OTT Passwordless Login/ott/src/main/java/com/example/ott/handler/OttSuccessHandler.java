package com.example.ott.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

//    private final OneTimeTokenGenerationSuccessHandler redirectHandler =
//            new RedirectOneTimeTokenGenerationSuccessHandler("/api/v1/ott-sent");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken)
            throws IOException, ServletException {
        // generate and send magic link (One time token)
        UriComponentsBuilder token = UriComponentsBuilder
                .fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .path("/api/v1/login-ott")
                .queryParam("token", oneTimeToken.getTokenValue());

        String magicLink = token.toUriString();
        log.info("Magic link: " + magicLink);
        log.info("""
                        One Time Token Details:
                                token      : {}
                                username   : {}
                                expiresAt  : {}
                        """,
                oneTimeToken.getTokenValue(),
                oneTimeToken.getUsername(),
                oneTimeToken.getExpiresAt());

        // 👉 CUSTOM REDIRECT WITH DATA
        String redirectUrl = UriComponentsBuilder
                .fromPath("/api/v1/ott-sent")
                .queryParam("username", oneTimeToken.getUsername())
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);

        // REDIRECT WITHOUT DATA
        //redirectHandler.handle(request, response, oneTimeToken);
    }
}
