package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.user.model.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    public int signUp(UserSignUpDto dto) {
        int result = Const.FAIL;
        try {
            return mapper.signUp(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }
}