package sarasa.wantedinternship.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.dto.request.LoginDto;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto;

        try {
            loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read and parse the login request.", e);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Member member = (Member) authResult.getPrincipal();

        String accessToken = createAccessToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);
    }

    private String createAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("memberId", member.getId());
        claims.put("email", member.getEmail());

        String subject = member.getEmail();

        Instant expiration = jwtProvider.getTokenExpiration(jwtProvider.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());

        return jwtProvider.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

}
