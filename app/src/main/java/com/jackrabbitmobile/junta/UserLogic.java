package com.jackrabbitmobile.junta;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.jackrabbitmobile.junta.model.User;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by SamMyxer on 1/14/15.
 */
public class UserLogic {

    private UserLogic() {
    }

    public static class UserLogicHolder {
        private static final UserLogic INSTANCE = new UserLogic();
    }

    public static UserLogic getInstance() {
        return UserLogicHolder.INSTANCE;
    }

    public User currentUser() {
        return (User) ParseUser.getCurrentUser();
    }

    public boolean isLogged() {
        User currentUser = currentUser();
        return currentUser != null;
    }

    public void login(final UserLoginListener listener, String name, String password, String phoneNumber) {
        final User user = new User();
        user.setName(name);
        user.setUsername(phoneNumber);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    listener.loginSuccessful(user);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    listener.loginError(e);
                }
            }
        });
    }

    public interface UserLoginListener {
        public void loginSuccessful(ParseUser user);

        public void loginError(ParseException exception);
    }
}
