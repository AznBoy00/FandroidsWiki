package org.wikipedia.notes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

public class ConfirmationDialog extends DialogFragment {

    //region (Member attributes)

    public class DialogState implements Serializable {
        public String title, message;
        public @StringRes int titleId = -1;
        public @StringRes int messageId = -1;
        public int accentColor = 0;
        public @DrawableRes int iconRes = -1;
        public Bundle extras = new Bundle();

        public void saveState(Bundle outState){
            outState.putString("title", title);
            outState.putInt("titleId", titleId);
            outState.putString("message", message);
            outState.putInt("messageId", messageId);
            outState.putInt("accentColor", accentColor);
            outState.putInt("iconRes", iconRes);
            outState.putBundle("extras", extras);
        }

        public void restoreState(Bundle savedInstanceState){
            title = savedInstanceState.getString("title");
            titleId = savedInstanceState.getInt("titleId");
            message = savedInstanceState.getString("message");
            messageId = savedInstanceState.getInt("messageId");
            accentColor = savedInstanceState.getInt("accentColor");
            iconRes = savedInstanceState.getInt("iconRes");
            extras = savedInstanceState.getBundle("extras");
        }
    }

    public interface MyClickListener {
        void onClick(Bundle extras);
    }

    MyClickListener onYesListener;
    MyClickListener onNoListener;
    DialogInterface.OnCancelListener onCancelListener;
    DialogInterface.OnDismissListener onDismissListener;

    public DialogState mDialogState = new DialogState();

    //endregion -- end --

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDialogState.saveState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mDialogState.restoreState(savedInstanceState);

        if(mDialogState != null){
            if(mDialogState.title == null && mDialogState.titleId != -1)
                mDialogState.title = getContext().getString(mDialogState.titleId);
            if(mDialogState.message == null && mDialogState.messageId != -1)
                mDialogState.message = getContext().getString(mDialogState.messageId);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(mDialogState.title)
                .setMessage(mDialogState.message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onYesListener != null)
                            onYesListener.onClick(mDialogState.extras);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onNoListener != null)
                            onNoListener.onClick(mDialogState.extras);
                    }
                })
                .setOnCancelListener(this.onCancelListener)
                .setOnDismissListener(this.onDismissListener);

        if (mDialogState.iconRes != -1)
            builder.setIcon(mDialogState.iconRes);

        final AlertDialog confirmDialog = builder.create();

        if (mDialogState.accentColor != 0) {
            confirmDialog.setOnShowListener(
                    new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            confirmDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setTextColor(mDialogState.accentColor);

                            confirmDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(mDialogState.accentColor);
                        }
                    }
            );
        }

        return confirmDialog;
    }

    //region Setters {}
    public ConfirmationDialog setTitle(String title) {
        mDialogState.title = title;
        return this;
    }

    public ConfirmationDialog setTitle(@StringRes int textId) {
        mDialogState.titleId = textId;
        return this;
    }

    public ConfirmationDialog setMessage(String message) {
        mDialogState.message = message;
        return this;
    }

    public ConfirmationDialog setMessage(@StringRes int textId) {
        mDialogState.messageId = textId;
        return this;
    }

    public ConfirmationDialog setIcon(@DrawableRes int iconRes) {
        mDialogState.iconRes = iconRes;
        return this;
    }
    //endregion

    //region Set listeners
    public static void resetOnYesClickListener(AppCompatActivity context, String tagName,
                                               MyClickListener onYesConfirm){
        Fragment frag = context.getSupportFragmentManager().findFragmentByTag(tagName);
        if(frag != null){
            ConfirmationDialog dialog = (ConfirmationDialog) frag;
            dialog.setOnYesClickListener(onYesConfirm);
        }
    }

    public ConfirmationDialog setOnYesClickListener(MyClickListener listener) {
        this.onYesListener = listener;
        return this;
    }

    public ConfirmationDialog setOnNoClickListener(MyClickListener listener) {
        this.onNoListener = listener;
        return this;
    }

    public ConfirmationDialog setOnCancelListener(DialogInterface.OnCancelListener listener) {
        this.onCancelListener = listener;
        return this;
    }

    public ConfirmationDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.onDismissListener = listener;
        return this;
    }

    public ConfirmationDialog setAccentColor(int color) {
        mDialogState.accentColor = color;
        return this;
    }
    //endregion

}
