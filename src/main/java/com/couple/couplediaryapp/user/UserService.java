package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    public int signUp(UserSignUpDto dto) {
        int result = Const.FAIL;
        dto.setUpw(BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt()));

        try {
            return mapper.signUp(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public UserEntity signIn(UserSignInDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setResult(Const.FAIL);

        try {
            userEntity = mapper.getUser(dto.getUid());
            if (userEntity == null) {
                return userEntity;
            } else if (BCrypt.checkpw(dto.getUpw(), userEntity.getUpw())) {
                userEntity.setResult(Const.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEntity;
    }
    //
    public UserSelProfileVo getProfile(UserSelProfileDto dto){
        //
        return mapper.getProfile(dto);
    }
    //
    public UserSelPartnerVo getPartnerProfile(UserSelProfileDto dto){
        //
        return mapper.getPartnerProfile(dto);
    }
}