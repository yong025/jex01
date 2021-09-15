package org.zerock.jex01.board.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy //자동으로 프록시객체를 만드는 어노테이션
@ComponentScan(basePackages = "org.zerock.jex01.board.aop")
public class BoardAOPConfig {
}
