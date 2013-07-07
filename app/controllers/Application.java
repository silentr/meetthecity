package controllers;

import java.util.List;

import models.Tour;
import models.User;
import play.Logger;
import play.api.i18n.Lang;
import play.api.templates.Html;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import views.html.index;
import views.html.home;
import views.html.about;

public class Application extends Controller {

    static String language="en";
    
    public static Result index() {
        return ok(index.render(new Html(new StringBuilder())));
    }
    
    public static Result home() {
        List<Tour> tourList = Tour.find.all();
        return ok(home.render(tourList));
    }

    public static Result getAllUsers() {
        List<User> user = User.find.all();

        return ok(Json.toJson(user));
    }

    public static Result getUser(String id) {
        User user = User.find.byId(id);
        return user != null ? ok(Json.toJson(user)) : notFound();
    }
    
    public static Result changeLanguage(String selectedLanguage) {
        
        language = selectedLanguage;
        Lang.apply(language);
        return redirect(routes.Application.home());
    }
    
    public static String getLanguageString(String key) {
        
        String word="";
        word = Messages.get(new Lang(language,""), key);
        return word;
    }
    
    public static String getLanguage() {
        
        return language;
    }
    
    public static Result about() {
        return ok(about.render());
    }
}
