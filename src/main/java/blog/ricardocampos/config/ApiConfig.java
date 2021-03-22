package blog.ricardocampos.config;

import blog.ricardocampos.controllers.ItemsController;
import blog.ricardocampos.controllers.UserController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApiConfig extends Application {

    private static Set<Class<?>> endPoints;

    @Override
    public Set<Class<?>> getClasses() {
        if (endPoints == null) {
            endPoints = new HashSet<>();
            endPoints.add(ItemsController.class);
            endPoints.add(UserController.class);
        }
        return endPoints;
    }
}
