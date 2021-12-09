package kh.com.semi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import kh.com.semi.dto.FileDTO;

public class FileDAO {
	private BasicDataSource bds = new BasicDataSource();

	public FileDAO() {
		try {
			Context iCtx = new InitialContext();
			Context envCtx = (Context) iCtx.lookup("java:comp/env");
			bds = (BasicDataSource) envCtx.lookup("jdbc/bds");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws Exception {
		return bds.getConnection();
	}

	// 파일 이름 가져오는 메서드
	public FileDTO getFileNames(int board_seq) {
		String sql = "SELECT * FROM tbl_boardPic WHERE board_seq = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, board_seq);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int board_picSeq = rs.getInt("board_picSeq");
				String origin_name = rs.getString("origin_name");
				String system_name = rs.getString("system_name");
				String picAddr = rs.getString("picAddr");
				return new FileDTO(board_picSeq, board_seq, origin_name, system_name,picAddr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertFile(int board_seq, String origin_name, String system_name, String filePath) {
		String sql = "INSERT INTO tbl_boardPic VALUES(board_picSeq.nextval,?,?,?,?)";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

			System.out.println(con);
			pstmt.setInt(1, board_seq);
			pstmt.setString(2, origin_name);
			pstmt.setString(3, system_name);
			pstmt.setString(4, filePath);
			int rs = pstmt.executeUpdate();
			if (rs != -1) {
				return rs;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
