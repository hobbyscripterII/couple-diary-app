package com.couple.couplediaryapp.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSelProfileVo {
    //
    @Schema(title = "나의 프로필 이름")
    private String myNm;
    @Schema(title = "나의 프로필 사진")
    private String myPic;
    @Schema(title = "나의 연인의 프로필 사진")
    private String partnerPic;
    @Schema(title = "나의 생일")
    private String birthDay;
    @Schema(title = "연애를 시작한 날")
    private String startedAt;
}