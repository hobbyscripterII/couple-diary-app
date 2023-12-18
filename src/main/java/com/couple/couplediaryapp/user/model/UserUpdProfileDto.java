package com.couple.couplediaryapp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserUpdProfileDto {
    @JsonIgnore
    private int userId;
    @Schema(title = "회원 이름")
    private String nm;
    @Schema(title = "회원 프로필 사진")
    private String pic;
    @Schema(title = "회원 생년월일")
    private String birth;
    @Schema(title = "만난 날")
    private String startedAt;
}
