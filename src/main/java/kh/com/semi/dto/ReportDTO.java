package kh.com.semi.dto;

public class ReportDTO {
	public ReportDTO() {
	}

	private int report_seq;
	private String report_reason;
	private int report_count;
	private int board_seq;

	public ReportDTO(int report_seq, String report_reason, int report_count, int board_seq) {
		super();
		this.report_seq = report_seq;
		this.report_reason = report_reason;
		this.report_count = report_count;
		this.board_seq = board_seq;
	}

	public int getReport_seq() {
		return report_seq;
	}

	public void setReport_seq(int report_seq) {
		this.report_seq = report_seq;
	}

	public String getReport_reason() {
		return report_reason;
	}

	public void setReport_reason(String report_reason) {
		this.report_reason = report_reason;
	}

	public int getReport_count() {
		return report_count;
	}

	public void setReport_count(int report_count) {
		this.report_count = report_count;
	}

	public int getBoard_seq() {
		return board_seq;
	}

	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}

}
