package servlet.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Admin;
import bean.Client;
import model.service.ClientService;

/**
 * 
 * @instruction
 * �����ߵ�¼���ǳ�������
 */
public class ClientLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ǰ��¼��������
	 */
	public final static String LOGIN_CLIENT = "LOGIN_CLIENT";
	public static final String LOGIN_ADMIN = "LOGIN_ADMIN";
	public final static String LOGIN_DOCTOR = "LOGIN_DOCTOR";
	
	ClientService clientService = new ClientService();
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String m = request.getParameter("m");

		if ("saveLogin".equals(m)) {

			String clientName = request.getParameter("clientName");

			String clientPwd = request.getParameter("clientPwd");

			// �����ݿ��ѯ������
			Client client = clientService.getClient(clientName);

			if (client == null || !(client.getClientPwd().equals(clientPwd))) {

				// ��¼ʧ��

				request.setAttribute("msg", "�û������������!");
				

				request.getRequestDispatcher("/client/login.jsp").forward(request, response);

			}else if(client.getIsActive() == 0) {
				
				request.setAttribute("msg", "��ǰ�˻�������!");

				request.getRequestDispatcher("/client/login.jsp").forward(request, response);
			}
			else {

				// ��¼�ɹ�

				request.getSession().setAttribute(LOGIN_CLIENT, client);

				response.sendRedirect(request.getContextPath() + "/client/index.jsp");

			}

		}else if("logOutClient".equals(m)){

			request.getSession().removeAttribute(LOGIN_CLIENT);
			request.getSession().removeAttribute(LOGIN_ADMIN);
			request.getSession().removeAttribute(LOGIN_DOCTOR);
			
			request.getRequestDispatcher("/client/login.jsp").forward(request, response);
//			request.getRequestDispatcher("/indexAll.jsp").forward(request, response);

		}else {
			request.getSession().removeAttribute(LOGIN_CLIENT);
			request.getSession().removeAttribute(LOGIN_ADMIN);
			request.getSession().removeAttribute(LOGIN_DOCTOR);
			request.getRequestDispatcher("/client/login.jsp").forward(request, response);
		}

	}

}
