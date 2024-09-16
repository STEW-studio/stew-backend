package studio.stew.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    public Application createApplication(ApplicationRequestDto.ApplicationCreateRequestDto requestDto, Long userId, Long tutorId) {
        String imgUrl = awsS3Service.uploadFile(requestDto.getImgUrl());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("튜터를 찾을 수 없습니다. ID: " + tutorId));

        Application application = Application.builder()
                .title(requestDto.getTitle())
                .imgUrl(imgUrl)
                .purpose(requestDto.getPurpose())
                .intensity(requestDto.getIntensity())
                .memo(requestDto.getMemo())
                .status(false)
                .tutor(tutor)
                .user(user)
                .build();

        return applicationRepository.save(application);
    }

    public Page<Application> getSentApplicationList(Long userId, Integer page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        return applicationRepository.findAllByUser(user, PageRequest.of(page, 5));

    }

    public Page<Tutor> getReceivedApplications(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        List<Tutor> tutors = tutorRepository.findByUser(user);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tutors.size());

        return new PageImpl<>(tutors.subList(start, end), pageable, tutors.size());
    }

    public List<Application> getReceivedApplicationsByTutor(Tutor tutor) {
        return applicationRepository.findByTutor(tutor);
    }

    public Application getApplication(Long appId){
        return applicationRepository.findById(appId)
                .orElseThrow(() -> new EntityNotFoundException("신청서를 찾을 수 없습니다. ID: " + appId));
    }

    public void delete(Application application){
        this.applicationRepository.delete(application);
    }

    public List<Tutor> getTutorsWithApplications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        return tutorRepository.findByUser(user);
    }

    public void updateStatus(Long appId) {
        Application application = applicationRepository.findById(appId)
                .orElseThrow(() -> new EntityNotFoundException("신청서를 찾을 수 없습니다. ID: " + appId));

        application.setStatus(true);
        applicationRepository.save(application);
    }
}
