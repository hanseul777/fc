package org.zerock.fc.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString // 주소값을 출력했을 때 가리키고 있는 객체의 값을 문자열로 나오게 바꿔줌
public class PageMaker {
    private int start, end, page, size, total;
    private boolean prev, next;

    public PageMaker(int page, int size, int total){

        this.page = page;
        this.size = size;
        this.total = total;

        end =(int)(Math.ceil(page/10.0)*10); //끝페이지를 먼저 구하기
        start = end-9;

        prev = start > 0;
        next = (total/(double)size) > end;
        //next가 false면 출력 x -> next > end(마지막페이지) : false
        //next는 출력을 안해도 end는 size의 디폴트를 10으로 줬기 때문에 10단위로 찍혀서 실제 값이 없어도 번호가 찍힘.
        //ex) 실제 데이터는 13번까지만 있는데 20번까지 출력됨.

        //위의 오류를 if문으로 처리
//        if(end*size > total){
//            end = (int)(Math.ceil(total/(double)size));
//        }
        //삼항연사자로 표현하면
        end = end*size > total ? (int)(Math.ceil(total/(double)size)) : end;

    }

}
