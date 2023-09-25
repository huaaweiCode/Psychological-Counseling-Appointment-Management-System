package servlet.doctor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Client;
import bean.ClientArchive;
import bean.Doctor;
import bean.Question;
import model.service.ClientArchiveService;
import model.service.DoctorService;
import model.service.QuestionService;
import servlet.client.ClientLoginServlet;
import utils.UploadResult;
import utils.Util;
import model.service.ClientService;

/**
 * 
 * @instruction
 * ��ѯʦ�ˣ�ԤԼ��ѯ��ҵ������ ������
 */
@MultipartConfig
public class DoctorSubServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ClientService clientService = new ClientService();

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
		Doctor doctorNow = (Doctor) request.getSession().getAttribute(DoctorLoginServlet.LOGIN_DOCTOR);

		if ("subClientList".equals(m)) {
			// ��ѯ����

			// ��ѯ������ѯ����
			List<ClientArchive> list = clientArchiveService.getAllSubFromClient(doctorNow.getDoctorId());

			request.setAttribute("clientArchiveList", list);

			request.getRequestDispatcher("/doctor/subClientList.jsp").forward(request, response);

		} else if ("subOnList".equals(m)) {
			// ��ѯ�е�

			// ��ʾ������ѯ�е�

			List<ClientArchive> list = clientArchiveService.subOnList(doctorNow.getDoctorId());

			request.setAttribute("clientArchiveList", list);

			request.getRequestDispatcher("/doctor/subOnList.jsp").forward(request, response);

		} else if ("doctorConsult".equals(m)) {
			// ��ѯ��¼

			// �Ѿ���ɵ���ѯ��¼

			List<ClientArchive> list = clientArchiveService.getSubOk(doctorNow.getDoctorId());

			request.setAttribute("clientArchiveList", list);

			request.getRequestDispatcher("/doctor/doctorConsult.jsp").forward(request, response);

		} else if ("subShow".equals(m)) {
			// �鿴��������

			String archives_id = request.getParameter("archivesId");

			String clientId = request.getParameter("clientId");

			// ͨ��Id�õ�ClientArchive����
			ClientArchive clientArchive = clientArchiveService.getClientArchiveById(Integer.parseInt(archives_id));

			request.setAttribute("clientArchive", clientArchive);

			// ͨ��clientId�õ�Client����
			Client client = clientService.getClientByClientId(Integer.parseInt(clientId));

			request.setAttribute("client", client);

			request.getRequestDispatcher("/doctor/subShow.jsp").forward(request, response);

		} else if ("updateStatusFalse".equals(m)) {
			// ��������

			// �л�statusΪʧ��״̬��-1��

			String archivesId = request.getParameter("archivesId");

			String clientId = request.getParameter("clientId");

			String applyTime = request.getParameter("applyTime");

			clientArchiveService.updateStatusFalseResponse(Integer.parseInt(archivesId), Integer.parseInt(clientId),
					applyTime, response, doctorNow);

		} else if ("planSub".equals(m)) {
			// ������ѯ����������ѯʱ��ص㣬status��Ϊ 1 ͨ�����뵫δ���

			// ȡ�ò���

			String archivesId = request.getParameter("archivesId");

			String clientId = request.getParameter("clientId");

			String startDatetime = request.getParameter("startDatetime");

			String endDatetime = request.getParameter("endDatetime");

			String subPlace = request.getParameter("subPlace");

			// ��װ����
			ClientArchive clientArchive = new ClientArchive();
			clientArchive.setArchivesId(Integer.parseInt(archivesId));
			clientArchive.setClientId(Integer.parseInt(clientId));
			clientArchive.setSubPlace(subPlace);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				clientArchive.setEndDatetime(sdf.parse(endDatetime));
				clientArchive.setStartDatetime(sdf.parse(startDatetime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			clientArchiveService.planSubResponse(clientArchive, response, doctorNow);

		} else if ("updateStatusFinish".equals(m)) {
			// �����ѯ

			// �л�statusΪ���״̬��3��

			String archivesId = request.getParameter("archivesId");

			String clientId = request.getParameter("clientId");

			clientArchiveService.updateStatusFinishResponse(Integer.parseInt(archivesId), Integer.parseInt(clientId),
					response, doctorNow);

		} else if ("uploadSubDoc".equals(m)) {
			// �ϴ���ѯ�ĵ�

			//���������ĵ�

			UploadResult uploadResult = Util.upload("subDoc", request, Util.UPLOAD_TYPE_ATTACHMENT);
			
			
			//Ŀ����ѯ��¼
			String archivesId = request.getParameter("archivesId");
			
			//��ѯ��¼�е��ĵ�����
			clientArchiveService.uploadSubDoc(archivesId,uploadResult,response);

		}

	}

}
