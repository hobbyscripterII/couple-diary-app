package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.diary.model.DiaryInsDto;
import com.couple.couplediaryapp.diary.model.DiarySelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface DiaryMapper {


    //일기 등록
    int insDairy(DiaryInsDto dto);// 일기 내용 등록
    int insDiaryPics(DiaryInsDto dto);// 일기 사진 등록
    int insDiaryHash(DiaryInsDto dto);// 일기 해시태그 등록


    // 일기 목록 조회
    List<DiarySelVo> getDiary(int coupleId);
    List<String> getDiaryPics(int diaryId);
    List<String> getDiaryHash(int diaryId);


    // 일기 읽기
    DiarySelVo selDiary(int diaryId);
    List<String> selPicDiary(int diaryId);
    List<String> selHashDiary(int diaryId);


}
