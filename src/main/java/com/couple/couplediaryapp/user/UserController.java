package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입", description = "회원가입 기능")
    public ResVo signUp(@RequestBody UserSignUpDto dto) {
        return new ResVo(service.signUp(dto));
    }

    public Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.USER_ID)));
    }

    public Integer getCoupleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.COUPLE_ID)));
    }

    //
    @Operation(summary = "로그인", description = "로그인 처리 기능")
    @PostMapping
    public UserEntity signIn(@RequestBody UserSignInDto dto, HttpServletRequest request) {
        UserEntity userEntity = service.signIn(dto);

        if (userEntity != null) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.USER_ID, userEntity.getUserId());
            session.setAttribute(SessionConst.COUPLE_ID, userEntity.getCoupleId());
        }
        return userEntity;
    }

    //
    @GetMapping("/profile")
    @Operation(summary = "프로필 출력", description = "프로필 출력 기능")
    public UserSelProfileVo getProfile(HttpServletRequest request) {
        //
        return service.getProfile(UserSelProfileDto
                .builder()
                .coupleId(getCoupleId(request))
                .userId(getUserId(request))
                .build());
    }

    @PatchMapping("/profile")
    @Operation(summary = "프로필 수정", description = "프로필 수정")
    ResVo updProfile(UserUpdProfileDto dto, HttpServletRequest request) {
        int userId = getUserId(request);
        dto.setUserId(userId);
        return service.updProfile(dto);
    }
}


