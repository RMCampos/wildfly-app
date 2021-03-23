package blog.ricardocampos.repository.service;

import blog.ricardocampos.vo.UserLogin;

import javax.ejb.Stateless;

@Stateless
public class UserService {

    public UserLogin findByEmail(String email) {
        if (!email.equals("ricardo@ricardocampos.blog")) {
            return null;
        }

        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(email);
        userLogin.setPassword("123456");

        return userLogin;
    }
}
