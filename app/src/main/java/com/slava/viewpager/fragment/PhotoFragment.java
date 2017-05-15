package com.slava.viewpager.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slava.viewpager.R;
import com.slava.viewpager.common.BaseFragment;
import com.slava.viewpager.interfaces.OnHeadlineSelectedListener;

/**
 * Created by slava on 11.05.17.
 */

public class PhotoFragment extends BaseFragment {
    public static final String DATA_RECEIVE = "data_receive";
    TextView textView;
    ImageView imageView;
    OnHeadlineSelectedListener mCallback;




    @Override
    public int getLayout() {
        return R.layout.fragment_photo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.text_view);
        imageView = (ImageView) view.findViewById(R.id.image_view);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            //TODO: sends, but dont show;
            textView.setText(args.getString(DATA_RECEIVE));
            Log.d("i m here", " "+args.getString(DATA_RECEIVE));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }

    }
}
