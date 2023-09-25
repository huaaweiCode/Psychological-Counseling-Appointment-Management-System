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
import bean.Doctor;
import servlet.admin.AdminLoginServlet;
import servlet.doctor.DoctorLoginServlet;

/**
 * 
 * @instruction
 * ×ÉÑ¯Ê¦µÇÂ¼¹ýÂËÆ÷
 */
public class DoctorLoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public DoctorLoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;

		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession();

		String path = request.getRequestURI();


		Doctor doctor = (Doctor) session.getAttribute(DoctorLoginServlet.LOGIN_DOCTOR);

		if (doctor == null) {

			System.out.println("DoctorLoginFilter:×ÉÑ¯Ê¦Î´µÇÂ¼£¬±»À¹½Ø£º" + path);
			
			// Î´µÇÂ¼
			// ÖØ¶¨Ïòµ½µÇÂ¼Ò³Ãæ
			java.io.PrintWriter writer = response.getWriter();
			
			writer.println("<html><script>");
			writer.println("window.open('" + request.getContextPath() + "/doctor/DoctorLoginServlet','_top')");
			writer.println("</script></html>");
			writer.close();
			return;
		}

		// ¹ýÂËÆ÷Á´
		chain.doFilter(req, res);

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
