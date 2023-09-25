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
 * 来访者登录，登出控制器
 */
public class ClientLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 当前登录的来访者
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

			// 向数据库查询来访者
			Client client = clientService.getClient(clientName);

			if (client == null || !(client.getClientPwd().equals(clientPwd))) {

				// 登录失败

				request.setAttribute("msg", "用户名或密码错误!");
				

				request.getRequestDispatcher("/client/login.jsp").forward(request, response);

			}else if(client.getIsActive() == 0) {
				
				request.setAttribute("msg", "当前账户不可用!");

				request.getRequestDispatcher("/client/login.jsp").forward(request, response);
			}
			else {

				// 登录成功

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
