package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn; // �����ͺ��̽��� �����ϰ����ִ� �ϴ��� ��ü
	private PreparedStatement pstmt;
	private ResultSet rs; // ��� ������ ���� �� �ִ� �ϳ��� ��ü
	
	// Ctrl+Shift+o �� �ܺ� ���̺귯�� �߰�
	
	// MySQL�� �����ϰ� ���ִ� �κ�
	
	// try/catch���� �̿��Ͽ� ����ó��
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS"; // local�� ���� ��ǻ�ͷν� 3306��Ʈ(MySQL�ּ�)�� BBS�� ����
			String dbID = "root"; // DB ID : DB ID ����
			String dbPassword = "root"; // DB Password : DB Password�Է�
			Class.forName("com.mysql.jdbc.Driver"); // MySQLDriver : MySQL�� ������ �� �ֵ��� �Ű�ü��Ȱ�� ���ִ� �ϳ��� ���̺귯��
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // ���������� Driver�����ؼ� URL�˻�, ���̵� ����, �н����� ����
		} catch(Exception e) { // ���ܰ� �߻��� ��� �ش翹�ܸ� ���
			e.printStackTrace();
		}
	}
	
	// �α��� ��� ����
	// �ϳ��� ������ ���� �α��� �õ��� ���ִ� �޼��� 
	
		public int login(String userID, String userPassword) {
			String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; // �ϳ��� ������ �̸� �غ��� ���Ҵٰ�
			try {
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery(); // ������ ������ ����� �־��ش�.
				if(rs.next()) { // ���(���̵� �ִ�)�� �����Ѵٸ� if���� ������ �ȴ�.
					if(rs.getString(1).equals(userPassword)) { // ����� ���� userPassword�� �޾Ƽ� ������ �õ��� userPassword�� �����ϴٸ�
						return 1; // �α��� ����
					}
					else
						return 0; // userPassword�� ���� �������� �ʴٸ� return 0(��й�ȣ ����ġ)
				}
				return -1; // ����� �������� �ʴ´ٸ� return -1(���̵� ����)�� ���� ������ �ȴ�.
			} catch(Exception e) {
				e.printStackTrace(); // ���ܰ� �߻��� ��� �ش翹�ܸ� ���
			}
			return -2; // ������ ���̽� ����
		}
		
	// ȸ������ ��� ����
	// �Ѹ��� ����ڸ� ȸ�������ϰ� ���ִ� �޼���
		
		public int join(User user) { 
			
		}
}
