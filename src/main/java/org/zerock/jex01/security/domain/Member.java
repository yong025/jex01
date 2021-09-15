package org.zerock.jex01.security.domain;


import lombok.*;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    private String mid;
    private String mpw;
    private String mname;
    private String enabled;

    private List<MemberRole> roleList;
}
