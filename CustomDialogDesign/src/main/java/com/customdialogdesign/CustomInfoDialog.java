package com.customdialogdesign;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.StringRes;

/**
 * Created by Sánchez Deivis on 27,abril,2022
 */
public class CustomInfoDialog extends CustomDialog<CustomInfoDialog> {
    private static final String STORAGE = "cd_dont_show";

    private static final String KEY_DONT_SHOW_AGAIN = "key_dont_show_again";

    private CheckBox cbDontShowAgain;
    private Button confirmButton;

    private int infoDialogId;

    public CustomInfoDialog(Context context) {
        super(context);
    }

    public CustomInfoDialog(Context context, int theme) {
        super(context, theme);
    }

    {
        cbDontShowAgain = findView(R.id.cd_cb_dont_show_again);
        confirmButton = findView(R.id.cd_btn_confirm);
        confirmButton.setOnClickListener(new ClickListenerDecorator(null, true));
        infoDialogId = -1;
    }

    public CustomInfoDialog setNotShowAgainOptionEnabled(int dialogId) {
        infoDialogId = dialogId;
        cbDontShowAgain.setVisibility(View.VISIBLE);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean notShow = cbDontShowAgain.isChecked();
                storage(getContext()).edit().putBoolean(String.valueOf(infoDialogId), notShow).apply();
                dismiss();
            }
        });
        return this;
    }

    public CustomInfoDialog setNotShowAgainOptionChecked(boolean defaultChecked) {
        cbDontShowAgain.setChecked(defaultChecked);
        return this;
    }

    public CustomInfoDialog setConfirmButtonText(@StringRes int text) {
        return setConfirmButtonText(string(text));
    }

    public CustomInfoDialog setConfirmButtonText(String text) {
        confirmButton.setText(text);
        return this;
    }

    public CustomInfoDialog setConfirmButtonColor(int color) {
        confirmButton.setTextColor(color);
        return this;
    }

    @Override
    public Dialog show() {
        if (infoDialogId == -1) {
            return super.show();
        }

        boolean shouldShowDialog = !storage(getContext()).getBoolean(String.valueOf(infoDialogId), false);
        if (shouldShowDialog) {
            return super.show();
        } else {
            return super.create();
        }
    }

    @Override
    void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_DONT_SHOW_AGAIN, cbDontShowAgain.isChecked());
    }

    @Override
    void restoreState(Bundle savedState) {
        super.restoreState(savedState);
        cbDontShowAgain.setChecked(savedState.getBoolean(KEY_DONT_SHOW_AGAIN));
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_info;
    }

    public static void reset(Context context, int dialogId) {
        storage(context).edit().putBoolean(String.valueOf(dialogId), false).apply();
    }

    private static SharedPreferences storage(Context context) {
        return context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }
}

