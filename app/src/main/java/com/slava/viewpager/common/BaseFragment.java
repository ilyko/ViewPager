package com.slava.viewpager.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by slava on 11.05.17.
 */

public abstract class BaseFragment extends Fragment {

    private int[] permissions;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);

    }
    public abstract int getLayout();

    public void setPermissions(int[] permissions) {
        this.permissions = permissions;
    }

    public void requestPermissions(){

    }
}
