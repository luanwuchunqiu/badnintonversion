package com.myweather.app.badmintonversion.view.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.view.activity.VideoPlayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * Created by zyt on 2017/9/7.
 */

public class TrainFragment extends Fragment {

    @BindView(R.id.background1)
    ImageView background1;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.layout1)
    RelativeLayout layout1;
    @BindView(R.id.background2)
    ImageView background2;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.layout2)
    RelativeLayout layout2;
    @BindView(R.id.background3)
    ImageView background3;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.layout3)
    RelativeLayout layout3;
    Unbinder unbinder;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, container, false);

        unbinder = ButterKnife.bind(this, view);


        //   GrayscaleTransformation（灰度级转换）
// 使用构造方法 GrayscaleTransformation(Context context)
        Glide.with(this)
                .load(R.drawable.backgroun11)



                .into(background1);
        Glide.with(this)
                .load(R.drawable.backgroun21)

                .into(background2);
        Glide.with(this)
                .load(R.drawable.backgroun31)

                .into(background3);
        return view;



    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnTouch(R.id.layout1)
    public boolean onLayout1Touched(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                text1.setVisibility(View.GONE);
                Glide.with(this)
                        .load(R.drawable.background1)

                        .into(background1);
            }
            break;
            case MotionEvent.ACTION_UP: {
                text1.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(R.drawable.backgroun11)

                        .into(background1);
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                startActivity(intent);
            }
            break;
        }
        return true;
    }

    @OnTouch(R.id.layout2)
    public boolean onLayout2Touched(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                text2.setVisibility(View.GONE);
                Glide.with(this)
                        .load(R.drawable.background2)

                        .into(background2);
            }
            break;
            case MotionEvent.ACTION_UP: {
                text2.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(R.drawable.backgroun21)

                        .into(background2);
            }
            break;
        }
        return true;
    }

    @OnTouch(R.id.layout3)
    public boolean onLayout3Touched(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                text3.setVisibility(View.GONE);
                Glide.with(this)
                        .load(R.drawable.background3)

                        .into(background3);
            }
            break;
            case MotionEvent.ACTION_UP: {
                text3.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.backgroun31)


                        .into(background3);
            }
            break;
        }
        return true;
    }
}
