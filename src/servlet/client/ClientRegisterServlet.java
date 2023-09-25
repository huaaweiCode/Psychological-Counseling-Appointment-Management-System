package servlet.client;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Client;
import model.service.ClientService;

/**
 * 
 * @instruction
 * ������ע�������
 */
public class ClientRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ClientService clientService = new ClientService();

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String m = request.getParameter("m");
		
		if("addClient".equals(m)) {
			
			//ע��
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String pwd = request.getParameter("pwd");
			String confirmPwd = request.getParameter("confirmPwd");
			
			if(pwd.trim() == "" || confirmPwd.trim() == "") {
				
				//�ض���ע�����
				response.sendRedirect(request.getContextPath() + "/client/reg/reg.jsp");
				return;
			}
			
			if(pwd.trim().equals(confirmPwd.trim())) {
				//��������һ��
				
				Client client = new Client();
				client.setName(name);
				client.setClientName(phone);
				client.setClientPwd(pwd);
				client.setPhone(phone);
				client.setEmail(email);
				client.setRegionTime(new Date());
				client.setIsActive(1);
				
				//����û�
				clientService.addClient(client);
				
				//�ض��򵽵�¼����
				response.sendRedirect(request.getContextPath() + "/client/login.jsp");
				return;				
			}else {
				
				//�ض���ע�����
				response.sendRedirect(request.getContextPath() + "/client/reg/reg.jsp");
				return;
			}
			
			
			
		}else if("testPhone".equals(m)) {
			
			//��֤�绰�Ƿ����
			String phone = request.getParameter("phone");
			
			clientService.checkPhoneResponse(phone,response);
			
		}else {
			
			request.getRequestDispatcher("/client/reg/reg.jsp").forward(request, response);
			
		}
	
	}

}
