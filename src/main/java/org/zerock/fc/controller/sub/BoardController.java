package org.zerock.fc.controller.sub;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.zerock.fc.annotations.Controller;
import org.zerock.fc.annotations.GetMapping;
import org.zerock.fc.annotations.PostMapping;
import org.zerock.fc.dto.BoardDTO;
import org.zerock.fc.dto.PageDTO;
import org.zerock.fc.dto.PageMaker;
import org.zerock.fc.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

@Log4j2
@Controller(value = "/board")
public class BoardController {

    @GetMapping("/board/register.do")
    public String registerGet(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("board register get....do");

        return "board/register";
    }

    @PostMapping("/board/register.do")
    public String registerPost(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("board register post....do");

        BoardDTO boardDTO = BoardDTO.builder() //파라미터 수집
                .title(request.getParameter("title"))
                .content(request.getParameter("content"))
                .writer(request.getParameter("writer"))
                .build();

        Integer bno = BoardService.INSTANCE.register(boardDTO);


        return "re:/board/list.do?bno=" + bno;
    }

    private Integer getInt(String str){
        try {
            int value = Integer.parseInt(str);
            if (value <= 0) {
                return null;
            }
            return value;
        }catch (Exception e){
            return null;
        }
    }
    @GetMapping(value = "/board/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response)throws Exception{


        System.out.println("---------------------------");
        Integer page = getInt(request.getParameter("page"));
        Integer size = getInt(request.getParameter("size"));

        PageDTO pageDTO = PageDTO.builder().build();

        if(page!=null) {pageDTO.setPage(page);}
        if(size!=null) {pageDTO.setSize(size);}

        log.info("=========== step1 ============");
        log.info(pageDTO);

        List<BoardDTO> dtoList = BoardService.INSTANCE.getList(pageDTO);

        log.info("=========== step2 ============");
        log.info(dtoList);

        //택배보내기
        request.setAttribute("dtoList",dtoList);

        PageMaker pageMaker = new PageMaker(pageDTO.getPage(), pageDTO.getSize(),1230);
        request.setAttribute("pageMaker",pageMaker);

        return "board/list";
    }


    @GetMapping(value = "/board/read.do")
    public String read(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("---------------------------");

        //파라미터 수집
        Integer bno = getInt(request.getParameter("bno"));
        Integer page = getInt(request.getParameter("page"));
        Integer size = getInt(request.getParameter("size"));

        PageDTO pageDTO = PageDTO.builder().build();

        if(page!=null) {pageDTO.setPage(page);}
        if(size!=null) {pageDTO.setSize(size);}//null이면 디폴트값으로만 결과가 나옴

        //게시물가져오기
        BoardDTO boardDTO = BoardService.INSTANCE.read(bno);

        //택배상자에 담아서 보내주기
        request.setAttribute("boardDTO",boardDTO);
        request.setAttribute("pageDTO", pageDTO);

        request.getRequestDispatcher("/WEB-INF/board/read.jsp").forward(request,response);

        return "board/read";
    }

    @GetMapping(value = "/board/modify.do")
    public String modify(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("---------------------------");

//파라미터 수집
        Integer bno = getInt(request.getParameter("bno"));
        Integer page = getInt(request.getParameter("page"));
        Integer size = getInt(request.getParameter("size"));

        PageDTO pageDTO = PageDTO.builder().build();

        if(page!=null) {pageDTO.setPage(page);}
        if(size!=null) {pageDTO.setSize(size);}//null이면 디폴트값으로만 결과가 나옴

        //게시물가져오기
        BoardDTO boardDTO = BoardService.INSTANCE.read(bno);

        //택배상자에 담아서 보내주기
        request.setAttribute("boardDTO",boardDTO);
        request.setAttribute("pageDTO", pageDTO);

        return "board/modify";
    }

    @PostMapping("/board/modify.do")
    public String modifyPost(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("board modify post....do");

        Integer bno = getInt(request.getParameter("bno"));
        String modifyTitle = request.getParameter("title");
        String modifyContent = request.getParameter("content");

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(bno)
                .title(modifyTitle)
                .content(modifyContent)
                .build();

        BoardService.INSTANCE.modify(boardDTO);

        request.setAttribute("title", boardDTO.getTitle());
        request.setAttribute("content", boardDTO.getContent());

        return "re:/board/list.do";
    }

    @PostMapping("/board/remove.do")
    public String removePost(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("board remove post....do");
        Integer bno = Integer.parseInt(request.getParameter("bno"));

        BoardService.INSTANCE.remove(bno);

        return "re:/board/list.do";
    }

    @GetMapping("/board/upload.do")
    public String uploadGet(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("board upload get....do");
        return "/upload";
    }

    @PostMapping("/board/upload.do")
    public String uploadPost(HttpServletRequest request, HttpServletResponse response)throws Exception{

        System.out.println("board upload post....do");
        String uploadfolder = "/Users/hanseul/upload"; // upload할 폴더를 선택
        byte[] buffer = new byte[1024*8]; //파일복사를 위해 선언
        Collection<Part> parts = request.getParts();

        parts.forEach(part -> {
            log.info(part);

            String type = part.getContentType();
            // type이 null : 우리가 사용하던 일반적인 파일형식이라는 말
            // 일반파일과 이미지파일의 업로드가 다르다.
            if(type == null) {
                log.info("partName : " + part.getName()); // 일반파일을 처리할 때만 사용한다.
                return; // null이 아니면 밑으로 내려간다.
            }

            String fileName = part.getSubmittedFileName();

//            System.currentTimeMillis() 같은파일 중복으로 올리기 위해 사용 -> System.currentTimeMillis()+"_"+fileName로 파일이름을 지정
            String uploadFileName = System.currentTimeMillis()+"_"+fileName;

            log.info(fileName);

            //원본파일 저장
            try (InputStream in = part.getInputStream();
                 OutputStream fos = new FileOutputStream(uploadfolder+ File.separator+uploadFileName); //원본파일의 오리지널네임
                 //File.separator 파일경로
            ) {
                while (true){
                    int count = in.read(buffer);
                    if(count == -1){break;}
                    fos.write(buffer,0,count);
                }
            }catch(Exception e){

            }//원본파일 저장 끝

            //썸네일은 이미지파일에서만 만들어져야한다. -> 이미지로 시작하는 애들만 썸네일생성
            if(type.startsWith("image")){

                try{
                    Thumbnails.of(new File(uploadfolder+ File.separator+uploadFileName))//오리지널 파일
                            .size(100,100)//100픽셀짜리로 만들어 줌
                            .toFile(new File(uploadfolder+ File.separator+"s_"+uploadFileName));
                }catch (Exception e){}

            }

            log.info("-------------------------------");// 파일하나씩 구분선
        });//for each

        return "re:/board/list.do";
    }

}