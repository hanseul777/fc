package org.zerock.fc.dao;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;
import org.zerock.fc.dto.AttachDTO;
import org.zerock.fc.dto.BoardDTO;
import org.zerock.fc.dto.PageDTO;

import java.util.List;

@Log4j2
public enum BoardDAO {
    INSTANCE;

    //계속사용해야 해서 상수로 고정시켜놓음. BoardMapper의 namespace사용
    private static final String PREFIX ="org.zerock.fc.dao.BoardMapper";

    //register도 insert로 사용하기 위해서 void -> Interger로 변경해줌. (ResigerController의 bno로 불리기 위해서
    public Integer insert(BoardDTO boardDTO) throws RuntimeException {
        Integer bno = null; // 예외처리 안에서 return은 가능하면 한 번만 하도록 위에 변수를 빼줘서 맨 마지막에 리턴을 넣어준다.
        //SqlSession이 connection의 기능을해준다.
        try (SqlSession session = MyBatisLoader.INSTANCE.getFactory().openSession(true)){
            session.insert(PREFIX+".insert",boardDTO);
            bno = boardDTO.getBno();

            List<AttachDTO> attachDTOList = boardDTO.getAttachDTOList();
            if(attachDTOList != null && attachDTOList.size() > 0) { //attachDTOList에 첨부파일이 없으면 null -> 첨부파일이 있을 때만 for가 진행되도록해준다.
                for (AttachDTO attachDTO : attachDTOList) {
                    attachDTO.setBno(bno); // bno먼저 추가하고 시작해야함
                    session.insert(PREFIX + ".insertAttach", attachDTO);
                }
            }
            session.commit();

        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return bno;
    }
    public BoardDTO select(Integer bno) throws RuntimeException {
        BoardDTO dto = null;
        try (SqlSession session = MyBatisLoader.INSTANCE.getFactory().openSession(true)){
            dto = session.selectOne(PREFIX+".select",bno); // 한 개의 항목만 가져오면 selectOne
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return dto;
    }

    public List<BoardDTO> list(PageDTO pageDTO) throws RuntimeException {
        List<BoardDTO> list = null;
        try (SqlSession session = MyBatisLoader.INSTANCE.getFactory().openSession(true)){
            list = session.selectList(PREFIX+".list",pageDTO);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    public void delete(Integer bno) throws RuntimeException{
        try (SqlSession session = MyBatisLoader.INSTANCE.getFactory().openSession(true)){
            session.selectList(PREFIX+".delete",bno);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    public void update(BoardDTO dto)throws RuntimeException{
        try (SqlSession session = MyBatisLoader.INSTANCE.getFactory().openSession(true)){
            session.selectList(PREFIX+".update",dto);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
