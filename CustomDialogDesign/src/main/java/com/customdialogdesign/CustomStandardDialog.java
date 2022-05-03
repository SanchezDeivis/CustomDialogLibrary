package com.customdialogdesign;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * Created by Sánchez Deivis on 27,abril,2022
 */
public class CustomStandardDialog extends CustomDialog<CustomStandardDialog> {

    public static final int POSITIVE_BUTTON = R.id.cd_btn_yes;
    public static final int NEGATIVE_BUTTON = R.id.cd_btn_no;
    public static final int NEUTRAL_BUTTON = R.id.cd_btn_neutral;
    public static final int OVERFLOW_VIEW_BUTTON = R.id.tv_view_all_text_less;

    private Button positiveButton;
    private Button negativeButton;
    private Button neutralButton;
    private TextView overflowViewButton;

    public CustomStandardDialog(Context context) {
        super(context);
    }

    public CustomStandardDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomStandardDialog(Context context, ButtonLayout buttonLayout) {
        super(context, 0, buttonLayout.layoutRes);
    }

    public CustomStandardDialog(Context context, int theme, ButtonLayout buttonLayout) {
        super(context, theme, buttonLayout.layoutRes);
    }


    {
        positiveButton = findView(R.id.cd_btn_yes);
        negativeButton = findView(R.id.cd_btn_no);
        neutralButton = findView(R.id.cd_btn_neutral);
        overflowViewButton = findView(R.id.tv_view_all_text_less);
    }

    public CustomStandardDialog setPositiveButton(@StringRes int text, View.OnClickListener listener) {
        return setPositiveButton(string(text), listener);
    }

    public CustomStandardDialog setPositiveButton(String text, @Nullable View.OnClickListener listener) {
        positiveButton.setVisibility(VISIBLE);
        positiveButton.setText(text);
        positiveButton.setOnClickListener(new ClickListenerDecorator(listener, true));
        return this;
    }

    public CustomStandardDialog setNegativeButtonText(@StringRes int text) {
        return setNegativeButton(string(text), null);
    }

    public CustomStandardDialog setNegativeButtonText(String text) {
        return setNegativeButton(text, null);
    }

    public CustomStandardDialog setNegativeButton(@StringRes int text, View.OnClickListener listener) {
        return setNegativeButton(string(text), listener);
    }

    public CustomStandardDialog setNegativeButton(String text, @Nullable View.OnClickListener listener) {
        negativeButton.setVisibility(VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(new ClickListenerDecorator(listener, true));
        return this;
    }

    public CustomStandardDialog setNeutralButtonText(@StringRes int text) {
        return setNeutralButton(string(text), null);
    }

    public CustomStandardDialog setNeutralButtonText(String text) {
        return setNeutralButton(text, null);
    }

    public CustomStandardDialog setNeutralButton(@StringRes int text, @Nullable View.OnClickListener listener) {
        return setNeutralButton(string(text), listener);
    }

    public CustomStandardDialog setNeutralButton(String text, @Nullable View.OnClickListener listener) {
        neutralButton.setVisibility(VISIBLE);
        neutralButton.setText(text);
        neutralButton.setOnClickListener(new ClickListenerDecorator(listener, true));
        return this;
    }

    public CustomStandardDialog setOverflowViewButton(/*@StringRes int text,*/ @Nullable View.OnClickListener listener) {
        return setOverflowViewButton(/*string(text),*/ listener);
    }

    public CustomStandardDialog setOverflowViewButton(String text, @Nullable View.OnClickListener listener) {
        //overflowViewButton.setVisibility(VISIBLE);
        //overflowViewButton.setText(text);
        overflowViewButton.setOnClickListener(new ClickListenerDecorator(listener, false));
        return this;
    }

    public CustomStandardDialog setButtonsColor(@ColorInt int color) {
        positiveButton.setTextColor(color);
        negativeButton.setTextColor(color);
        neutralButton.setTextColor(color);
        return this;
    }

    public CustomStandardDialog setButtonsColorRes(@ColorRes int colorRes) {
        return setButtonsColor(color(colorRes));
    }

    public CustomStandardDialog setOnButtonClickListener(View.OnClickListener listener) {
        return setOnButtonClickListener(true, listener);
    }

    public CustomStandardDialog setOnButtonClickListener(boolean closeOnClick, View.OnClickListener listener) {
        View.OnClickListener clickHandler = new ClickListenerDecorator(listener, closeOnClick);
        positiveButton.setOnClickListener(clickHandler);
        neutralButton.setOnClickListener(clickHandler);
        negativeButton.setOnClickListener(clickHandler);
        return this;
    }

    public CustomStandardDialog setPositiveButtonText(@StringRes int text) {
        return setPositiveButton(string(text), null);
    }

    public CustomStandardDialog setPositiveButtonText(String text) {
        return setPositiveButton(text, null);
    }

    public CustomStandardDialog setPositiveButtonColor(@ColorInt int color,@ColorInt int colorBackground) {
        positiveButton.setTextColor(color);
        //positiveButton.setBackgroundColor(colorBackground);
        return this;
    }

    public CustomStandardDialog setNegativeButtonColor(@ColorInt int color) {
        negativeButton.setTextColor(color);
        return this;
    }

    public CustomStandardDialog setNeutralButtonColor(@ColorInt int color) {
        neutralButton.setTextColor(color);
        return this;
    }

    public CustomStandardDialog setPositiveButtonColorRes(@ColorRes int colorRes,
                                                          @ColorRes int colorBackground) {
        return setPositiveButtonColor(color(colorRes),color(colorBackground));
    }

    public CustomStandardDialog setNegativeButtonColorRes(@ColorRes int colorRes) {
        return setNegativeButtonColor(color(colorRes));
    }

    public CustomStandardDialog setNeutralButtonColorRes(@ColorRes int colorRes) {
        return setNeutralButtonColor(color(colorRes));
    }

    @Override
    protected int getLayout() {
        return ButtonLayout.HORIZONTAL.layoutRes;
    }

    public enum ButtonLayout {
        HORIZONTAL(R.layout.dialog_standard),
        VERTICAL(R.layout.dialog_standard_vertical);
        final @LayoutRes int layoutRes;
        ButtonLayout(@LayoutRes int layoutRes) {
            this.layoutRes = layoutRes;
        }
    }
}
