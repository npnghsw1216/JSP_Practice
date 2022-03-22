<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BBS(Bulletin Board System)</title>
</head>
<body>
	<%
		session.invalidate(); // 현제 이페이지에 접속한 회원이 세션을 빼앗긴 후 로그아웃
	%>
	<script>
		location.href = 'main.jsp';
	</script>
</body>
</html>