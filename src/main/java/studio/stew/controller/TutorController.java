package studio.stew.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.aws.AwsS3Service;
import studio.stew.converter.TutorConverter;
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
    @PostMapping(consumes = "multipart/form-data")
    public DataResponseDto<TutorResponseDto.TutorCreateResponseDto> createTutor(
            @RequestPart TutorRequestDto.TutorCreateRequestDto requestDto,
            @RequestPart(required = false)List<MultipartFile> portfolio,
            @RequestParam(name="userId") Long userId,
            @RequestPart(required = false)MultipartFile profile) {
        String imgUrl = awsS3Service.uploadFile(profile);
        Long tutorId = tutorService.createTutor(userId, requestDto, portfolio, imgUrl);
        TutorResponseDto.TutorCreateResponseDto responseDto = TutorConverter.toTutorCreateResponseDto(tutorId);
        return DataResponseDto.of(responseDto, "튜터가 생성되었습니다.");
    }

}
