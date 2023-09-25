package servlet.doctor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Announcement;
import bean.Client;
import bean.Doctor;
import bean.Message;
import bean.MessageBoard;
import model.service.AnnouncmentService;
import model.service.DoctorService;
import model.service.MessageBoardService;
import model.service.MessageService;
import servlet.client.ClientLoginServlet;

/**
 * 
 * @instruction
 * ��ѯʦ�ˣ���ҳ���޸Ļ�����Ϣ(��������)������
 */
public class DoctorBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DoctorService doctorService = new DoctorService();

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
		Doctor doctorNow = (Doctor) request.getSession().getAttribute(DoctorLoginServlet.LOGIN_DOCTOR);

		if ("updatePwd".equals(m)) {

			// �޸�����

			String newPwd = request.getParameter("newPwd");

			String password = request.getParameter("password");

			String msg = "";
			if (doctorNow.getDoctorPwd().equals(password)) {

				// �ɹ�
				doctorService.updateDoctorPwd(doctorNow.getDoctorId(), newPwd);

				msg = "{\"result\":\"true\",\"msg\":\"�޸ĳɹ�,�����µ�¼��\"}";

				request.getSession().removeAttribute(ClientLoginServlet.LOGIN_CLIENT);

			} else if (!doctorNow.getDoctorPwd().equals(password)) {
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ�ԭʼ�������\"}";
			} else {
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ������ԣ�\"}";
			}

			response.setContentType("application/json; charset=utf-8");

			Writer writer = response.getWriter();

			writer.write(msg);

			writer.close();

		} else if ("doctorIndex".equals(m)) {

			// �����ݣ���ҳ�õ��ģ�

			// ��ѯ������ʾ������(���µ�ʮ����
			ArrayList<MessageBoard> newMessageBoardList = messageBoardService.getMessageBoardNum(10);

			request.setAttribute("newMessageBoardList", newMessageBoardList);

			// ��ѯ����δ������Ϣ(���µ�ʮ����
			ArrayList<Message> messageList = messageService.getMessageNum(10, doctorNow.getDoctorId(), "doctor");

			request.setAttribute("messageList", messageList);

			// ��ѯ������ʾ�Ĺ���(���µ�ʮ��)
			ArrayList<Announcement> announcmentList = announcmentService.getAnnouncmentNum(10);

			request.setAttribute("announcmentList", announcmentList);

			// ת������ҳ(�����ߺ���ѯʦ����һ����ҳ)
			request.getRequestDispatcher("/client/home.jsp").forward(request, response);

		} else if ("doctorInfo".equals(m)) {

			// �鿴������Ϣ

			request.getRequestDispatcher("/doctor/doctor_info.jsp").forward(request, response);
		}

	}

}
