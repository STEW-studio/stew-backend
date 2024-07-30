package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    }

    @Getter
    @Setter
    @Builder
    public static class ApplicationReceivedResponseDto {
        private List<TutorProfileDto> tutorProfiles;
    }

    @Getter
    @Setter
    @Builder
    public static class TutorProfileDto {
        @JsonProperty("img_url")
        private String imgUrl;

        private String intro;

        @JsonProperty("created_at")
        private LocalDateTime createdAt;

        private List<ApplicationSummaryDto> applications;
    }

    @Getter
    @Setter
    @Builder
    public static class ApplicationSummaryDto {
        @JsonProperty("user_name")
        private String userName;
        private String title;
        private boolean status;
        private LocalDateTime createdAt;
    }
}
