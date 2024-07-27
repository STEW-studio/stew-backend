package studio.stew.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.aws.AwsS3Service;
import studio.stew.converter.TutorConverter;
import studio.stew.domain.Portfolio;
import studio.stew.domain.Sports;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;
import studio.stew.dto.TutorRequestDto;
import studio.stew.repository.PortfolioRepository;
import studio.stew.repository.SportsRepository;
import studio.stew.repository.TutorRepository;
import studio.stew.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {
    private final UserRepository userRepository;
    private final SportsRepository sportsRepository;
    private final TutorRepository tutorRepository;
    private final AwsS3Service awsS3Service;
    private final PortfolioRepository portfolioRepository;
    public Long createTutor(Long userId, TutorRequestDto.TutorCreateRequestDto requestDto, List<MultipartFile> portfolio, String profileUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        Sports sports = sportsRepository.findById(requestDto.getSportsId())
                .orElseThrow(()-> new EntityNotFoundException("종목을 찾을 수 없습니다."));
        Tutor tutor = TutorConverter.toTutor(user, requestDto, sports, profileUrl);
        Tutor newTutor = tutorRepository.save(tutor);

        if (portfolio != null && !portfolio.isEmpty()) {
            for(MultipartFile img : portfolio) {
                String imgUrl = awsS3Service.uploadFile(img);
                Portfolio portfolioImg = Portfolio.builder()
                        .tutor(newTutor)
                        .imgUrl(imgUrl)
                        .build();
                portfolioRepository.save(portfolioImg);
            }
        }
        return newTutor.getTutorId();
    }
}
