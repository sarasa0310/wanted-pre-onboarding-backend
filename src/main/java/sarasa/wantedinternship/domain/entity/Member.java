package sarasa.wantedinternship.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
