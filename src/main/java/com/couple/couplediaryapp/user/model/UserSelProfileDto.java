package com.couple.couplediaryapp.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSelProfileDto {
    private int coupleId;
    private int userId;
}
