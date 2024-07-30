package studio.stew.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.stew.aws.AwsS3Service;
import studio.stew.domain.Application;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;
import studio.stew.dto.ApplicationRequestDto;
import studio.stew.repository.ApplicationRepository;
import studio.stew.repository.TutorRepository;
import studio.stew.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final TutorRepository tutorRepository;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;

    public Application createApplication(ApplicationRequestDto.ApplicationCreateRequestDto requestDto) {
        String imgUrl = awsS3Service.uploadFile(requestDto.getImgUrl());

        Application application = Application.builder()
                .title(requestDto.getTitle())
                .imgUrl(imgUrl)
                .purpose(requestDto.getPurpose())
                .intensity(requestDto.getIntensity())
                .memo(requestDto.getMemo())
                .status(false)
                .build();

        return applicationRepository.save(application);
    }

    public Page<Application> getSentApplications(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
        return applicationRepository.findByUserAndCreatedAtBetween(userId, startDateTime, endDateTime, pageable);
    }

    public Page<Application> getReceivedApplications(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
        Optional<User> user = userRepository.findById(userId);
        List<Tutor> profiles = tutorRepository.findByUser(user.get());
        List<Long> profileIds = profiles.stream().map(Tutor::getTutorId).collect(Collectors.toList());
        return applicationRepository.findByTutorInAndCreatedAtBetween(profileIds, startDateTime, endDateTime, pageable);
    }

    public Application getApplication(Long userId){
        Optional<Application> application = this.applicationRepository.findById(userId);
        return application.get();
    }

    public void delete(Application application){
        this.applicationRepository.delete(application);
    }
}
