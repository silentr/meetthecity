package models.form;

import models.User;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class EditProfile {
 
    public static User user;
    
    public String username;

    @Required(message = "enter your old password")
    public String oldpassword;
    
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
    
    public boolean isPasswordEdit;
    
    public String validate() {
        return password.equals(passwordConfirmation) ? null : "passwords are different";
    }
    
    public static User editUser(EditProfile form) {
   
        user.password = form.password;
        user.email = form.email;
        user.phone = form.phone;
        user.firstname = form.firstname;
        user.lastname = form.lastname;
        user.country = form.country;
        return user;
    }
}
