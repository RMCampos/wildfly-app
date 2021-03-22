package blog.ricardocampos.config;

import blog.ricardocampos.controllers.LoginController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/login")
public class LoginConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> rests = new HashSet<>();
        rests.add(LoginController.class);
        return rests;
    }
}
