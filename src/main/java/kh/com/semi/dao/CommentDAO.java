package kh.com.semi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import kh.com.semi.dto.CommentDTO;

public class CommentDAO {
	private BasicDataSource bds;

	public CommentDAO() {
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

	// 댓글 목록 긁어오기
	public ArrayList<CommentDTO> getCommentList(int board_seq) {
		String sql = "SELECT * FROM tbl_comment WHERE board_seq = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

			pstmt.setInt(1, board_seq);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<CommentDTO> list = new ArrayList<>();
			while (rs.next()) {
				int comment_seq = rs.getInt("comment_seq");
				String writer_nickname = rs.getString("writer_nickname");
				String comment_content = rs.getString("comment_content");
				Date written_date = rs.getDate("written_date");
				String writer_id = rs.getString("writer_id");
				list.add(new CommentDTO(comment_seq, writer_nickname, comment_content, written_date, board_seq,
						writer_id));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 댓글 등록 메서드
	public int insertComment(CommentDTO dto) {
		String sql = "INSERT INTO tbl_comment values(comment_seq.nextval,?,?,sysdate,?,?)";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, dto.getWriter_nickname());
			pstmt.setString(2, dto.getComment_content());
			pstmt.setInt(3,dto.getBoard_seq());
			pstmt.setString(4,dto.getWriter_id());
			
			int rs = pstmt.executeUpdate();
			if(rs!=-1) {
				return rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 댓글 삭제 메서드
	public int deleteComment(int comment_seq) {
		String sql = "DELETE FROM tbl_comment WHERE comment_seq = ? ";
		try(Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, comment_seq);
			int rs = pstmt.executeUpdate();
			if(rs!=-1) {
				return rs;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	// 댓글 수정 메서드
	public int modifyComment(int comment_seq, String comment_content) {
		String sql = "UPDATE tbl_comment SET comment_content=? WHERE comment_seq =?";
		try(Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, comment_content);
			pstmt.setInt(2, comment_seq);
			int rs = pstmt.executeUpdate();
			if(rs!=-1) {
				return rs;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}return -1;
	}
}
