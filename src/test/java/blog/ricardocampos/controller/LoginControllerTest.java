package blog.ricardocampos.controller;

import blog.ricardocampos.config.LoginConfig;
import blog.ricardocampos.vo.UserLogin;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
public class LoginControllerTest {

    private Client client;
    private WebTarget loginControllerTarget;
    // https://github.com/agoncal/agoncal-sample-jaxrs/blob/master/04-JWT/src/test/java/org/agoncal/sample/jaxrs/jwt/rest/JWTEchoEndpointTest.java

    @ArquillianResource
    private URI baseURL;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        // Import Maven runtime dependencies
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();


        return ShrinkWrap.create(WebArchive.class)
                .addClasses(LoginConfig.class)
                .addClasses(LoginController.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);
    }

    @Before
    public void initWebTarget() {
        client = ClientBuilder.newClient();
        loginControllerTarget = client.target(baseURL).path("login/do-login/try");
    }

    @Test
    @InSequence(1)
    public void loginTest() {
        UserLogin userLogin = new UserLogin("ricardo@ricardocampos.blog", "123456");

        Response response = loginControllerTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(userLogin, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus(), equalTo(200));
    }
}
