package controllers;

import models.User;
import models.form.EditProfile;
import models.form.Login;
import models.form.SignUp;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile;
import views.html.signin;
import views.html.signup;
import views.html.editprofile;

public class UserManagment extends Controller {

    public static final String SESSION_USERNAME = "username";

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {

            Logger.debug("User didn't logged in, errors = " + loginForm.errors());

            return badRequest(signin.render(loginForm));
        } else {
            session().clear();
            session(SESSION_USERNAME, loginForm.get().username);

            Logger.debug("User " + loginForm.get().username + " logged in");

            return redirect(routes.Application.index());
        }
    }

    public static Result signin() {
        return ok(signin.render(Form.form(Login.class)));
    }

    public static Result signupSubmit() {
        Form<SignUp> signUpForm = Form.form(SignUp.class).bindFromRequest();

        if (signUpForm.hasErrors()) {
            Logger.debug("User didn't signed up errors = " + signUpForm.errors());

            return badRequest(signup.render(signUpForm));
        } else {
            User user = SignUp.createUser(signUpForm.get());
            user.save();
            Logger.debug(user + " signed up");

            return redirect(routes.UserManagment.signin());
        }
    }

    public static Result signup() {
        return ok(signup.render(Form.form(SignUp.class)));
    }

    public static Result signout() {
        Logger.info(session(SESSION_USERNAME) + " has signed out");
        session().clear();

        return redirect(routes.UserManagment.signin());
    }

    public static boolean isUserSignedIn() {
        return session(SESSION_USERNAME) != null;
    }

    public static Result userProfile() {

        User user = null;
        String username = session().get(UserManagment.SESSION_USERNAME);
        if (username != null) user = User.find.byId(username);

        return ok(profile.render(user));
    }

    public static Result editUserProfile() {

        User user = null;
        String username = session().get(UserManagment.SESSION_USERNAME);
        if (username != null) user = User.find.byId(username);

        return ok(editprofile.render(user, Form.form(EditProfile.class), false));
    }

    public static Result submitEdit() {

        Form<EditProfile> editProfile = Form.form(EditProfile.class).bindFromRequest();

        User user = null;
        String username = session().get(UserManagment.SESSION_USERNAME);
        if (username != null) user = User.find.byId(username);
        
        if (editProfile.hasErrors()) {
            Logger.debug("The changes could not be saved = " + editProfile.errors());
            if(editProfile.errors().toString().contains("password"))
                return badRequest(editprofile.render(user, editProfile, true));
            else
                return badRequest(editprofile.render(user, editProfile, false));
        } 
        else {
            editProfile.get();
            EditProfile.user = user;
            User updatedUser = EditProfile.editUser(editProfile.get());
            updatedUser.update();
            Logger.debug(user + " has editted his profile");

            return redirect(routes.UserManagment.userProfile());
        }
    }
}
