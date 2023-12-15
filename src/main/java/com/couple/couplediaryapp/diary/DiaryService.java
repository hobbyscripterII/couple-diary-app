package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.diary.model.DiaryInsDto;
import com.couple.couplediaryapp.diary.model.DiarySelVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.couple.couplediaryapp.common.Const.FAIL;
import static com.couple.couplediaryapp.common.Const.SUCCESS;

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
    public List<DiarySelVo> getDiary(int ccouple) {
        //
        List<DiarySelVo> list = mapper.getDiary(ccouple);
        for (DiarySelVo vo : list ) {
            List<String> pics = mapper.getDiaryPics(vo.getDiaryId());
            List<String> hashs = mapper.getDiaryHash(vo.getDiaryId());
            vo.setPics(pics);
            vo.setHashContents(hashs);
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
}
