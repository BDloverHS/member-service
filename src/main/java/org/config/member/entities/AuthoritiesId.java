package org.config.member.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.config.member.constants.Authority;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AuthoritiesId {
    private Member member;
    private Authority authority;
}
