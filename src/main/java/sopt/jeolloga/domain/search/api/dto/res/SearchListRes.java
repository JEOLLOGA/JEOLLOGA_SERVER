package sopt.jeolloga.domain.search.api.dto.res;

import sopt.jeolloga.domain.search.api.dto.SearchDto;

import java.util.List;

public record SearchListRes(
        List<SearchDto> searchList
) {
}
