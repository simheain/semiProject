package kh.com.semi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import kh.com.semi.dto.BoardDTO;

public class BoardDAO {
	private BasicDataSource bds;

	public BoardDAO() {
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

	// seq_board�� ������ �޼���
	public int getBoardSeq() {
		String sql = "SELECT board_seq FROM tbl_board ORDER BY 1 DESC";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// �Խ��ǿ� �� ����ϴ� �޼���
	public int insert(BoardDTO dto) {
		String sql = "INSERT INTO tbl_board VALUES(board_seq.nextval,?,?,?,sysdate,0,0,'n',?)";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, dto.getWriter_nickname());
			pstmt.setString(2, dto.getBoard_title());
			pstmt.setString(3, dto.getBoard_content());
			pstmt.setString(4, dto.getId());

			int rs = pstmt.executeUpdate();
			if (rs != -1) {
				return rs;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// �Խ��� ��ü ��� ������� �޼���
	public ArrayList<BoardDTO> selectAll() {
		String sql = "SELECT * FROM tbl_board";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

			ResultSet rs = pstmt.executeQuery();
			ArrayList<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				int board_seq = rs.getInt("board_seq");
				String writer_nickname = rs.getString("writer_nickname");
				String board_title = rs.getString("board_title");
				String board_content = rs.getString("board_content");
				Date written_date = rs.getDate("written_date");
				int view_count = rs.getInt("view_count");
				int recommended_count = rs.getInt("recommended_count");
				String report_yn = rs.getString("report_yn");
				String id = rs.getString("id");

				list.add(new BoardDTO(board_seq, writer_nickname, board_title, board_content, written_date, view_count,
						recommended_count, report_yn, id));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��õ�� + ���ִ� �޼���
	public int plusRecommended_count(int board_seq) {
		String sql = "UPDATE tbl_board SET recommended_count = recommended_count +1 WHERE board_seq = ?";
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

	// ��ȸ�� - ���ִ� �޼��� (��õ ������ �� reload �Ǽ� ��ȸ�� �ö󰡴°� �Ž����� �ۼ���)
	public int minusView_count(int board_seq) {
		String sql = "UPDATE tbl_board SET view_count = view_count -1 WHERE board_seq = ?";
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

	// ��ȸ�� + ���ִ� �޼���
	public int plusView_count(int board_seq) {
		String sql = "UPDATE tbl_board SET view_count = view_count +1 WHERE board_seq = ?";
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

	// board_seq ���� ��ȸ�ϴ� �޼���
	public BoardDTO selectBySeq(int board_seq) {
		String sql = "SELECT * FROM tbl_board WHERE board_seq = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, board_seq);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String writer_nickname = rs.getString("writer_nickname");
				String board_title = rs.getString("board_title");
				String board_content = rs.getString("board_content");
				Date written_date = rs.getDate("written_date");
				int view_count = rs.getInt("view_count");
				int recommended_count = rs.getInt("recommended_count");
				String report_yn = rs.getString("report_yn");
				String id = rs.getString("id");

				BoardDTO dto = new BoardDTO(board_seq, writer_nickname, board_title, board_content, written_date,
						view_count, recommended_count, report_yn, id);
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// �Խñ� �����ϴ� �޼���
	public int deleteBySeq(int board_seq) {
		String sql = "DELETE FROM tbl_board WHERE board_seq = ?";
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

	// �Խñ� �����ϴ� �޼���
	public int modifyBySeq(int board_seq, String board_title, String board_content) {
		String sql = "UPDATE tbl_board SET board_title=?, board_content=? WHERE board_seq =? ";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, board_title);
			pstmt.setString(2, board_content);
			pstmt.setInt(3, board_seq);

			int rs = pstmt.executeUpdate();
			if (rs != -1) {
				return rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// �Խñ� �� ���� ��ȸ�ϴ� �޼��� (����¡�� �ʿ�)
	public int countAll() {
		String sql = "SELECT count(*) FROM tbl_board";
		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// ���� ���� �� ���� ��ȸ�ϴ� �޼��� (����¡�� �ʿ�)
	public ArrayList<BoardDTO> getBoardList(int startRange, int endRange) {
		String sql = "SELECT * FROM " + "(SELECT ROW_NUMBER() OVER (ORDER BY board_seq desc) ����,"
				+ "a.* FROM tbl_board a)" + "WHERE ���� BETWEEN ? AND ?";

		try (Connection con = this.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

			pstmt.setInt(1, startRange);
			pstmt.setInt(2, endRange);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				int board_seq = rs.getInt("board_seq");
				String board_title = rs.getString("board_title");
				String writer_nickname = rs.getString("writer_nickname");
				Date written_date = rs.getDate("written_date");
				int view_count = rs.getInt("view_count");
				int recommended_count = rs.getInt("recommended_count");
				list.add(new BoardDTO(board_seq, writer_nickname, board_title, null, written_date, view_count,
						recommended_count, null, null));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
