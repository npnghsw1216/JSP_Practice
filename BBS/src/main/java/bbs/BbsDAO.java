package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BbsDAO {
	
	private Connection conn; // 데이터베이스에 접근하게해주는 하니의 객체
	private PreparedStatement pstmt;
	private ResultSet rs; // 어떠한 정보를 담을 수 있는 하나의 객체
	
	// Ctrl+Shift+o 로 외부 라이브러리 추가
	
	// MySQL에 접속하게 해주는 부분
	
	// try/catch문을 이용하여 예외처리
	
	public BbsDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS"; // local은 본인 컴퓨터로써 3306포트(MySQL주소)의 BBS에 접속
			String dbID = "root"; // DB ID : DB ID 접속
			String dbPassword = "root"; // DB Password : DB Password입력
			Class.forName("com.mysql.jdbc.Driver"); // MySQLDriver : MySQL에 접속할 수 있도록 매게체역활을 해주는 하나의 라이브러리
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // 실질적으로 Driver를통해서 URL검색, 아이디 접속, 패스워드 접속
		} catch(Exception e) { // 예외가 발생한 경우 해당예외를 출력
			e.printStackTrace();
		}
	}
	
	// 현재의 시간을 가져오는 메서드
	// 게시판을 글을 작성할 때 현재 서버의 시간을 넣어주는 역할
	
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); // 현재 연결되있는 객채를 이용해서 실행 준비 단계로 만들어준다.
			rs = pstmt.executeQuery(); // 쿼리를 실행했을 때의 결과를 가져온다.
			if(rs.next()) { // 결과가 있는 경우
				return rs.getString(1); // 현재의 날짜를 그대로 반환한다. 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ""; // 데이터베이스 오류(거의 오류날일이 없다. 하지만 오류가 있을 때 빈 문자열을 반환으로 오류를 알려준다.)
	}
	
		// 게시글 번호설정 
		
		public int getNext() {
			String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC"; // 내림차순을 해서 제일 마지막에 쓰인 글의 번호를 가져온다.
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL); // 현재 연결되있는 객채를 이용해서 실행 준비 단계로 만들어준다.
				rs = pstmt.executeQuery(); // 쿼리를 실행했을 때의 결과를 가져온다.
				if(rs.next()) { // 결과가 있는 경우
					return rs.getInt(1) + 1; // 나온 결과에다가 1을 더해서 그 다음 게시글의 번호가 들어갈 수 있도록 해준다.(타입 주의)
				}
				return 1; // 첫 번째 게시물인 경우
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // 데이터베이스 오류
		}
		
		// 실제로 글을 쓰게 해주는 메서드
		
		public int write(String bbsTitle, String userID, String bbsContent) {
			String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)"; // BBS테이블 안에 총 6개의 인자가 들어갈 수 있게 해주는 쿼리문
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL); // 현재 연결되있는 객채를 이용해서 실행 준비 단계로 만들어준다.
				pstmt.setInt(1, getNext()); // bbs 데이터베이스 안에 값들을 넣어준다.
				pstmt.setString(2, bbsTitle);
				pstmt.setString(3, userID);
				pstmt.setString(4, getDate());
				pstmt.setString(5, bbsContent);
				pstmt.setInt(6, 1);
				return pstmt.executeUpdate(); // 성공적으로 수행을 했다면 이렇게 0 이상의 결과를 반환한다.
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // 그렇지 않을 경우(오류가 발생했을 땐) -1을 반환한다.(데이터베이스 오류)
		}
}
