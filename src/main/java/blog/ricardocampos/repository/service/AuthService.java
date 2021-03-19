package blog.ricardocampos.repository.service;

import blog.ricardocampos.vo.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthService {

    @Inject
    UserService userService;

    public User attempt(User user) {
        User userBd = userService.findByEmail(user.getEmail());
        if (userBd == null) {
            return new User();
        }

        // todo: password
        if (!user.getPassword().equals(userBd.getPassword())) {
            return new User();
        }

        // register log

        return userBd;
    }
}
