package com.couple.couplediaryapp.diary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DiaryUpdDto {
    @Schema(title = "일기 아이디")
    private int diaryId;
    @JsonIgnore
    @Schema(title = "커플 아이디")
    private int coupleId;
    @Schema(title = "일기 제목")
    private String title;
    @Schema(title = "일기 내용")
    private String contents;
    @Schema(title = "일기 사진 목록")
    private List<String> diaryPics;
    @Schema(title = "일기 해시태그 목록")
    private List<String> hashContents;
    @Schema(title = "일기 이모지")
    private int emoji;
    @JsonIgnore
    private int picsId;
    @JsonIgnore
    private int hashId;
    @JsonIgnore
    private String pic;
    @JsonIgnore
    private String hash;
}