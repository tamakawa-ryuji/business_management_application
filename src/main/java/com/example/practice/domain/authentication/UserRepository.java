package com.example.practice.domain.authentication;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
        @Select("select * from users where username = #{username}")
        Optional<User> findByUsername(String username);

        @Select("select * from users")
        List<User> findAll();

        @Insert("insert into users (username, password, authority) values (#{param1}, #{param2}, #{param3})")
        void insert(String username, String password, String authority);

}

