package com.dvslibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Sánchez Deivis on 10,junio,2022
 */
public class OptionsAdapter extends ArrayAdapter<ItemsOptions> {
    public OptionsAdapter(Context context, List<ItemsOptions> itemsOptions) {
        super(context,0,itemsOptions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_item_option, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else vh = (ViewHolder) convertView.getTag();

        ItemsOptions option = getItem(position);
        if (option != null) {
            vh.id.setText(option.idItemOption);
            vh.name.setText(option.nameItemOption);
        }

        return convertView;
    }

    private static final class ViewHolder {
        TextView id;
        TextView name;

        public ViewHolder(View v) {
            id = (TextView) v.findViewById(R.id.tv_id_item_opc);
            name = (TextView) v.findViewById(R.id.tv_name_item_opc);
        }
    }
}
