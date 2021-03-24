package blog.ricardocampos.controller;

import blog.ricardocampos.repository.entity.UserEntity;
import blog.ricardocampos.repository.service.AuthService;
import blog.ricardocampos.security.JwtUtil;
import blog.ricardocampos.vo.UserLogin;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;

@Path("/do-login")
@LocalBean
@Stateless
public class LoginController {

    @Context
    UriInfo uriInfo;

    @Inject
    AuthService authService;

    @Inject
    JwtUtil jwtUtil;

    @POST
    @Path("/try")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserLogin userLogin) {
        try {
            if (userLogin == null) {
                String mensagemErro = "No user credentials specified!";
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"" + mensagemErro + "\"}")
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }

            // Authenticate the user using the credentials provided
            UserEntity userEntity = authService.attempt(userLogin);

            // Issue a token for the user
            String token = jwtUtil.createToken(
                    userEntity.getId().toString(),
                    userEntity.getEmail(),
                    userEntity.getName(),
                    800000L
            );

            if (token == null) {
                String mensagemErro = "No token!";
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"" + mensagemErro + "\"}")
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }

            // Return the token on the response
            return Response.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity("{user: " + userEntity + "}")
                    .build();
        } catch (Exception e) {
            String mensagemErro = "Usuário ou senha inválidos!";
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"" + mensagemErro + "\"}")
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }
}
