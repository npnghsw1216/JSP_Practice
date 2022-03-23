package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
	
	private Connection conn; // �����ͺ��̽��� �����ϰ����ִ� �ϴ��� ��ü
	private ResultSet rs; // ��� ������ ���� �� �ִ� �ϳ��� ��ü
	
	// Ctrl+Shift+o �� �ܺ� ���̺귯�� �߰�
	
	// MySQL�� �����ϰ� ���ִ� �κ�
	
	// try/catch���� �̿��Ͽ� ����ó��
	
	public BbsDAO() {
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
	
	// ������ �ð��� �������� �޼���
	// �Խ����� ���� �ۼ��� �� ���� ������ �ð��� �־��ִ� ����
	
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); // ���� ������ִ� ��ä�� �̿��ؼ� ���� �غ� �ܰ�� ������ش�.
			rs = pstmt.executeQuery(); // ������ �������� ���� ����� �����´�.
			if(rs.next()) { // ����� �ִ� ���
				return rs.getString(1); // ������ ��¥�� �״�� ��ȯ�Ѵ�. 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ""; // �����ͺ��̽� ����(���� ���������� ����. ������ ������ ���� �� �� ���ڿ��� ��ȯ���� ������ �˷��ش�.)
	}
	
		// �Խñ� ��ȣ���� 
		
		public int getNext() {
			String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC"; // ���������� �ؼ� ���� �������� ���� ���� ��ȣ�� �����´�.
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL); // ���� ������ִ� ��ä�� �̿��ؼ� ���� �غ� �ܰ�� ������ش�.
				rs = pstmt.executeQuery(); // ������ �������� ���� ����� �����´�.
				if(rs.next()) { // ����� �ִ� ���
					return rs.getInt(1) + 1; // ���� ������ٰ� 1�� ���ؼ� �� ���� �Խñ��� ��ȣ�� �� �� �ֵ��� ���ش�.(Ÿ�� ����)
				}
				return 1; // ù ��° �Խù��� ���
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // �����ͺ��̽� ����
		}
		
		// ������ ���� ���� ���ִ� �޼���
		
		public int write(String bbsTitle, String userID, String bbsContent) {
			String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)"; // BBS���̺� �ȿ� �� 6���� ���ڰ� �� �� �ְ� ���ִ� ������
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL); // ���� ������ִ� ��ä�� �̿��ؼ� ���� �غ� �ܰ�� ������ش�.
				pstmt.setInt(1, getNext()); // bbs �����ͺ��̽� �ȿ� ������ �־��ش�.
				pstmt.setString(2, bbsTitle);
				pstmt.setString(3, userID);
				pstmt.setString(4, getDate());
				pstmt.setString(5, bbsContent);
				pstmt.setInt(6, 1);
				return pstmt.executeUpdate(); // ���������� ������ �ߴٸ� �̷��� 0 �̻��� ����� ��ȯ�Ѵ�.
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // �׷��� ���� ���(������ �߻����� ��) -1�� ��ȯ�Ѵ�.(�����ͺ��̽� ����)
		}
		
		// �Խ��� �� ��� ��� ����
		
		public ArrayList<Bbs> getList(int pageNumber) {
			String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; // ������ �����ʾ� bbsAvailable�� ���� 1�� ���� �� ������ 10������ �������� �ϴ� ������
			ArrayList<Bbs> list = new ArrayList<Bbs>(); // Bbs��� Ŭ�������� ������ �ν��Ͻ��� ������ �� �ִ� ����Ʈ�� �����.
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext() - (pageNumber -1)*10);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					list.add(bbs); // ����Ʈ�� �ش� �ν��Ͻ� ��ȯ
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		// �Խñ� ����¡ ó�� �޼���
		
		public boolean nextPage(int pageNumber) {
			String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
			ArrayList<Bbs> list = new ArrayList<Bbs>(); 
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext() - (pageNumber -1)*10);
				rs = pstmt.executeQuery();
				if(rs.next()) { // ����� �ϳ��� �����Ѵٸ� 
					return true; // ���� �������� �Ѿ �� �ְ� �˷��ش�.(1������ -> 2������)
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false; // �Խñ� 10������ 11�� ���� true�� ���� 2�������� �̵�
		}
		
		// �Խñ� ��Ͽ��� �� �Խñ��� Ŭ���ؼ� Ȯ���ϰ� ���ִ� ��� 
		
		public Bbs getBbs(int bbsID) {
			String SQL = "SELECT * FROM BBS WHERE bbsID = ?"; // bbsID�� Ư���� ������ ��� ��� ������ ������ �� �ֵ��� ���ִ� ������
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				rs = pstmt.executeQuery();
				if(rs.next()) { // ����� �ϳ��� �����Ѵٸ� 
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					return bbs; 
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null; // �ش���� �������� ������ null ��ȯ
		}
		
		// �Խù� ����(��ư) ��� ����
		
		public int update(int bbsID, String bbsTitle, String bbsContent) {
			String SQL = "UPDATE BBS SET bbsTitle =  ?, bbsContent = ? WHERE bbsID = ?"; // Ư���� bbsID�� �ش��ϴ� bbsTitle, bbsContent�� �ٲ��ش�.
			try {	
				PreparedStatement pstmt = conn.prepareStatement(SQL); // ���� ������ִ� ��ä�� �̿��ؼ� ���� �غ� �ܰ�� ������ش�.
				pstmt.setString(1, bbsTitle); // bbs �����ͺ��̽� �ȿ� ������ �־��ش�.
				pstmt.setString(2, bbsContent);
				pstmt.setInt(3, bbsID);
				return pstmt.executeUpdate(); // ���������� ������ �ߴٸ� �̷��� 0 �̻��� ����� ��ȯ�Ѵ�.
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // �׷��� ���� ���(������ �߻����� ��) -1�� ��ȯ�Ѵ�.(�����ͺ��̽� ����)
		}	
		
		// �Խñ� ����(��ư) ��� ����
		
		public int delete(int bbsID) {
			String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?"; // ���� �����ϴ��� �ۿ� ���� ������ �������� �� �ֵ��� bbsAvailable���� 0���� �ٲ۴�.
			try {	
				PreparedStatement pstmt = conn.prepareStatement(SQL); // ���� ������ִ� ��ä�� �̿��ؼ� ���� �غ� �ܰ�� ������ش�.
				pstmt.setInt(1, bbsID); 
				return pstmt.executeUpdate(); // ���������� ������ �ߴٸ� �̷��� 0 �̻��� ����� ��ȯ�Ѵ�.
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // �׷��� ���� ���(������ �߻����� ��) -1�� ��ȯ�Ѵ�.(�����ͺ��̽� ����)
		}	
}
