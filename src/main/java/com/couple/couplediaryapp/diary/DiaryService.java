package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
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

    // 주영
    // 일기 수정
    int updDiary(DiaryUpdDto dto) {
        int result = Const.FAIL;

        try {
            int updDiaryRows = mapper.updDiary(dto); // 일기 수정
            int updDiaryPicsRows = updDiaryItems(Const.PICS, dto); // 일기 사진 수정
            int updDiaryHashPows = updDiaryItems(Const.HASH, dto); // 일기 해시태그 수정
            result = Const.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public int updDiaryItems(String itemName, DiaryUpdDto dto) {
        List<Integer> getItemsId = itemName.equals(Const.PICS) ? getPicsId(dto.getDiaryId()) : getHashId(dto.getDiaryId());
        int savedItemsCnt = getItemsId.size();
        int updItemsCnt = itemName.equals(Const.PICS) ? dto.getDiaryPics().size() : dto.getHashContents().size();
        int result = Const.FAIL;

        try {
            // 회원이 등록했던 아이템을 모두 삭제했을 때
            if (savedItemsCnt != 0 && updItemsCnt == 0) {
                return itemName.equals(Const.PICS) ? mapper.delDiaryPics(dto.getDiaryId()) : mapper.delDiaryHashs(dto.getDiaryId());
            }
            // 개수 변경없이 내용만 변경했을 때
            if (savedItemsCnt == updItemsCnt) {
                return result = itemName.equals(Const.PICS) ? updDiaryPic(dto, getItemsId) : updDiaryHash(dto, getItemsId);
            }
            // 등록한 아이템이 없었으나 사용자가 새로 추가했을 때
            if (savedItemsCnt == 0 && updItemsCnt > 1) {
                DiaryInsDto insDto = new DiaryInsDto();
                insDto.setDiaryId(dto.getDiaryId());
                insDto.setPics(dto.getDiaryPics());
                insDto.setHashContents(dto.getHashContents());
                if (itemName.equals(Const.PICS)) {
                    return mapper.insDiaryPics(insDto);
                } else {
                    return mapper.insDiaryHash(insDto);
                }
            }
            // 저장된 아이템 개수가 수정된 아이템 개수보다 많을 때
            if (savedItemsCnt > updItemsCnt) {
                for (int i = updItemsCnt; i < savedItemsCnt; i++) {
                    if (itemName.equals(Const.PICS)) {
                        mapper.delDiaryPic(getItemsId.get(i));
                    } else {
                        mapper.delDiaryHash(getItemsId.get(i));
                    }
                }
                if (itemName.equals(Const.PICS)) {
                    updDiaryPic(dto, getItemsId);
                } else {
                    updDiaryHash(dto, getItemsId);
                }
            }
            // 저장된 아이템 개수가 수정된 아이템 개수보다 적을 때
            if (savedItemsCnt < updItemsCnt) {
                for (int i = savedItemsCnt; i < updItemsCnt; i++) {
                    if (itemName.equals(Const.PICS)) {
                        mapper.insDiaryPicPart(new DiaryPicsInsDto(dto.getDiaryId(), dto.getDiaryPics().get(i)));
                    } else {
                        mapper.insDiaryHashPart(new DiaryHashInsDto(dto.getDiaryId(), dto.getHashContents().get(i)));
                    }
                }
                result = SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 사진 개별 수정
    public int updDiaryPic(DiaryUpdDto dto, List<Integer> getPicsId) {
        int savedPicsCnt = getPicsId.size();
        int updPicsCnt = dto.getDiaryPics().size();
        int length = savedPicsCnt > updPicsCnt ? (savedPicsCnt - updPicsCnt) : savedPicsCnt;
        int result = 0;

        for (int i = 0; i < length; i++) {
            dto.setPicsId(getPicsId.get(i));
            dto.setPic(dto.getDiaryPics().get(i));
            result += mapper.updDiaryPics(dto);
        }
        return length == result ? 1 : 0;
    }

    // 해시태그 개별 수정
    public int updDiaryHash(DiaryUpdDto dto, List<Integer> getHashId) {
        int savedHashCnt = getHashId.size();
        int updHashCnt = dto.getDiaryPics().size();
        int length = savedHashCnt > updHashCnt ? (savedHashCnt - updHashCnt) : savedHashCnt;
        int result = 0;

        for (int i = 0; i < length; i++) {
            dto.setHashId(getHashId.get(i));
            dto.setHash(dto.getHashContents().get(i));
            result += mapper.updDiaryHash(dto);
        }
        return length == result ? 1 : 0;
    }

    // picsId 가져오기(couple_pics 테이블의 PK)
    public List<Integer> getPicsId(int diaryId) {
        List<Integer> getPicsId = mapper.getPicsId(diaryId);
        return getPicsId;
    }

    // HashId 가져오기(couple_hash 테이블의 PK)
    public List<Integer> getHashId(int diaryId) {
        List<Integer> getHashId = mapper.getHashId(diaryId);
        return getHashId;
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
}
