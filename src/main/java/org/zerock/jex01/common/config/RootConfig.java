package org.zerock.jex01.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zerock.jex01.board.config.BoardRootConfig;

import javax.sql.DataSource;
import java.util.ArrayList;

@Configuration //java파일로 xml처럼 설정하는 방법
@Import(BoardRootConfig.class) //따로 로딩해도 되나 같이 로딩하는게 좋음
@EnableTransactionManagement
public class RootConfig {

    //한번만 하는 설정
    @Bean
    //SqlSessionFactory는 DB와 SQL 실행에 대한 모든 것을 가진 가장 중요한 객체. 이 객체가 DataSource를 참조하여 myBatis와 mySQL 서버 연동

    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //sqlSessionFactoryBean.setMapperLocations(); 어노테이션만 추가하면 되므로 굳이 mapper를 지정하는 코드 작성 필요x
        sqlSessionFactoryBean.setDataSource(dataSource());

        return sqlSessionFactoryBean.getObject(); //getObject를 씀으로서 다운캐스팅 x
    }

    //기본적으로 싱글톤 -> 한번만 로딩함함
    //DB 연결
   @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        //config.setDriverClassName("com.mysql.cj.jdbc.Driver");
       config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy"); //sql문을 보기위해서 추가
        //config.setJdbcUrl("jdbc:mysql://localhost:3306/springdb");
       config.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/springdb"); //sql문을 로그에서 보기위해서 추가
       config.setUsername("springuser");
        config.setPassword("springuser");

        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "names") //bean을 설정시키도록 하는 어노테이션,대부분은 이름을 메서드의 이름과 일치시킴
    public ArrayList<String> names() { //예외적으로 메서드 이름을 명사로 설정
        ArrayList<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");;
        list.add("CCC");;

        return list;
    }


}
