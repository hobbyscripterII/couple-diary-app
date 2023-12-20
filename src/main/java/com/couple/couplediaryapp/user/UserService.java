package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.user.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.couple.couplediaryapp.common.Const.FAIL;

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
    public UserSelProfileVo getProfile(int userId) {
        //
        return mapper.getProfile(userId);
    }

    ResVo updProfile(UserUpdProfileDto dto) {
        int result = mapper.updProfile(dto);
        return new ResVo(result);
    }
    //
}