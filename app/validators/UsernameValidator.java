package validators;

import static play.libs.F.Tuple;
import models.User;
import play.data.validation.Constraints.Validator;

public class UsernameValidator extends Validator<String> {

    @Override
    public boolean isValid(String username) {
        return User.find.where().eq("username", username).findUnique() == null;
    }

    public Tuple<String, Object[]> getErrorMessageKey() {
        return Tuple("username_exist", new Object[] {});
    }

}
