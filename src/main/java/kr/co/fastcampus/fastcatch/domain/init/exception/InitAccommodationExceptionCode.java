package kr.co.fastcampus.fastcatch.domain.init.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InitAccommodationExceptionCode {

    NOT_FOUND_FILE("파일이 존재하지 않습니다."),
    JSON_ERROR("json 변환 중 에러가 발생하였습니다."),
    FAILED_SAVE("저장 중 에러가 발생하였습니다.")
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }

}
