package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class RoomNotFoundException extends BaseException {
    public RoomNotFoundException() {
        super(ErrorCode.ROOM_NOT_FOUND.getErrorMsg());
    }
}
