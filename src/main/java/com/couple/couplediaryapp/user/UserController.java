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

import static com.couple.couplediaryapp.common.Const.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입", description = "회원가입")
    public ResVo signUp(@RequestBody UserSignUpDto dto) {
        return new ResVo(service.signUp(dto));
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping
    public UserEntity login(@RequestBody UserSignInDto dto, HttpServletRequest request) throws Exception {
        log.info("dto = {}", dto);
        try {
            UserEntity entity = service.signIn(dto);
            if (entity.getResult() == SUCCESS) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionConst.USER_ID, entity.getUserId());
                session.setAttribute(SessionConst.COUPLE_ID, entity.getCoupleId());
                return entity;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @GetMapping("/logout")
    public ResVo logout(HttpServletRequest request) throws Exception {
        try {
            HttpSession session = request.getSession();
            session.invalidate();
            return new ResVo(SUCCESS);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @GetMapping("/profile")
    @Operation(summary = "프로필 출력", description = "프로필 출력")
    public UserSelProfileVo getProfile(HttpServletRequest request) throws Exception {
        // 내가 누구랑 커플인지를 로그인한 유저가 누구인지를 알기위해 P.K를 얻어옵니다.
        int userId = getUserId(request);
        try {
            if (Utils.isNotNull(userId)) {
                // 로그인하는 유저의 P.K가 null이 아닐 때 service에 유저의 P.K값을 리턴해줍니다.
                return service.getProfile(userId);
            } else {
                // 만약 유저의 P.K가 null일 경우 예외 던집니다.
                throw new NullPointerException();
            }
        } catch (Exception e) {
            // 성공할 때 제대로 리턴됐는지 확인하기 위해
            throw new Exception();
        }
    }

    @PatchMapping("/profile")
    @Operation(summary = "프로필 수정", description = "프로필 수정")
    ResVo updProfile(@RequestBody UserUpdProfileDto dto, HttpServletRequest request) {
        int userId = getUserId(request);
        dto.setUserId(userId);
        return service.updProfile(dto);
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


