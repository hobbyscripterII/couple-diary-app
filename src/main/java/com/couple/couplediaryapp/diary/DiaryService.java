package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.diary.model.DiarySelVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryMapper mapper;

    public List<DiarySelVo> getDiary(int ccouple) {
        //
        List<DiarySelVo> list = mapper.getDiary(ccouple);
        for (DiarySelVo vo : list ) {
            List<String> pics = mapper.getDiaryPics(vo.getDiaryId());
            List<String> hashs = mapper.getDiaryHash(vo.getDiaryId());
            vo.setPics(pics);
            vo.setHashs(hashs);
        }
        return list;
    }
}
