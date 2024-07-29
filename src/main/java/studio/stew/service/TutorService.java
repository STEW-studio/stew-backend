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
    public Long createTutor(Long userId, TutorRequestDto.TutorCreateRequestDto requestDto, List<MultipartFile> portfolio, MultipartFile profile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        Sports sports = sportsRepository.findById(requestDto.getSportsId())
                .orElseThrow(()-> new EntityNotFoundException("종목을 찾을 수 없습니다."));
        String profileUrl = null;
        if (profile != null && !profile.isEmpty()) {
            profileUrl = awsS3Service.uploadFile(profile);
        }
        Tutor tutor = TutorConverter.toTutor(user, requestDto, sports, profileUrl);
        Tutor newTutor = tutorRepository.save(tutor);
        uploadPortfolio(portfolio, newTutor, false);

        return newTutor.getTutorId();
    }
    public Long updateTutor(Long tutorId, TutorRequestDto.TutorUpdateRequestDto requestDto, List<MultipartFile> newPortfolio, MultipartFile newProfile) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(()->new EntityNotFoundException("튜터를 찾을 수 없습니다."));
        if (requestDto.getAge() != null) {tutor.setAge(requestDto.getAge());}
        if (requestDto.getName() != null) {tutor.setName(requestDto.getName());}
        if (requestDto.getSportsId() != null) {
            Sports newSport = sportsRepository.findById(requestDto.getSportsId())
                    .orElseThrow(() -> new EntityNotFoundException("운동종목을 찾을 수 없습니다."));
            tutor.setSports(newSport);
        }
        if (requestDto.getCareer() != null) {tutor.setCareer(requestDto.getCareer());}
        if (requestDto.getIntro() != null) {tutor.setIntro(requestDto.getIntro());}
        if (requestDto.getGender() != null) {tutor.setGender(requestDto.getGender());}
        if (requestDto.getPrice() != null) {tutor.setPrice(requestDto.getPrice());}
        if (requestDto.getLocation() != null) {tutor.setLocation(requestDto.getLocation());}
        if (requestDto.getSelfIntro() != null) {tutor.setSelf_intro(requestDto.getSelfIntro());}
        if (requestDto.getSportsIntro() != null) {tutor.setSports_intro(requestDto.getSportsIntro());}
        if (newProfile != null) {
            String imgUrl = tutor.getImgUrl();
            int lastSlashIndex = imgUrl.lastIndexOf('/');
            String filename = imgUrl.substring(lastSlashIndex+1);
            awsS3Service.deleteFile(filename);
            String newProfileUrl = awsS3Service.uploadFile(newProfile);
            tutor.setImgUrl(newProfileUrl);
        }
        uploadPortfolio(newPortfolio, tutor, true);
        tutorRepository.save(tutor);
        return tutor.getTutorId();
    }
    public void uploadPortfolio(List<MultipartFile> portfolio, Tutor tutor, boolean update) {
        if (update) {
            List<Portfolio> portfolioList = portfolioRepository.findAllByTutor(tutor);
            for(Portfolio imgUrl : portfolioList) {
                portfolioRepository.delete(imgUrl);
                awsS3Service.deleteFile(imgUrl.getFilename());
                System.out.println("imgUrl = " + imgUrl);
            }
        }
        for(MultipartFile img : portfolio) {
            String imgUrl = awsS3Service.uploadFile(img);
            int lastSlashIndex = imgUrl.lastIndexOf('/');
            String filename = imgUrl.substring(lastSlashIndex+1);
            Portfolio portfolioImg = Portfolio.builder()
                    .tutor(tutor)
                    .imgUrl(imgUrl)
                    .filename(filename)
                    .build();
            portfolioRepository.save(portfolioImg);
        }
    }
    public void deleteTutor(Long tutorId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("튜터가 없습니다."));
        tutorRepository.delete(tutor);
    }
}

