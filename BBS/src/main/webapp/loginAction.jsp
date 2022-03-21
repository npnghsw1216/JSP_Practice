<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>BBS(Bulletin Board System)</title>
</head>
<body>
	<%
		UserDAO userDAO = new UserDAO(); // userDAO라는 하나의 인스턴스 생성
		int result = userDAO.login(user.getUserID(), user.getUserPassword()); // 로그인을 시도할 수 있도록 한다.
		if(result == 1){ // userDAO에서의 result가 1(ID, Password가 모두 동일)이라면 
			PrintWriter script = response.getWriter(); // 하나의 스크립트 문장을 넣을 수 있도록 해준다.
			script.println("<script>");
			script.println("location.href = 'main.jsp'"); // main.jsp페이지로 가게 하도록 경로 설정
			script.println("</script>");
		}
		else if(result == 0){ // userDAO에서의 result가 0(비밀번호가 다를 때)이라면
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다.')"); // 메세지를 출력하게 해준다.
			script.println("history.back()"); // 이전 페이지로 이동하게 해준다.
			script.println("</script>");
		}
		else if(result == -1){ // userDAO에서의 result가 -1(아이디가 다를 때)이라면
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('존재하지 않는 아이디입니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		else if(result == -2){ // userDAO에서의 result가 -2(데이터베이스에 오류가 있을 때)이라면
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		
		//  라이브러리 파일 안에(lib) mysql-connector 추가
	%>
</body>
</html>