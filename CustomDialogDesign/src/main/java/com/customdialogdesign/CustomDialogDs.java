package com.customdialogdesign;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * Created by Sánchez Deivis on 27,abril,2022
 */
public abstract class CustomDialogDs<T extends CustomDialogDs> {

    private static final String KEY_SAVED_STATE_TOKEN = "key_saved_state_token";

    private Dialog dialog;
    private View dialogView;

    private ImageView iconView;
    private TextView topTitleView;
    private TextView titleView;
    private TextView messageView;
    private TextView messageMoreView;
    private TextView tvOverflowView;

    public CustomDialogDs(Context context) {
        this(context, 0);
    }

    public CustomDialogDs(Context context, int theme) {
        this(context, theme, 0);
    }

    public CustomDialogDs(Context context, int theme, int layoutRes) {
        if (layoutRes == 0) {
            layoutRes = getLayout();
        }
        if (theme == 0) {
            init(new AlertDialog.Builder(context), layoutRes);
        } else {
            init(new AlertDialog.Builder(context, theme), layoutRes);
        }
    }

    private void init(AlertDialog.Builder dialogBuilder, @LayoutRes int res) {
        dialogView = LayoutInflater.from(dialogBuilder.getContext()).inflate(res, null);
        dialog = dialogBuilder.setView(dialogView).create();

        iconView = findView(R.id.cd_icon);
        topTitleView = findView(R.id.cd_top_title);
        titleView = findView(R.id.cd_title);
        messageView = findView(R.id.cd_message);
        messageMoreView = findView(R.id.cd_message_more);
        tvOverflowView = findView(R.id.tv_view_all_text_less);
    }

    @LayoutRes
    protected abstract int getLayout();

    public T configureView(@NonNull ViewConfiguratorCd<View> viewViewConfiguratorCd) {
        viewViewConfiguratorCd.configureView(dialogView);
        return (T) this;
    }

    public T configureTitleView(@NonNull ViewConfiguratorCd<TextView> viewConfiguratorCd) {
        viewConfiguratorCd.configureView(titleView);
        return (T) this;
    }

    public T configureMessageView(@NonNull ViewConfiguratorCd<TextView> viewConfiguratorCd) {
        viewConfiguratorCd.configureView(messageView);
        return (T) this;
    }

    public T setMessageShort(@StringRes int message) {
        return setMessageShort(string(message));
    }

    public T setMessageShort(CharSequence message) {
        messageView.setVisibility(View.VISIBLE);
        messageView.setText(message);
        return (T) this;
    }


    public T setMessage(@StringRes int message,@StringRes int textButton,@ColorRes int textColor) {
        return setMessage(string(message),string(textButton),color(textColor));
    }

    public T setMessage(CharSequence message,CharSequence textButton,@ColorInt int textColor) {

        String str_message= (String) message;
        boolean isLargeText = str_message.contains(".\n\n");
        String substring = "";

        if (isLargeText) {
            substring = str_message.substring(0, str_message.indexOf("\n\n"));
        }

        messageView.setText(message);
        messageMoreView.setText(message);
        tvOverflowView.setTextColor((textColor));

        /**
         *Checks if the text contains a full stop and or if it exceeds 130 characters
         */
        if (isLargeText || messageView.getText().length() > 130) {
            messageView.setText(substring);
            messageView.setVisibility(View.VISIBLE);
            messageMoreView.setVisibility(View.GONE);
            tvOverflowView.setVisibility(View.VISIBLE);
            tvOverflowView.setText(textButton);

        } else {
            messageView.setVisibility(View.GONE);
            messageMoreView.setVisibility(View.VISIBLE);
            tvOverflowView.setVisibility(View.GONE);
        }
        return (T) this;
    }

    public T editMessageViewDialog(@StringRes int textButton) {
        return editMessageViewDialog(string(textButton));
    }

    public T editMessageViewDialog(CharSequence textButton) {

        if (messageView.getVisibility() == View.VISIBLE) {
            messageView.setVisibility(View.GONE);
            messageMoreView.setVisibility(View.VISIBLE);
            tvOverflowView.setText(textButton);
        } else {
            messageView.setVisibility(View.VISIBLE);
            messageMoreView.setVisibility(View.GONE);
            tvOverflowView.setText(textButton);
        }
        return (T) this;
    }

