package blog.ricardocampos.repository.service;

import blog.ricardocampos.repository.entity.UserEntity;

import javax.ejb.Stateless;

@Stateless
public class UserService {

    public UserEntity findByEmail(String email) {
        if (!email.equals("ricardo@ricardocampos.blog")) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("ricardo@ricardocampos.blog");
        userEntity.setName("Ricardo Campos");

        return userEntity;
    }
}
