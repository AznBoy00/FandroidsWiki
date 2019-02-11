package org.wikipedia.page;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableStringBuilder;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;
import org.wikipedia.R;
import org.wikipedia.page.PageActivity;
import org.wikipedia.page.PageFragment;

import android.support.v4.app.Fragment;

import static android.net.sip.SipErrorCode.TIME_OUT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class PageFragmentTest {

    //Activity activity;
    private Fragment myFragment;
    @Before
    public void setUp() throws Exception {
//        activity = Robolectric.buildActivity(PageActivity.class)
//                .create()
//                .resume()
//                .get();
//        FragmentManager manager = fragment.getFragmentManager();
//        fragment = (PageFragment) manager.findFragmentById(R.id.page_fragment);

        PageFragment myFragment = new PageFragment();
        startFragment( myFragment );
        assertNotNull( myFragment );
    }

    @Test
    public void testTextToSpeechButton() {


        Button button = (Button) myFragment.getView().findViewById(R.id.article_menu_text_to_speech);
        button.performClick();
        ShadowHandler.idleMainLooper();
        Assert.assertEquals( ShadowToast.getTextOfLatestToast(), "Execution of Text-To-Speech");
    }

    public void startFragment( Fragment fragment )
    {
        FragmentManager fragmentManager = new FragmentActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( fragment, null );
        fragmentTransaction.commitAllowingStateLoss();
    }

}
