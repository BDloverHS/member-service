package org.config.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.config.global.exceptions.BadRequestException;
import org.config.global.libs.Utils;
import org.config.member.services.MemberUpdateService;
import org.config.member.validators.JoinValidator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;
    private final MemberUpdateService updateService;
    private final JoinValidator joinValidator;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(@RequestBody @Valid RequestJoin form, Errors errors) {

        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        updateService.process(form);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid RequestLogin form, Errors errors) {

    }
}
