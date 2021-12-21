package oauth2.example.oauth2jwt.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String fullname;

    @Column(unique = true, nullable = false)
    private String picture;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String nickname, String fullname, String picture, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.fullname = fullname;
        this.picture = picture;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public User update(String nickname, String fullname, String picture) {
        this.nickname = nickname;
        this.fullname = fullname;
        this.picture = picture;
        return this;
    }
}
