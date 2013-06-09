package controllers;

import java.util.List;

import models.User;
import play.*;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result createUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        user.save();

        return redirect(routes.Application.index());
    }

    public static Result getAllUsers() {
        List<User> user = new Model.Finder<String, User>(String.class, User.class).all();

        return ok(Json.toJson(user));
    }

    public static Result getUser(String id) {
        User user = new Model.Finder<String, User>(String.class, User.class).byId(id);

        return user != null ? ok(Json.toJson(user)) : notFound();
    }
}
