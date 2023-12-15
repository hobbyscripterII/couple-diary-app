package com.couple.couplediaryapp.diary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DiaryInsDto {
    @JsonIgnore
    private int diaryId;
    @JsonIgnore
    private int ccouple;
    @JsonIgnore
    private int cuser;

    @Schema(title = "작성한 일기의 제목")
    private String title;
    @Schema(title = "작성한 일기의 내용")
    private String contents;
    @Schema(title = "작성한 일기의 이모지")
    private int emoji;
    @Schema(title = "작성한 일기의 사진 정보")
    private List<String> pics;
    @Schema(title = "작성한 일기의 해시태그")
    private List<String> hashContents;
}
