package models.form;

import models.User;
import play.Logger;

public class Login {

    public String username;

    public String password;

    public String validate() {
        Logger.debug("validating = " + username + ", password = " + password);

        return User.authenticate(username, password) == null ? "Invalid username or password" : null;
    }
}