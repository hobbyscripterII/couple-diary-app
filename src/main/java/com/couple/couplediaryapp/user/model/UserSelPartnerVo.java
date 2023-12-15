package com.couple.couplediaryapp.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSelPartnerVo {
    //
    @Schema(title = "파트너 이름")
    private String partnerNm;
    @Schema(title = "파트너 프로필 사진")
    private String partnerPic;
    @Schema(title = "파트너 생일")
    private String partnerBirth;
    @Schema(title = "만난 날")
    private String startedAt;
}
