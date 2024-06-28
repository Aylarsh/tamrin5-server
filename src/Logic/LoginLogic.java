package Logic;

import Loader.UserDatabase;
import Model.User;

public class LoginLogic {

    public synchronized String login(String username, String password) {
        UserDatabase.load();
        boolean exists = false;
        for (User user : UserDatabase.getUsers()) {
            System.out.println(user);
            if (user.getName().equals(username)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            return "SUCCESS";
        } else {
            return "FAILURE";
        }
    }
}
