package com.myweather.app.badmintonversion.model;



import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.Impl.UserModelImpl;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by zyt on 2017/10/14.
 */

public class UserModel implements UserModelImpl{


    @Override
    public void getUser(String userAccount, String password, final BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("userAccount", userAccount);
        query.addWhereEqualTo("password", password);
        query.setLimit(1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                //查询成功
                if(e==null){
                    //这里可以保存到SP
                    listener.getSuccess(list.get(0));
                }
                //查询失败
                else {
                    listener.getFailure(e);

                }

            }
        });


    }

    /**
     * 根据objectId获取User
     *
     * @param objectId
     * @param listener
     */

    @Override
    public void getUser(String objectId, final BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", objectId);
        query.setLimit(1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e ==null){
                    listener.getSuccess(list.get(0));
                }else{
                    listener.getFailure(e);
                }
            }
        });
    }




    /**
     * 更换用户的头像
     *
     * @param path
     * @param listener
     */
    public void updateUserPhoto(String path, final String objectId, final BaseListener listener) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    final User user = new User();
                    user.setUserPhoto(bmobFile);
                    user.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){listener.getSuccess(user);}
                            else{listener.getFailure(e);}
                        }
                    });
                }
                else{}
            }
        });}

        public void updateUsername(String name,String objectId,final BaseListener listener){
            final User user = new User();
            user.setUsername(name);
            user.update(objectId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){listener.getSuccess(user);}
                    else {listener.getFailure(e);}
                }
            });
        }


       /* bmobFile.upload(BaseApplication.getmContext(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                final User user = new User();
                user.setPhoto(bmobFile);
                user.update(BaseApplication.getmContext(), objectId, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        listener.getSuccess(user);

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        listener.getFailure();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                listener.getFailure();
            }
        });*/


    /**
     * 判断当前用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        List<UserLocal> list = DataSupport.findAll(UserLocal.class);
        if (list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将当前登录的对象保持到数据库中
     *
     * @param userLocal
     */
    public void putUserLocal(UserLocal userLocal) {
        DataSupport.deleteAll(UserLocal.class);
        userLocal.save();
    }

    /**
     * 获取当前登录的对象
     *
     * @return
     */
    public UserLocal getUserLocal() {
        return DataSupport.findFirst(UserLocal.class);
    }


    /**
     * 注册功能
     *
     * @param user
     */
    public void onRegister(User user, final BaseListener listener) {
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    listener.getSuccess(null);
                }
            }
        });
       /* user.save(BaseApplication.getmContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showLong(BaseApplication.getmContext(), "注册成功");
                listener.getSuccess(null);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showLong(BaseApplication.getmContext(), "注册失败");
                listener.getFailure();
            }
        });*/
    }

    /**
     * 判断当前手机号码是否注册
     *
     * @param phone
     * @param listener
     * @return
     */

    public void isPhoneRegister(String phone, final BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("Number", phone);
        query.setLimit(1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    listener.getSuccess(list.get(0));
                }else{
                    listener.getFailure(e);
                }
            }
        });

    }

    public void isAccountResgister(String userAccount,final BaseListener listener){
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("userAccount", userAccount);
        query.setLimit(1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    listener.getSuccess(list.get(0));
                }else{
                    listener.getFailure(e);
                }
            }
        });
    }
}
