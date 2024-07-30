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

    @PostMapping(value = "/apps/{userId}", consumes = "multipart/form-data")
    public DataResponseDto<ApplicationResponseDto.ApplicationCreateResponseDto> applicationCreate(
            @PathVariable Long userId,
            @ModelAttribute @Valid ApplicationRequestDto.ApplicationCreateRequestDto applicationCreateRequestDto) {

        Application application = applicationService.createApplication(applicationCreateRequestDto);
        Long applicationId = application.getApplicationId();

        ApplicationResponseDto.ApplicationCreateResponseDto responseDto = ApplicationConverter.toApplicationCreateResponseDto(applicationId);

        return DataResponseDto.of(responseDto, "신청서를 작성했습니다.");
    }

    @GetMapping("/apps/{userId}/sent")
    public DataResponseDto<Page<ApplicationResponseDto.ApplicationSentResponseDto>> getSentApplications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "2000-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "latest") String sort){
        Sort sorting = sort.equals("latest") ? Sort.by(Sort.Direction.DESC, "createdAt") : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Application> applicationPage = applicationService.getSentApplications(userId, startDate, endDate, pageable);

        List<ApplicationResponseDto.ApplicationSentResponseDto> applicationSentResponseDtoList = ApplicationConverter.toApplicationSentResponseDtoList(applicationPage.getContent());
        Page<ApplicationResponseDto.ApplicationSentResponseDto> applicationSentResponseDtoPage = new PageImpl<>(applicationSentResponseDtoList, pageable, applicationPage.getTotalElements());
        return DataResponseDto.of(applicationSentResponseDtoPage, "보낸 신청서를 조회했습니다.");
    }

    @GetMapping("/apps/{userId}/received")
    public DataResponseDto<ApplicationResponseDto.ApplicationReceivedResponseDto> getReceivedApplications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "2000-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "latest") String sort){
        Sort sorting = sort.equals("latest") ? Sort.by(Sort.Direction.DESC, "createdAt") : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<Tutor> tutors = applicationService.getTutorsWithApplications(userId);

        List<ApplicationResponseDto.TutorProfileDto> tutorProfiles = ApplicationConverter.toTutorProfileDtoList(tutors, applicationService.getReceivedApplications(userId, startDate, endDate, pageable).getContent());

        ApplicationResponseDto.ApplicationReceivedResponseDto responseDto = ApplicationResponseDto.ApplicationReceivedResponseDto.builder()
                .tutorProfiles(tutorProfiles)
                .build();

        return DataResponseDto.of(responseDto, "받은 신청서 목록입니다.");
    }

    @GetMapping("/apps/delete/{userId}")
    public DataResponseDto<ApplicationResponseDto.ApplicationCreateResponseDto> applicationDelete(
            @PathVariable Long userId) {
        Application application = applicationService.getApplication(userId);

        Long applicationId = application.getApplicationId();
        ApplicationResponseDto.ApplicationCreateResponseDto responseDto = ApplicationConverter.toApplicationCreateResponseDto(applicationId);

        applicationService.delete(application);

        return DataResponseDto.of(responseDto, "신청서를 삭제했습니다.");
    }
}
