package studio.stew.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.stew.domain.Sports;
import studio.stew.repository.SportsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SportsService {

    private final SportsRepository sportsRepository;

    public List<Sports> getList(String kw) {
        return sportsRepository.findByNameContaining(kw);
    }
}
