package studio.stew.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.aws.AwsS3Service;
import studio.stew.converter.TutorConverter;
import studio.stew.domain.Tutor;
import studio.stew.dto.TutorRequestDto;
import studio.stew.dto.TutorResponseDto;
import studio.stew.response.DataResponseDto;
import studio.stew.service.TutorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tutors")
public class TutorController {
    private final TutorService tutorService;
    private final AwsS3Service awsS3Service;
    private final TutorConverter tutorConverter;
    @PostMapping(value = "/{userId}", consumes = "multipart/form-data")
    public DataResponseDto<TutorResponseDto.TutorCreateResponseDto> createTutor(
            @RequestPart TutorRequestDto.TutorCreateRequestDto requestDto,
            @RequestPart(required = false)List<MultipartFile> portfolio,
            @PathVariable(name="userId") Long userId,
            @RequestPart(required = false)MultipartFile profile) {
        Long tutorId = tutorService.createTutor(userId, requestDto, portfolio, profile);
        TutorResponseDto.TutorCreateResponseDto responseDto = TutorConverter.toTutorCreateResponseDto(tutorId);
        return DataResponseDto.of(responseDto, "튜터가 생성되었습니다.");
    }
    @PatchMapping(value = "/{tutorId}", consumes = "multipart/form-data")
    public DataResponseDto<TutorResponseDto.TutorUpdateResponseDto> updateTutor(
            @RequestPart TutorRequestDto.TutorUpdateRequestDto requestDto,
            @RequestPart(required = false)List<MultipartFile> newPortfolio,
            @PathVariable(name="tutorId") Long tutorId,
            @RequestPart(required = false)MultipartFile newProfile) {
        Long updatedTutorId = tutorService.updateTutor(tutorId, requestDto, newPortfolio, newProfile);
        TutorResponseDto.TutorUpdateResponseDto responseDto = TutorConverter.toTutorUpdateResponseDto(tutorId);
        return DataResponseDto.of(responseDto, "튜터가 수정되었습니다.");
    }

    @DeleteMapping(value = "/{tutorId}")
    public DataResponseDto deleteTutor(@PathVariable(name="tutorId") Long tutorId) {
        tutorService.deleteTutor(tutorId);
        return DataResponseDto.of(null,"tutor_id: "+tutorId+" 튜터가 삭제되었습니다");
    }
    @GetMapping(value="/{tutorId}")
    public DataResponseDto<TutorResponseDto.TutorDetailDto> getTutorDetail(@PathVariable Long tutorId) {
        TutorResponseDto.TutorDetailDto response = tutorService.getTutorDetail(tutorId);
        return DataResponseDto.of(response,"튜터 상세보기를 완료했습니다.");
    }
    @GetMapping(value="/today-tutor")
    public DataResponseDto<List<TutorResponseDto.TodayTutorDto>> getTodayTutors() {
        return DataResponseDto.of(tutorService.todayTutorService(),"오늘의 튜터를 조회했습니다.");
    }
    @Operation(summary = "전체 튜터 조회 API",description = "전체 튜터 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @GetMapping
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다."),
    })
    public DataResponseDto<TutorResponseDto.TutorPreviewListDto> getAllTutorList(@RequestParam(name = "page") Integer page) {
        Page<Tutor> response = tutorService.getAllTutorList(page-1);
        return DataResponseDto.of(tutorConverter.toTutorPreviewListDto(response),"전체 튜터 목록을 조회했습니다.");
    }
}
