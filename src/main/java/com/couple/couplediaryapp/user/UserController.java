package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.common.Utils;
import com.couple.couplediaryapp.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    public Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.USER_ID)));
    }

    public Integer getCoupleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.COUPLE_ID)));
    }

    @PostMapping("sign-up")
    @Operation(summary = "회원가입", description = "회원가입")
    public ResVo signUp(@RequestBody UserSignUpDto dto) {
        return new ResVo(service.signUp(dto));
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping
    public UserEntity signIn(@RequestBody UserSignInDto dto, HttpServletRequest request) throws Exception {
        UserEntity entity = null; // 얕은 복사용 객체 생성

        try {
            UserEntity entity_ = service.signIn(dto); // 회원가입 서비스 호출
            // entity_가 null이 아닐 경우 if문을 실행한다.
            if (Utils.isNotNull(entity_)) {
                // session에 회원 id와 커플 id를 저장한다.
                HttpSession session = request.getSession();
                session.setAttribute(SessionConst.USER_ID, entity_.getUserId());
                session.setAttribute(SessionConst.COUPLE_ID, entity_.getCoupleId());
                // try - catch문 밖에있는 entity에 값을 대입한다.(얕은 복사)
                entity = entity_;
            } else {
                // entity_가 null이면 예외를 던진다.
                throw new NullPointerException();
            }
        } catch (Exception e) {
            // 예외 발생시에도 예외를 던진다.
            throw new Exception();
        }
        // entity_가 null이 아닐 경우에만 entity에 값을 담아 return한다.
        return entity;
    }

    //
    @GetMapping("/profile")
    @Operation(summary = "프로필 출력", description = "프로필 출력")
    public UserSelProfileVo getProfile(HttpServletRequest request) {
        //
        if (getUserId(request) == 0) {
            return new UserSelProfileVo(); // 이건 낼 다시 해보기
        }
        return service.getProfile(getUserId(request));
    }

    @PatchMapping("/profile")
    @Operation(summary = "프로필 수정", description = "프로필 수정")
    ResVo updProfile(UserUpdProfileDto dto, HttpServletRequest request) {
        int userId = getUserId(request);
        dto.setUserId(userId);
        return service.updProfile(dto);
    }
}


