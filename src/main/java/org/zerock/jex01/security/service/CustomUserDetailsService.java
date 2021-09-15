package org.zerock.jex01.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.jex01.security.domain.Member;
import org.zerock.jex01.security.dto.MemberDTO;
import org.zerock.jex01.security.mapper.MemberMapper;


@Log4j2
@RequiredArgsConstructor //생성자 생성
@Service
public class CustomUserDetailsService implements UserDetailsService {//<<로그인할 때 얘를 이용한다.

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//사용자의 아이디 개념
        //userDetails에 사용자의 정보가 다 들어가있음 ( 인터페이스 타입 )
        //UserDetails를 구현하거나 UserDetails를 가지고 있는 클래스를 구현해야함.

        log.warn("CustomUserDetailsService----------------------------------");
        log.warn("CustomUserDetailsService----------------------------------");
        log.warn(username);
        //아이디가 틀렸을 때  - Username Not Found
        //비밀번호가 틀렸을 때 - Bad Cridential
        //권한이 아예 없을 때 Access Denied
        log.warn(memberMapper);
        log.warn("CustomUserDetailsService----------------------------------");
        log.warn("CustomUserDetailsService----------------------------------");
        log.warn("CustomUserDetailsService----------------------------------");


        Member member = memberMapper.findByMid(username);// null이면 사용자가 없음

        if(member == null){
            throw new UsernameNotFoundException("NOT EXIST");
        }

        String[] authorities = member.getRoleList().stream().map(memberRole -> memberRole.getRole()).toArray(String[]:: new);
        // toArray(String[]:: new) 새로운 배열을 만들어준다.

        User result = new MemberDTO(member);
//        User result = (User) User.builder()//다운캐스팅
//                .username(username)
//                .password(member.getMpw())
//                .accountExpired(false)//이 계정 만료 ?
//                .accountLocked(false) // 이 계정 잠긴 계정 ?
//                .authorities(authorities) //이 계정 권한은 ? 앞에 ROLE_를 붙여서 권한명시를 꼭 해줘야함.
//                .build();


        return result; //반환은 userDetails 타입
    }
}