    public T setTitle(@StringRes int title) {
        return setTitle(string(title));
    }

    public T setTopTitle(@StringRes int title) {
        return setTopTitle(string(title));
    }

    public T setTitle(CharSequence title) {
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);
        return (T) this;
    }

    public T setTopTitle(CharSequence title) {
        topTitleView.setVisibility(View.VISIBLE);
        topTitleView.setText(title);
        return (T) this;
    }

    public T setTopTitleColor(int color) {
        topTitleView.setTextColor(color);
        return (T) this;
    }

    public T setIcon(Bitmap bitmap) {
        iconView.setVisibility(View.VISIBLE);
        iconView.setImageBitmap(bitmap);
        return (T) this;
    }

    public T setIcon(Drawable drawable) {
        iconView.setVisibility(View.VISIBLE);
        iconView.setImageDrawable(drawable);
        return (T) this;
    }

    public T setIcon(@DrawableRes int iconRes) {
        iconView.setVisibility(View.VISIBLE);
        iconView.setImageResource(iconRes);
        return (T) this;
    }

    public T setIconTintColor(int iconTintColor) {
        iconView.setColorFilter(iconTintColor);
        return (T) this;
    }

    public T setTitleGravity(int gravity) {
        titleView.setGravity(gravity);
        return (T) this;
    }

    public T setMessageGravity(int gravity) {
        messageView.setGravity(gravity);
        return (T) this;
    }

    public T setTopColor(@ColorInt int topColor) {
        findView(R.id.cd_color_area).setBackgroundColor(topColor);
        return (T) this;
    }

    public T setTopColorRes(@ColorRes int topColoRes) {
        return setTopColor(color(topColoRes));
    }

    /*
     * You should call method saveInstanceState on handler object and then use saved info to restore
     * your dialog in onRestoreInstanceState. Static methods wasDialogOnScreen and getDialogId will
     * help you in this.
     */
    public T setInstanceStateHandler(int id, CustomSaveStateHandler handler) {
        handler.handleDialogStateSave(id, this);
        return (T) this;
    }

    public T setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return (T) this;
    }

    public T setSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean hasSavedStateHere =
                    savedInstanceState.keySet().contains(KEY_SAVED_STATE_TOKEN) &&
                            savedInstanceState.getSerializable(KEY_SAVED_STATE_TOKEN) == getClass();
            if (hasSavedStateHere) {
                restoreState(savedInstanceState);
            }
        }
        return (T) this;
    }

    public Dialog show() {
        dialog.show();
        return dialog;
    }

    public Dialog create() {
        return dialog;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_SAVED_STATE_TOKEN, getClass());
    }

    void restoreState(Bundle savedState) {
    }

    boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    protected String string(@StringRes int res) {
        return dialogView.getContext().getString(res);
    }

    protected int color(@ColorRes int colorRes) {
        return ContextCompat.getColor(getContext(), colorRes);
    }

    protected Context getContext() {
        return dialogView.getContext();
    }

    protected <ViewClass extends View> ViewClass findView(int id) {
        return (ViewClass) dialogView.findViewById(id);
    }

    protected class ClickListenerDecorator implements View.OnClickListener {

        private View.OnClickListener clickListener;
        private boolean closeOnClick;

        protected ClickListenerDecorator(View.OnClickListener clickListener, boolean closeOnClick) {
            this.clickListener = clickListener;
            this.closeOnClick = closeOnClick;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                if (clickListener instanceof CustomDialogCompat.DialogOnClickListenerAdapter) {
                    CustomDialogCompat.DialogOnClickListenerAdapter listener =
                            (CustomDialogCompat.DialogOnClickListenerAdapter) clickListener;
                    listener.onClick(dialog, v.getId());
                } else {
                    clickListener.onClick(v);
                }
            }
            if (closeOnClick) {
                dismiss();
            }
        }
    }
}
