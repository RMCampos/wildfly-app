package blog.ricardocampos.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("loginCredentials")
@RequestScoped
public class LoginCredentials {

    private String email;
    private char[] credential;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getCredential() {
        return credential;
    }

    public void setCredential(char[] credential) {
        this.credential = credential;
    }

    public String getPassword() {
        return new String(credential);
    }

    public void setPassword(final String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password can not be null.");
        }

        this.credential = password.toCharArray();
    }

}
