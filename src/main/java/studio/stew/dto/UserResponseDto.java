package studio.stew.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import studio.stew.domain.enums.Gender;

public class UserResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto{
        String name;
        Gender gender;
        Long age;
        String location;
    }
}
