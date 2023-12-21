package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.diary.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface DiaryMapper {
    //일기 등록
    int insDiary(DiaryInsDto dto);// 일기 내용 등록
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

    // 주영
    int updDiary(DiaryUpdDto dto); // 일기 수정
    int updDiaryPics(DiaryUpdDto dto); // 일기 사진 부분 수정
    int updDiaryHash(DiaryUpdDto dto); // 일기 해시태그 부분 수정
    int delDiaryPic(int picsId); // 일기 사진 부분 삭제
    int delDiaryHash(int diaryId); // 일기 해시태그 부분 삭제
    int insDiaryPicPart(DiaryPicsInsDto dto); // 일기 사진 부분 등록
    int insDiaryHashPart(DiaryHashInsDto dto); // 일기 해시태그 부분 등록
    List<Integer> getPicsId(int diaryId); // picsId 가져오기(couple_pics PK)
    List<Integer> getHashId(int diaryId); // hashId 가져오기(couple_hash PK)

    // 일기 삭제
    int delDiaryPics(int diaryId);
    int delDiaryHashs(int diaryId);
    int delDiary(int diaryId);
}
