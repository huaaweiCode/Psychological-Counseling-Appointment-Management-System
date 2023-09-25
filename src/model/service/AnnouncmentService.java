package model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import bean.Announcement;
import bean.Doctor;
import model.dao.AnnouncmentDao;
import utils.ResultDate;
import utils.Util;

/**
 * 
 * @instruction
 * �����Service
 */
public class AnnouncmentService {

	AnnouncmentDao announcmentDao = new AnnouncmentDao();

	
	/**
	 * ��ѯ���������Ĺ���
	 * 
	 * @param search ��װ�˲�ѯ������Map����
	 * @return ��ѯ���Ĺ����б�
	 */
	public List<Announcement> listSearch(Map<String, String> search) {

		return announcmentDao.listSearch(search);
	}


	/**
	 * �л��������ʾ�����أ�0���أ�1��ʾ
	 * @param announcmentId Ҫ�л��Ĺ���id
	 * @param action Ҫ���µ���״̬
	 * @param response ��Ӧ����
	 */
	public void toggleDoctorActive(String announcmentId, String action, HttpServletResponse response) {


		int i = announcmentDao.toggleClientActive(announcmentId, action);

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
	 * ����announcementId������һ�����棬��Ӧ���ͻ���
	 * @param announcementId ����id
	 * @param response ��Ӧ����
	 */
	public void getAnnouncementToResponse(int announcementId, HttpServletResponse response) {
		Announcement announcement = announcmentDao.getAnnouncement(announcementId);

		ResultDate rd = new ResultDate();
		if (announcement != null) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("��ѯ�ɹ�");
			rd.getDataList().add(announcement);

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
	 * @param announcement Ҫ���ӵĹ������
	 * @param response ��Ӧ����
	 */
	public void addAnnouncement(Announcement announcement, HttpServletResponse response) {

		int i = announcmentDao.addAnnouncement(announcement);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// ��������ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("��������ɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);
		
	}


	/**
	 * ��ѯ������ʾ�Ĺ��棨����num����
	 * @param num ��ѯ�Ĺ�������
	 * @return ���µ�num��������б�
	 */
	public ArrayList<Announcement> getAnnouncmentNum(int num) {
		
		return announcmentDao.getAnnouncmentNum(num);
	}
	
}
