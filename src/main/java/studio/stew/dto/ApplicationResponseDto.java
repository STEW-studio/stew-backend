package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import studio.stew.domain.enums.Gender;

import java.time.LocalDateTime;
import java.util.List;

public class ApplicationResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationCreateResponseDto {
        @JsonProperty("application_id")
        Long applicationId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationSentResponseListDto {
        List<ApplicationResponseDto.ApplicationSentResponseDto> applicationList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationSentResponseDto {
        @JsonProperty("img_url")
        String imgUrl;

        @JsonProperty("tutor_name")
        String tutorName;

        @JsonProperty("user_name")
        String userName;

        String title;

        boolean status;

        @JsonProperty("created_at")
        LocalDateTime createdAt;

        @JsonProperty("application_id")
        Long applicationId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationReceivedResponseDto {
        private List<TutorProfileDto> tutorProfiles;
        private int listSize;
        private int totalPage;
        private long totalElements;
        private boolean isFirst;
        private boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TutorProfileDto {
        @JsonProperty("img_url")
        private String imgUrl;

        private String intro;

        @JsonProperty("created_at")
        private LocalDateTime createdAt;

        private List<ApplicationInfoDto> applicationList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationInfoDto {
        @JsonProperty("user_name")
        private String userName;
        private String title;
        private boolean status;
        private LocalDateTime createdAt;

        @JsonProperty("application_id")
        private Long applicationId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationStatusUpdateDto{
        boolean status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationDetailDto{
//        @JsonProperty("tutor_id")
//        Long tutorId;
        @JsonProperty("tutor_img")
        String tutorImg;
        @JsonProperty("tutor_name")
        String tutorName;
        @JsonProperty("sport_name")
        String sportName;
        String intro;
        Float score;
        Integer reviewCount;
        Long price;

        @JsonProperty("user_img")
        String userImg;
        String title;
        String userName;
        Gender gender;
        Integer age;
        String location;
        List<String> purpose;
        int intensity;
        String memo;
    }
}
