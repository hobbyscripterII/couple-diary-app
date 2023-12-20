package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.common.Utils;
import com.couple.couplediaryapp.diary.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.couple.couplediaryapp.common.Const.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryMapper mapper;

    //일기 읽기
    DiarySelVo selDiary(int diaryId) {
        DiarySelVo list = mapper.selDiary(diaryId);
        try {
            if (list != null) {
                List<String> hashContents = mapper.selHashDiary(diaryId);
                list.setHashContents(hashContents);
                List<String> pics = mapper.selPicDiary(diaryId);
                list.setPics(pics);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    // 일기 목록
    public List<DiarySelVo> getDiary(int coupleId) {
        //
        List<DiarySelVo> list = mapper.getDiary(coupleId);
        for (DiarySelVo vo : list) {
            List<String> pics = mapper.getDiaryPics(vo.getDiaryId());
            List<String> hashs = mapper.getDiaryHash(vo.getDiaryId());
            vo.setPics(pics);
            vo.setHashContents(hashs);
            //
        }
        return list;
    }

    // 일기 등록
    public ResVo insDiary(DiaryInsDto dto) {
        try {
            mapper.insDairy(dto);
            mapper.insDiaryPics(dto);
            mapper.insDiaryHash(dto);
            return new ResVo(SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResVo(FAIL);
        }
    }

    //승민
    ResVo delDiary(int diaryId) {
        try {
            int delPic = mapper.delDiaryPics(diaryId);
            int delHash = mapper.delDiaryHashs(diaryId);
            int delDiary = mapper.delDiary(diaryId);
            return new ResVo(SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResVo(FAIL);
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 주영(작업 진행 중)
    public int updDiaryTest(DiaryUpdDto dto) throws Exception {
        int result = FAIL;

        try {
            int updDiaryRows = mapper.updDiary(dto); // 1. diary 테이블 수정
            int updDiaryPicsRows = updDiaryItems(dto, PICS); // 2. diary_pics 테이블 수정
            int updDiaryHashRows = updDiaryItems(dto, HASH); // 3. diary_hash 테이블 수정
            result = SUCCESS;
        } catch (Exception e) {
            throw new Exception();
        }
        return result;
    }

    public int updDiaryItems(DiaryUpdDto dto, String items) throws Exception {
        int result = FAIL; // return 값

        try {
            // 해당 일기의 pics, hash PK를 담을 list
            List<Integer> getItemsId = items.equals(PICS) ? getPicsId(dto.getDiaryId()) : getHashId(dto.getDiaryId());
            // DB에 저장된 items의 개수
            int savedItemsCnt = getItemsId.size();
            // JSON에서 넘어온 items의 개수
            int updItemsCnt = items.equals(PICS) ? dto.getDiaryPics().size() : dto.getHashContents().size();

            // DB > JSON
            if (savedItemsCnt > updItemsCnt) {
                int length = (savedItemsCnt - updItemsCnt); // update 해야 할 length
                int delDiaryItemsRows = delDiaryItemsPart(updItemsCnt, savedItemsCnt, items);
                int updDiaryItemsRows = updDiaryItemsPart(dto, getItemsId, length, items);
                // DB < JSON (진행 중)
            } else {
                int length = (updItemsCnt - savedItemsCnt);
                int insDiaryItemsRows = insDiaryItemsPart(dto, getItemsId, length, items);
                int updDiaryItemsRows = updDiaryItemsPart(dto, getItemsId, length, items);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            throw new Exception(); // 완료하고 주석 풀기
        }
        return result;
    }

    // 아이템 개별 등록
    public int insDiaryItemsPart(DiaryUpdDto dto, List<Integer> getItemsId, int length, String items) {
        Object obj = null;
        DiaryPicsInsDto picsDto = new DiaryPicsInsDto();
        DiaryHashInsDto hashDto = new DiaryHashInsDto();

        if (items.equals(PICS)) {
            picsDto.setDiaryId(dto.getDiaryId());
            obj = picsDto;
        } else {
            hashDto.setDiaryId(dto.getDiaryId());
            obj = hashDto;
        }

        log.info("obj = {}", obj);

//        for (int i = 0; i < length; i++) {
//            dto.setPicsId(getItemsId.get(i));
//            dto.setPic(dto.getDiaryPics().get(i));
//
//            dto.setHashId(getItemsId.get(i));
//            dto.setHash(dto.getHashContents().get(i));
//        }

//        return items.equals(PICS) ? mapper.insDiaryPics(dto) : mapper.insDiaryHash(dto);
        return FAIL;
    }

    // 아이템 개별 업로드
    public int updDiaryItemsPart(DiaryUpdDto dto, List<Integer> getItemsId, int length, String items) {
        for (int i = 0; i < length; i++) {
            dto.setPicsId(getItemsId.get(i));
            dto.setPic(dto.getDiaryPics().get(i));

            dto.setHashId(getItemsId.get(i));
            dto.setHash(dto.getHashContents().get(i));
        }
        return items.equals(PICS) ? mapper.updDiaryPics(dto) : mapper.updDiaryHash(dto);
    }

    // 아이템 개별 삭제
    public int delDiaryItemsPart(int min, int max, String items) {
        int delDiaryItemsRows = 0;
        for (int i = min; i < max; i++) {
            delDiaryItemsRows = items.equals(PICS) ? mapper.delDiaryPic(i) : mapper.delDiaryHash(i);
        }
        return delDiaryItemsRows >= SUCCESS ? SUCCESS : FAIL;
    }

    public List<Integer> getPicsId(int diaryId) {
        return mapper.getPicsId(diaryId);
    }

    public List<Integer> getHashId(int diaryId) {
        return mapper.getHashId(diaryId);
    }
}
