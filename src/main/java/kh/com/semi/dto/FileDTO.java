package kh.com.semi.dto;

public class FileDTO {
	public FileDTO() {
	}

	private int board_picSeq;
	private int board_seq;
	private String origin_name;
	private String system_name;
	private String picAddr;

	public FileDTO(int board_picSeq, int board_seq, String origin_name, String system_name, String picAddr) {
		super();
		this.board_picSeq = board_picSeq;
		this.board_seq = board_seq;
		this.origin_name = origin_name;
		this.system_name = system_name;
		this.picAddr = picAddr;
	}

	public int getBoard_picSeq() {
		return board_picSeq;
	}

	public void setBoard_picSeq(int board_picSeq) {
		this.board_picSeq = board_picSeq;
	}

	public int getBoard_seq() {
		return board_seq;
	}

	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}

	public String getOrigin_name() {
		return origin_name;
	}

	public void setOrigin_name(String origin_name) {
		this.origin_name = origin_name;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public String getPicAddr() {
		return picAddr;
	}

	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}

}
