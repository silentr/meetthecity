package models.form;

import models.User;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.ValidateWith;
import validators.UsernameValidator;

public class SignUp {

    @Required(message = "username is missing")
    @ValidateWith(message = "username already exists", value = UsernameValidator.class)
    public String username;

    @Required(message = "password is missing")
    @MinLength(message = "password is too short", value = 8)
    public String password;

    public String passwordConfirmation;

    @Required(message = "email is empty")
    @Email(message = "wrong email")
    public String email;

    public String phone;

    public String firstname;

    public String lastname;

    public String country;

    public String validate() {
        return password.equals(passwordConfirmation) ? null : "passwords are different";
    }
    
    public static User createUser(SignUp form) {
        User user = new User();
        user.username = form.username;
        user.password = form.password;
        user.email = form.email;
        user.firstname = form.firstname;
        user.lastname = form.lastname;
        user.country = form.country;
        return user;
    }
}
