package com.couple.couplediaryapp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSignUpDto {
    @JsonIgnore
    private int userId;
    @Schema(name = "회원 아이디")
    private String uid;
    @Schema(name = "회원 비밀번호")
    private String upw;
    @Schema(name = "회원 이름")
    private String nm;
    @Schema(name = "회원 프로필 사진")
    private String pic;
    @Schema(name = "회원 성별")
    private String gender;
    @Schema(name = "회원 생년월일")
    private Date birth;
    @Schema(name = "처음만난 날")
    private Date startedAt;
}
