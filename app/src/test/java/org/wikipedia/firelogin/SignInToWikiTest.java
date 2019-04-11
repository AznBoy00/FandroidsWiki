package org.wikipedia.firelogin;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignInToWikiTest {

    SignInToWikiHelper signInToWikiHelper = new SignInToWikiHelper();
    FirebaseUser user = mock(FirebaseUser.class);
    @Test
    public void isUserSignInNullReturnFalse() {
        assertFalse(signInToWikiHelper.isUserSignIn(null));
    }

    @Test
    public void isUserSignInUserReturnTrue() {
        assertTrue(signInToWikiHelper.isUserSignIn(user));
    }

    @Test
    public void onSignedInInitializeUserEqual(){
        when(user.getDisplayName()).thenReturn("Mike");
        assertEquals("Mike",signInToWikiHelper.onSignedInInitialize(user));
    }

    @Test
    public void onSignedInInitializeUserNotEqual(){
        when(user.getDisplayName()).thenReturn("Ben");
        assertNotEquals("Mike",signInToWikiHelper.onSignedInInitialize(user));
    }

    @Test
    public void onSignedInInitializeVerifyZeroTime(){
        verify(user,times(0)).getUid();
    }

    @Test
    public void onSignedInInitializeVerifyOneTime(){
        when(user.getDisplayName()).thenReturn("Ben");
        assertNotEquals("Mike",signInToWikiHelper.onSignedInInitialize(user));
        verify(user,times(1)).getDisplayName();
    }

    @Test
    public void onSignedInInitializeVerifyTwoTimes(){
        when(user.getDisplayName()).thenReturn("Ben");
        user.getDisplayName();
        assertNotEquals("Mike",signInToWikiHelper.onSignedInInitialize(user));
        verify(user,times(2)).getDisplayName();
    }

    @Test
    public void onSignOutCleanUpEqual(){
        assertEquals("anonymous",signInToWikiHelper.onSignOutCleanUp());
    }

    @Test
    public void onSignOutCleanUpNotEqual(){
        assertNotEquals("Mike",signInToWikiHelper.onSignOutCleanUp());
    }


}
