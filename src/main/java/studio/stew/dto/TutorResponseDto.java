package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class TutorResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TutorCreateResponseDto {
        @JsonProperty("tutor_id")
        Long tutorId;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TutorUpdateResponseDto {
        @JsonProperty("tutor_id")
        Long tutorId;
        LocalDateTime updatedAt;
    }
}
