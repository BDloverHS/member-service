package org.config.member.services;

import lombok.RequiredArgsConstructor;
import org.config.global.libs.Utils;
import org.config.member.constants.TokenAction;
import org.config.member.entities.Member;
import org.config.member.entities.TempToken;
import org.config.member.exceptions.MemberNotFoundException;
import org.config.member.repositories.MemberRepository;
import org.config.member.repositories.TempTokenRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Lazy
@Service
@RequiredArgsConstructor
public class TempTokenService {
    private final MemberRepository memberRepository;
    private final TempTokenRepository tempTokenRepository;
    private final RestTemplate restTemplate;
    private final Utils utils;

    /**
     * 임시 접근 토큰 발급
     *
     * @return
     */
    public TempToken issue(String email, TokenAction action) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        TempToken token = TempToken.builder()
                .token(UUID.randomUUID().toString())
                .member(member).action(action)
                .expireTime(LocalDateTime.now().plusMinutes(3L))
                .build();

        tempTokenRepository.saveAndFlush(token);

        return token;
    }

    /**
     * 발급받은 토큰으로 접근 가능한ㅇ 주소 생성 후 메일 전송
     *
     * @param token
     */
    public void sendEmail(String token) {

    }
}
