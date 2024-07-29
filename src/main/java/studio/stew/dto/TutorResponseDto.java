package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TutorPreviewListDto {
        List<TutorPreviewDto> tutorList;
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
    public static class TutorPreviewDto {
        String imgUrl;
        String name;
        String sportName;
        String location;
        String career;
        String intro;
        Float score;
        Integer reviewCount;
        Long price;
    }
}
