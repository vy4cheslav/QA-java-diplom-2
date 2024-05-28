package user;

import lombok.Getter;
import lombok.Setter;

public class CreatedUser {

    @Getter @Setter
    private String email;


    @Setter@Getter
    private String password;

    public CreatedUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
