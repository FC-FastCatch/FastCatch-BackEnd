package kr.co.fastcampus.fastcatch.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    @NonNull
    private String email;

    @NonNull
    private String password;

    @Column(length = 30)
    @NonNull
    private String name;

    @Column(unique = true, length = 30)
    @NonNull
    private String nickname;

    @NonNull
    private LocalDate birthday;

    @Builder
    public Member(
        Long id, String email, String password,
        String name, String nickname, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
    }

}
