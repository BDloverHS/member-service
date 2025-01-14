package org.config.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.config.global.exceptions.BadRequestException;
import org.config.global.libs.Utils;
import org.config.global.rests.JSONData;
import org.config.member.jwt.TokenService;
import org.config.member.services.MemberUpdateService;
import org.config.member.validators.JoinValidator;
import org.config.member.validators.LoginValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;
    private final MemberUpdateService updateService;
    private final JoinValidator joinValidator;
    private final TokenService tokenService;
    private final LoginValidator loginValidator;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(@RequestBody @Valid RequestJoin form, Errors errors) {

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
    public JSONData login(@RequestBody @Valid RequestLogin form, Errors errors) {

        loginValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        String email = form.getEmail();
        String token = tokenService.create(email);

        return new JSONData(token);
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public void test() {
        System.out.println("회원 전용 URL");
    }
}
