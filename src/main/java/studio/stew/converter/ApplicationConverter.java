package studio.stew.converter;

import org.springframework.data.domain.Page;
import studio.stew.domain.Application;
import studio.stew.domain.Tutor;
import studio.stew.dto.ApplicationResponseDto;
import studio.stew.service.ApplicationService;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationConverter {
    public static ApplicationResponseDto.ApplicationCreateResponseDto toApplicationCreateResponseDto(Long applicationId) {
        return ApplicationResponseDto.ApplicationCreateResponseDto.builder()
                .applicationId(applicationId)
                .build();
    }

    public static ApplicationResponseDto.ApplicationStatusUpdateDto toApplicationStatusUpdateDto() {
        return ApplicationResponseDto.ApplicationStatusUpdateDto.builder()
                .status(true)
                .build();
    }

    public static ApplicationResponseDto.ApplicationSentResponseDto toApplicationSentResponseDto(Application application) {
        return ApplicationResponseDto.ApplicationSentResponseDto.builder()
                .imgUrl(application.getTutor().getImgUrl()) // 튜터 imgurl 가져와야함
                .tutorName(application.getTutor().getName()) // 튜터에 이름 가져와야함 ===> 여기까지 다 튜터 객체가 필요
                .userName(application.getUser().getName()) // 유저에 가서 이름 가져와야함
                .title(application.getTitle())
                .status(application.isStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }

    public static ApplicationResponseDto.ApplicationSentResponseListDto toApplicationSentResponseDtoList(Page<Application> applicationList) {
        List<ApplicationResponseDto.ApplicationSentResponseDto> applicationSentResponseDtoList = applicationList.stream()
                .map(ApplicationConverter::toApplicationSentResponseDto)
                .collect(Collectors.toList());

        return ApplicationResponseDto.ApplicationSentResponseListDto.builder()
                .applicationList(applicationSentResponseDtoList)
                .isFirst(applicationList.isFirst())
                .isLast(applicationList.isLast())
                .totalElements(applicationList.getTotalElements())
                .totalPage(applicationList.getTotalPages())
                .listSize(applicationList.getSize())
                .build();
    }

    public static ApplicationResponseDto.ApplicationInfoDto toApplicationInfoDto(Application application) {
        return ApplicationResponseDto.ApplicationInfoDto.builder()
                .userName(application.getUser().getName())
                .title(application.getTitle())
                .status(application.isStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }

    public static ApplicationResponseDto.TutorProfileDto toTutorProfileDto(Tutor tutor, List<Application> applications) {
        List<ApplicationResponseDto.ApplicationInfoDto> applicationInfoDtoList = applications.stream()
                .map(ApplicationConverter::toApplicationInfoDto)
                .collect(Collectors.toList());

        return ApplicationResponseDto.TutorProfileDto.builder()
                .imgUrl(tutor.getImgUrl())
                .intro(tutor.getIntro())
                .createdAt(tutor.getCreatedAt())
                .applicationList(applicationInfoDtoList)
                .build();
    }

    public static List<ApplicationResponseDto.TutorProfileDto> toTutorProfileDtoList(List<Tutor> tutors, ApplicationService applicationService) {
        return tutors.stream()
                .map(tutor -> toTutorProfileDto(tutor, applicationService.getReceivedApplicationsByTutor(tutor)))
                .collect(Collectors.toList());
    }
}
