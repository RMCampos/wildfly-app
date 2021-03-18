package blog.ricardocampos.controllers;

import blog.ricardocampos.vo.User;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class MainController {

    @Path("getuser")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        User user = new User();
        user.setId(1);
        user.setNome("Ricardo");
        user.setSobrenome("Campos");

        return user;
    }
}
