package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.domain.enums.Gender;

import java.util.List;

public class TutorRequestDto {
    @Getter
    @Setter
    public static class TutorCreateRequestDto {
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
        MultipartFile profile;
        List<MultipartFile> portfolio;

    }
    @Getter
    public static class TutorUpdateRequestDto {
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
    }
}

