package model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import bean.MessageBoard;
import model.dao.MessageBoardDao;
import utils.ResultDate;
import utils.Util;

/**
 * 
 * @instruction
 * ���԰�Service
 */
public class MessageBoardService {

	MessageBoardDao messageBoardDao = new MessageBoardDao();

	/**
	 * ��ѯ��������������
	 * 
	 * @param search ��ѯ����
	 * @return ���Զ��󼯺�
	 */
	public List<MessageBoard> listSearch(Map<String, String> search) {

		return messageBoardDao.listSearch(search);
	}

	/**
	 * �л����Ե���ʾ�����أ�0���أ�1��ʾ
	 * @param messageBoardId ����id
	 * @param action Ҫ����Ϊ��ֵ
	 * @param response ��Ӧ����
	 */
	public void toggleMessageBoardActive(String messageBoardId, String action, HttpServletResponse response) {

		int i = messageBoardDao.toggleMessageBoardActive(messageBoardId, action);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �޸ĳɹ�
			rd.setIsSuccess(true);
			rd.setMsg("�޸ĳɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * �������Ե�id��ѯ�����Ҹ�����Ӧ
	 * 
	 * @param messageBoardId ����id
	 * @param response��Ӧ����
	 */
	public void getMessageBoardToResponse(int messageBoardId, HttpServletResponse response) {
		MessageBoard messageBoard = messageBoardDao.getMessageBoard(messageBoardId);

		ResultDate rd = new ResultDate();
		if (messageBoard != null) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("��ѯ�ɹ�");
			rd.getDataList().add(messageBoard);

		} else {

			// ��ѯʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * ����һ������
	 * @param messageBoard ��װ�õ����Զ���
	 * @param response ��Ӧ����
	 */
	public void addMessageBoard(MessageBoard messageBoard, HttpServletResponse response) {

		int i = messageBoardDao.addMessageBoard(messageBoard);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// ���Գɹ�
			rd.setIsSuccess(true);
			rd.setMsg("���Գɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * �õ����µ�num������
	 * @param num ��������
	 * @return ���µ�num�����Զ��󼯺�
	 */
	public ArrayList<MessageBoard> getMessageBoardNum(int num) {
		
		return messageBoardDao.getMessageBoardNum(num);
	}

}
