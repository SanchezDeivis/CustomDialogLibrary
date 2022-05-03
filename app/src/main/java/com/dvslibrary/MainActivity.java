package com.dvslibrary;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;

import androidx.appcompat.app.AppCompatActivity;

import com.customdialogdesign.CustomStandardDialogDs;

public class MainActivity extends AppCompatActivity {

    private boolean textIsHidden =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomStandardDialogDs dialog = createStandardDialog(this,
                "Hola este es mi primer dialog",
                MainActivity.this.getResources().getString(R.string.messaguelarge),
                "...Ver Mas",
                this.getResources().getColor(R.color.purple_200));

        dialog.setPositiveButton("setPositiveButton", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        }).setOverflowViewButton("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textIsHidden){
                    dialog.editMessageViewDialog("...Ver Menos");
                    textIsHidden =false;
                }else {
                    dialog.editMessageViewDialog("...Ver Mas");
                    textIsHidden =true;
                }
            }
        }).setNegativeButton("setNegativeButton", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).setCancelable(false).show();

    }

    public static CustomStandardDialogDs createStandardDialog(Context context, String title,
                                                              String message, String textButton, @ColorInt int textColor) {


        return new CustomStandardDialogDs(context)
                .setTopColorRes(R.color.purple_200)
                .setIcon(R.drawable.ic_launcher_foreground)
                .configureTitleView(tit -> tit.setTextSize(14))
                .configureMessageView(mess -> mess.setTextSize(14))
                .setTitle(title.toUpperCase())
                .setMessage(message,textButton,textColor)
                .setPositiveButtonColor(context.getResources().getColor(com.google.android.material.R.color.design_default_color_on_secondary),
                        context.getResources().getColor(R.color.purple_200));
    }
}