package blog.ricardocampos.controllers;

import blog.ricardocampos.repository.entity.ItemEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/items")
@Stateless
@LocalBean
public class ItemsController {

    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ItemEntity> getAllItems() {
        return new ArrayList<>();
    }
}
