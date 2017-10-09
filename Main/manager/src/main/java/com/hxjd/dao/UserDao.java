package com.hxjd.dao;

import com.hxjd.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Nandem on 2017/9/11.
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
