package com.couple.couplediaryapp.diary;

import com.couple.couplediaryapp.common.ResVo;
import com.couple.couplediaryapp.common.SessionConst;
import com.couple.couplediaryapp.common.Utils;
import com.couple.couplediaryapp.diary.model.DiaryInsDto;
import com.couple.couplediaryapp.diary.model.DiarySelVo;
import com.couple.couplediaryapp.diary.model.DiaryUpdDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.couple.couplediaryapp.common.Const.FAIL;

@Slf4j
@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService service;

    public Integer getCoupleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.valueOf(String.valueOf(session.getAttribute(SessionConst.COUPLE_ID)));
    }

    public Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Integer) session.getAttribute(SessionConst.USER_ID);
    }

    // 일기 등록
    @PostMapping
    @Operation(summary = "일기 등록", description = "일기 등록")
    public ResVo insDiary(@RequestBody DiaryInsDto dto, HttpServletRequest request) throws Exception {
        dto.setCuser(getUserId(request));
        dto.setCcouple(getCoupleId(request));
        return service.insDiary(dto);
    }

    // 일기 읽기
    @GetMapping("/{diaryId}")
    @Operation(summary = "일기 읽기", description = "일기 읽기")
    DiarySelVo selDiary(@PathVariable int diaryId) throws Exception {
        return service.selDiary(diaryId);
    }

    // 일기 목록
    @GetMapping
    @Operation(summary = "일기 목록", description = "일기 목록 출력")
    public List<DiarySelVo> getDiary(HttpServletRequest request) throws Exception {
        //
        int coupleId = getCoupleId(request);
        // 로그인 했을 때 그 유저가 어디 커플인지를 알기위해 P.K를 얻어옵니다.
        try {
            if( Utils.isNotNull(coupleId) ){
                // 들어오는 커플
                return service.getDiary(coupleId);
            } else {
                // null 혹은 0이 발견될 경우 예외를 던진다.
                throw new NullPointerException();
            }
        }catch (Exception e){
            // 예외 발생 시 예외를 던진다.
            throw new Exception();
        }
        //
    }

    // 일기 수정
    @Operation(summary = "일기 수정", description = "일기 수정")
    @PatchMapping
    ResVo updDiary(@RequestBody DiaryUpdDto dto, HttpServletRequest request) throws Exception {
        // 커플 id(pk)를 얻어온다.
        int coupleId = getCoupleId(request);
        try {
            // dto 값이 null이 아니거나 커플 id가 0이 아닐 경우 아래 문장을 실행한다.
            if (Utils.isNotNull(dto) || Utils.isNotNull(coupleId)) {
                // dto에 커플 id를 넣는다.
                dto.setCoupleId(coupleId);
                // 일기 수정 메소드를 호출한다.
                return new ResVo(service.updDiary(dto));
            } else {
                // null 혹은 0이 발견될 경우 예외를 던진다.
                throw new NullPointerException();
            }
        } catch (Exception e) {
            // 예외 발생시 예외를 던진다.
            throw new Exception();
        }
    }

    // 일기 삭제
    @DeleteMapping("/{diaryId}")
    @Operation(summary = "일기 삭제", description = "일기 삭제")
    ResVo delDiary(@PathVariable int diaryId) throws Exception {
        return service.delDiary(diaryId);
    }
}
