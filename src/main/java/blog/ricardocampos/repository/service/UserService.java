package blog.ricardocampos.repository.service;

import blog.ricardocampos.vo.User;

import javax.ejb.Stateless;

@Stateless
public class UserService {

    public User findByEmail(String email) {
        if (!email.equals("ricardo@ricardocampos.blog")) {
            return null;
        }

        User user = new User();
        user.setId(1);
        user.setEmail(email);
        user.setPassword("123456");
        user.setNome("Ricardo");
        user.setSobrenome("Campos");

        return user;
    }
}
