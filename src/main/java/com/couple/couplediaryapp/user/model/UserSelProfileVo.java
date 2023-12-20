package com.couple.couplediaryapp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSelProfileVo {
    //
    @Schema(title = "나의 프로필 이름")
    private String nm;
    @Schema(title = "나의 프로필 사진")
    private String pic;
    @Schema(title = "파트너의 프로필 사진")
    private String partnerPic;
    @Schema(title = "나의 생일")
    private String birth;
    @Schema(title = "연애를 시작한 날")
    private String startedAt;
    //
    @Schema(title = "파트너의 이름")
    private String partnerNm;
    @Schema(title = "파트너의 생일")
    private String partnerBirth;
}
