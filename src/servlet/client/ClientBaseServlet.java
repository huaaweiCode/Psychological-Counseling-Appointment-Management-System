package servlet.client;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Admin;
import bean.Announcement;
import bean.Client;
import bean.Message;
import bean.MessageBoard;
import model.service.AnnouncmentService;
import model.service.ClientService;
import model.service.MessageBoardService;
import model.service.MessageService;
import servlet.admin.AdminLoginServlet;

/**
 * 
 * @instruction
 * �����߶ˣ���ҳ���޸Ļ�����Ϣ(��������)������
 */
public class ClientBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ClientService clientService = new ClientService();

	MessageBoardService messageBoardService = new MessageBoardService();

	MessageService messageService = new MessageService();

	AnnouncmentService announcmentService = new AnnouncmentService();

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String m = request.getParameter("m");
		
		// ��ǰ��¼�û�
		Client clientNow = (Client) request.getSession().getAttribute(ClientLoginServlet.LOGIN_CLIENT);

		if ("updatePwd".equals(m)) {

			// �޸�����

			String newPwd = request.getParameter("newPwd");

			String password = request.getParameter("password");

			String msg = "";
			if (clientNow.getClientPwd().equals(password)) {

				// �ɹ�
				clientService.updateClientPwd(clientNow.getClientId(), newPwd);

				msg = "{\"result\":\"true\",\"msg\":\"�޸ĳɹ�,�����µ�¼��\"}";

				request.getSession().removeAttribute(ClientLoginServlet.LOGIN_CLIENT);

			} else if (!clientNow.getClientPwd().equals(password)) {
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ�ԭʼ�������\"}";
			} else {
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ������ԣ�\"}";
			}

			response.setContentType("application/json; charset=utf-8");

			Writer writer = response.getWriter();

			writer.write(msg);

			writer.close();

		} else if ("clientIndex".equals(m)) {

			// �����ݣ���ҳ�õ��ģ�

			// ��ѯ������ʾ������(���µ�ʮ����
			ArrayList<MessageBoard> newMessageBoardList = messageBoardService.getMessageBoardNum(10);

			request.setAttribute("newMessageBoardList", newMessageBoardList);

			// ��ѯ����δ������Ϣ(���µ�ʮ����
			ArrayList<Message> messageList = messageService.getMessageNum(10, clientNow.getClientId(), "client");

			request.setAttribute("messageList", messageList);

			// ��ѯ������ʾ�Ĺ���(���µ�ʮ��)
			ArrayList<Announcement> announcmentList = announcmentService.getAnnouncmentNum(10);

			request.setAttribute("announcmentList", announcmentList);

			// ת������ҳ
			request.getRequestDispatcher("/client/home.jsp").forward(request, response);

		} else if ("clientInfo".equals(m)) {

			// �鿴������Ϣ

			request.getRequestDispatcher("/client/client_info.jsp").forward(request, response);
		}else if("updateBase".equals(m)){
			
			//�޸Ļ�����Ϣ
			
			Client client = new Client();
			client.setName(request.getParameter("name"));
			client.setSex( Integer.parseInt( request.getParameter("sex")));
			client.setAge(Integer.parseInt(request.getParameter("age")));
			client.setPhone(request.getParameter("phone"));
			client.setEmail(request.getParameter("email"));
			
			
			//����
			int i = clientService.updateClientBase(client,clientNow.getClientId());
			
			Writer writer = response.getWriter();
			
			//������Ӧ����
			response.setContentType("application/json; charset=utf-8");
			
			String msg = "";
			if(i > 0) {
				//�ɹ�
				msg = "{\"result\":\"true\",\"msg\":\"�޸ĳɹ�,��ˢ�£�\"}";
				
				
				//�����ڲ�ѯһ����������Ϣ
				Client clientNew = clientService.getClient(clientNow.getClientName());
				
				request.getSession().setAttribute(ClientLoginServlet.LOGIN_CLIENT,clientNew);
				
			}else {
				//ʧ��
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ���ˢ��ҳ�������\"}";
			}
			writer.write(msg);
			writer.close();
		
		}

	}

}
