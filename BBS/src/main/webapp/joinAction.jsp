<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />
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
		// 로그인이 이미 된 사람은 회원가입을 할 수없게 해주는 기능
		
		String userID = null;
		if(session.getAttribute("userID") != null) { // 세션을 확인해서 userID라는 이름으로 세션id가 존재하는지 화긴
		userID = (String) session.getAttribute("userID"); // 존재하면 userID에 해당 세션 값을 넣어줄 수 있도록 한다.
		}
		if(userID != null) { // userID가 null값이 아닌 경우 
			PrintWriter script = response.getWriter(); // 이미 로그인이 되었다고 사용자에게 알려주고 main.jsp페이지로 이동해주는 경로
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다.')");
			script.println("loaction.href = 'main.jsp'");
			script.println("<script>");
		}
		if(user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null 
			|| user.getUserGender() == null || user.getUserEmail() == null){ // 사용자가 회원가입할 때 의 경우의 수를 생각하고 and 연산자 || 를 이용하여 모든 경우의 수에 대한 조건 작성
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {
			UserDAO userDAO = new UserDAO(); // userDAO라는 하나의 인스턴스 생성
			int result = userDAO.join(user); // user라는 인스턴슬를 사용함을 써 위에(javaBean) 부분을 사용해 회원가입을 수행하게 만들어 준다.
			if(result == -1){ // userDAO에서의 result가 -1(ID는 유니크하게 하나여기 하기 때문에 데이베이스에 ID가 존재하면)이라면 
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.')"); // 메세지를 출력하게 해준다.
				script.println("history.back()"); // 이전 페이지로 이동하게 해준다.
				script.println("</script>");
			}
			else { // userDAO에서의 result가 0(회원가입의 성공)이라면
				session.setAttribute("userID", user.getUserID()); // 회원가입을 성공하였을 때 회원 ID를 가져와 세션id를 할당해준다.
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'main.jsp'"); // 회원가입 성공(result==0)과 동시에 현재는 else문이어서 그냥 -1값이 아니면 다 main.jsp 메인페이지로 이동하는 경로
				script.println("</script>");
			}
		}
	%>
</body>
</html>