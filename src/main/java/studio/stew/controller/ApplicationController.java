package studio.stew.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.aws.AwsS3Service;
import studio.stew.converter.ApplicationConverter;
import studio.stew.domain.Application;
import studio.stew.dto.ApplicationRequestDto;
import studio.stew.dto.ApplicationResponseDto;
import studio.stew.response.DataResponseDto;
import studio.stew.service.ApplicationService;

import java.time.LocalDate;

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
    public Page<Application> getSentApplications(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "latest") String sort){
        Sort sorting = sort.equals("latest") ? Sort.by(Sort.Direction.DESC, "createdAt") : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sorting);
        return applicationService.getSentApplications(userId, startDate, endDate, pageable);
    }

    @GetMapping("/apps/{userId}/received")
    public Page<Application> getReceivedApplications(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "latest") String sort){
        Sort sorting = sort.equals("latest") ? Sort.by(Sort.Direction.DESC, "createdAt") : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sorting);
        return applicationService.getReceivedApplications(userId, startDate, endDate, pageable);
    }

    @GetMapping("/apps/delete/{userId}")
    public void applicationDelete(
            @PathVariable Long userId) {
        Application application = applicationService.getApplication(userId);
        applicationService.delete(application);
    }
}
