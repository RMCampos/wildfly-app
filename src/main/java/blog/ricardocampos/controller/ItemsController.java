package blog.ricardocampos.controller;

import blog.ricardocampos.repository.entity.ItemEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Path("/items")
@Stateless
@LocalBean
public class ItemsController {

    @GET
    @Path("/get-all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ItemEntity> getAllItems() {
        List<ItemEntity> itemEntityList = new ArrayList<>();
        for (long i=0; i<10; i++) {
            ItemEntity entity = new ItemEntity();
            entity.setId(i+1);
            entity.setNome("Item " + i);
            entity.setValor(new BigDecimal(String.valueOf(i)));
            itemEntityList.add(entity);
        }
        return itemEntityList;
    }
}
