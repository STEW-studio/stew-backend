package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import studio.stew.domain.enums.Gender;

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
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TutorDetailDto {
        String imgUrl;
        String name;
        Gender gender;
        Integer age;
        String location;
        @JsonProperty("sports_id")
        Long sportsId;
        Long price;
        String career;
        String intro;
        @JsonProperty("self_intro")
        String selfIntro;
        @JsonProperty("sports_intro")
        String sportsIntro;
        List<String> portfolio;
        @JsonProperty("total_review_score")
        Float totalScore;
        @JsonProperty("total_review_count")
        Integer reviewCount;
        TutorReviewDto reviewDto;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TutorReviewDto {
        @JsonProperty("review_score")
        Float reviewScore;
        @JsonProperty("reviewer_name")
        String reviewer;
        @JsonProperty("reviewer_location")
        String reviewLocation;
        @JsonProperty("reviewer_profile")
        String reviewProfile;
        @JsonProperty("review_content")
        String reviewContent;
        @JsonProperty("reviewer_age")
        Long reviewAge;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodayTutorDto {
        Long tutorId;
        String imgUrl;
        String name;
        String sportName;
        String location;
        String career;
        String intro;
        Long price;
    }
}
