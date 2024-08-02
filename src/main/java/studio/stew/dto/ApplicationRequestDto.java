package studio.stew.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ApplicationRequestDto {

    @Getter
    @Setter
    public static class ApplicationCreateRequestDto{
        @NotNull
        private String title;

        private MultipartFile imgUrl;

        @NotNull
        private List<String> purpose;

        @NotNull
        private int intensity;

        @NotNull
        private String memo;

    }
}
