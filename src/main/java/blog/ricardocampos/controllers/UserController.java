package blog.ricardocampos.controllers;

import blog.ricardocampos.vo.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Stateless
@LocalBean
public class UserController {

    @GET
    @Path("/get-user-test")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        User user = new User();
        user.setId(1);
        user.setNome("Ricardo");
        user.setSobrenome("Campos");

        return user;
    }
}
