package kr.co.fastcampus.fastcatch.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(unique = true, length = 30, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(unique = true, length = 30, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String phoneNumber;

    @Builder
    public Member(
        Long id,
        String email,
        String password,
        String name,
        String nickname,
        LocalDate birthday,
        String phoneNumber
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }
}
