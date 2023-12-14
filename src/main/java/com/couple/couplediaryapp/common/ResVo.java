package com.couple.couplediaryapp.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResVo {
    @Schema(name = "성공 플래그", description = "1: 성공 0: 실패")
    private int result;
}
