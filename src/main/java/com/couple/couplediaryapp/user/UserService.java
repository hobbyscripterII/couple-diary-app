package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.couple.couplediaryapp.common.Const.FAIL;
import static com.couple.couplediaryapp.common.Const.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    public int signUp(UserSignUpDto dto) {
        try {
            dto.setUpw(BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt()));
            return mapper.signUp(dto);
        } catch (Exception e) {
            return FAIL;
        }
    }

    public UserEntity signIn(UserSignInDto dto) {
        UserEntity entity = new UserEntity();
        try {
            UserEntity entity_ = mapper.getUser(dto.getUid());
            log.info("entity_ = {}", entity_);
            if (entity_ == null) {
                entity.setResult(FAIL);
            } else if (BCrypt.checkpw(dto.getUpw(), entity_.getUpw())) {
                entity_.setResult(SUCCESS);
                entity = entity_;
            }
            return entity;
        } catch (Exception e) {
            entity.setResult(FAIL);
            return entity;
        }
    }

    //
    public UserSelProfileVo getProfile(int userId) {
        // controller에서 리턴받은 값을 받은 후 mapper에 전달합니다.
        return mapper.getProfile(userId);
    }

    ResVo updProfile(UserUpdProfileDto dto) {
        int result = mapper.updProfile(dto);
        return new ResVo(result);
    }
    //
}