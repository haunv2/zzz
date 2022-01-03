package com.securityConfig;

import com.jwtConfig.JwtUserDetailService;
import com.jwtConfig.jwtFilter.JwtFilterAuthentication;
import com.jwtConfig.jwtUtil.JwtTokenUtil;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class webSecurity extends WebSecurityConfigurerAdapter {

    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/user/login", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/user/register", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/product/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/product/filter", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/category/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/province/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/district/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/ward/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/video/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/count"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/v2/api-docs"),
            new AntPathRequestMatcher("/webjars/**")
    );
    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(
            new OrRequestMatcher(
                    PUBLIC_URLS));
    private final UserDetailsService userDetailsService;
    private final JwtFilterAuthentication jwtFilterAuthentication;

    @Autowired
    public webSecurity(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        // create new jwt filter
        this.userDetailsService = userDetailsService;
        this.jwtFilterAuthentication = new JwtFilterAuthentication(userDetailsService, jwtTokenUtil, PROTECTED_URLS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PUBLIC_URLS); // ignore public urls
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        // we don't need session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().requestMatchers(PROTECTED_URLS).authenticated(); // authenticate secured urls
        //handle exception when authenticate
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
                .and()
                .addFilterBefore(jwtFilterAuthentication, UsernamePasswordAuthenticationFilter.class); // add filter before UsernamePasswordAuthenticationFilter

        // we don't need these
        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

}
