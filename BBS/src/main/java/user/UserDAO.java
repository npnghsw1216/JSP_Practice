package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn; // 데이터베이스에 접근하게해주는 하니의 객체
	private PreparedStatement pstmt;
	private ResultSet rs; // 어떠한 정보를 담을 수 있는 하나의 객체
	
	// Ctrl+Shift+o 로 외부 라이브러리 추가
	
	// MySQL에 접속하게 해주는 부분
	
	// try/catch문을 이용하여 예외처리
	
	public UserDAO() {
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
	
	// 로그인 기능 구현
	// 하나의 계정에 대한 로그인 시도를 해주는 메서드 
	
		public int login(String userID, String userPassword) {
			String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; // 하나의 문장을 미리 준비해 놓았다가
			try {
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery(); // 쿼리가 실행한 결과를 넣어준다.
				if(rs.next()) { // 결과(아이디가 있다)가 존재한다면 if문을 만나게 된다.
					if(rs.getString(1).equals(userPassword)) { // 결과로 나온 userPassword를 받아서 접속을 시도한 userPassword와 동일하다면
						return 1; // 로그인 성공
					}
					else
						return 0; // userPassword가 서로 동일하지 않다면 return 0(비밀번호 불일치)
				}
				return -1; // 결과가 존재하지 않는다면 return -1(아이디가 없다)의 값을 가지게 된다.
			} catch(Exception e) {
				e.printStackTrace(); // 예외가 발생한 경우 해당예외를 출력
			}
			return -2; // 데이터 베이스 오류
		}
		
	// 회원가입 기능 구현
	// 한명의 사용자를 회원가입하게 해주는 메서드
		
		public int join(User user) { 
			
		}
}
