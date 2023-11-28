package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class DuplicatedNicknameException extends BaseException {

    public DuplicatedNicknameException() {
        super(ErrorCode.DUPLICATED_NICKNAME.getErrorMsg());
    }
}
