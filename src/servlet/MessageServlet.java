package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.websocket.Session;
import bean.Admin;
import bean.Client;
import bean.Doctor;
import bean.Message;
import model.service.MessageService;
import servlet.admin.AdminLoginServlet;
import servlet.client.ClientLoginServlet;
import servlet.doctor.DoctorLoginServlet;
import utils.ConfigProperties;

/**
 * Servlet implementation class MessageServlet
 */
/**
 * 
 * @instruction
 * ��Ϣģ�������
 */
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MessageService MessageService = new MessageService();

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String m = request.getParameter("m");

		if ("addMessage".equals(m)) {
			
			// ����һ����Ϣ
			
			// ���ܲ���
			String receiver = request.getParameter("receiver");// ���
			String receiverId = request.getParameter("receiverId");//������id
			String receiverName = request.getParameter("receiverName");// ����������
			String context = request.getParameter("context");// ��Ϣ����
			
			System.out.println(receiver+"  receiver");
			
			//�õ���ǰ��¼���û�
			Map<String, Object> currentUser = getCurrentUser(request);
			
			//��װ����
			Message message = new Message();
			message.setSender( (String)currentUser.get("reqeustUser"));
			message.setSenderId( (Integer)currentUser.get("reqeustUserId"));
			message.setSenderName((String)currentUser.get("reqeustUserName"));
			message.setReceiver(receiver);
			message.setReceiverId(Integer.parseInt(receiverId));
			message.setReceiverName(receiverName);
			message.setContext(context);
			message.setSendTime(new Date());
			message.setIsRead(0);
			
			//������Ϣ
			MessageService.sendMessage(message,response);
			
			

		} else if ("listSendMessage".equals(m)) {

			// ��ѯһ���û����͵�������Ϣ

			// ���ܲ�ѯ����
			String receiver = request.getParameter("receiver");// ���
			String receiverName = request.getParameter("receiverName");// ����������
			String startSendTime = request.getParameter("startSendTime");// ����ʱ��,��ʼ
			String endSendTime = request.getParameter("endSendTime");// ����ʱ��,ֹ
			String isRead = request.getParameter("isRead");// �Ƿ��Ѷ�
			String context = request.getParameter("context");// ��Ϣ����
			
			//�õ���ǰ��¼���û�
			Map<String, Object> currentUser = getCurrentUser(request);

			// ��������װ��search��
			Map<String, String> search = new HashMap<String, String>();
			search.put("receiver", receiver);
			search.put("receiverName", receiverName);
			search.put("startSendTime", startSendTime);
			search.put("endSendTime", endSendTime);
			search.put("isRead", isRead);
			search.put("context", context);
			

			// ��ѯ���͵���Ϣ
			List<Message> list = MessageService.listSendMessage(search,(String)currentUser.get("reqeustUser"),(Integer)currentUser.get("reqeustUserId"));

			request.setAttribute("search", search);

			request.setAttribute("messageList", list);


			request.getRequestDispatcher("/admin/messageSendList.jsp").forward(request, response);

		} else if ("listReceivMessage".equals(m)) {

			// ��ѯһ���û����յ���������Ϣ

			// ���ܲ�ѯ����
			String sender = request.getParameter("sender");// ���
			String senderName = request.getParameter("senderName");// ����������
			String startSendTime = request.getParameter("startSendTime");// ����ʱ��,��ʼ
			String endSendTime = request.getParameter("endSendTime");// ����ʱ��,ֹ
			String isRead = request.getParameter("isRead");// �Ƿ��Ѷ�
			String context = request.getParameter("context");// ��Ϣ����

			// ��������װ��search��
			Map<String, String> search = new HashMap<String, String>();
			search.put("sender", sender);
			search.put("senderName", senderName);
			search.put("startSendTime", startSendTime);
			search.put("endSendTime", endSendTime);
			search.put("isRead", isRead);
			search.put("context", context);
			
			//�õ���ǰ��¼���û�
			Map<String, Object> currentUser = getCurrentUser(request);

			// ��ѯ���յ���
			List<Message> list = MessageService.listReceivMessage(search,(String)currentUser.get("reqeustUser"),(Integer)currentUser.get("reqeustUserId"));

			request.setAttribute("search", search);

			request.setAttribute("messageList", list);

			request.setAttribute("listSize", list.size());

			request.getRequestDispatcher("/admin/messageReceiveList.jsp").forward(request, response);

		}else if("updateIsRead".equals(m)) {
			//�л�Ϊ�Ѷ�
			
			//Ҫ�л���Ϣ��Id
			String messageId = request.getParameter("messageId");
			
			MessageService.toggleIsRead(Integer.parseInt(messageId),response);
		}else if ("newMessage".equals(m)) {

			// ��ѯ����Ϣ������
			
			//�õ��û����ͣ�admin,client,doctor��
			String user = request.getParameter("user");
			
			//�õ���ǰ��¼���û�
			Map<String, Object> currentUser = getCurrentUser(request,user);
			
			//��ѯδ����Ϣ����
			MessageService.newMessageNumResponse(currentUser,response);

		}

	}

	/**
	 * �õ���ǰ��¼���û���ݺ��û�Id(֪����ʲô���͵��û�)
	 * @param request
	 * @return
	 */
	private Map<String, Object> getCurrentUser(HttpServletRequest request, String user) {

		Map<String, Object> currentUser = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		
		if("admin".equals(user)) {

			Admin admin = (Admin) session.getAttribute(AdminLoginServlet.LOGIN_ADMIN);
			currentUser.put("reqeustUser", "admin");
			currentUser.put("reqeustUserId", admin.getAdminId());
			currentUser.put("reqeustUserName", admin.getName());
			
		}
		else if("doctor".equals(user)) {

			Doctor doctor = (Doctor) session.getAttribute(DoctorLoginServlet.LOGIN_DOCTOR);
			currentUser.put("reqeustUser", "doctor");
			currentUser.put("reqeustUserId", doctor.getDoctorId());
			currentUser.put("reqeustUserName", doctor.getName());
			
		}
		else if("client".equals(user)) {

			Client client = (Client) session.getAttribute(ClientLoginServlet.LOGIN_CLIENT);
			currentUser.put("reqeustUser", "client");
			currentUser.put("reqeustUserId", client.getClientId());
			currentUser.put("reqeustUserName", client.getName());
			
		}
		else {
			currentUser.put("reqeustUser", "no");
			currentUser.put("reqeustUserId", "0");
			currentUser.put("reqeustUserName", "noUser");
		}
		
		return currentUser;
	
	}

	/**
	 * �õ���ǰ��¼���û���ݺ��û�Id����֪����ʲô���͵��û���
	 * @param request
	 * @return
	 */
	public static  Map<String, Object> getCurrentUser(HttpServletRequest request) {

		Map<String, Object> currentUser = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(AdminLoginServlet.LOGIN_ADMIN) != null) {

			Admin admin = (Admin) session.getAttribute(AdminLoginServlet.LOGIN_ADMIN);
			currentUser.put("reqeustUser", "admin");
			currentUser.put("reqeustUserId", admin.getAdminId());
			currentUser.put("reqeustUserName", admin.getName());
			
		}
		else if(session.getAttribute(DoctorLoginServlet.LOGIN_DOCTOR) != null) {

			Doctor doctor = (Doctor) session.getAttribute(DoctorLoginServlet.LOGIN_DOCTOR);
			currentUser.put("reqeustUser", "doctor");
			currentUser.put("reqeustUserId", doctor.getDoctorId());
			currentUser.put("reqeustUserName", doctor.getName());
			
		}
		else if(session.getAttribute(ClientLoginServlet.LOGIN_CLIENT) != null) {

			Client client = (Client) session.getAttribute(ClientLoginServlet.LOGIN_CLIENT);
			currentUser.put("reqeustUser", "client");
			currentUser.put("reqeustUserId", client.getClientId());
			currentUser.put("reqeustUserName", client.getName());
			
		}
		else {
			currentUser.put("reqeustUser", "no");
			currentUser.put("reqeustUserId", "0");
			currentUser.put("reqeustUserName", "noUser");
		}
		
		return currentUser;
	
	}

}
