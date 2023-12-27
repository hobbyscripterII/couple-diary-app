package com.couple.couplediaryapp.diary;

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
    DiarySelVo selDiary(int diaryId) throws Exception {
        try {
            DiarySelVo list = mapper.selDiary(diaryId);
            if (Utils.isNotNull(list)) {
                // 불러온 다이어리 목록이 null이 아닐 경우 아래의 문장을 실행한다.
                List<String> hashContents = mapper.selHashDiary(diaryId);
                list.setHashContents(hashContents);
                List<String> diaryPics = mapper.selPicDiary(diaryId);
                list.setPics(diaryPics);
                return list;
            } else {
                // 불러올 다이어리 목록이 없을 경우 NullPointException을 던진다.
                throw new NullPointerException();
            }
        } catch (Exception e) {
            // 예외 발생 시 예외를 던진다.
            throw new Exception();
        }
    }

    // 일기 목록
    public List<DiarySelVo> getDiary(int coupleId) {
        // controller에서 커플의 P.K를 받은 후 list의 주소값에 넣어줍니다.
        List<DiarySelVo> list = mapper.getDiary(coupleId);
        // foreach는 이 때 list size는 등록된 일기의 목록만큼 저장됩니다.
        for (DiarySelVo vo : list) {
            // pics, hashs - 조회된 일기의 P.K 값으로 일기의 pics와 hashContents를 조회합니다.
            List<String> diaryPics = mapper.getDiaryPics(vo.getDiaryId());
            List<String> hashContents = mapper.getDiaryHash(vo.getDiaryId());
            // vo.setPics, vo.setHashContents - 조회된 정보들을 담아줍니다
            vo.setPics(diaryPics);
            vo.setHashContents(hashContents);
            //
        }
        // return 해줄 때의 list. 조회된 일기정보, 사진과 해시태그의 정보를 담아서 리턴해준다.
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
    ResVo delDiary(int diaryId) throws Exception {
        try {
            int delPic = mapper.delDiaryPics(diaryId);
            int delHash = mapper.delDiaryHashs(diaryId);
            int delDiary = mapper.delDiary(diaryId);
            return new ResVo(SUCCESS);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // 주영
    // 일기 수정
    public int updDiary(DiaryUpdDto dto) throws Exception {
        try {
            int updDiaryRows = mapper.updDiary(dto); // 1. diary 테이블 수정
            int updDiaryPicsRows = updDiaryItems(dto, PICS); // 2. diary_pics 테이블 수정
            int updDiaryHashRows = updDiaryItems(dto, HASH); // 3. diary_hash 테이블 수정
            return SUCCESS;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // 일기 사진/해시태그 수정
    public int updDiaryItems(DiaryUpdDto dto, String items) throws Exception {
        try {
            // 인자값(PICS 혹은 HASH)으로 아이템을 구분하고 삼항식으로 해당 아이템에 맞게 세팅한다.
            List<Integer> idList = items.equals(PICS) ? getPicsId(dto.getDiaryId()) : getHashId(dto.getDiaryId());
            // JSON에서 받아온 아이템의 개수
            int jsonCnt = items.equals(PICS) ? dto.getDiaryPics().size() : dto.getHashContents().size();
            // DB에 저장된 아이템의 개수
            int dbCnt = idList.size();

            if (dbCnt == jsonCnt) { // DB == JSON
                // DB에서 받아온 아이템과 JSON에서 받아온 아이템의 개수가 일치할 경우 전체 UPDATE를 실시한다.
                updDiaryItemsPart(dto, idList, jsonCnt, items);
            } else if (dbCnt > jsonCnt) { // DB > JSON
                if (jsonCnt == 0) {
                    // DB에 저장된 아이템이 있으나 수정된 아이템의 개수가 0일 때 모든 데이터를 삭제한다.
                    delDiaryItems(dto.getDiaryId(), items);
                } else {
                    // 단순히 DB에 저장된 아이템이 클 경우 더미 아이템을 삭제하고 첫번째 아이템부터 업데이트시킨다.
                    delDiaryItemsPart(jsonCnt, dbCnt, idList, items);
                    updDiaryItemsPart(dto, idList, jsonCnt, items);
                }
            } else { // DB < JSON
                if (dbCnt == 0) {
                    // DB에 저장된 아이템의 개수가 0이며 JSON에서 넘어온 아이템의 개수가 0보다 클 때 INSERT를 실행한다.
                    insDiaryItemsPart(0, dto, items);
                } else {
                    // DB에 저장된 아이템보다 JSON에서 넘어온 아이템의 개수가 더 클 경우 그 개수만큼 INSERT를 먼저 실행하고 이전의 아이템을 UPDATE 시킨다.
                    insDiaryItemsPart(dbCnt, dto, items);
                    updDiaryItemsPart(dto, idList, dbCnt, items);
                }
            }
            return SUCCESS;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // 사진, 해시태그 개별 등록
    public int insDiaryItemsPart(int min, DiaryUpdDto dto, String itemName) throws Exception {
        int max = itemName.equals(PICS) ? dto.getDiaryPics().size() : dto.getHashContents().size();

        try {
            if (itemName.equals(PICS)) {
                DiaryPicsInsDto picsDto = new DiaryPicsInsDto();
                picsDto.setDiaryId(dto.getDiaryId());
                for (int i = min; i < max; i++) {
                    picsDto.setPic(dto.getDiaryPics().get(i));
                    mapper.insDiaryPicPart(picsDto);
                }
            } else {
                DiaryHashInsDto hashDto = new DiaryHashInsDto();
                hashDto.setDiaryId(dto.getDiaryId());
                for (int i = min; i < max; i++) {
                    hashDto.setHash(dto.getHashContents().get(i));
                    mapper.insDiaryHashPart(hashDto);
                }
            }
            return SUCCESS;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // 사진, 해시태그 개별 수정
    public int updDiaryItemsPart(DiaryUpdDto dto, List<Integer> getItemsId, int length, String items) throws Exception {
        try {
            if (items.equals(PICS)) {
                for (int i = 0; i < length; i++) {
                    dto.setPic(dto.getDiaryPics().get(i));
                    dto.setPicsId(getItemsId.get(i));
                    mapper.updDiaryPics(dto);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    dto.setHashId(getItemsId.get(i));
                    dto.setHash(dto.getHashContents().get(i));
                    mapper.updDiaryHash(dto);
                }
            }
            return SUCCESS;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // 사진, 해시태그 전체 삭제
    public int delDiaryItems(int diaryId, String items) {
        return items.equals(PICS) ? mapper.delDiaryPics(diaryId) : mapper.delDiaryHashs(diaryId);
    }

    // 사진, 해시태그 개별 삭제
    public int delDiaryItemsPart(int min, int max, List<Integer> idList, String itemName) throws Exception {
        try {
            if (itemName.equals(PICS)) {
                for (int i = min; i < max; i++) {
                    mapper.delDiaryPic(idList.get(i));
                }
            } else {
                for (int i = min; i < max; i++) {
                    mapper.delDiaryHash(idList.get(i));
                }
            }
            return SUCCESS;
        } catch (Exception e) {
            throw new Exception();
        }
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