package model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import bean.Message;
import model.dao.MessageDao;
import utils.ResultDate;
import utils.Util;


/**
 * 
 * @instruction
 * ��ϢService
 */
public class MessageService {

	
	MessageDao messageDao = new MessageDao();

	
	/**
	 * ��ѯ �û� �Ѿ����͵���Ϣ�����û����͵ģ�
	 * @param search ��ѯ��������
	 * @param reqeustUser ��ѯ�����(��ǰ��¼�û�)
	 * @param reqeustUserId ��ѯ��id(��ǰ��¼�û�)
	 * @return ��Ϣ���󼯺�
	 */
	public List<Message> listSendMessage(Map<String, String> search, String reqeustUser, Integer reqeustUserId) {
		
		return messageDao.listSendMessage(search,reqeustUser,reqeustUserId);
	}


	/**
	 * ��ѯ ���˸� �û� ���͵���Ϣ�����û����ܵ��ģ�
	 * @param search ��ѯ��������
	 * @param reqeustUser ��ѯ�����(��ǰ��¼�û�)
	 * @param reqeustUserId ��ѯ��id(��ǰ��¼�û�)
	 * @return ��Ϣ���󼯺�
	 */
	public List<Message> listReceivMessage(Map<String, String> search, String reqeustUser, Integer reqeustUserId) {

		return messageDao.listReceivMessage(search,reqeustUser,reqeustUserId);
	}


	/**
	 * ����һ����Ϣ
	 * @param message ��Ϣ����
	 * @param response ��Ӧ����
	 */
	public void sendMessage(Message message, HttpServletResponse response) {


		int i = messageDao.sendMessage(message);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("���ͳɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	
	}


	/**
	 * �л���ϢΪ�Ѷ�״̬
	 * @param messageId ��Ϣid
	 * @param response ��Ӧ����
	 */
	public void toggleIsRead(int messageId, HttpServletResponse response) {


		int i = messageDao.toggleIsRead(messageId);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �ɹ�
			rd.setIsSuccess(true);

		} else {
			// �޸�ʧ��
			rd.setIsSuccess(false);

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	
	}


	/**
	 * ��ѯ����δ������Ϣ�����µ�num����
	 * @param num ��Ϣ�������
	 * @param reqeustUser ��ѯ�����(��ǰ��¼�û�)
	 * @param reqeustUserId ��ѯ��id(��ǰ��¼�û�)
	 * @return ��Ϣ���󼯺�
	 */
	public ArrayList<Message> getMessageNum(int num, Integer reqeustUserId,String requestUser) {
		return messageDao.getMessageNum(num,reqeustUserId,requestUser);
	}


	/**
	 * ��ѯδ����Ϣ����
	 * @param currentUser ��ǰ��¼�û�����(�����û����ͣ�id)
	 * @param response ��Ӧ����
	 */
	public void newMessageNumResponse(Map<String, Object> currentUser, HttpServletResponse response) {


		int num = messageDao.newMessageNum((String)currentUser.get("reqeustUser"),(int)currentUser.get("reqeustUserId"));

		ResultDate rd = new ResultDate();
		if (num == -1) {
			
			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ��");
			

		} else {
			
			// �ɹ�
		    rd.setIsSuccess(true);
		    rd.getDataList().add(num);

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	
	}
}
