package org.zerock.jex01.security.config;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.jex01.security.handler.CustomAccessDeniedHandler;
import org.zerock.jex01.security.handler.CustomLoginSuccessHandler;
import org.zerock.jex01.security.service.CustomUserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity //security를 접목하기 위해 사용한다.
@Log4j2
@MapperScan(basePackages = "org.zerock.jex01.security.mapper")
@ComponentScan(basePackages = "org.zerock.jex01.security.service")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()//인가받은 사용자만 쓸거다.
//                .antMatchers("/sample/doAll").permitAll()
//                .antMatchers("/sample/doMember").access("hasRole('ROLE_MEMBER')")
//                .antMatchers("/sample/doAdmin").access("hasRole('ROLE_ADMIN')");

        http.formLogin().loginPage("/customLogin")
                .loginProcessingUrl("/login"); //실제적으로 동작할 페이지 설정(/login) 보이는 페이지는 /customlogin으로 띄우고 내부적으로는 /login 으로 처리해라

        //http.logout().invalidateHttpSession(true); 얘가 없어도 logout가능
        //disable하면 get방식으로도 logout가능
        //logout경로만 주면 된다.

        http.csrf().disable();//나는 security설정에 csrf를 사용하지 않겠다.
        //웹에서 개발자모드에서 확인 시, csrf관련 값들이 사라지는 것을 확인 할 수 있다.

        http.rememberMe().tokenRepository(persistentTokenRepository())
                .key("zerock").tokenValiditySeconds(60 * 60 * 24 * 30);

        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());//객체를 바로만들지않고 bean을 주입해서 넣는다.
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(){//객체를 바로만들지않고 bean을 주입해서 넣는다.
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService); // 0913과 다르게 변수로 빼줬음.

//        auth.userDetailsService(customUserDetailsService());//customUserDetailsService()를 통해서 로그인 프로세스를 진행하겠다.

//        auth.inMemoryAuthentication().withUser("member1").password("$2a$10$xKAe9I.KAAgZoHCCmN9TyuMWWlbh4ScKHXQwggvj3ltkbF9mgjqBC")
//                .roles("ADMIN"); //자동으로 앞에 ROLE_를 붙여준다.
//        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$xKAe9I.KAAgZoHCCmN9TyuMWWlbh4ScKHXQwggvj3ltkbF9mgjqBC")
//                .roles("MEMBER","ADMIN");
    }

//    @Bean
//    public CustomUserDetailsService customUserDetailsService(){  //0913과 다르게 주석처리
//        return new CustomUserDetailsService(); //mapper주입방법이 달라짐
//    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
}
