package org.wikipedia.main;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.wikipedia.BuildConfig;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.auth.AccountUtil;
import org.wikipedia.util.ResourceUtil;
import org.wikipedia.util.UriUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainDrawerView extends ScrollView {

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public interface Callback {
        void loginLogoutClick();

        void LogoutClickByfirebase();

        void notificationsClick();

        void settingsClick();

        void configureFeedClick();

        void aboutClick();

        void qrCodeReadClick();

        void mlKitClick();

        void groupChatClick();

        void notificationClick();

        void beaconClick();

        void noteClick();
    }

    @BindView(R.id.main_drawer_account_name)
    TextView accountNameView;
    @BindView(R.id.main_drawer_login_button)
    TextView loginLogoutButton;
    @BindView(R.id.main_drawer_account_avatar)
    ImageView accountAvatar;
    @BindView(R.id.main_drawer_account_wiki_globe)
    ImageView accountWikiGlobe;
    @BindView(R.id.main_drawer_notifications_container)
    ViewGroup notificationsContainer;
    @Nullable
    Callback callback;

    @BindView(R.id.smart_camera)
    Button button_smart_camera;
    @BindView(R.id.notification_settings)
    Button button_notify_me;
    @BindView(R.id.button_qr_reader)
    Button button_qr_reader;
    @BindView(R.id.wiki_plusplus)
    Button button_wiki_plusplus;
    @BindView(R.id.notes)
    Button button_note;
    @BindView(R.id.group_chat)
    Button button_group_chat;
    @BindView(R.id.direct_message)
    Button button_direct_message;
    @BindView(R.id.button_nearby)
    Button button_nearby;


    public MainDrawerView(Context context) {
        super(context);
        init();
    }

    public MainDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainDrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCallback(@Nullable Callback callback) {
        this.callback = callback;
    }

    public void updateState() {

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // keep original login
            if (AccountUtil.isLoggedIn()) {
                accountNameView.setText(AccountUtil.getUserName());
                accountNameView.setVisibility(VISIBLE);
                loginLogoutButton.setText(getContext().getString(R.string.preference_title_logout));
                loginLogoutButton.setTextColor(ResourceUtil.getThemedColor(getContext(), R.attr.colorError));
                accountAvatar.setVisibility(View.VISIBLE);
                accountWikiGlobe.setVisibility(View.GONE);
                notificationsContainer.setVisibility(View.VISIBLE);
            } else {
                accountNameView.setText(user.getDisplayName());
                accountNameView.setVisibility(VISIBLE);
                loginLogoutButton.setText(getContext().getString(R.string.preference_title_logout));
                loginLogoutButton.setTextColor(ResourceUtil.getThemedColor(getContext(), R.attr.colorError));
                accountAvatar.setVisibility(View.VISIBLE);
                accountWikiGlobe.setVisibility(View.GONE);
                notificationsContainer.setVisibility(View.VISIBLE);
                button_smart_camera.setVisibility(View.VISIBLE);
                button_notify_me.setVisibility(View.VISIBLE);
                button_qr_reader.setVisibility(View.VISIBLE);
                button_note.setVisibility(View.VISIBLE);
                button_group_chat.setVisibility(View.VISIBLE);
                button_wiki_plusplus.setVisibility(View.GONE);
                button_direct_message.setVisibility(View.VISIBLE);
                button_nearby.setVisibility(View.VISIBLE);
            }
        } else {
            accountNameView.setVisibility(GONE);
            loginLogoutButton.setText(getContext().getString(R.string.main_drawer_login));
            loginLogoutButton.setTextColor(ResourceUtil.getThemedColor(getContext(), R.attr.colorAccent));
            accountAvatar.setVisibility(View.GONE);
            accountWikiGlobe.setVisibility(View.VISIBLE);
            notificationsContainer.setVisibility(View.GONE);
            button_smart_camera.setVisibility(View.GONE);
            button_notify_me.setVisibility(View.GONE);
            button_qr_reader.setVisibility(View.GONE);
            button_note.setVisibility(View.GONE);
            button_group_chat.setVisibility(View.GONE);
            button_direct_message.setVisibility(View.GONE);
            button_nearby.setVisibility(View.GONE);
            button_wiki_plusplus.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.main_drawer_settings_container)
    void onSettingsClick() {
        if (callback != null) {
            callback.settingsClick();
        }
    }

    @OnClick(R.id.main_drawer_configure_container)
    void onConfigureClick() {
        if (callback != null) {
            callback.configureFeedClick();
        }
    }

    @OnClick(R.id.main_drawer_notifications_container)
    void onNotificationsClick() {
        if (callback != null) {
            callback.notificationsClick();
        }
    }

    @OnClick(R.id.main_drawer_donate_container)
    void onDonateClick() {
        UriUtil.visitInExternalBrowser(getContext(),
                Uri.parse(String.format(getContext().getString(R.string.donate_url),
                        BuildConfig.VERSION_NAME, WikipediaApp.getInstance().language().getSystemLanguageCode())));
    }

    @OnClick(R.id.main_drawer_about_container)
    void onAboutClick() {
        if (callback != null) {
            callback.aboutClick();
        }
    }

    @OnClick(R.id.main_drawer_help_container)
    void onHelpClick() {
        UriUtil.visitInExternalBrowser(getContext(),
                Uri.parse(getContext().getString(R.string.android_app_faq_url)));
    }

    @OnClick(R.id.main_drawer_login_button)
    void onLoginClick() {
        if (callback != null && user != null) {
            //callback.loginLogoutClick();
            callback.LogoutClickByfirebase();
            updateState();
        } else if (callback != null) {

            callback.loginLogoutClick();
            updateState();
        }
    }

    // call back to open qr read activity
    @OnClick(R.id.button_qr_reader)
    void onQRClick() {
        if (callback != null && user != null) {
            //Log.e("test", "test");
            callback.qrCodeReadClick();
        }
    }

    //call back to open ml kit activity
    @OnClick({R.id.smart_camera})
    void onMLKitClick() {
        if (callback != null && user != null) {
            Log.e("test", "test");
            callback.mlKitClick();
        }
    }

    //call back to open notification setting activity
    @OnClick({R.id.notification_settings})
    void onNotificationClick() {
        if (callback != null && user != null) {
            //Log.e("test", "test");
            callback.notificationClick();
        }
    }

    //call back to open group chat activity
    @OnClick({R.id.group_chat})
    void onGroupChatClick() {
        if (callback != null && user != null) {
            //Log.e("test", "test");
            callback.groupChatClick();
        }
    }

    //call back to open beacon nearby activity
    @OnClick({R.id.button_nearby})
    void onNearbyClick() {
        if (callback != null && user != null) {
            //Log.e("test", "test");
            callback.beaconClick();
        }
    }

    @OnClick({R.id.notes})
    void onNoteClick() {
        if (callback != null && user != null) {
            Log.e("test", "test");
            callback.noteClick();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.view_main_drawer, this);
        ButterKnife.bind(this);
    }

}
