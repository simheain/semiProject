package kh.com.semi.dto;

import java.sql.Date;

public class CommentDTO {
	public CommentDTO() {
	}

	private int comment_seq;
	private String writer_nickname;
	private String comment_content;
	private Date written_date;
	private int board_seq;
	private String writer_id;

	public CommentDTO(int comment_seq, String writer_nickname, String comment_content, Date written_date, int board_seq,
			String writer_id) {
		super();
		this.comment_seq = comment_seq;
		this.writer_nickname = writer_nickname;
		this.comment_content = comment_content;
		this.written_date = written_date;
		this.board_seq = board_seq;
		this.writer_id = writer_id;
	}

	public int getComment_seq() {
		return comment_seq;
	}

	public void setComment_seq(int comment_seq) {
		this.comment_seq = comment_seq;
	}

	public String getWriter_nickname() {
		return writer_nickname;
	}

	public void setWriter_nickname(String writer_nickname) {
		this.writer_nickname = writer_nickname;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public Date getWritten_date() {
		return written_date;
	}

	public void setWritten_date(Date written_date) {
		this.written_date = written_date;
	}

	public int getBoard_seq() {
		return board_seq;
	}

	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

}
