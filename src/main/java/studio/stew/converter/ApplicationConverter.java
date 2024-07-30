package studio.stew.converter;

import studio.stew.domain.Application;
import studio.stew.dto.ApplicationResponseDto;

public class ApplicationConverter {
    public static ApplicationResponseDto.ApplicationCreateResponseDto toApplicationCreateResponseDto(Long applicationId) {
        return ApplicationResponseDto.ApplicationCreateResponseDto.builder()
                .applicationId(applicationId)
                .build();
    }
}
