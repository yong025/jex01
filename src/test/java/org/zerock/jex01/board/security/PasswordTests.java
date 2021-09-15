package org.zerock.jex01.board.security;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.common.config.RootConfig;
import org.zerock.jex01.security.config.SecurityConfig;
import org.zerock.jex01.security.domain.Member;
import org.zerock.jex01.security.mapper.MemberMapper;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BoardRootConfig.class, RootConfig.class, SecurityConfig.class}) //경로 지정 필요
public class PasswordTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    MemberMapper memberMapper;

    @Test
    public void testMember(){
        String mid = "admin0";

        Member member = memberMapper.findByMid(mid);

        log.info("-------------------------------------------");
        log.info(member);
        log.info("-------------------------------------------");
    }

    @Test
    public void testEncode() {
        String str = "member1";
        String enStr = passwordEncoder.encode(str);//암호화 시키는 코드
        log.warn(enStr);

    }

    @Test
    public void testDcode() {//맞는패스워드인지 틀린패스워드인지 확인하는 로직
        String str = "$2a$10$xKAe9I.KAAgZoHCCmN9TyuMWWlbh4ScKHXQwggvj3ltkbF9mgjqBC";

        boolean match = passwordEncoder.matches("member1", str);//"member1과 변수 str의 암호화된 값이 매치되는지 확인

        log.warn(match);
    }


    @Test
    public void insertMember() {

        //insert into tbl_member (mid,mpw,mname) values ('mid','mpw','mname')

        String query = "insert into tbl_member (mid,mpw,mname) values ('v1','v2','v3');";

        for (int i = 0; i < 10; i++) {

            String mid = "user" + i; //user0
            String mpw = passwordEncoder.encode("pw" + i); //pw0 -> Bcrypt 암호화
            String mname = "유저" + i; //유저0

            String result = query;

            result = result.replace("v1", mid);//내용물 바꿔치기 "v1"을  mid로
            result = result.replace("v2", mpw);
            result = result.replace("v3", mname);

            System.out.println(result);

        }

    }


    @Test
    public void insertAdmin() {

        //insert into tbl_member (mid,mpw,mname) values ('mid','mpw','mname')

        String query = "insert into tbl_member (mid,mpw,mname) values ('v1','v2','v3');";

        for (int i = 0; i < 10; i++) {

            String mid = "admin" + i; //user0
            String mpw = passwordEncoder.encode("pw" + i); //pw0 -> Bcrypt 암호화
            String mname = "관리자" + i; //유저0

            String result = query;

            result = result.replace("v1", mid);//내용물 바꿔치기 "v1"을  mid로
            result = result.replace("v2", mpw);
            result = result.replace("v3", mname);

            System.out.println(result);

        }
    }

    @Test
    public void insertMemberRole(){

        String sql = "insert into tbl_member_role (mid, role) values ('%s','%s');";

        for(int i=0; i< 10; i++){

            String result = String.format(sql, "user"+i, "ROLE_MEMBER");//들어갈 변수 , 첫번째 %, 두번째 %

            System.out.println(result);
        }
    }

    @Test
    public void insertAdminRole(){

        String sql = "insert into tbl_member_role (mid, role) values ('%s','%s');";

        for(int i=0; i< 10; i++){

            String result = String.format(sql, "admin"+i, "ROLE_MEMBER");//들어갈 변수 , 첫번째 %, 두번째 %

            System.out.println(result);

            String result2 = String.format(sql, "admin"+i, "ROLE_ADMIN");

            System.out.println(result2);
        }
    }
}
