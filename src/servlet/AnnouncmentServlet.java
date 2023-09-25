package servlet;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bean.Admin;
import bean.Announcement;
import bean.Doctor;
import model.service.AnnouncmentService;
import servlet.admin.AdminLoginServlet;
import utils.ResultDate;
import utils.UploadResult;
import utils.Util;

/**
 * 
 * @instruction
 * ����ģ�������
 */
public class AnnouncmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AnnouncmentService announcementService = new AnnouncmentService();

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String m = request.getParameter("m");
		
		

		if ("listAnnouncment".equals(m)) {
			
			//�õ���ǰ��¼���û�
			Map<String, Object> currentUser =MessageServlet.getCurrentUser(request);

			// ���ܲ�ѯ����
			
			String creater = request.getParameter("creater");
			String title = request.getParameter("title");
			String context = request.getParameter("context");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			

			// ��������װ��search��
			Map<String, String> search = new HashMap<String, String>();
			search.put("creater", creater);
			search.put("title", title);
			search.put("context", context);
			search.put("startTime", startTime);
			search.put("endTime", endTime);

			// ��ѯ���������Ĺ���
			List<Announcement> list = announcementService.listSearch(search);

			request.setAttribute("search", search);

			request.setAttribute("announcementList", list);

			request.setAttribute("listSize", list.size());
			
			
			if(currentUser.get("reqeustUser").equals("admin")) { //����Ա���д��������Ȩ�ޣ�
				
				request.getRequestDispatcher("/admin/announcementList.jsp").forward(request, response);
			
			}else {//��ͨ�����ߺ���ѯʦ
				
				request.getRequestDispatcher("/client/announcementList.jsp").forward(request, response);
				
			}

			

		} else if ("updateActive".equals(m)) {

			// ͣ�ú������л�

			String announcementId = request.getParameter("id");

			String action = request.getParameter("action");

			announcementService.toggleDoctorActive(announcementId, action, response);

		} else if ("selecteAnnouncement".equals(m)) {// ajax

			// �鿴����

			String announcementId = request.getParameter("id");

			// ��ѯ�����ҽ����ݷ��أ�JSON��ʽ��
			announcementService.getAnnouncementToResponse(Integer.parseInt(announcementId), response);

		} else if ("addAnnouncement".equals(m)) {
			
			// ����

			// ȡ�ñ����ֵ
			
			String title = request.getParameter("title");
			String context = request.getParameter("context");
			
			String isActive = request.getParameter("isActive");

			//������
			Admin admin =  (Admin) request.getSession().getAttribute(AdminLoginServlet.LOGIN_ADMIN);


			// ���������װΪAnnouncement����
			Announcement announcement = new Announcement();
			announcement.setTitle(title);
			announcement.setContext(context);
			announcement.setCreateTime(new Date());
			announcement.setIsActive(Integer.parseInt(isActive));
			announcement.setCreaterId(admin.getAdminId());
			
			//����һ������
			announcementService.addAnnouncement(announcement,response);
			
		}

	}

}
