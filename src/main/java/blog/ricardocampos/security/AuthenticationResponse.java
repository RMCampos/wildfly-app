package blog.ricardocampos.security;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1297387771821869087L;
    private UserRealm account;

    public AuthenticationResponse(User user) {
        this.account = new UserRealm(user);
    }

    public UserRealm getAccount() {
        return this.account;
    }

}
