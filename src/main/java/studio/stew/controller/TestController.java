package studio.stew.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studio.stew.dto.TutorResponseDto;
import studio.stew.response.DataResponseDto;
import studio.stew.service.TutorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TutorService tutorService;

    @GetMapping("/tutors")
    public DataResponseDto<List<TutorResponseDto.TutorPreviewDto>> getTutorsBySports(
            @RequestParam Long sportsId1,
            @RequestParam Long sportsId2) {

        List<TutorResponseDto.TutorPreviewDto> tutorList = tutorService.getRandomTutorsBySports(sportsId1, sportsId2);
        return DataResponseDto.of(tutorList, "2개의 튜터 프로필을 조회했습니다.");
    }
}
