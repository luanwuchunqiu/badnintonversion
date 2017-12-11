package com.myweather.app.badmintonversion.dao;



import com.myweather.app.badmintonversion.entity.UserData;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;

import java.util.List;

/**
 * Created by zyt on 2017/10/25.
 */

public interface UserDataDao {

    void save(String userId, List<String> data,BaseListener baseListener);
    void saveOrUpdate(String userId, List<String> data, BaseListener baseListener);
    void delete(String userId);
    void update(UserData userData,List<String> data,BaseListener baseListener);
    void findByUserId(String userId,BaseListener baseListener);
    void getUserData(String userId,BaseListener baseListener);
}
