package com.hxjd.dao;

import com.hxjd.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Time: 14:27
 * Date: 2017/9/15
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: MyBatis数据库操作，这里应该无法直接运行，需要先建对应的数据库和表。如果不会直接问我。
 */
public interface UserDao
{
    @Select("SELECT * FROM user")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getOne(int id);

    @Insert("INSERT INTO user(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
    void insert(User user);

    @Update("UPDATE user SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(int id);

}
