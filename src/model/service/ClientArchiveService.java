package model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import bean.ClientArchive;
import bean.Doctor;
import bean.Message;
import model.dao.ClientArchiveDao;
import model.dao.MessageDao;
import utils.ResultDate;
import utils.UploadResult;
import utils.Util;
import utils.mail.Mail;
import utils.mail.MailSend;

/**
 * 
 * @instruction
 * ��ѯ��¼��Service
 */
public class ClientArchiveService {

	ClientArchiveDao clientArchiveDao = new ClientArchiveDao();

	MessageDao messageDao = new MessageDao();

	/**
	 * ��ѯ���ж�������ѯ��¼
	 * 
	 * @return ��ѯ��¼����
	 */
	public int getClientArchiveNum() {
		return clientArchiveDao.getClientArchiveNum();
	}

	/**
	 * ��ѯ����������ԤԼ������
	 * 
	 * @param clientId ��ѯ��¼��������id
	 * @return ��ѯ��¼���󼯺�
	 */
	public List<ClientArchive> onSubList(Integer clientId) {
		return clientArchiveDao.listClientArchive(clientId, -1, 1);
	}

	/**
	 * ��ѯ�������Ѿ���ɵ�ԤԼ
	 * 
	 * @param clientId ��ѯ��¼��������id
	 * @return ��ѯ��¼���󼯺�
	 */
	public List<ClientArchive> clientConsult(Integer clientId) {
		return clientArchiveDao.listClientArchive(clientId, 2, 3);
	}

