package sopt.jeolloga.domain.member.core;

import org.springframework.stereotype.Service;
import sopt.jeolloga.common.type.MemberType;
import sopt.jeolloga.domain.member.api.dto.res.TypeResultRes;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Service
public class RecommendService {

    public TypeResultRes recommendByType(MemberType type) {
        if (type == null) {
            throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        }
        return toTypeRes(type);
    }

    public TypeResultRes recommendByTestResult(String result) {
        String code = computeTypeCodeFromNine(result);
        MemberType type = parseTypeOrThrow(code);
        return toTypeRes(type);
    }

    private MemberType parseTypeOrThrow(String code) {
        try {
            return MemberType.valueOf(code);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        }
    }

    private TypeResultRes toTypeRes(MemberType type) {
        return new TypeResultRes(
                type.name(),
                type.getTagline(),
                type.getDescription(),
                type.getRequirement(),
                type.getBestMate(),
                type.getWorstMate()
        );
    }

    private String computeTypeCodeFromNine(String raw) {
        if (raw == null) throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        String s = raw.trim().toUpperCase();
        if (s.length() != 9) throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        if (!s.matches("[AB]{9}")) throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);

        String p1 = s.substring(0, 3);
        String p2 = s.substring(3, 6);
        String p3 = s.substring(6, 9);

        char first  = (countA(p1) >= 2) ? 'I' : 'E';
        char second = (countA(p2) >= 2) ? 'A' : 'H';
        char third  = (countA(p3) >= 2) ? 'J' : 'P';

        return new String(new char[]{first, second, third});
    }

    private int countA(String part) {
        int cnt = 0;
        for (int i = 0; i < part.length(); i++) if (part.charAt(i) == 'A') cnt++;
        return cnt;
    }
}