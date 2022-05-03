package com.customdialogdesign;

import android.view.View;

/**
 * Created by Sánchez Deivis on 27,abril,2022
 */
public interface ViewConfigurator<T extends View> {
    void configureView(T v);
}
