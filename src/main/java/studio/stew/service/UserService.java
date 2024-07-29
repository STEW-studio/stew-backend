package studio.stew.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;
import studio.stew.repository.TutorRepository;
import studio.stew.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    public Page<Tutor> getTutorList(Long userId, Integer page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        Page<Tutor> TutorPage = tutorRepository.findAllByUser(user, PageRequest.of(page, 9));
        return TutorPage;
    }
}
