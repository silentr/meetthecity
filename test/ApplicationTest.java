import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import play.mvc.Result;
import play.test.WithApplication;

public class ApplicationTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void homepageCheck() {
        Result result = callAction(controllers.routes.ref.Application.index());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Meet the City");
    }

    @Test
    public void signInCheck() {
        Result result = callAction(controllers.routes.ref.UserManagment.signin());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Meet the City");
    }

    @Test
    public void signUpCheck() {
        Result result = callAction(controllers.routes.ref.UserManagment.signup());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Meet the City");
    }
    
    @Test
    public void viewATourTestSuccess(){
        Result result = callAction(controllers.routes.ref.Application.viewATour(1));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Trip to Istanbul");
    }
    
    @Test
    public void viewATourTestFail(){
        Result result = callAction(controllers.routes.ref.Application.viewATour(7));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }
    
    @Test
    public void joinATourTestSuccess(){
        Result result = callAction(controllers.routes.ref.Application.joinATour(), fakeRequest()
                .withSession("username", "sadek").withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("true");
    }
    
    @Test
    public void leaveATourTestSuccess(){
        Result result = callAction(controllers.routes.ref.Application.joinATour(), fakeRequest()
                .withSession("username", "sadek").withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("true");
    }
    
    @Test
    public void joinATourTestAnonymous(){
        Result result = callAction(controllers.routes.ref.Application.joinATour(), fakeRequest().
                withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }
}
