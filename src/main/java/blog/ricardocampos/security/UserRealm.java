package blog.ricardocampos.security;

public class UserRealm {

    private final User user;

    public UserRealm(String nome, String email) {
        this(new User(nome, email));
    }

    public UserRealm(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
