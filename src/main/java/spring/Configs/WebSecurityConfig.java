package spring.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import spring.Service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    //**Подключение и распределение ролей на страницах**

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .antMatchers("/user/**", "/mein", "/activate/*").authenticated()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/index")
                    .permitAll()
                .and()
                    .rememberMe()
                .and()
                    .logout()
                     .permitAll();
        http.cors().and().csrf().disable();
    }

    //**Работа с БД**


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    //**Подключение модулей информации для вёрстки**
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(

                // статика
                "/css/**",
                "/js/**",
                "/fonts/**",
                "/img/**"
        );
    }




}
