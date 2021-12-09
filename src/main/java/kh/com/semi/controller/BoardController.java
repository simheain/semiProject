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
import kh.com.semi.dao.FileDAO;
import kh.com.semi.dto.BoardDTO;
import kh.com.semi.dto.FileDTO;
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
		System.out.println("��û�� URL : " + cmd);
		
		if(cmd.equals("/toBoard.bo")) { // ������ �������� toBoard.bo Ÿ�� ������.
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : " + currentPage);
			BoardService service = new BoardService();
			HashMap<String,Object> naviMap = service.getPageNavi(currentPage);
			ArrayList<BoardDTO> list = service.getBoardList((int)naviMap.get("currentPage"));
			String id = "aaa123";
			
			System.out.println("test.jsp�� ��û");
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
			String folderName = "files"; 
			String filePath = request.getServletContext().getRealPath(folderName); //~~�� �ִ� files��� ���� , html ���� ���ٸ� ${pageContext.request.contextPath}()���� ����
			System.out.println("filePath : " + filePath);
			File dir = new File(filePath);
			if (!dir.exists())
				dir.mkdir();
			int fileSize = 1024 * 1024 * 10;
			
			String id = "aaa123"; // ���߿� �����ؾ��� ���� �� ������ ��
			String writer_nickname = "���ű�"; // ���߿� �����ؾ��� ���� �� ������ ����
			try {
				MultipartRequest multi = new MultipartRequest(request,filePath,fileSize,"utf-8",
						new DefaultFileRenamePolicy());
				
				String board_title = multi.getParameter("title");
				String board_content = multi.getParameter("board_content");
				
				int rs = dao.insert(new BoardDTO(0,writer_nickname,board_title,board_content,null,0,0,null,id));
				int board_seq = dao.getBoardSeq();
				System.out.println("���� ������ Ȯ���Դϴ�." + board_seq);
				
				String origin_name = multi.getOriginalFileName("file");
				String system_name = multi.getFilesystemName("file");
				FileDAO daoFile = new FileDAO();
				int rsFile = daoFile.insertFile(board_seq,origin_name,system_name,folderName);
				if(rs!=-1 || rsFile !=-1) {
					response.sendRedirect("/toBoard.bo?currentPage=1");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(cmd.equals("/toDetailView.bo")){
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("�Խñ� ��ȣ : " + board_seq);
			System.out.println("currentPage : " + currentPage);
			// ������ ���⸸ �ҰŸ� origin name�� ���� �ʿ䰡 ����. ����� �̸��� �˸� ������.
			// ��ȸ�� + ���ִ� �޼��� ȣ��
			FileDAO fileDAO = new FileDAO();
			FileDTO fileDTO = new FileDTO();
			dao.plusView_count(board_seq);
			BoardDTO dto = dao.selectBySeq(board_seq);
			if(dto!= null) {
				fileDTO = fileDAO.getFileNames(board_seq);
				// System.out.println("������ʹ� ���� �����Դϴ� : " + fileDTO.getSystem_name());
				request.setAttribute("fileDTO", fileDTO);
				RequestDispatcher rd = request.getRequestDispatcher("/board/DetailView.jsp");
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("dto", dto);
				rd.forward(request,response);
			}
		}else if(cmd.equals("/deleteProc.bo")) {
			System.out.println("���� ��û ����");
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			System.out.println("���� ��û�� �Խñ� ��ȣ : " + board_seq);
			
			int rs = dao.deleteBySeq(board_seq);
			if(rs!=-1) {
				response.sendRedirect("/toBoard.bo?currentPage=1");
			}
		}else if(cmd.equals("/modifyProc.bo")) {
			System.out.println("���� ��û ����");
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
		}else if (cmd.equals("/RecommendProc.bo")) {
			System.out.println("��õ��û ����");
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			int board_seq = Integer.parseInt(request.getParameter("board_seq"));
			int rs = dao.plusRecommended_count(board_seq);
			int minus = dao.minusView_count(board_seq);
			System.out.println("��õ�� ī��Ʈ : " + rs);
			if(rs!=-1 && minus !=-1) {
				response.sendRedirect("/toDetailView.bo?board_seq=" + board_seq +"&currentPage=" + currentPage);
			}
		}
	}
	

}
