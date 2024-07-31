package studio.stew.converter;

import studio.stew.domain.Sports;
import studio.stew.dto.SportsResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SportsConverter {

    public static SportsResponseDto.SportsNameIdResponseDto toSportsNameIdResponseDto(Sports sports) {
        return SportsResponseDto.SportsNameIdResponseDto.builder()
                .sportsId(sports.getSportsId())
                .name(sports.getName())
                .build();
    }

    public static SportsResponseDto.SportsNameIdResponseListDto toSportsNameIdResponseListDto(List<Sports> sportsList) {
        List<SportsResponseDto.SportsNameIdResponseDto> sportsNameIdResponseDtoList = sportsList.stream()
                .map(SportsConverter::toSportsNameIdResponseDto)
                .toList();

        return SportsResponseDto.SportsNameIdResponseListDto.builder()
                .sportsList(sportsNameIdResponseDtoList)
                .build();
    }
}
