package studio.stew.converter;

import studio.stew.domain.Application;
import studio.stew.domain.Tutor;
import studio.stew.dto.ApplicationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationConverter {
    public static ApplicationResponseDto.ApplicationCreateResponseDto toApplicationCreateResponseDto(Long applicationId) {
        return ApplicationResponseDto.ApplicationCreateResponseDto.builder()
                .applicationId(applicationId)
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

    public static List<ApplicationResponseDto.ApplicationSentResponseDto> toApplicationSentResponseDtoList(List<Application> applications) {
        return applications.stream()
                .map(ApplicationConverter::toApplicationSentResponseDto)
                .collect(Collectors.toList());
    }

    public static ApplicationResponseDto.ApplicationSummaryDto toApplicationSummaryDto(Application application) {
        return ApplicationResponseDto.ApplicationSummaryDto.builder()
                .userName(application.getUser().getName())
                .title(application.getTitle())
                .status(application.isStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }

    public static List<ApplicationResponseDto.ApplicationSummaryDto> toApplicationSummaryDtoList(List<Application> applications) {
        return applications.stream()
                .map(ApplicationConverter::toApplicationSummaryDto)
                .collect(Collectors.toList());
    }

    public static ApplicationResponseDto.TutorProfileDto toTutorProfileDto(Tutor tutor, List<Application> applications) {
        return ApplicationResponseDto.TutorProfileDto.builder()
                .imgUrl(tutor.getImgUrl())
                .intro(tutor.getIntro())
                .createdAt(tutor.getCreatedAt())
                .applications(toApplicationSummaryDtoList(applications))
                .build();
    }

    public static List<ApplicationResponseDto.TutorProfileDto> toTutorProfileDtoList(List<Application> applications) {
        return applications.stream()
                .collect(Collectors.groupingBy(Application::getTutor))
                .entrySet()
                .stream()
                .map(entry -> toTutorProfileDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
