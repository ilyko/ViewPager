package com.slava.viewpager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.slava.viewpager.R;
import com.slava.viewpager.common.BaseFragment;
import com.slava.viewpager.interfaces.OnHeadlineSelectedListener;

/**
 * Created by slava on 11.05.17.
 */

public class CommandFragment extends BaseFragment implements View.OnClickListener {
    OnHeadlineSelectedListener mCallback;
    public final static String BUNDLE_KEY = "bundle_key";
    Button button;

    @Override
    public int getLayout() {
        return R.layout.fragment_button;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = (Button) view.findViewById(R.id.btn_take);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
                if (v.getId() == R.id.btn_take) {
                    mCallback.passData("Text to pass FragmentB");
        Log.d("Button", "clicked");
    }
}
}
