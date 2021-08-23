package org.zerock.fc.service;

import lombok.extern.log4j.Log4j2;
import org.zerock.fc.dao.BoardDAO;
import org.zerock.fc.dto.BoardDTO;
import org.zerock.fc.dto.PageDTO;

import java.util.List;

@Log4j2
//service는 다리의 역할만 하고있다.
public enum BoardService {
    INSTANCE;

    public Integer register(BoardDTO boardDTO) throws RuntimeException {
      return BoardDAO.INSTANCE.insert(boardDTO);
    }

    public List<BoardDTO> getList(PageDTO pageDTO) throws RuntimeException {
        //BoardDTO의 정보를 사이즈크기만큼으로 나눠서(페이징) 다시 리스트에 담아준다.
        return BoardDAO.INSTANCE.list(pageDTO);
    }

    public BoardDTO read(Integer bno) throws RuntimeException{
        log.info("BoardService................................." + bno);
        return BoardDAO.INSTANCE.select(bno);
    }

    public void remove(Integer bno) throws RuntimeException {
        BoardDAO.INSTANCE.delete(bno);
    }

    public void modify(BoardDTO dto) throws RuntimeException{
        BoardDAO.INSTANCE.update(dto);

    }
}
