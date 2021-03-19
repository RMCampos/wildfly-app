package blog.ricardocampos.controllers;

import blog.ricardocampos.repository.service.AuthService;
import blog.ricardocampos.repository.service.UserService;
import blog.ricardocampos.vo.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Stateless
@LocalBean
public class LoginController {

    @Inject
    AuthService authService;

    @POST
    @Path("/try")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User login(User user) {
        return authService.attempt(user);
    }
}
