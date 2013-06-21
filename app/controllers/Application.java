package controllers;

import static play.data.Form.form;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.Location;
import models.Tour;
import models.User;
import models.form.TourForm;
import play.Logger;
import play.api.templates.Html;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import scala.collection.mutable.StringBuilder;
import validators.Secured;
import views.html.createatour;
import views.html.index;
import views.html.tours;
import views.html.viewatour;
import views.html.helper.form;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(new Html(new StringBuilder())));
    }

    public static Result getAllUsers() {
        List<User> user = User.find.all();

        return ok(Json.toJson(user));
    }

    public static Result getUser(String id) {
        User user = User.find.byId(id);
        return user != null ? ok(Json.toJson(user)) : notFound();
    }
}
