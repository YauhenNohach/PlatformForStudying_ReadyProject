package by.itstep.application.configuration.security;

import by.itstep.application.entity.type.Role;
import by.itstep.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/static/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests(
                        urlConfig -> urlConfig
                                .antMatchers("/login", "/registration").permitAll()
                                .antMatchers("/api/v*/registration/**", "/api/v1/login", "/test/get/**").permitAll()
                                .antMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .antMatchers("/group/create", "/test/create",
                                        "/teacher/assign", "test/time", "/teacher/tests", "/students/all",
                                        "/group/all", "/group/**/addStudent/**").hasAuthority(Role.ADMIN.getAuthority())
                                .antMatchers("/test/all", "questions/**").hasAuthority(Role.USER.getAuthority())
                                .anyRequest().authenticated()
                )
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users"));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
