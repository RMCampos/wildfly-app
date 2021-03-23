package blog.ricardocampos.repository.service;

import blog.ricardocampos.vo.UserLogin;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthService {

    @Inject
    UserService userService;

    public UserLogin attempt(UserLogin userLogin) {
        UserLogin userLoginBd = userService.findByEmail(userLogin.getEmail());
        if (userLoginBd == null) {
            return new UserLogin();
        }

        // todo: password
        if (!userLogin.getPassword().equals(userLoginBd.getPassword())) {
            return new UserLogin();
        }

        // register log

        return userLoginBd;
    }
}
