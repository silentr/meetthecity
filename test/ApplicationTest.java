import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

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
    public void viewATourTestSuccess() {
        Result result = callAction(controllers.routes.ref.Application.viewATour(1));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Trip to Istanbul");
    }

    @Test
    public void viewATourTestFail() {
        Result result = callAction(controllers.routes.ref.Application.viewATour(7));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }

    @Test
    public void joinATourTestSuccess() {
        Result result = callAction(controllers.routes.ref.Application.joinATour(),
                fakeRequest().withSession("username", "sadek").withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("true");
    }

    @Test
    public void leaveATourTestSuccess() {
        Result result = callAction(controllers.routes.ref.Application.joinATour(),
                fakeRequest().withSession("username", "sadek").withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/plain");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("true");
    }

    @Test
    public void joinATourTestAnonymous() {
        Result result = callAction(controllers.routes.ref.Application.joinATour(), fakeRequest()
                .withFormUrlEncodedBody(ImmutableMap.of("tourId", "1")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }

//    @Test
//    public void createATourSubmitTestSuccess() throws UnsupportedEncodingException {
//
//        File fnew = new File("ankara.png");
//        BufferedImage originalImage;
//        ByteArrayOutputStream baos = null;
//        try {
//            originalImage = ImageIO.read(fnew);
//            baos = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "jpg", baos);
//            byte[] imageInByte = baos.toByteArray();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        Map<String, String> requestMap = new HashMap<String, String>();
//        requestMap.put("date", "21.06.2013");
//        requestMap.put("country", "Turkey");
//        requestMap.put("city", "Ankara");
//        requestMap.put("name", "Ankara sightseeing");
//        requestMap.put("price", "0");
//        requestMap.put("imgFile", baos.toString("ISO-8858-2"));
//        requestMap.put("description", "Full description");
//        Result result = callAction(controllers.routes.ref.Application.createATourSubmit(),
//                fakeRequest().withSession("username", "sadek").withFormUrlEncodedBody(requestMap));
//        assertThat(status(result)).isEqualTo(OK);
//        assertThat(contentType(result)).isEqualTo("text/plain");
//        assertThat(charset(result)).isEqualTo("utf-8");
//        assertThat(contentAsString(result)).contains("Tours");
//    }
}
