package com.couple.couplediaryapp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data

public class UserUpdProfileDto {

    @JsonIgnore
    private int userId;
    private String nm;
    private String pic;
    private String birth;
    private String startedAt;

}
