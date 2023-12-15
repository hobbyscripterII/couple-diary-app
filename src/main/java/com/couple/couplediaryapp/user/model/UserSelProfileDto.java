package com.couple.couplediaryapp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSelProfileDto {
    //
    @Schema(title = "로그인 유저의 커플 P.K")
    private int coupleId;
    @Schema(title = "로그인 유저의 P.K")
    private int userId;
}
