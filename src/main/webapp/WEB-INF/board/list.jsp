<%--
  Created by IntelliJ IDEA.
  User: hanseul
  Date: 2021/08/18
  Time: 4:19 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script> // 스크립트는 브라우저에서 동작을 하는 것 : script안에 들어가는 내용은 자바스크립트로 작동한다. -> 서버에서 값을 만들어서 여기로 보내준다면 그 값으로 반영
//URL에 있는 파라미터를 추출하는 기능 : param
let num = '${param.bno}' //''없으면 값이 없을 때 에러가 날 것. ''로 들어가면 공백인 문자열로 들어가진다.

if(num){
    alert(num) // 브라우저입장에서는 엄청난 긴급상황인 것 : 경고창만뜨고 아무것도 실행되지 않는다. 경고창에서 확인버튼을 눌려야지만 다음작업이 이루어진다.
    //뒤로가기를 하면 경고창이 다시 뜬다 -> URL에 조건이 없어지고 board/list만 남아야한다.
    window.history.replaceState(null,'','/board/list.do');
}// 어제 받은 책의 p.661 '브라우저의 랜더링과정'을 참고하기. + p.673 파싱어쩌구
</script>

<h1>Page List</h1>
<h4>${pageMaker}</h4>

<form action="/board/list.do" method="get">
    <!-- 눈에 보이지 않게 데이터를 전달하기 위해 사용 :hidden-->
    <input type="hidden" name="page" value="3"> <!--새로로딩하면 page = 1 -->
    <select name="size">
        <!--selected : 로딩되었을 때 처음에 나오는 값-->
        <option value="10" ${pageMaker.size == 10?"selected":""}>10</option><!--size를 10으로 설정-->
        <option value="20" ${pageMaker.size == 20?"selected":""}>20</option><!--size를 20으로 설정 : 10에서 20으로 변경되니까 1페이지부터 다시 나옴(다시 로딩)-->
        <option value="50" ${pageMaker.size == 50?"selected":""}>50</option>
        <option value="100" ${pageMaker.size == 100?"selected":""}>100</option>
    </select>
    <button type="submit">적용</button>
</form>
<ul>
    <c:forEach items="${dtoList}" var="dto">
        <li>
            <div>
                <div>${dto.bno}</div>
                <div><a href="/board/read.do?bno=${dto.bno}&page=${pageMaker.page}&size=${pageMaker.size}">${dto.title}</a></div>
                <div>${dto.viewcount}</div>
            </div>
        </li>
    </c:forEach>
</ul>
<hr/>
<style>
    .pageList{
        list-style: none;
        display: flex;
        flex-direction: row;
    }
    .pageList li{
        margin-left: 0.3em;
        background-color: green;
        font-family: "Roboto Light";
        border: 1px solid greenyellow;
    }
    .current {
        font-size:large;
    }
</style>
<ul class="pageList">
    <c:if test="${pageMaker.prev}"><!--test에 입력되어있는 값을 조건으로 pageMaker.prev가 true면 밑의 문장을 실행-->
    <li><a href="/board/list.do?page=${pageMaker.start-1}&size=${pageMaker.size}}">prev</a></li>
    </c:if>
    <!--원래는 iteams를 이용해서 출력을 했었는데 begin이랑 end를 사용해줌-->
    <c:forEach begin="${pageMaker.start}" end="${pageMaker.end}" var="page">
        <li class="${page == pageMaker.page?"current":""}"><a href="/board/list.do?page=${page}&size=${pageMaker.size}">${page}</a></li>
    </c:forEach>
    <c:if test="${pageMaker.next}"><!--test에 입력되어있는 값을 조건으로 pageMaker.prev가 true면 밑의 문장을 실행-->
    <li><a href="/board/list.do?page=${pageMaker.end+1}&size=${pageMaker.size}">next</a></li>
    </c:if>
</ul>

</body>
</html>
