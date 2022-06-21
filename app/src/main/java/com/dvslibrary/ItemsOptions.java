package com.dvslibrary;

import androidx.annotation.NonNull;

/**
 * Created by Sánchez Deivis on 10,junio,2022
 */
public class ItemsOptions {
    public final String idItemOption;
    public final String nameItemOption;

    public ItemsOptions(String nameItemOption, String idItemOption) {
        this.nameItemOption = nameItemOption;
        this.idItemOption = idItemOption;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemsOptions{" +
                "idItemOption='" + idItemOption + '\'' +
                ", nameItemOption='" + nameItemOption + '\'' +
                '}';
    }
}
