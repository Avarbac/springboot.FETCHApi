package crud_boot.springboot.example.config;

import crud_boot.springboot.example.config.handler.LoginSuccessUserHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsServiceImpl; // сервис, с помощью которого тащим пользователя
    private final LoginSuccessUserHandler loginSuccessUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsServiceImpl, LoginSuccessUserHandler loginSuccessUserHandler) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.loginSuccessUserHandler = loginSuccessUserHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll() // доступность всем
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // разрешаем входить на /user пользователям с ролью User
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()  // Spring сам подставит свою логин форму
                .successHandler(loginSuccessUserHandler) // подключаем наш SuccessHandler для перенеправления по ролям
                .and()
                .logout().permitAll().logoutSuccessUrl("/");
        }
}
