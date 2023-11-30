package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class AlreadyReservedRoomException extends BaseException {

    public AlreadyReservedRoomException() {
        super(ErrorCode.ALREADY_RESERVED_ROOM.getErrorMsg());
    }

}
