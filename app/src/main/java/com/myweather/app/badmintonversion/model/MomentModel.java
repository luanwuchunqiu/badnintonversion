package com.myweather.app.badmintonversion.model;

import com.myweather.app.badmintonversion.entity.Moment;
import com.myweather.app.badmintonversion.entity.MomentLocal;
import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by zyt on 2017/10/18.
 */

public class MomentModel {
    //通过用户名ID得到用户
    public void getUserFromId(Moment moment, final BaseListener listener){
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(moment.getUserId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    listener.getSuccess(user);
                }else {
                    listener.getFailure(e);
                }

            }
        });

    }

    //朋友圈的一条记录保存到本地

    public void save2Local(Moment moment){
        MomentLocal momentLocal = new MomentLocal();
        momentLocal.setObjectId(moment.getObjectId());
        momentLocal.setUserId(moment.getUserId());
        List<String> photos = new ArrayList<String>();
        List<BmobFile> bmobFiles = moment.getPhotos();
       for(int i=0;i<bmobFiles.size();i++){
           photos.add(bmobFiles.get(i).getFilename());
       }
        momentLocal.setPhotos(photos);
        momentLocal.setMomentText(moment.getMomentText());
        momentLocal.save();

    }

}
