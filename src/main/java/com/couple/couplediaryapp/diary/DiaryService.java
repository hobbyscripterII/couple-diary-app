package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.Utils;
import com.couple.couplediaryapp.diary.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                // 불러온 다이어리 목록이 null이 아닐 경우 아래의 문장을 실행한다.
                List<String> hashContents = mapper.selHashDiary(diaryId);
                list.setHashContents(hashContents);
                List<String> pics = mapper.selPicDiary(diaryId);
                list.setPics(pics);
            } else {
                // 불러올 다이어리 목록이 없을 경우 NullPointException을 던진다.
                throw new NullPointerException();
            }
        } catch (Exception e) {
            // 예외 발생 시 예외를 던진다.
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

    //일기 등록
    public ResVo insDiary(DiaryInsDto dto) throws Exception {
        try {
            int insDiaryRows = mapper.insDiary(dto);
            int insDiaryHashRows = mapper.insDiaryHash(dto);
            int insDiaryPicsRows = mapper.insDiaryPics(dto);
            // 다이어리 삽입, 해시태그 개수 동일, 사진 개수 동일 시 아래 문장을 실행하여 1을 출력한다.
            if (Utils.isNotNull(insDiaryRows) || insDiaryHashRows == dto.getHashContents().size() || insDiaryPicsRows == dto.getPics().size()) {
                return new ResVo(SUCCESS);
            } else {
                // 불러올 다이어리 목록이 없을 경우 NullPointException을 던진다.
                throw new NullPointerException();
            }
        } catch (Exception e) {
            // 예외 발생 시 예외를 던진다.
            throw new Exception();
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

    // 주영
    public int updDiary(DiaryUpdDto dto) throws Exception {
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
        try {
            List<Integer> idList = items.equals(PICS) ? getPicsId(dto.getDiaryId()) : getHashId(dto.getDiaryId());
            int jsonCnt = items.equals(PICS) ? dto.getDiaryPics().size() : dto.getHashContents().size();
            int dbCnt = idList.size();

            if (dbCnt == jsonCnt) { // DB == JSON
                return updDiaryItemsPart(dto, idList, jsonCnt, items);
            } else if (dbCnt > jsonCnt) { // DB > JSON
                if (jsonCnt == 0) { // 저장된 모든 사진 삭제
                    return delDiaryItems(dto.getDiaryId(), items);
                }
                int delItem = delDiaryItemsPart(jsonCnt, dbCnt, idList, items);
                int updItem = updDiaryItemsPart(dto, idList, jsonCnt, items);
            } else { // DB < JSON
                if (dbCnt == 0) { // 사진 등록
                    return insDiaryItemsPart(0, dto, items);
                }
                int insItem = insDiaryItemsPart(dbCnt, dto, items);
                int updItem = updDiaryItemsPart(dto, idList, dbCnt, items);
            }
        } catch (Exception e) {
            throw new Exception();
        }
        return SUCCESS;
    }

    // 아이템 개별 INSERT
    public int insDiaryItemsPart(int min, DiaryUpdDto dto, String itemName) throws Exception {
        int result = 0;
        int max = itemName.equals(PICS) ? dto.getDiaryPics().size() : dto.getHashContents().size();

        try {
            if (itemName.equals(PICS)) {
                DiaryPicsInsDto picsDto = new DiaryPicsInsDto();
                picsDto.setDiaryId(dto.getDiaryId());
                for (int i = min; i < max; i++) {
                    picsDto.setPic(dto.getDiaryPics().get(i));
                    result += mapper.insDiaryPicPart(picsDto);
                }
            } else {
                DiaryHashInsDto hashDto = new DiaryHashInsDto();
                hashDto.setDiaryId(dto.getDiaryId());
                for (int i = min; i < max; i++) {
                    hashDto.setHash(dto.getHashContents().get(i));
                    result += mapper.insDiaryHashPart(hashDto);
                }
            }
        } catch (Exception e) {
            throw new Exception();
        }
        return result;
    }

    // 아이템 개별 UPDATE
    public int updDiaryItemsPart(DiaryUpdDto dto, List<Integer> getItemsId, int length, String items) throws Exception {
        int result = 0;

        try {
            if (items.equals(PICS)) {
                for (int i = 0; i < length; i++) {
                    dto.setPic(dto.getDiaryPics().get(i));
                    dto.setPicsId(getItemsId.get(i));
                    result += mapper.updDiaryPics(dto);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    dto.setHashId(getItemsId.get(i));
                    dto.setHash(dto.getHashContents().get(i));
                    result += mapper.updDiaryHash(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            throw new Exception(); // 완료하고 주석 풀기
        }
        return result;
    }

    // 아이템 전체 DELETE
    public int delDiaryItems(int diaryId, String items) {
        return items.equals(PICS) ? mapper.delDiaryPics(diaryId) : mapper.delDiaryHashs(diaryId);
    }

    // 아이템 개별 DELETE
    public int delDiaryItemsPart(int min, int max, List<Integer> idList, String itemName) { // 지울 pk가 필요함..
        int result = 0;
        try {
            for (int i = min; i < max; i++) {
                result += itemName.equals(PICS) ? mapper.delDiaryPic(idList.get(i)) : mapper.delDiaryHash(idList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result >= SUCCESS ? SUCCESS : FAIL;
    }

    // 사진 PK 가져오기
    public List<Integer> getPicsId(int diaryId) {
        return mapper.getPicsId(diaryId);
    }

    // 해시태그 PK 가져오기
    public List<Integer> getHashId(int diaryId) {
        return mapper.getHashId(diaryId);
    }
}
