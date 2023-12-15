package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.diary.model.DiarySelVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService service;
    //
    public Integer getCoupleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.COUPLE_ID)));
    }
    //
    @GetMapping
    public List<DiarySelVo> getDiary(HttpServletRequest request){
        //
        return service.getDiary(getCoupleId(request));
    }
}
