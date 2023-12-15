package com.couple.couplediaryapp.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSelPartnerVo {
    //
    @Schema(title = "연인의 프로필 이름")
    private String partnerNm;
    @Schema(title = "연인의 프로필 사진")
    private String partnerPic;
    @Schema(title = "연인의 생일")
    private String partnerBirth;
    @Schema(title = "나와 연인과 연애를 시작한 날짜")
    private String coupleDate;
}
