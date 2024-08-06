package studio.stew.dto;

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
        String gender;
        Integer age;
        String location;
        Long sportsid;
        Long price;
        String career;
        String intro;
        String selfintro;
        String sportsintro;
        MultipartFile profile;
        List<MultipartFile> portfolio;

    }
    @Getter
    public static class TutorUpdateRequestDto {
        String name;
        Gender gender;
        Integer age;
        String location;

        Long sportsId;

        Long price;

        String career;

        String intro;

        String selfIntro;

        String sportsIntro;
    }
}

