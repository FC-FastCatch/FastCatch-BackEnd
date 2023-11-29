package kr.co.fastcampus.fastcatch.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public record MemberUpdateRequest(
    @Size(min = 2, message = "이름은 최소 2자 이상이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 한글 또는 영문으로만 입력하셔야 합니다.")
    String name,
    @Size(min = 2, max = 14, message = "닉네임은 2자 이상 14자 이하로 입력하셔야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "닉네임은 한글 또는 영문으로만 입력하셔야 합니다.")
    String nickname,
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull(message = "생일을 필수로 입력하셔야 합니다.(yyyy-MM-dd)")
    LocalDate birthday,
    @Pattern(regexp = "^[0-9]*$", message = "휴대폰 번호는 숫자로만 입력하셔야 합니다.")
    @NotBlank(message = "핸드폰 번호를 필수로 입력하셔야 합니다.")
    String phoneNumber
) {

}
