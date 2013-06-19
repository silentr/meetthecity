import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.*;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.WithApplication;

import com.google.common.collect.ImmutableMap;

public class SignInTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void authenticationSuccess() {
        Result result = callAction(controllers.routes.ref.UserManagment.authenticate(), fakeRequest()
                .withFormUrlEncodedBody(ImmutableMap.of("username", "maxime", "password", "secret")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat("maxime").isEqualTo(session(result).get("username"));
    }

    @Test
    public void authenticationFail() {
        Result result = callAction(controllers.routes.ref.UserManagment.authenticate(), fakeRequest()
                .withFormUrlEncodedBody(ImmutableMap.of("username", "omg", "password", "noway")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(session(result).get("username")).isNull();
    }
}
