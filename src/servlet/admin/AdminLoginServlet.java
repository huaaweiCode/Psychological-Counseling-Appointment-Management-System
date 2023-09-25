package servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Admin;
import model.service.AdminService;

/**
 * 
 * @instruction
 * ����Ա��¼���ǳ�������
 */
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ǰ��¼�Ĺ���Ա
	 */
	public static final String LOGIN_ADMIN = "LOGIN_ADMIN";
	public final static String LOGIN_CLIENT = "LOGIN_CLIENT";
	public final static String LOGIN_DOCTOR = "LOGIN_DOCTOR";

	AdminService adminService = new AdminService();

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String m = request.getParameter("m");

		if ("saveLogin".equals(m)) {

			String adminName = request.getParameter("adminName");

			String adminPwd = request.getParameter("adminPwd");

			// �����ݿ��ѯ����Ա
			Admin admin = adminService.getAdmin(adminName);

			if (admin == null || !(admin.getAdminPwd().equals(adminPwd))) {

				// ��¼ʧ��

				request.setAttribute("msg", "�û������������!");

				request.getRequestDispatcher("/admin/login.jsp").forward(request, response);

			} else if (admin.getIs_active() == 0) {

				request.setAttribute("msg", "��ǰ�˻�������!");

				request.getRequestDispatcher("/admin/login.jsp").forward(request, response);

			} else {

				// ��¼�ɹ�

				request.getSession().setAttribute(LOGIN_ADMIN, admin);

				response.sendRedirect(request.getContextPath() + "/admin/index.jsp");

			}

		} else if ("logOutAdmin".equals(m)) {

			request.getSession().removeAttribute(LOGIN_CLIENT);
			request.getSession().removeAttribute(LOGIN_ADMIN);
			request.getSession().removeAttribute(LOGIN_DOCTOR);

			request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
//			request.getRequestDispatcher("/indexAll.jsp").forward(request, response);

		} else {
			request.getSession().removeAttribute(LOGIN_CLIENT);
			request.getSession().removeAttribute(LOGIN_ADMIN);
			request.getSession().removeAttribute(LOGIN_DOCTOR);
			request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
		}

	}

}
