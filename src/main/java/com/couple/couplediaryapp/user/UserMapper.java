package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int signUp(UserSignUpDto dto);
    UserEntity getUser(String uid);
    //
    UserSelProfileVo getProfile(UserSelProfileDto dto); // 유저 프로필 조회
    UserSelPartnerVo getPartnerProfile(UserSelProfileDto dto); // 상대 연인의 프로필 조회
}
