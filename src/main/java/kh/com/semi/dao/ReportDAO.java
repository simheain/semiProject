package kh.com.semi.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import kh.com.semi.dto.ReportDTO;

public class ReportDAO {
	private BasicDataSource bds;

	public ReportDAO() {
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

	// 신고 내용 입력하는 메서드
	public int insertReport(ReportDTO dto) {
		String sql = "INSERT INTO tbl_report VALUES (report_seq.nextval,?,?,?)";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, dto.getReport_reason());
			pstmt.setInt(2, dto.getReport_count());
			pstmt.setInt(3, dto.getBoard_seq());
			int rs = pstmt.executeUpdate();
			if (rs != -1) {
				return rs;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// 신고 카운트 + 해주는 메서드
	public int plusReport_count(int board_seq) {
		String sql = "UPDATE tbl_report SET report_count = 	report_count + 1 WHERE board_seq = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, board_seq);
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
