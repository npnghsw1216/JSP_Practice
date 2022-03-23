<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %> 
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />
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
		String userID = null;
		if(session.getAttribute("userID") != null) { // 세션을 확인해서 userID라는 이름으로 세션id가 존재하는지 확인
			userID = (String) session.getAttribute("userID"); // 존재하면 userID에 해당 세션 값을 넣어줄 수 있도록 한다.
		}
		if(userID == null) { // userID가 null값이 아닌 경우 
			PrintWriter script = response.getWriter(); // 로그인이 되어있지 않으면, login.jsp 페이지로 이동하게 하는 경로
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("loaction.href = 'login.jsp'");
			script.println("<script>");
		} else {
			if(bbs.getBbsTitle() == null || bbs.getBbsContent() == null){ // 사용자가 게시글을 작성할 때 의 경우의 수를 생각하고 and 연산자 || 를 이용하여 모든 경우의 수에 대한 조건 작성
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('입력이 안 된 사항이 있습니다.')");
					script.println("history.back()");
					script.println("</script>");
				} else {
					BbsDAO bbsDAO = new BbsDAO(); // bbsDAO라는 하나의 인스턴스 생성
					int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent()); // write함수를 이용하여 실제로 게시글을 작성할 수 있게 해준다.
					if(result == -1){ // 데이터베이스 오류가 발생한다면.
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('글쓰기에 실패했습니다.')"); // 메세지를 출력하게 해준다.
						script.println("history.back()"); // 이전 페이지로 이동하게 해준다.
						script.println("</script>");
					}
					else { // 성공적으로 게시글을 작성 한 경우 메인화면으로 돌아가게 해준다.
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("location.href = 'bbs.jsp'"); // 게시글 작성 후 bbs.jsp페이지로 이동해주는 경로
						script.println("</script>");
					}
				}		
		}
	%>
</body>
</html>