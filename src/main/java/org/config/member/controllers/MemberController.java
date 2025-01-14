package org.config.member.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.config.global.exceptions.BadRequestException;
import org.config.global.libs.Utils;
import org.config.global.rests.JSONData;
import org.config.member.MemberInfo;
import org.config.member.jwt.TokenService;
import org.config.member.services.MemberUpdateService;
import org.config.member.validators.JoinValidator;
import org.config.member.validators.LoginValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "회원 인증/인가 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    @Value("${front.domain}")
    private String frontDomain;

    private final Utils utils;
    private final MemberUpdateService updateService;
    private final JoinValidator joinValidator;
    private final TokenService tokenService;
    private final LoginValidator loginValidator;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(@RequestBody @Valid RequestJoin form, Errors errors) {

        System.out.println("테스트");

        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        updateService.process(form);
    }

    /**
     * 로그인 성공 시 토큰 발급
     *
     * @param form
     * @param errors
     */
    @PostMapping("/login")
    public JSONData login(@RequestBody @Valid RequestLogin form, Errors errors, HttpServletResponse response) {
        loginValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        String email = form.getEmail();
        String token = tokenService.create(email);

        if (StringUtils.hasText(frontDomain)){
            String[] domains = frontDomain.split(",");
            for(String domain : domains) {
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                cookie.setDomain(domain);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }

        return new JSONData(token);
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public void test(@AuthenticationPrincipal MemberInfo memberInfo) {
        System.out.println(memberInfo);
        System.out.println("회원 전용 URL");
    }
}
