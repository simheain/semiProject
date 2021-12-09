package kh.com.semi.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kh.com.semi.dao.CommentDAO;
import kh.com.semi.dto.CommentDTO;

@WebServlet("*.co")
public class CommentController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionDo(request, response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommentDAO dao = new CommentDAO();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String url = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String cmd = url.substring(ctxPath.length());
		System.out.println("요청한 URL : " + cmd);

		if (cmd.equals("/insertProc.co")) {
			System.out.println("댓글 등록 요청 도착");
			// int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			String comment_content = request.getParameter("comment_content");

			String nickname = "검은이빨";
			String id = "blackteeth";
			int rs = dao.insertComment(new CommentDTO(0, nickname, comment_content, null, board_seq, id));
			if (rs == 1) {
				response.getWriter().write("success");
				System.out.println("댓글 등록 성공");
			} else {
				response.getWriter().write("fail");
			}
		} else if (cmd.equals("/getCommentProc.co")) {
			System.out.println("댓글 조회 요청 도착");
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			System.out.println("board_seq : " + board_seq);
			ArrayList<CommentDTO> commentList = dao.getCommentList(board_seq);

			Gson gson = new Gson();
			String rs = gson.toJson(commentList);
			// System.out.println(rs);
			response.getWriter().write(rs);
		} else if (cmd.equals("/deleteProc.co")) {
			System.out.println("댓글 삭제 요청 도착");
			int comment_seq = Integer.parseInt(request.getParameter("comment_seq"));
			int rs = dao.deleteComment(comment_seq);
			if(rs==1) {
				response.getWriter().write("success");
			}else {
				response.getWriter().write("fail");
			}
		} else if(cmd.equals("/modifyProc.co")) {
			System.out.println("댓글 수정 요청 도착");
			int comment_seq = Integer.parseInt(request.getParameter("comment_seq"));
			
			String comment_content = request.getParameter("comment_content");
			System.out.println("target.val 테스트 : " + comment_seq);
			System.out.println("수정 테스트 : " + comment_content);
			int rs = dao.modifyComment(comment_seq,comment_content);
			if(rs ==1) {
				response.getWriter().write("success");
			}else {
				response.getWriter().write("fail");
			}
		}
	}

}
