import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.WithApplication;

import com.google.common.collect.ImmutableMap;

public class SignupTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void submitSuccessAndLogin() {
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("username", "test2");
        requestMap.put("password", "12345678");
        requestMap.put("passwordConfirmation", "12345678");
        requestMap.put("email", "test2@mail.com");
        requestMap.put("firstname", "Test2 firstname");
        requestMap.put("lastname", "Test2 Lastname");

        Result signUpResult = callAction(controllers.routes.ref.Application.signupSubmit(), fakeRequest()
                .withFormUrlEncodedBody(Collections.unmodifiableMap(requestMap)));
        assertThat(status(signUpResult)).isEqualTo(SEE_OTHER);

        User user = User.find.byId("test2");
        assertThat(user).isNotNull();
        assertThat(user.username).isEqualTo("test2");
        assertThat(user.password).isEqualTo("12345678");
        assertThat(user.email).isEqualTo("test2@mail.com");
        assertThat(user.firstname).isEqualTo("Test2 firstname");
        assertThat(user.lastname).isEqualTo("Test2 Lastname");

        Result loginResult = callAction(controllers.routes.ref.Application.authenticate(), fakeRequest()
                .withFormUrlEncodedBody(ImmutableMap.of("username", user.username, "password", user.password)));
        assertThat(status(loginResult)).isEqualTo(SEE_OTHER);
        assertThat(user.username).isEqualTo(session(loginResult).get("username"));
        assertThat(User.find.byId("test2")).isNotNull();
    }

    @Test
    public void emptyUsername() {
        Result result = callAction(
                controllers.routes.ref.Application.signupSubmit(),
                fakeRequest().withFormUrlEncodedBody(
                        ImmutableMap.of("username", "", "password", "12345678", "passwordConfirmation", "12345678",
                                "email", "test2@mail.com")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void existingUsername() {
        Result result = callAction(
                controllers.routes.ref.Application.signupSubmit(),
                fakeRequest().withFormUrlEncodedBody(
                        ImmutableMap.of("username", "maxime", "password", "12345678", "passwordConfirmation",
                                "12345678", "email", "test2@mail.com")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void badEmail() {
        Result result = callAction(
                controllers.routes.ref.Application.signupSubmit(),
                fakeRequest().withFormUrlEncodedBody(
                        ImmutableMap.of("username", "maxime", "password", "12345678", "passwordConfirmation",
                                "12345678", "email", "test2mail.com")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void tooSmallPassword() {
        Result result = callAction(
                controllers.routes.ref.Application.signupSubmit(),
                fakeRequest().withFormUrlEncodedBody(
                        ImmutableMap.of("username", "test2", "password", "1234567", "passwordConfirmation", "1234567",
                                "email", "test2@mail.com")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void passwordConfirmationFail() {
        Result result = callAction(
                controllers.routes.ref.Application.signupSubmit(),
                fakeRequest().withFormUrlEncodedBody(
                        ImmutableMap.of("username", "test2", "password", "12345678", "passwordConfirmation",
                                "87654321", "email", "test2@mail.com")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void emptyPassword() {
        Result result = callAction(
                controllers.routes.ref.Application.signupSubmit(),
                fakeRequest().withFormUrlEncodedBody(
                        ImmutableMap.of("username", "test2", "password", "", "passwordConfirmation", "", "email",
                                "test2@mail.com")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
    }
}
