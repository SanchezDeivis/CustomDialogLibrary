package com.customdialogdesign;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Created by Sánchez Deivis on 07,junio,2022
 */
public class CustomTextInputDialog extends CustomDialogDs<CustomTextInputDialog>{

    private static final String KEY_HAS_ERROR = "key_has_error";
    private static final String KEY_TYPED_TEXT = "key_typed_text";
    private EditText inputField;
    private TextView errorMessage;
    private TextView confirmButton;
    private TextView negativeButton;
    private CustomTextInputDialog.TextFilter filter;

    public CustomTextInputDialog(Context context) {
        super(context);
       confirmButton = (TextView)findView(R.id.cd_btn_confirm);
        negativeButton = (TextView)findView(R.id.cd_btn_negative);
        inputField = (EditText)findView(R.id.cd_text_input);
        errorMessage = (TextView)findView(R.id.cd_error_message);
        inputField.addTextChangedListener(new CustomTextInputDialog.HideErrorOnTextChanged());
    }

    public CustomTextInputDialog(Context context, int theme) {
        super(context, theme);
        confirmButton = (TextView)findView(R.id.cd_btn_confirm);
        negativeButton = (TextView)findView(R.id.cd_btn_negative);
        inputField = (EditText)findView(R.id.cd_text_input);
        errorMessage = (TextView)findView(R.id.cd_error_message);
        inputField.addTextChangedListener(new CustomTextInputDialog.HideErrorOnTextChanged());
    }

    public CustomTextInputDialog configureEditText(@NonNull ViewConfiguratorCd<EditText> viewConfigurator) {
        viewConfigurator.configureView(inputField);
        return this;
    }

    public CustomTextInputDialog setConfirmButton(@StringRes int text, CustomTextInputDialog.OnTextInputConfirmListener listener) {
        return setConfirmButton(string(text), listener);
    }

    public CustomTextInputDialog setConfirmButton(String text, CustomTextInputDialog.OnTextInputConfirmListener listener) {
        confirmButton.setText(text);
        confirmButton.setOnClickListener(new CustomTextInputDialog.TextInputListener(listener));
        return this;
    }

    public CustomTextInputDialog setConfirmButtonColor(int color) {
        confirmButton.setTextColor(color);
        return this;
    }

    public CustomTextInputDialog setNegativeButton(@StringRes int text, View.OnClickListener listener) {
        return setNegativeButton(string(text), listener);
    }

    public CustomTextInputDialog setNegativeButton(String text, View.OnClickListener listener) {
        negativeButton.setVisibility(View.VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(new ClickListenerDecorator( listener, true));
        return this;
    }

    public CustomTextInputDialog setNegativeButtonColor(int color) {
        negativeButton.setTextColor(color);
        return this;
    }

    public CustomTextInputDialog setInputFilter(@StringRes int errorMessage, CustomTextInputDialog.TextFilter filter) {
        return setInputFilter(string(errorMessage), filter);
    }

    public CustomTextInputDialog setInputFilter(String errorMessage, CustomTextInputDialog.TextFilter filter) {
        this.filter = filter;
        this.errorMessage.setText(errorMessage);
        return this;
    }

    public CustomTextInputDialog setErrorMessageColor(int color) {
        errorMessage.setTextColor(color);
        return this;
    }

    public CustomTextInputDialog setInputType(int inputType) {
        inputField.setInputType(inputType);
        return this;
    }

    public CustomTextInputDialog addTextWatcher(TextWatcher textWatcher) {
        inputField.addTextChangedListener(textWatcher);
        return this;
    }

    public CustomTextInputDialog setInitialInput(@StringRes int text) {
        return setInitialInput(string(text));
    }

    public CustomTextInputDialog setInitialInput(String text) {
        inputField.setText(text);
        return this;
    }

    public CustomTextInputDialog setHint(@StringRes int hint) {
        return setHint(string(hint));
    }

    public CustomTextInputDialog setHint(String text) {
        inputField.setHint(text);
        return this;
    }

    private void setError() {
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        errorMessage.setVisibility(View.GONE);
    }

    void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("key_has_error", errorMessage.getVisibility() == View.VISIBLE);
        outState.putString("key_typed_text", inputField.getText().toString());
    }

    void restoreState(Bundle savedState) {
        super.restoreState(savedState);
        if (savedState.getBoolean("key_has_error", false)) {
            this.setError();
        }

        this.inputField.setText(savedState.getString("key_typed_text"));
    }

    protected int getLayout() {
        return R.layout.custom_dialog_text_input;
    }

    public interface TextFilter {
        boolean check(String var1);
    }

    public interface OnTextInputConfirmListener {
        void onTextInputConfirmed(String var1);
    }

    private class HideErrorOnTextChanged implements TextWatcher {

        private HideErrorOnTextChanged() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            hideError();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    private class TextInputListener implements View.OnClickListener {

        private OnTextInputConfirmListener wrapped;

        private TextInputListener(OnTextInputConfirmListener wrapped) {
            this.wrapped = wrapped;
        }

        public void onClick(View v) {
            String text = inputField.getText().toString();
            if (filter != null) {
                boolean isWrongInput = !filter.check(text);
                if (isWrongInput) {
                    setError();
                    return;
                }
            }

            if (wrapped != null) {
                wrapped.onTextInputConfirmed(text);
            }

            dismiss();
        }
    }
}
