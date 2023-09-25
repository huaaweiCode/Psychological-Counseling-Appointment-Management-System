package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Client;
import bean.MessageBoard;
import model.service.MessageBoardService;
import servlet.client.ClientLoginServlet;

/**
 * 
 * @instruction
 * ����ģ�������
 */
public class MessageBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MessageBoardService messageBoardService = new MessageBoardService();
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String m = request.getParameter("m");

		if ("listMessageBoard".equals(m)) {

			// ���ܲ�ѯ����
			
			String creater = request.getParameter("creater");
			String context = request.getParameter("context");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			

			// ��������װ��search��
			Map<String, String> search = new HashMap<String, String>();
			search.put("creater", creater);
			search.put("context", context);
			search.put("startTime", startTime);
			search.put("endTime", endTime);

			// ��ѯ��������������
			List<MessageBoard> list = messageBoardService.listSearch(search);
			

			request.setAttribute("search", search);

			request.setAttribute("messageBoardList", list);

			request.setAttribute("listSize", list.size());
			
			//�õ���ǰ��¼���û�
			Map<String, Object> currentUser =MessageServlet.getCurrentUser(request);
			
			if(currentUser.get("reqeustUser").equals("admin")) { //����Ա
				
				request.getRequestDispatcher("/admin/messageBoard.jsp").forward(request, response);
				
			}else {//��ͨ�����ߺ���ѯʦ
				
				request.getRequestDispatcher("/client/messageBoard.jsp").forward(request, response);
			}
			

			

		} else if ("updateActive".equals(m)) {

			// ��ʾ�������л�

			String messageBoardId = request.getParameter("id");
			

			String action = request.getParameter("action");
			
			messageBoardService.toggleMessageBoardActive(messageBoardId, action, response);

		} else if ("selecteMessageBoard".equals(m)) {// ajax

			// �鿴����

			String messageBoardId = request.getParameter("id");

			// ��ѯ�����ҽ����ݷ��أ�JSON��ʽ��
			messageBoardService.getMessageBoardToResponse(Integer.parseInt(messageBoardId), response);

		} else if ("addMessageBoard".equals(m)) {
			
			// ����

			// ȡ�ñ����ֵ
			
			String context = request.getParameter("context");
			
			//������
			Client client =  (Client) request.getSession().getAttribute(ClientLoginServlet.LOGIN_CLIENT);


			// ���������װΪMessageBoard����
			MessageBoard messageBoard = new MessageBoard();
			messageBoard.setContext(context);
			messageBoard.setCreateTime(new Date());
			messageBoard.setIsActive(1);
			messageBoard.setCreaterId(client.getClientId());
			
			//����һ������
			messageBoardService.addMessageBoard(messageBoard,response);
			
		}

	}

}
