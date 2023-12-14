package com.couple.couplediaryapp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class UserEntity {
    @Schema(title = "성공 플래그", description = "1. 성공 0. 실패")
    private int result;
    @JsonIgnore
    private int coupleId;
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private String uid;
    @JsonIgnore
    private String upw;
    @Schema(title = "회원 이름")
    private String nm;
    @Schema(title = "회원 프로필 사진")
    private String pic;
    @Schema(title = "회원 생년월일")
    private Date birth;
    @Schema(title = "처음만난 날")
    private Date startedAt;
    @JsonIgnore
    private String gender;
}
