package servlet.admin;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.service.ClientService;
import org.apache.struts2.json.JSONUtil;

import bean.Admin;
import bean.Announcement;
import bean.Message;
import bean.MessageBoard;
import model.service.AdminService;
import model.service.AnnouncmentService;
import model.service.ClientArchiveService;
import model.service.DoctorService;
import model.service.MessageBoardService;
import model.service.MessageService;

/**
 * 
 * @instruction
 * ����Ա�ˣ���ҳ���޸Ļ�����Ϣ(��������)������
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	AdminService adminService = new AdminService();
	
	DoctorService doctorService = new DoctorService();
	
	ClientService clientService = new ClientService();
	
	ClientArchiveService clientArchiveService = new ClientArchiveService();
	
	MessageBoardService messageBoardService = new MessageBoardService();
	
	MessageService messageService = new MessageService();
	
	AnnouncmentService announcmentService = new AnnouncmentService();
	
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String m = request.getParameter("m");
		
		//��ǰ��¼�û�
		Admin adminNow = (Admin)request.getSession().getAttribute(AdminLoginServlet.LOGIN_ADMIN);

		if("updatePwd".equals(m)){
			
			//�޸�����
			
			String newPwd = request.getParameter("newPwd");
			
			String password = request.getParameter("password");
			
			String msg = "";
			if( adminNow.getAdminPwd().equals(password) ) {
				
				//�ɹ�
				adminService.updateAdminPwd(adminNow.getAdminId(),newPwd);
				
				msg = "{\"result\":\"true\",\"msg\":\"�޸ĳɹ�,�����µ�¼��\"}";
				
				request.getSession().removeAttribute(AdminLoginServlet.LOGIN_ADMIN);
				
				
			}else if(!adminNow.getAdminPwd().equals(password)){
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ�ԭʼ�������\"}";
			}else {
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ������ԣ�\"}";
			}
			
			
			response.setContentType("application/json; charset=utf-8");
			
			Writer writer = response.getWriter();
			
			writer.write(msg);
			
			writer.close();
			
			
		}else if("updateBase".equals(m)){
			
			//�޸Ļ�����Ϣ
			
			Admin admin = new Admin();
			admin.setName(request.getParameter("name"));
			admin.setSex( Integer.parseInt( request.getParameter("sex")));
			admin.setAge(Integer.parseInt(request.getParameter("age")));
			admin.setPhone(request.getParameter("phone"));
			admin.setEmail(request.getParameter("email"));
			
			
			//����
			int i = adminService.updateAdminBase(admin,adminNow.getAdminId());
			
			Writer writer = response.getWriter();
			
			//������Ӧ����
			response.setContentType("application/json; charset=utf-8");
			
			String msg = "";
			if(i > 0) {
				//�ɹ�
				msg = "{\"result\":\"true\",\"msg\":\"�޸ĳɹ�,��ˢ�£�\"}";
				
				
				//�����ڲ�ѯһ�����Ա��Ϣ
				Admin adminNew = adminService.getAdmin(adminNow.getAdminName());
				
				request.getSession().setAttribute(AdminLoginServlet.LOGIN_ADMIN,adminNew);
				
			}else {
				//ʧ��
				msg = "{\"result\":\"false\",\"msg\":\"�޸�ʧ�ܣ���ˢ��ҳ�������\"}";
			}
			writer.write(msg);
			writer.close();
		
		}else if("adminIndex".equals(m)){
			
			//�����ݣ���ҳ�õ��ģ�
			
			//��ѯ���ж�����ѯʦ
			int doctorNum = doctorService.getDoctorNum();
			
			request.setAttribute("doctorNum", doctorNum);
			
			//��ѯ���ж���ע��������
			int clientNum = clientService.getClientNum();
			
			request.setAttribute("clientNum", clientNum);
			
			//��ѯ���ж�����ѯ��¼
			int clientArchive = clientArchiveService.getClientArchiveNum();
			
			request.setAttribute("clientArchive", clientArchive);
			
			
			//��ѯ������ʾ������(���µ�ʮ����
			ArrayList<MessageBoard> newMessageBoardList = messageBoardService.getMessageBoardNum(10);
			
			request.setAttribute("newMessageBoardList", newMessageBoardList);
			
			
			//��ѯ����δ������Ϣ(���µ�ʮ����
			ArrayList<Message> messageList = messageService.getMessageNum(10,adminNow.getAdminId(),"admin");
			
			request.setAttribute("messageList", messageList);

			
			//��ѯ������ʾ�Ĺ���(���µ�ʮ��)
			ArrayList<Announcement> announcmentList = announcmentService.getAnnouncmentNum(10); 
			
			request.setAttribute("announcmentList", announcmentList);
			  
			
			//ת������ҳ
			request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
			
		}else if("adminInfo".equals(m)) {
			
			//�鿴������Ϣ
			
			request.getRequestDispatcher("/admin/admin_info.jsp").forward(request, response);
		}
		
	
	}

}
