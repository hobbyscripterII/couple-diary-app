package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.diary.model.DiarySelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface DiaryMapper {
    // 일기 목록 조회
    List<DiarySelVo> getDiary(int coupleId);
    List<String> getDiaryPics(int diaryId);
    List<String> getDiaryHash(int diaryId);
}
