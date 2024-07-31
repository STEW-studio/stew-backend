package studio.stew.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studio.stew.aws.AwsS3Service;
import studio.stew.converter.TutorConverter;
import studio.stew.domain.*;
import studio.stew.domain.enums.Gender;
import studio.stew.dto.TutorRequestDto;
import studio.stew.dto.TutorResponseDto;
import studio.stew.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TutorService {
    private final UserRepository userRepository;
    private final SportsRepository sportsRepository;
    private final TutorRepository tutorRepository;
    private final AwsS3Service awsS3Service;
    private final PortfolioRepository portfolioRepository;
    private final ReviewRepository reviewRepository;
    private final TutorConverter tutorConverter;
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
        if (portfolio != null && !portfolio.isEmpty()) {
            uploadPortfolio(portfolio, newTutor, false);
        }

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
    public TutorResponseDto.TutorDetailDto getTutorDetail(Long tutorId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("튜터가 없습니다."));
        List<String> portfolio = portfolioRepository.findAllImgUrlByTutor(tutor);
        Review review = reviewRepository.findTopByTutorOrderByCreatedAtDesc(tutor);
        User reviewer = userRepository.findById(review.getUser().getUserId())
                .orElseThrow(()->new EntityNotFoundException("유저가 없습니다."));
        TutorResponseDto.TutorReviewDto reviewDto = TutorConverter.toTutorReviewDto(reviewer, review);
        Float totalScore = calculateScore(tutor);
        Integer reviewCount = countReviews(tutor);
        TutorResponseDto.TutorDetailDto response = TutorConverter.toTutorDetailDto(tutor, portfolio, reviewDto, totalScore, reviewCount);
        return response;
    }
    public List<TutorResponseDto.TodayTutorDto> todayTutorService() {
        List<Tutor> todayTutorList = tutorRepository.findTodayTutors(PageRequest.of(0,4));
        List<TutorResponseDto.TodayTutorDto> result = new ArrayList<>();
        for(Tutor tutor : todayTutorList) {
            result.add(TutorConverter.toTodayTutorDto(tutor));
        }
        return result;
    }
    public Page<Tutor> getTutorList(Integer page, Long sportsId, String area, Long minPrice, Long maxPrice, Gender gender, String sortOption) {
        Specification<Tutor> spec = Specification.where(null);
        //필터링
        //종목 필터링
        if (sportsId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sports").get("id"), sportsId));
        }
        //지역 필터링
        if (area != null && !area.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("location"), "%" + area + "%"));
        }
        //가격 필터링
        if (minPrice != null && maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.between(root.get("price"), minPrice, maxPrice));
        }
        else if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        else if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        //성별 필터링
        if (gender != null)  {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("gender"), gender));
        }

        //평점순 정렬을 위한 튜터별 평점 계산
        List<Tutor> allTutors = tutorRepository.findAll();
        for(Tutor tutor : allTutors) {
            Float score = calculateScore(tutor);
            tutor.setScore(score);
        }
        tutorRepository.saveAll(allTutors);

        //정렬
        //default: 최신순 정렬
        Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");;
        if(sortOption != null) {
            if(sortOption.equals("높은가격순")) {
                sort = Sort.by(Sort.Direction.DESC, "price");
            }
            else if(sortOption.equals("낮은가격순")) {
                sort = Sort.by(Sort.Direction.ASC, "price");
            }
            else if(sortOption.equals("높은평점순")) {
                sort = Sort.by(Sort.Direction.DESC, "score");
            }
            else if(sortOption.equals("낮은평점순")) {
                sort = Sort.by(Sort.Direction.ASC, "score");
            }
        }
        return tutorRepository.findAll(spec, PageRequest.of(page, 9,sort));
    }
    public Float calculateScore (Tutor tutor) {
        Float totalScore = 0.0f;
        if(reviewRepository.countAllByTutor(tutor) != 0){
            totalScore = reviewRepository.sumAllScoreByTutor(tutor.getTutorId());
        }
        Integer reviewCount = countReviews(tutor);
        if(reviewCount == 0) {
            return 0.0f;
        }
        else {
            return totalScore/countReviews(tutor);
        }
    }
    public Integer countReviews (Tutor tutor) {
        Integer countReviews = reviewRepository.countAllByTutor(tutor);
        return countReviews;
    }

    public List<TutorResponseDto.TutorPreviewDto> getRandomTutorsBySports(Long sportsId1, Long sportsId2) {
        Random random = new Random();
        List<Tutor> tutors1 = tutorRepository.findBySports_SportsId(sportsId1);
        List<Tutor> tutors2 = tutorRepository.findBySports_SportsId(sportsId2);

        Tutor randomTutor1 = tutors1.get(random.nextInt(tutors1.size()));
        Tutor randomTutor2 = tutors2.get(random.nextInt(tutors2.size()));

        return List.of(tutorConverter.toTutorResponseDto(randomTutor1), tutorConverter.toTutorResponseDto(randomTutor2));
    }
}

