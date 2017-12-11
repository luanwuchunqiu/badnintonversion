package com.myweather.app.badmintonversion.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.VideoItem;
import com.myweather.app.badmintonversion.view.adapter.VideoPlayerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zyt on 2017/12/5.
 */

public class VideoPlayActivity extends AppCompatActivity {
    @BindView(R.id.qquick_start_back)
    ImageView qquickStartBack;
    @BindView(R.id.text_hot)
    TextView textHot;
    @BindView(R.id.text_recent)
    TextView textRecent;
    @BindView(R.id.video_recycler_view)
    RecyclerView videoRecyclerView;
    private List<VideoItem> videoItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_paly);
        ButterKnife.bind(this);
        initData();
        videoRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        videoRecyclerView.setAdapter(new VideoPlayerAdapter(videoItemList, this));
    }

    public void initData() {
        videoItemList = new ArrayList<>();
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
        videoItemList.add(new VideoItem("视频一", "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg"));
    }

    @OnClick({R.id.qquick_start_back, R.id.text_hot, R.id.text_recent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qquick_start_back:{
                onBackPressed();
            }
                break;
            case R.id.text_hot:{
                textHot.setBackground(getResources().getDrawable(R.drawable.video_button_pressed));
                textRecent.setBackground(getResources().getDrawable(R.drawable.video_button_unpressed));
            }
                break;
            case R.id.text_recent:{
                textHot.setBackground(getResources().getDrawable(R.drawable.video_button_unpressed));
                textRecent.setBackground(getResources().getDrawable(R.drawable.video_button_pressed));
            }
                break;
        }
    }
}
