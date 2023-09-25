package servlet.doctor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Client;
import bean.Doctor;
import model.service.DoctorService;

/**
 * 
 * @instruction
 * ��ѯʦ��¼���ǳ�������
 */
public class DoctorLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public final static String LOGIN_DOCTOR = "LOGIN_DOCTOR";
	public static final String LOGIN_ADMIN = "LOGIN_ADMIN";
	public final static String LOGIN_CLIENT = "LOGIN_CLIENT";
	
	DoctorService doctorService = new DoctorService();
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String m = request.getParameter("m");

		if ("saveLogin".equals(m)) {

			String doctorName = request.getParameter("doctorName");

			String doctorPwd = request.getParameter("doctorPwd");

			// �����ݿ��ѯ������
			Doctor doctor = doctorService.getDoctor(doctorName);

			if (doctor == null || !(doctor.getDoctorPwd().equals(doctorPwd))) {

				// ��¼ʧ��

				request.setAttribute("msg", "�û������������!");

				request.getRequestDispatcher("/doctor/login.jsp").forward(request, response);

			}else if(doctor.getIsActive() == 0) {
				
				// ��¼ʧ��

				request.setAttribute("msg", "��ǰ�˻�������!");

				request.getRequestDispatcher("/doctor/login.jsp").forward(request, response);
				
			}
			else {

				// ��¼�ɹ�

				request.getSession().setAttribute(LOGIN_DOCTOR, doctor);

				response.sendRedirect(request.getContextPath() + "/doctor/index.jsp");

			}

		}else if("logOutClient".equals(m)){

			request.getSession().removeAttribute(LOGIN_CLIENT);
			request.getSession().removeAttribute(LOGIN_ADMIN);
			request.getSession().removeAttribute(LOGIN_DOCTOR);
			
			request.getRequestDispatcher("/doctor/login.jsp").forward(request, response);
//			request.getRequestDispatcher("/indexAll.jsp").forward(request,response);
			
		}else {
			request.getSession().removeAttribute(LOGIN_CLIENT);
			request.getSession().removeAttribute(LOGIN_ADMIN);
			request.getSession().removeAttribute(LOGIN_DOCTOR);
			request.getRequestDispatcher("/doctor/login.jsp").forward(request, response);
		}

	}

}
