package org.config.member.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.config.member.constants.TokenAction;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class TempToken {
    @Id
    @Column(length = 45)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TokenAction action;

    private LocalDateTime expireTime;


}
