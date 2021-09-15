package org.zerock.jex01.board.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.common.config.RootConfig;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BoardRootConfig.class, RootConfig.class})
public class TimeServiceTests {

    @Autowired
    TimeService timeService;

    @Test
    public void testAdd() {

        String str ="Welcome to the Hotel California\n" +
                "Such a lovely place (Such a lovely place)\n" +
                "Such a lovely face\n" +
                "Plenty of room at the Hotel California\n" +
                "Any time of year (Any time of year)\n" +
                "You can find it here";

        timeService.addString(str);
    }

}
