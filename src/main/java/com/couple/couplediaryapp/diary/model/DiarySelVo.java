package com.couple.couplediaryapp.diary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.*;

@Data
public class DiarySelVo {
    //
    @Schema(title = "일기 아이디")
    private int diaryId;
    @Schema(title = "작성자의 프로필 정보")
    private String profilePic;
    @Schema(title = "작성한 일기의 제목")
    private String title;
    @Schema(title = "작성한 일기의 내용")
    private String contents;
    @Schema(title = "작성한 일기의 이모지")
    private int emoji;
    @Schema(title = "일기를 작성한 시간")
    private String createdAt;
    @Schema(title = "연애를 시작한 날")
    private String startedAt;
    //
    @Schema(title = "작성한 일기의 사진 정보")
    List<String> diaryPics;
    @Schema(title = "작성한 일기의 해시태그")
    List<String> hashContents;
}
