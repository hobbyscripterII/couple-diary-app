package com.couple.couplediaryapp.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignInDto {
    @Schema(title = "회원 아이디")
    private String uid;
    @Schema(title = "회원 비밀번호")
    private String upw;
}
