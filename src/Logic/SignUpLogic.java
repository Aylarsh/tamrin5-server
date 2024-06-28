package Logic;

import Loader.UserDatabase;
import Model.User;

import java.util.List;

public class SignUpLogic {
    public synchronized String signUp(String username, String password) {
        UserDatabase.load();
        boolean exists = false;
        System.out.println(exists);
        for (User user : UserDatabase.getUsers()) {
            System.out.println(user);
            if (user.getName().equals(username)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            return "FAILURE";
        } else {
            UserDatabase.getUsers().add(new User(username, password));
            UserDatabase.save();
            return "SUCCESS";
        }
    }
}
