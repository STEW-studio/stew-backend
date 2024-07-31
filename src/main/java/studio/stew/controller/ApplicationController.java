package studio.stew.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.aws.AwsS3Service;
import studio.stew.converter.ApplicationConverter;
import studio.stew.domain.Application;
import studio.stew.domain.Tutor;
import studio.stew.dto.ApplicationRequestDto;
import studio.stew.dto.ApplicationResponseDto;
import studio.stew.response.DataResponseDto;
import studio.stew.service.ApplicationService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ApplicationController {

    private final ApplicationService applicationService;
    private final AwsS3Service awsS3Service;

    @PostMapping(value = "/apps/{userId}/{tutorId}", consumes = "multipart/form-data")
    public DataResponseDto<ApplicationResponseDto.ApplicationCreateResponseDto> applicationCreate(
            @PathVariable Long userId,
            @PathVariable Long tutorId,
            @ModelAttribute @Valid ApplicationRequestDto.ApplicationCreateRequestDto applicationCreateRequestDto) {

        Application application = applicationService.createApplication(applicationCreateRequestDto, userId, tutorId);
        Long applicationId = application.getApplicationId();

        ApplicationResponseDto.ApplicationCreateResponseDto responseDto = ApplicationConverter.toApplicationCreateResponseDto(applicationId);

        return DataResponseDto.of(responseDto, "신청서를 작성했습니다.");
    }

    @GetMapping("/apps/{userId}/sent")
    public DataResponseDto<ApplicationResponseDto.ApplicationSentResponseListDto> getSentApplications(
            @PathVariable Long userId,
            @RequestParam(name = "page") Integer page){
        Page<Application> response = applicationService.getSentApplicationList(userId, page-1);

        return DataResponseDto.of(ApplicationConverter.toApplicationSentResponseDtoList(response),"보낸 신청서 목록입니다.");
    }

    @GetMapping("/apps/{userId}/received")
    public DataResponseDto<ApplicationResponseDto.ApplicationReceivedResponseDto> getReceivedApplications(
            @PathVariable Long userId,
            @RequestParam(name = "page") Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 5);
        Page<Application> applications = applicationService.getReceivedApplications(userId, pageRequest);

        List<Tutor> tutors = applicationService.getTutorsWithApplications(userId);
        List<ApplicationResponseDto.TutorProfileDto> tutorProfiles = ApplicationConverter.toTutorProfileDtoList(tutors, applicationService);

        ApplicationResponseDto.ApplicationReceivedResponseDto responseDto = ApplicationResponseDto.ApplicationReceivedResponseDto.builder()
                .tutorProfiles(tutorProfiles)
                .listSize(applications.getSize())
                .totalPage(applications.getTotalPages())
                .totalElements(applications.getTotalElements())
                .isFirst(applications.isFirst())
                .isLast(applications.isLast())
                .build();

        return DataResponseDto.of(responseDto, "받은 신청서 목록입니다.");
    }

    @GetMapping("/apps/delete/{appId}")
    public DataResponseDto<ApplicationResponseDto.ApplicationCreateResponseDto> applicationDelete(
            @PathVariable Long appId) {
        Application application = applicationService.getApplication(appId);

        ApplicationResponseDto.ApplicationCreateResponseDto responseDto = ApplicationConverter.toApplicationCreateResponseDto(appId);

        applicationService.delete(application);

        return DataResponseDto.of(responseDto, "신청서를 삭제했습니다.");
    }
}
