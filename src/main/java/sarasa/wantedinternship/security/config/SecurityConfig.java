package sarasa.wantedinternship.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sarasa.wantedinternship.security.jwt.JwtAuthenticationFilter;
import sarasa.wantedinternship.security.jwt.JwtProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(antMatcher(HttpMethod.POST, "/members")).permitAll()
//                        .requestMatchers(antMatcher(HttpMethod.POST, "/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/members")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .anyRequest().authenticated())
                .apply(new CustomFilterConfigurer());

        return http.build();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(authenticationManager, jwtProvider);

            builder.addFilter(jwtAuthenticationFilter);
        }

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
