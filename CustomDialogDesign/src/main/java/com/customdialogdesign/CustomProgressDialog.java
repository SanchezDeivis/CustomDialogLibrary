package com.customdialogdesign;

import android.content.Context;

/**
 * Created by Sánchez Deivis on 08,junio,2022
 */
public class CustomProgressDialog extends CustomDialogDs<CustomProgressDialog> {
    public CustomProgressDialog(Context context) {
        super(context);
        setCancelable(false);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        setCancelable(false);
    }

    protected int getLayout() {
        return R.layout.custom_dialog_progress;
    }
}