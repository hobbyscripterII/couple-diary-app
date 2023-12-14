package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.user.model.UserEntity;
import com.couple.couplediaryapp.user.model.UserSignUpDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int signUp(UserSignUpDto dto);
    UserEntity getUser(String uid);
}
