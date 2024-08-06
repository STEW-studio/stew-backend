package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.domain.enums.Gender;

import java.util.List;

public class TutorRequestDto {
    @Getter
    @Setter
    public static class TutorCreateRequestDto {
        @NotNull
        String name;
        @NotNull
        Gender gender;
        @NotNull
        Integer age;
        @NotNull
        String location;
        @NotNull
        @JsonProperty("sports_id")
        Long sportsId;
        @NotNull
        Long price;
        @NotNull
        String career;
        @NotNull
        String intro;
        @NotNull
        @JsonProperty("self_intro")
        String selfIntro;
        @NotNull
        @JsonProperty("sports_intro")
        String sportsIntro;

        @NotNull
        List<MultipartFile> portfolio;

        MultipartFile profile;

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

