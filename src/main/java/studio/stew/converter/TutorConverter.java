package studio.stew.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import studio.stew.domain.Sports;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;
import studio.stew.dto.TutorRequestDto;
import studio.stew.dto.TutorResponseDto;
import studio.stew.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class TutorConverter {
    private final ReviewRepository reviewRepository;
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
    public TutorResponseDto.TutorPreviewDto toTutorPreviewDto(Float score, Integer reviewCount, Tutor tutor) {
        return TutorResponseDto.TutorPreviewDto.builder()
                .price(tutor.getPrice())
                .intro(tutor.getIntro())
                .career(tutor.getCareer())
                .name(tutor.getName())
                .imgUrl(tutor.getImgUrl())
                .sportName(tutor.getSports().getName())
                .location(tutor.getLocation())
                .reviewCount(reviewCount)
                .score(score)
                .build();
    }
    public TutorResponseDto.TutorPreviewListDto toTutorPreviewListDto(Page<Tutor> tutorList) {
        List<TutorResponseDto.TutorPreviewDto> tutorPreViewDtoList = tutorList.stream()
                .map(tutor -> {
                    Float score = calculateScore(tutor);
                    Integer reviewCount = countReviews(tutor);
                    return toTutorPreviewDto(score, reviewCount, tutor);
                })
                .collect(Collectors.toList());

        return TutorResponseDto.TutorPreviewListDto.builder()
                .tutorList(tutorPreViewDtoList)
                .isFirst(tutorList.isFirst())
                .isLast(tutorList.isLast())
                .totalElements(tutorList.getTotalElements())
                .totalPage(tutorList.getTotalPages())
                .listSize(tutorList.getSize())
                .build();
    }
    public Float calculateScore (Tutor tutor) {
        Float totalScore = 0.0f;
        if(reviewRepository.countAllByTutor(tutor) != 0){
            totalScore = reviewRepository.sumAllScoreByTutor(tutor.getTutorId());
        }
        return totalScore/countReviews(tutor);
    }
    public Integer countReviews (Tutor tutor) {
        Integer countReviews = reviewRepository.countAllByTutor(tutor);
        return countReviews;
    }
}
