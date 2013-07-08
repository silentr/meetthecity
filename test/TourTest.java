import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.status;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import play.mvc.Result;
import play.test.WithApplication;

public class TourTest extends WithApplication {
    
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }
    
    @Test
    public void viewATourTestSuccess() {
        Result result = callAction(controllers.routes.ref.TourManagement.viewATour(1));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Trip to Istanbul");
    }

    @Test
    public void viewATourTestFail() {
        Result result = callAction(controllers.routes.ref.TourManagement.viewATour(7));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }

    @Test
    public void joinATourTestSuccess() {
        Result result = callAction(controllers.routes.ref.TourManagement.joinATour(),
                fakeRequest().withSession("username", "sadek").withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("true");
    }

    @Test
    public void leaveATourTestSuccess() {
        Result result = callAction(controllers.routes.ref.TourManagement.joinATour(),
                fakeRequest().withSession("username", "sadek").withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("true");
    }

    @Test
    public void joinATourTestAnonymous() {
        Result result = callAction(controllers.routes.ref.TourManagement.joinATour(), fakeRequest()
                .withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }
    
    @Test
    public void addReviewTest() {
        Result result = callAction(controllers.routes.ref.TourManagement.addReview(), 
                fakeRequest()
                .withSession("username", "maxime")
                .withFormUrlEncodedBody(ImmutableMap.of("tourId", "1", "rate", "2", "comment","Test comment")));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
    }
}
