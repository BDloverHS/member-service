package org.config.member.exceptions;

import org.config.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class TempTokenExpiredException extends CommonException {
    public TempTokenExpiredException() {
        super("Expired.tempToken", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
