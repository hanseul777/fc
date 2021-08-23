package org.zerock.fc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PageDTO { //현재페이지와 한 페이지에 출력되는 글의 개수정보를 담고있음

    @Builder.Default // builder.build할 때 기본 값을 줌
    private int page = 1;//현재페이지번호

    @Builder.Default
    private int size = 10; //한 페이지에 나오는 글의 개수

    // @Data로 size에 대한 getter는 생성완. skip에 대한 getter가 없어서 getSkip으로 만들어 줌 -> 매퍼에서 사용하기 위함
    public int getSkip(){//만들고 mybatis에서 skip을 넣으면 이 메서드가 출력
        //1페이지 - 0개스킵
        //2페이지 - 10개스킵
        //3페이지 - 20개스킵
        return (this.page -1) * size;
    }

}
