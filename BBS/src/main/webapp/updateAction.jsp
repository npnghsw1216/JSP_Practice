<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %> 
<%@ page import="bbs.Bbs" %> 
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
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
			script.println("</script>");
		} 
		// 현재 수정하고자하는 글의 번호가 들어오지 않았다면 유효하지 않다고 출력
		
		int bbsID = 0; 
		if(request.getParameter("bbsID") != null) {
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if(bbsID == 0){
			PrintWriter script = response.getWriter(); 
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다.')");
			script.println("loaction.href = 'bbs.jsp'"); // bbs.jsp 파일로 이동해주는 경로
			script.println("</script>");
		}
		Bbs bbs = new BbsDAO().getBbs(bbsID);
		if (!userID.equals(bbs.getUserID())) {
			PrintWriter script = response.getWriter(); 
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("loaction.href = 'bbs.jsp'"); 
			script.println("</script>");
		} else {
			// JavaBeans를 사용하지 않기 때문에 매게변수로 넘어온 값들을 체크
			if(request.getParameter("bbsTitle") == null || request.getParameter("bbsContent") == null
					|| request.getParameter("bbsTitle").equals("") || request.getParameter("bbsContent").equals("")){ 
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('입력이 안 된 사항이 있습니다.')");
					script.println("history.back()");
					script.println("</script>");
				} else {
					BbsDAO bbsDAO = new BbsDAO(); // bbsDAO라는 하나의 인스턴스 생성
					int result = bbsDAO.update(bbsID, request.getParameter("bbsTitle"), request.getParameter("bbsContent")); // write함수를 이용하여 실제로 게시글을 작성할 수 있게 해준다.
					if(result == -1){ // 데이터베이스 오류가 발생한다면.
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('글 수정에 실패했습니다.')"); // 메세지를 출력하게 해준다.
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