package com.iflytek.ads.dao.core.api;

import com.iflytek.ads.dao.core.sys.BaseDao;
import com.iflytek.ads.entity.core.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
