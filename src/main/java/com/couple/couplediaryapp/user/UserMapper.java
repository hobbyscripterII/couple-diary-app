package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.diary.model.DiaryUpdDto;
import com.couple.couplediaryapp.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    // 환
    UserSelProfileVo getProfile(UserSelProfileDto dto); // 유저 프로필 조회
    UserSelPartnerVo getPartnerProfile(UserSelProfileDto dto); // 상대 연인의 프로필 조회

    // 주영
    int signUp(UserSignUpDto dto); // 회원가입
    UserEntity getUser(String uid); // 로그인

    // 승민
    int updProfile(UserUpdProfileDto dto); // 유저 프로필 수정

}
