package com.couple.couplediaryapp.diary.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.*;

@Data
public class DiarySelVo {
    //
    @Schema(title = "조회할 일기의 P.K 해시태그와 사진조회할 때 사용할 것")
    private int diaryId;
    @Schema(title = "작성자의 프로필 정보")
    private String writerPic;
    @Schema(title = "작성한 일기의 제목")
    private String diaryTitle;
    @Schema(title = "작성한 내용 정보")
    private String contents;
    @Schema(title = "작성한 일기의 이모지")
    private int diaryEmoji;
    @Schema(title = "일기를 작성한 시간")
    private String createdAt;
    //
    @Schema(title = "작성한 일기의 사진 정보")
    List<String> pics;
    @Schema(title = "작성한 일기의 해시태그")
    List<String> hashs;
}
