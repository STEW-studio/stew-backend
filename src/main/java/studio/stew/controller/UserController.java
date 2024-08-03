package studio.stew.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import studio.stew.converter.TutorConverter;
import studio.stew.converter.UserConverter;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;
import studio.stew.dto.TutorResponseDto;
import studio.stew.dto.UserResponseDto;
import studio.stew.response.DataResponseDto;
import studio.stew.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final TutorConverter tutorConverter;
    @Operation(summary = "특정 유저의 튜터 목록 조회 API",description = "특정 유저의 튜터 목록을 조회하는 API이며, 페이징을 포함합니다. path variable로 userId를, query String으로 page 번호를 주세요")
    @GetMapping("/{userId}/tutors")
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다."),
    })
    public DataResponseDto<TutorResponseDto.TutorPreviewListDto> getTutorList(
            @PathVariable Long userId,
            @RequestParam(name = "page") Integer page) {
        Page<Tutor> response = userService.getTutorList(userId, page-1);
        return DataResponseDto.of(tutorConverter.toTutorPreviewListDto(response),"유저가 등록한 튜터 목록을 조회했습니다.");
    }

    @GetMapping("/{userId}")
    public DataResponseDto<UserResponseDto.UserInfoDto> getUserInfo(
            @PathVariable Long userId){
        User user = userService.getUserById(userId);

        UserResponseDto.UserInfoDto responseDto = UserConverter.toUserDto(user);
        return DataResponseDto.of(responseDto, "유저 정보를 조회했습니다.");
    }
}
