package com.demo.payment.mapper;

import com.demo.payment.entites.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User getUserById(@Param("id") Long id);
}
