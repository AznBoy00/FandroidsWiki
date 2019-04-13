package org.wikipedia.firelogin;

import com.google.firebase.auth.FirebaseUser;

public class SignInToWikiHelper {

    public static final String ANONYMOUS = "anonymous";

    public boolean isUserSignIn(FirebaseUser user) {

        if (user == null) {
            return false;
        }
        return true;
    }

    public String onSignedInInitialize(FirebaseUser user) {
        return user.getDisplayName();
    }

    public String onSignOutCleanUp() {
        return ANONYMOUS;
    }
}