	/**
	 * ���һ������,������Ӧ�ͻ���
	 * 
	 * @param clientArchive ��ѯ��¼����
	 * @param response ��Ӧ����
	 */
	public void addClientArchive(ClientArchive clientArchive, HttpServletResponse response) {

		int i = clientArchiveDao.addClientArchive(clientArchive);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("�ύ�ɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * ��ѯ idΪdoctorId ����ѯʦ�����ܵ�����������(δͨ����)
	 * 
	 * @param doctorId ��ѯ��¼�ж�Ӧ����ѯʦid
	 * @return ��ѯ��¼���󼯺�
	 */
	public List<ClientArchive> getAllSubFromClient(Integer doctorId) {
		return clientArchiveDao.listDoctorArchive(doctorId, 0, 0);
	}

	/**
	 * ��ʾ��ѯʦ����������ѯ�еļ�¼
	 * 
	 * @param doctorId ��ѯ��¼�ж�Ӧ����ѯʦid
	 * @return ��ѯ��¼���󼯺�
	 */
	public List<ClientArchive> subOnList(Integer doctorId) {
		return clientArchiveDao.listDoctorArchive(doctorId, 1, 2);
	}

	/**
	 * ��ʾ��ѯʦ�Ѿ���ɵ���ѯ
	 * 
	 * @param doctorId ��ѯ��¼�ж�Ӧ����ѯʦid
	 * @return ��ѯ��¼���󼯺�
	 */
	public List<ClientArchive> getSubOk(Integer doctorId) {
		return clientArchiveDao.listDoctorArchive(doctorId, 3, 3);
	}

	/**
	 * ͨ��archivesId�õ�ClientArchive����
	 * 
	 * @param archivesId ��ѯ��¼id
	 * @return ��ѯ��¼����
	 */
	public ClientArchive getClientArchiveById(int archivesId) {
		return clientArchiveDao.getClientArchiveById(archivesId);
	}

	/**
	 *  �������� 
	 * @param archivesId ��ѯ��¼id
	 * @param clientId ������id
	 * @param applyTime ����ʱ��
	 * @param response ��Ӧ����
	 * @param doctorNow �����Ŀ����ѯʦ���� 
	 */
	public void updateStatusFalseResponse(int archivesId, int clientId, String applyTime, HttpServletResponse response,
			Doctor doctorNow) {

		int i = clientArchiveDao.updateClientArchiveStuatus(archivesId, -1);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("�ɹ�");

			// ������Ϣ �ʼ� ����ѯ��

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date at = null;
			try {
				at = sdf.parse(applyTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Message message = new Message();
			message.setSender("admin");
			message.setSenderId(3);
			message.setSenderName("ϵͳ(����ظ�)");
			message.setReceiver("client");
			message.setReceiverId(clientId);
			message.setReceiverName("����ϵͳ��Ϣ��");
			message.setContext("��ѯʦ��" + doctorNow.getName() + "��ȡ��������(" + sdf.format(at) + "ʱ)������,����������������ϵ;" + "(����:"
					+ doctorNow.getEmail() + ",�绰��" + doctorNow.getPhone() + ")");
			message.setSendTime(new Date());
			message.setIsRead(0);
			
			//����վ����Ϣ
			messageDao.sendMessage(message);
			//�����ʼ�
			sendMessageEmail(message);

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	
	/**
	 * �����ʼ�
	 * @param message �ʼ�����Ϣ���ݣ���������
	 */
	private void sendMessageEmail(Message message) {

		String email = messageDao.getReceiverEmail(message.getReceiver(),message.getReceiverId());
		
		if(email != null) {
			
			Mail mail = new Mail();
			mail.setTitle("��ѯ����");
			mail.setContent(message.getContext());
			mail.setTo(email);
		    boolean result = MailSend.send(mail);
		    
		    if(!result) {
		    	System.out.println("����" + message.getReceiver() + " " + message.getReceiverId() + ",email:" + email + ",���͵��ʼ�ʧ��");
		    }
			
		}else {
			System.out.println("���䲻����" + message.getReceiver() + " " + message.getReceiverId());
		}
		
	}

	/**
	 * ������ѯ�����ؽ��
	 * 
	 * @param clientArchive ��ѯ��¼
	 * @param response ��Ӧ����
	 * @param doctorNow Ŀ����ѯʦ��Ϣ
	 */
	public void planSubResponse(ClientArchive clientArchive, HttpServletResponse response, Doctor doctorNow) {

		int i = -999;

		ResultDate rd = new ResultDate();

		if (clientArchive.getStartDatetime().compareTo(clientArchive.getEndDatetime()) < 0) {
			// ���ϣ���ʼ����С�ڽ�������

			i = clientArchiveDao.planSub(clientArchive);
		}

		if (i == 1) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("�ɹ�");

			// ������Ϣ �ʼ� ��������

//			Message message = new Message();
//			message.setSender("admin");
//			message.setSenderId(3);
//			message.setSenderName("ϵͳ(����ظ�)");
//			message.setReceiver("client");
//			message.setReceiverId(clientArchive.getClientId());
//			message.setReceiverName("����ϵͳ��Ϣ��");
//			message.setContext("��ѯʦ��" + doctorNow.getName() + "��Ϊ����������ѯ������\"�ҵ�ԤԼ\"�в鿴���飬����������������ϵ;" + "(����:"
//					+ doctorNow.getEmail() + ",�绰��" + doctorNow.getPhone() + ")");
//			message.setSendTime(new Date());
//			message.setIsRead(0);
//
//			//����վ����Ϣ
//			messageDao.sendMessage(message);
//
//			//�����ʼ�
//			sendMessageEmail(message);

		} else if (i == -999) {

			// ����������
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ʼ���ڲ��ܴ��ڽ������ڣ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * �����ѯ
	 * 
	 * @param archivesId ��ѯ��¼id
	 * @param clientId ������id
	 * @param response ��Ӧ����
	 * @param doctorNow Ŀ����ѯʦ��Ϣ
	 */
	public void updateStatusFinishResponse(int archivesId, int clientId, HttpServletResponse response,
			Doctor doctorNow) {

		int i = clientArchiveDao.updateClientArchiveStuatus(archivesId, 3);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("�ɹ�");

			// ������Ϣ �ʼ� ��������
			Message message = new Message();
			message.setSender("admin");
			message.setSenderId(3);
			message.setSenderName("ϵͳ(����ظ�)");
			message.setReceiver("client");
			message.setReceiverId(clientId);
			message.setReceiverName("����ϵͳ��Ϣ��");
			message.setContext("���룺��ѯʦ" + doctorNow.getName() + "����ѯ�Ѿ����,����������������ϵ;" + "(����:" + doctorNow.getEmail()
					+ ",�绰��" + doctorNow.getPhone() + ")");
			message.setSendTime(new Date());
			message.setIsRead(0);
			
			//����վ����Ϣ
			messageDao.sendMessage(message);


		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * ���۱�����ѯ
	 * 
	 * @param archivesId ��ѯ��¼id
	 * @param context ��ѯ�ߵ���������
	 * @param response ��Ӧ����
	 */
	public void evaluateSub(String archivesId, String context, HttpServletResponse response) {

		int i = clientArchiveDao.evaluateSub(archivesId, context);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("���۳ɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * ��ѯ��¼�����ѯ�ĵ�
	 * 
	 * @param archivesId ��ѯ��¼id
	 * @param uploadResult �ĵ����ϴ����
	 * @param response ��Ӧ����
	 */
	public void uploadSubDoc(String archivesId, UploadResult uploadResult, HttpServletResponse response) {

		ResultDate rd = new ResultDate();

		if (uploadResult.isSuccess()) {

			// �õ��ļ����·��
			String subDocPath = uploadResult.getLogicFileName();

			int i = clientArchiveDao.uploadSubDoc(archivesId, subDocPath);

			if (i == 1) {
				// �ɹ�
				rd.setIsSuccess(true);

			}

		} else {

			rd.setIsSuccess(false);
			
		}

		rd.setMsg(uploadResult.getMsg());
		
		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}
}
