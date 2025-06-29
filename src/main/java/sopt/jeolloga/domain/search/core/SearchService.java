package sopt.jeolloga.domain.search.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.core.MemberRepository;
import sopt.jeolloga.domain.search.Search;
import sopt.jeolloga.domain.search.api.dto.SearchDto;
import sopt.jeolloga.domain.search.api.dto.res.SearchListRes;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

import java.util.List;

@Service
public class SearchService {
    private final SearchRepository searchRepository;
    private final MemberRepository memberRepository;

    public SearchService(SearchRepository searchRepository, MemberRepository memberRepository) {
        this.searchRepository = searchRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveSearch(Long userId, String keyword) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        searchRepository.deleteByMemberAndContent(member, keyword);

        searchRepository.save(new Search(member, keyword));

        List<Search> all = searchRepository.findByMemberOrderByIdDesc(member);
        if (all.size() > 10) {
            searchRepository.deleteAll(all.subList(10, all.size()));
        }
    }

    @Transactional(readOnly = true)
    public SearchListRes getSearch(Long userId) {
        List<SearchDto> list = searchRepository.findTop10ByMember_IdOrderByIdDesc(userId)
                .stream()
                .map(s -> new SearchDto(s.getId(), s.getContent()))
                .toList();

        return new SearchListRes(list);
    }
}
