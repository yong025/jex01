package org.zerock.jex01.common.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageMaker {

    private int page,start,end,total,size;
    private boolean prev,next;

    public PageMaker(int page, int size, int total) {

        this.page = page;
        this.total = total;
        this.size = size;

        //마지막 페이지
        end = (int)(Math.ceil(page / 10.0) * 10);
        //처음 페이지
        start = end - 9;

        prev = start > 1; // start가 1보다 작다면 prev가 false -> 보이지 않음
        next = total/(double)size > end;

//        if (end * size  > total) { //만약 end페이지가 진짜 끝보다 더 큰 경우
//            end = (int)(Math.ceil((total / (double)size))); //더 크다면 진짜 끝을 end페이지로 넣어라
//        } //작다면 end가 end
        end = end * size > total ? (int)(Math.ceil(total / (double)size)) : end;

    }

}
