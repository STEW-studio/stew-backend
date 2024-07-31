package studio.stew.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studio.stew.converter.SportsConverter;
import studio.stew.domain.Sports;
import studio.stew.dto.SportsResponseDto;
import studio.stew.response.DataResponseDto;
import studio.stew.service.SportsService;

import java.util.List;

@RequestMapping("/api/sports")
@RequiredArgsConstructor
@RestController
public class SportsController {

    private final SportsService sportsService;

    @GetMapping
    public DataResponseDto<SportsResponseDto.SportsNameIdResponseListDto> sportsSearch(
            @RequestParam String kw){
        List<Sports> searchedList = sportsService.getList(kw);
        SportsResponseDto.SportsNameIdResponseListDto responseDto = SportsConverter.toSportsNameIdResponseListDto(searchedList);

        return DataResponseDto.of(responseDto,"운동 리스트를 검색했습니다.");
    }

}
