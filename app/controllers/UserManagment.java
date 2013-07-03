package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import models.User;
import models.form.EditProfile;
import models.form.Login;
import models.form.SignUp;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
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

            return redirect(routes.Application.home());
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
            if (editProfile.errors().toString().contains("password")) return badRequest(editprofile.render(user,
                    editProfile, true));
            else
                return badRequest(editprofile.render(user, editProfile, false));
        } else {


            MultipartFormData body = request().body().asMultipartFormData();
            FilePart picture = body.getFile("photofile");
            if (picture != null) {

                File file = picture.getFile();
                String path = "public/images/" + user.username + "_photo.png";
                File destinationFile = new File(path);
                editProfile.get().photo = "/assets/images/"+user.username + "_photo.png";
                try {
                    copyFile(file, destinationFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ;
            EditProfile.user = user;
            User updatedUser = EditProfile.editUser(editProfile.get());
            updatedUser.update();
            Logger.debug(user.username + " has editted his profile");
            
            return redirect(routes.UserManagment.userProfile());
        }
    }

    public static void copyFile(File source, File dest) throws IOException {

        if (!dest.exists()) {
            dest.createNewFile();
        }
        InputStream in = null;
        OutputStream out = null;
        try {

            in = new FileInputStream(source);
            out = new FileOutputStream(dest);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
