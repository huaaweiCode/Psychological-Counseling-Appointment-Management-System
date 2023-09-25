package servlet.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONUtil;

import bean.Client;
import bean.ClientArchive;
import bean.Doctor;
import bean.Question;
import model.service.ClientArchiveService;
import model.service.DoctorService;
import model.service.QuestionService;

/**
 * Servlet implementation class ClientSubServlet ��ͨ�����ߵ�ԤԼ��ѯҵ��
 */
/**
 * 
 * @instruction
 * �����߶ˣ�ԤԼҵ�����̿�����
 */
public class ClientSubServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DoctorService doctorService = new DoctorService();

	ClientArchiveService clientArchiveService = new ClientArchiveService();
	
	QuestionService questionService = new QuestionService();

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String m = request.getParameter("m");

		// ��ǰ��¼�û�
		Client clientNow = (Client) request.getSession().getAttribute(ClientLoginServlet.LOGIN_CLIENT);

		
		if ("subDoctorList".equals(m)) {//��ԤԼ��ѯʦ

			// ��ʾ�ɹ�ԤԼ����ѯʦ�б�

			List<Doctor> list = doctorService.listSearch(new HashMap<String, String>());

			request.setAttribute("doctorList", list);

			request.getRequestDispatcher("/client/submind.jsp").forward(request, response);

		} else if ("subClientList".equals(m)) {//�ҵ�ԤԼ

			// ��ʾ����ԤԼ������

			Client client = (Client) request.getSession().getAttribute(ClientLoginServlet.LOGIN_CLIENT);

			List<ClientArchive> list = clientArchiveService.onSubList(client.getClientId());
			
			
			request.setAttribute("clientArchiveList", list);

			request.getRequestDispatcher("/client/onSubList.jsp").forward(request, response);

		} else if ("subStep1".equals(m)) {

			// ԤԼ��һ������ת��ԤԼ����
			
			//��ѯԤԼ����ѯʦ
			
			String doctorId = request.getParameter("doctorId");
			
			Doctor doctor = doctorService.getDoctorById(Integer.parseInt(doctorId));
			
			request.setAttribute("doctor", doctor);
			
			//��ѯ�ʾ�����
			ArrayList<Question> list = questionService.listQuestion("");
			
			request.setAttribute("questionList", list);
			
			
			request.getRequestDispatcher("/client/subAdd.jsp").forward(request, response);


		} else if ("subStep2".equals(m)) {

			//ԤԼ�ڶ���
			// ����
			
			//ȡ�ø�������
			String expectTime = request.getParameter("expectTime");
			
			String expectPlace = request.getParameter("expectPlace");
			
			String doctorId = request.getParameter("doctorId");
			
			String clientDescription = request.getParameter("clientDescription");
			
			
			
			//����questionId������"��"����
			String questionIds = request.getParameter("questionIds");
			
			//����questionIds,����json�ַ������ͷ�ֵ
			HashMap<String,String> mapJsonLevel = questionService.getJSON(questionIds,request);
			
			
			ClientArchive clientArchive = new ClientArchive();
			clientArchive.setClientId(clientNow.getClientId());
			clientArchive.setDoctorId(Integer.parseInt(doctorId));
			clientArchive.setClientDescription(clientDescription);
			clientArchive.setQuestionContext(mapJsonLevel.get("JSON"));
			clientArchive.setLevel(Integer.parseInt(mapJsonLevel.get("level")));
			clientArchive.setApplyTime(new Date());
			clientArchive.setExpectPlace(expectPlace);
			clientArchive.setExpectTime(expectTime);
			
			clientArchiveService.addClientArchive(clientArchive,response);
			

		} else if ("clientConsult".equals(m)) {//�ҵ���ѯ

			// �Ѿ���ɵ�ԤԼ�б�

			Client client = (Client) request.getSession().getAttribute(ClientLoginServlet.LOGIN_CLIENT);

			List<ClientArchive> list = clientArchiveService.clientConsult(client.getClientId());
			
			request.setAttribute("clientArchiveList", list);
			
			request.getRequestDispatcher("/client/clientConsult.jsp").forward(request, response);

		}else if("evaluateSub".equals(m)) {
			//���۱�����ѯ
			
			String archivesId = request.getParameter("archivesId");
			
			String context = request.getParameter("context");
			
			System.out.println(context + "---");
			
			//���۱�����ѯ
			clientArchiveService.evaluateSub(archivesId,context,response);
			
		}

	}

}
