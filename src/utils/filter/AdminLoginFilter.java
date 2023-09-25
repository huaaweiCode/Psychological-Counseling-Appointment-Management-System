package utils.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Admin;
import servlet.admin.AdminLoginServlet;
import servlet.admin.AdminServlet;

/**
 * 
 * @instruction
 * ����Ա��¼�Ĺ�����
 */
public class AdminLoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;

		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession();

		String path = request.getRequestURI();

		if (path.endsWith("/admin/login")) {
			// ��������
			chain.doFilter(req, res);
			return;
		}
		
		
		

		Admin admin = (Admin) session.getAttribute(AdminLoginServlet.LOGIN_ADMIN);

		if (admin == null) {
			
			System.out.println("AdminLoginFilter:����Աδ��¼�������أ�" + path);

			// δ��¼
			// �ض��򵽵�¼ҳ��
			java.io.PrintWriter writer = response.getWriter();
			
			writer.println("<html><script>");
			writer.println("window.open('" + request.getContextPath() + "/admin/login','_top')");
			writer.println("</script></html>");
			writer.close();
			return;
		}

		// ��������
		chain.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
