package org.zerock.jex01.board.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@MapperScan(basePackages = "org.zerock.jex01.board.mapper")
@ComponentScan(basePackages = "org.zerock.jex01.board.service")
@Import(BoardAOPConfig.class) //BoardRootConfig를 호출할 때 같이 호출하라는 의미
public class BoardRootConfig {

}
