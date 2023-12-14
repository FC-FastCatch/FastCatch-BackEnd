package kr.co.fastcampus.fastcatch.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ReIssueTokenRequest(
    @Email(message = "올바른 이메일 형식을 입력하셔야 합니다.")
    @NotBlank(message = "회원 이메일을 필수로 입력하셔야 합니다.")
    String email
) {

}
