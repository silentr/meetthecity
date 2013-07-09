package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import play.data.Form;
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
import models.form.*;
import views.html.searchresult;
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
    
    public static Result search(){
        
        Form<Search> searchForm = Form.form(Search.class).bindFromRequest();
        String searchedString = searchForm.get().searchtext;
        Logger.debug("Searched String: "+searchedString);
        return redirect(routes.Application.resultSearch(searchedString));
   }
    
    public static Result resultSearch(String searchedString) {
        
        List<Tour> tourList = Tour.find.all();
        List<Tour> resultList = new ArrayList<Tour>();
        
        for (Tour tourItem : tourList) {
            
            if(tourItem.descriptionShort.toLowerCase(Locale.ENGLISH).contains(searchedString.toLowerCase(Locale.ENGLISH)))
                resultList.add(tourItem);
        }
        return ok(searchresult.render(resultList, searchedString));
    }

    public static Result about() {
        return ok(about.render());

    }
}
