package auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Auth {
    private String username = "ems_k";
    private String password = "qwer123!@#";
    public  boolean login(String username , String password){
        if(username.equals(this.username) && password.equals(this.password)) return true;
        return false;
    }
}
