package org.zerock.fc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Integer bno;
    private String title,content,writer;
    private int viewcount;
    private Timestamp regdate,updatedate;

    //한 보드에 이미지가 여러개 있기 때문에 List
    private List<AttachDTO> attachDTOList;

    //이미지를 보관
    public void addAttach(AttachDTO attachDTO){
        if(attachDTOList == null) {
            attachDTOList = new ArrayList<>();
        }
        attachDTOList.add(attachDTO);
    }

}
