package studio.stew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class SportsResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SportsNameIdResponseListDto {
        List<SportsResponseDto.SportsNameIdResponseDto> sportsList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SportsNameIdResponseDto {
        @JsonProperty("sports_id")
        Long sportsId;

        String name;
    }
}
