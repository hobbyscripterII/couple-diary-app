package com.couple.couplediaryapp.user;

import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.user.model.UserSignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    @Operation(summary = "회원가입", description = "회원가입 기능")
    public ResVo signUp(@RequestBody UserSignUpDto dto) {
        return new ResVo(service.signUp(dto));
    }
}
