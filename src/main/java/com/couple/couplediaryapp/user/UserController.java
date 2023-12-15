package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.user.model.UserSignInDto;
import com.couple.couplediaryapp.user.model.UserEntity;
import com.couple.couplediaryapp.user.model.UserSignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

//    @PostMapping
//    @Operation(summary = "회원가입", description = "회원가입 기능")
//    public ResVo signUp(@RequestBody UserSignUpDto dto) {
//        return new ResVo(service.signUp(dto));
//    }

    @PostMapping
    @Operation(summary = "로그인", description = "로그인 기능")
    public UserEntity signIn(@RequestBody UserSignInDto dto, HttpServletRequest request) {
        UserEntity userEntity = service.signIn(dto);

        if (userEntity != null) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.USER_ID, userEntity.getUserId());
            session.setAttribute(SessionConst.COUPLE_ID, userEntity.getCoupleId());
        }
        return userEntity;
    }

    public Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.USER_ID)));
    }

    public Integer getCoupleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.COUPLE_ID)));
    }

}
