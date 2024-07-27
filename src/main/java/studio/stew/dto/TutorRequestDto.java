package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import studio.stew.domain.enums.Gender;

public class TutorRequestDto {
    @Getter
    @Setter
    public static class TutorCreateRequestDto {
        @NotNull
        String name;
        @NotNull
        Gender gender;
        @NotNull
        int age;
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

    }
}

