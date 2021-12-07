package kh.com.semi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kh.com.semi.dao.BoardDAO;
import kh.com.semi.dto.BoardDTO;
import kh.com.semi.service.BoardService;


@WebServlet("*.bo")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionDo(request, response);
	}

private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BoardDAO dao = new BoardDAO();
		
		String url = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String cmd = url.substring(ctxPath.length());
		System.out.println("요청한 URL : " + cmd);
		
		if(cmd.equals("/toBoard.bo")) { // 데이터 받으려면 toBoard.bo 타서 가야함.
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : " + currentPage);
			BoardService service = new BoardService();
			HashMap<String,Object> naviMap = service.getPageNavi(currentPage);
			ArrayList<BoardDTO> list = service.getBoardList((int)naviMap.get("currentPage"));
			String id = "aaa123";
			
			System.out.println("test.jsp의 요청");
			ArrayList<BoardDTO> dtoList = dao.selectAll();
			request.setAttribute("dtoList", dtoList);

			  if(list!=null) {
				RequestDispatcher rd = request.getRequestDispatcher("/board/Board.jsp");
				request.setAttribute("naviMap", naviMap);
				request.setAttribute("list",list);
				rd.forward(request, response);
			} 
			 
		}
		else if(cmd.equals("/toWrite.bo")) {
			response.sendRedirect("/board/Write.jsp");
		}else if(cmd.equals("/writeProc.bo")) {
			String filePath = request.getServletContext().getRealPath("files");
			File dir = new File(filePath);
			if (!dir.exists())
				dir.mkdir();
			int fileSize = 1024 * 1024 * 10;
			
			String id = "aaa123"; // 나중에 수정해야함 임의 값 지정한 것
			String writer_nickname = "갈매기"; // 나중에 수정해야함 임의 값 지정한 것임
			try {
				MultipartRequest multi = new MultipartRequest(request,filePath,fileSize,"utf-8",
						new DefaultFileRenamePolicy());
				String board_title = multi.getParameter("title");
				String board_content = multi.getParameter("board_content");
				System.out.println("board_title :" + board_title);
				int rs = dao.insert(new BoardDTO(0,writer_nickname,board_title,board_content,null,0,0,null,id));
			}catch(Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("/toBoard.bo?currentPage=1");
		}else if(cmd.equals("/toDetailView.bo")){
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("게시글 번호 : " + board_seq);
			System.out.println("currentPage : " + currentPage);
			
			// 조회수 + 해주는 메서드 호출
			dao.plusView_count(board_seq);
			BoardDTO dto = dao.selectBySeq(board_seq);
			if(dto!= null) {
				RequestDispatcher rd = request.getRequestDispatcher("/board/DetailView.jsp");
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("dto", dto);
				rd.forward(request,response);
			}
		}else if(cmd.equals("/deleteProc.bo")) {
			System.out.println("삭제 요청 도착");
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			System.out.println("삭제 요청된 게시글 번호 : " + board_seq);
			
			int rs = dao.deleteBySeq(board_seq);
			if(rs!=-1) {
				response.sendRedirect("/toBoard.bo?currentPage=1");
			}
		}else if(cmd.equals("/modifyProc.bo")) {
			System.out.println("수정 요청 도착");
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			String title = request.getParameter("title");
			String board_content = request.getParameter("board_content");
			System.out.println("board_seq : " + board_seq);
			System.out.println("title : " + title);
			System.out.println("board_content : " + board_content);
			int rs = dao.modifyBySeq(board_seq, title, board_content);
			if(rs!=-1) {
				response.sendRedirect("/toDetailView.bo?board_seq=" + board_seq +"&currentPage=" + currentPage);
			}
		}
	}
	

}