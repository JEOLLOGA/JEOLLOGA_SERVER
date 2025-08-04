package sopt.jeolloga.domain.auth.kakao;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RedirectUriResolver {

    private static final String LOCAL_IDENTIFIER = "localhost";
    private static final String QA_IDENTIFIER = "dev-gototemplestay.vercel.app";

    private static final String LOCAL_REDIRECT_URI = "http://localhost:3000/auth";
    private static final String QA_REDIRECT_URI = "https://dev-gototemplestay.vercel.app/auth";
    private static final String PROD_REDIRECT_URI = "https://gototemplestay.com/auth";

    public String resolve(HttpServletRequest request) {
        String base = extractBase(request);

        if (base.contains(LOCAL_IDENTIFIER)) {
            return LOCAL_REDIRECT_URI;
        } else if (base.contains(QA_IDENTIFIER)) {
            return QA_REDIRECT_URI;
        } else {
            return PROD_REDIRECT_URI;
        }
    }

    private String extractBase(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");
        return origin != null ? origin : (referer != null ? referer : "");
    }

    public boolean isLocalRequest(HttpServletRequest request) {
        String base = extractBase(request);
        return base.contains(LOCAL_IDENTIFIER);
    }
}
