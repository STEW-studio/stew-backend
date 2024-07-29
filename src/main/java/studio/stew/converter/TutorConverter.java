package studio.stew.converter;

import studio.stew.domain.Sports;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;
import studio.stew.dto.TutorRequestDto;
import studio.stew.dto.TutorResponseDto;

import java.time.LocalDateTime;

public class TutorConverter {
    public static Tutor toTutor(User user, TutorRequestDto.TutorCreateRequestDto requestDto, Sports sports, String imgUrl) {
        return Tutor.builder()
                .price(requestDto.getPrice())
                .user(user)
                .name(requestDto.getName())
                .sports(sports)
                .sports_intro(requestDto.getSportsIntro())
                .gender(requestDto.getGender())
                .self_intro(requestDto.getSelfIntro())
                .intro(requestDto.getIntro())
                .career(requestDto.getCareer())
                .age(requestDto.getAge())
                .location(requestDto.getLocation())
                .imgUrl(imgUrl)
                .build();
    }

    public static TutorResponseDto.TutorCreateResponseDto toTutorCreateResponseDto(Long tutorId) {
        return TutorResponseDto.TutorCreateResponseDto.builder()
                .tutorId(tutorId)
                .build();
    }
    public static TutorResponseDto.TutorUpdateResponseDto toTutorUpdateResponseDto(Long tutorId) {
        return TutorResponseDto.TutorUpdateResponseDto.builder()
                .tutorId(tutorId)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
