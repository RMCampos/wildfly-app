package blog.ricardocampos.repository.service;

import blog.ricardocampos.repository.entity.UserEntity;
import blog.ricardocampos.vo.UserLogin;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthService {

    @Inject
    UserService userService;

    public UserEntity attempt(UserLogin userLogin) {
        UserEntity userEntity = userService.findByEmail(userLogin.getEmail());
        if (userEntity == null) {
            return null;
        }

        if (userLogin.getPassword() == null) {
            return null;
        }

        if (!userLogin.getPassword().equals("123456")) {
            return null;
        }

        // register log

        return userEntity;
    }
}
