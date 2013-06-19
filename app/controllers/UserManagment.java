package controllers;

import models.User;
import models.form.Login;
import models.form.SignUp;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.signin;
import views.html.signup;

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
}
