package org.zerock.fc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachDTO {

    //BoardDTO를 참고해서 생성
    private Integer bno;
    private String fname, savename;
    private boolean imgyn;

}
