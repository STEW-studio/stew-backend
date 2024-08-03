package studio.stew.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import studio.stew.domain.User;
import studio.stew.dto.UserResponseDto;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public static UserResponseDto.UserInfoDto toUserDto(User user) {
        return UserResponseDto.UserInfoDto.builder()
                .name(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .location(user.getLocation())
                .build();
    }
}
