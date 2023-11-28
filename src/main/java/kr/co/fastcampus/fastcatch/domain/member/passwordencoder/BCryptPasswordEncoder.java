package kr.co.fastcampus.fastcatch.domain.member.passwordencoder;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder {

    public String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String memberPassword, String hashedPassword) {
        return BCrypt.checkpw(memberPassword, hashedPassword);
    }
}
