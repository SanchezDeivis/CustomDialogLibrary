package com.dvslibrary;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.customdialogdesign.CustomChoiceDialog;
import com.customdialogdesign.CustomInfoDialogDs;
import com.customdialogdesign.CustomProgressDialog;
import com.customdialogdesign.CustomStandardDialogDs;
import com.customdialogdesign.CustomTextInputDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean textIsHidden = true;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        Button buttonCustomDialog = findViewById(R.id.btn_custom_dialog);
        Button buttonTextImputDialog = findViewById(R.id.btn_text_input);
        Button buttonProgressDialog = findViewById(R.id.btn_progress_dialog);
        Button buttonChoiceDialog = findViewById(R.id.btn_choice_dialog);
        Button buttonMultichoiceDialog = findViewById(R.id.btn_multi_choice_dialog);
        Button buttonInfoDialog = findViewById(R.id.btn_info_dialog);

        buttonInfoDialog.setOnClickListener(view -> {

            CustomInfoDialogDs createInfoDialog = createInfoDialog(context,
                    "Select the option",
                    "Select the option and confirm Select the option and confirm\n\nSelect the option and confirm Select the option and confirm \n\nSelect the option and confirm",
                    "...Ver Mas",
                    context.getResources().getColor(R.color.purple_200));

            createInfoDialog
                    //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                    .setNotShowAgainOptionEnabled(0)
                    .setNotShowAgainOptionChecked(false)
                    .setConfirmButtonText(R.string.confirm)
                    .show();
        });

        buttonCustomDialog.setOnClickListener(view -> {

            CustomStandardDialogDs dialog = createStandardDialog(context,
                    "This is standar dialog",
                    MainActivity.this.getResources().getString(R.string.messaguelarge),
                    "...Ver Mas",
                    context.getResources().getColor(R.color.purple_200));

            dialog.setPositiveButton("setPositiveButton", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            }).setOverflowViewButton("", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (textIsHidden) {
                        dialog.editMessageViewDialog("...Ver Menos");
                        textIsHidden = false;
                    } else {
                        dialog.editMessageViewDialog("...Ver Mas");
                        textIsHidden = true;
                    }
                }
            }).setNegativeButton("setNegativeButton", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }).setCancelable(false).show();
        });

        buttonTextImputDialog.setOnClickListener(view -> {

            CustomTextInputDialog inputDialog = createInputDialog(context
                    , "Insert the text.",
                    "I can insert the text for content and pruduct",
                    "ver mas...",
                    context.getResources().getColor(R.color.purple_200));

            inputDialog.setConfirmButton("add product", new CustomTextInputDialog.OnTextInputConfirmListener() {
                @Override
                public void onTextInputConfirmed(String text) {
                    Toast.makeText(context, "Show the text insert: " + text, Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("Cancel", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputDialog.dismiss();
                }

            }).setCancelable(false).show();
        });

        buttonProgressDialog.setOnClickListener(view -> {

            CustomProgressDialog progressDialog = createProgressDialog(
                    context,
                    "Loading",
                    "Loading message...");

            progressDialog.show();
        });

        buttonChoiceDialog.setOnClickListener(view -> {

            ArrayAdapter<ItemsOptions> adapter = new OptionsAdapter(context, loadOptions());

            CustomChoiceDialog createChoiceDialog = createChoiceDialog(context,
                    "Select to product", "Choice a product");

            createChoiceDialog.setItems(adapter, new CustomChoiceDialog.OnItemSelectedListener<ItemsOptions>() {
                @Override
                public void onItemSelected(int position, ItemsOptions item) {
                    Toast.makeText(context, ("You option thi: " + item.idItemOption), Toast.LENGTH_SHORT).show();
                }
            }).show();
        });

        buttonMultichoiceDialog.setOnClickListener(view -> {

            String[] items = {"First option", "Second option", "Third option", "Fourth option",
                    "Fifth option", "Sixth option"};

            CustomChoiceDialog createMultichoiceDialog = createMultichoiceDialog(context,
                    "Select to product", "Choice a or more products");

            createMultichoiceDialog.setItemsMultiChoice(items, new CustomChoiceDialog.OnItemsSelectedListener<String>() {
                        @Override
                        public void onItemsSelected(List<Integer> positions, List<String> items) {
                            Toast.makeText(MainActivity.this,
                                            "you selected" + TextUtils.join("\n", items),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .setConfirmButtonText(R.string.confirm)
                    .show();
        });

    }

    private List<ItemsOptions> loadOptions() {

        List<ItemsOptions> result = new ArrayList<>();
        String[] raw = {"First option%1$", "Second option%2$", "Third option%3$", "Fourth option%4$",
                "Fifth option%5$", "Sixth option%6$"};
        for (String op : raw) {
            String[] info = op.split("%");
            result.add(new ItemsOptions(info[1], info[0]));
        }
        return result;
    }

    public static CustomStandardDialogDs createStandardDialog(Context context, String title,
                                                              String message, String textButton, @ColorInt int textColor) {
        return new CustomStandardDialogDs(context)
                .setTopColorRes(R.color.purple_200)
                .setIcon(R.drawable.ic_launcher_foreground)
                .configureTitleView(tit -> tit.setTextSize(14))
                .configureMessageView(mess -> mess.setTextSize(14))
                .setTitle(title.toUpperCase())
                .setMessage(message, textButton, textColor)
                .setPositiveButtonColor(context.getResources().getColor(com.google.android.material.R.color.design_default_color_on_secondary),
                        context.getResources().getColor(R.color.purple_200));
    }

    public static CustomTextInputDialog createInputDialog(Context context, String title,
                                                          String message, String textButton,
                                                          @ColorInt int textColor) {
        return new CustomTextInputDialog(context)
                .setTopColorRes(com.google.android.material.R.color.design_default_color_primary)
                .setIcon(R.drawable.ic_launcher_foreground)
                .configureTitleView(tit -> tit.setTextSize(14))
                .configureMessageView(mess -> mess.setTextSize(14))
                .setTitle(title.toUpperCase())
                .setCancelable(false)
                .setMessage(message, "", 0)
                .configureEditText(v -> {
                    v.setTextColor(ContextCompat.getColor(context, R.color.purple_200));
                    v.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                });
    }

    public static CustomProgressDialog createProgressDialog(Context context, String title,
                                                            String message) {
        return new CustomProgressDialog(context)
                .setTopColorRes(com.google.android.material.R.color.design_default_color_primary)
                .setIcon(R.drawable.ic_launcher_foreground)
                .configureTitleView(tit -> tit.setTextSize(14))
                .configureMessageView(mess -> mess.setTextSize(14))
                .setTitle(title.toUpperCase())
                .setCancelable(true)
                .setMessage(message, "", 0);
    }

    public static CustomChoiceDialog createChoiceDialog(Context context, String title, String message) {

        return new CustomChoiceDialog(context)
                .setTopColorRes(com.google.android.material.R.color.design_default_color_primary)
                .setIcon(R.drawable.ic_launcher_foreground)
                .configureTitleView(tit -> tit.setTextSize(14))
                .configureMessageView(mess -> mess.setTextSize(14))
                .setTitle(title.toUpperCase())
                .setCancelable(true)
                .setMessage(message, "", 0);
    }

    public CustomChoiceDialog createMultichoiceDialog(Context context, String title, String message) {

        return new CustomChoiceDialog(context,
                android.R.attr.checkboxStyle)
                .setTopColorRes(com.google.android.material.R.color.design_default_color_primary)
                .setIcon(R.drawable.ic_launcher_foreground)
                .configureTitleView(tit -> tit.setTextSize(14))
                .configureMessageView(mess -> mess.setTextSize(14))
                .setTitle(title.toUpperCase())
                .setCancelable(true)
                .setMessage(message, "", 0);
    }

    public CustomInfoDialogDs createInfoDialog(Context context, String title, String message,
                                               String textButton, @ColorInt int textColor) {

        return new CustomInfoDialogDs(context)
                .setTopColorRes(com.google.android.material.R.color.design_default_color_primary)
                .setIcon(R.drawable.ic_launcher_foreground)
                .configureTitleView(tit -> tit.setTextSize(14))
                .configureMessageView(mess -> mess.setTextSize(14))
                .setTitle(title.toUpperCase())
                .setCancelable(true)
                .setMessageShort(message);
    }
}