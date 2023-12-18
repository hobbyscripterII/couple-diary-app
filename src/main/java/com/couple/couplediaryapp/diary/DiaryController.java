package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.diary.model.DiaryInsDto;
import com.couple.couplediaryapp.diary.model.DiarySelVo;
import com.couple.couplediaryapp.diary.model.DiaryUpdDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService service;

    public Integer getCoupleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.COUPLE_ID)));
    }

    public Integer getUserId (HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Integer) session.getAttribute(SessionConst.USER_ID);
    }

    // 일기 등록
    @PostMapping
    @Operation(summary = "일기 등록", description = "일기 등록")
    public ResVo insDiary (@RequestBody DiaryInsDto dto, HttpServletRequest request){
        dto.setCuser(getUserId(request));
        dto.setCcouple(getCoupleId(request));
        return service.insDiary(dto);
    }

    // 일기 읽기
    @GetMapping("/{diaryId}")
    @Operation(summary = "일기 읽기", description = "일기 읽기")
    DiarySelVo selDiary (@PathVariable int diaryId) {
        return service.selDiary(diaryId);
    }

    // 일기 목록
    @GetMapping
    @Operation(summary = "일기 목록", description = "일기 목록 출력")
    public List<DiarySelVo> getDiary(HttpServletRequest request){
        return service.getDiary(getCoupleId(request));
    }

    // 일기 수정
    @Operation(summary = "일기 수정", description = "일기 수정 기능")
    @PatchMapping
    int updDiary(@RequestBody DiaryUpdDto dto, HttpServletRequest request) {
        dto.setCoupleId(getCoupleId(request));
        return service.updDiary(dto);
    }


}
