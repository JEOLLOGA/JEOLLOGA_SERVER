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

    public TypeResultRes recommendByType(String typeCode) {
        MemberType type = parseTypeOrThrow(typeCode);
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
}