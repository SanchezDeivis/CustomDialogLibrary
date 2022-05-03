package com.customdialogdesign;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * Created by Sánchez Deivis on 27,abril,2022
 */
public class CustomDialogDesign extends CustomDialogDs<CustomDialogDesign> {

    private View addedView;
    private InstanceStateManager instanceStateManager;

    public CustomDialogDesign(Context context) {
        super(context);
    }

    public CustomDialogDesign(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialogDesign setView(@LayoutRes int layout) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup parent = findView(R.id.cd_custom_view_container);
        addedView = inflater.inflate(layout, parent, true);
        return this;
    }

    public CustomDialogDesign setView(View customView) {
        ViewGroup container = findView(R.id.cd_custom_view_container);
        container.addView(customView);
        addedView = customView;
        return this;
    }

    @Override
    public CustomDialogDesign configureView(@NonNull ViewConfiguratorCd<View> configurator) {
        if (addedView == null) {
            throw new IllegalStateException(string(R.string.ex_msg_dialog_view_not_set));
        }
        configurator.configureView(addedView);
        return this;
    }

    public CustomDialogDesign setListener(int viewId, View.OnClickListener listener) {
        return setListener(viewId, false, listener);
    }

    public CustomDialogDesign setListener(int viewId, boolean dismissOnClick, View.OnClickListener listener) {
        if (addedView == null) {
            throw new IllegalStateException(string(R.string.ex_msg_dialog_view_not_set));
        }
        View.OnClickListener clickListener = new ClickListenerDecorator(listener, dismissOnClick);
        findView(viewId).setOnClickListener(clickListener);
        return this;
    }

    public CustomDialogDesign setInstanceStateManager(InstanceStateManager instanceStateManager) {
        this.instanceStateManager = instanceStateManager;
        return this;
    }

    @Override
    void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        instanceStateManager.saveInstanceState(outState);
    }

    @Override
    void restoreState(Bundle savedState) {
        super.restoreState(savedState);
        instanceStateManager.restoreInstanceState(savedState);
    }

    @Override
    protected int getLayout() {
        return R.layout.custom_dialog_custom;
    }


    public interface InstanceStateManager {
        void saveInstanceState(Bundle outState);

        void restoreInstanceState(Bundle savedState);
    }
}
