package model.service;

import bean.Admin;
import model.dao.AdminDao;

/**
 * 
 * @instruction
 * ����Ա��Service
 */
public class AdminService {

	AdminDao adminDao = new AdminDao();

	/**
	 * �����˺Ų�ѯ����Ա(���ߵ绰����)
	 * @param adminName ����Ա�˺�
	 * @return adminName ��ѯ���Ĺ���Ա
	 */
	public Admin getAdmin(String adminName) {
		return adminDao.getAdmin(adminName);
	}

	/**
	 * �޸Ĺ���Ա����
	 * @param adminId ����Աid
	 * @param newPwd  ������
	 * @return ִ��update����Ӱ������
	 */
	public int updateAdminPwd(Integer adminId, String newPwd) {
		return adminDao.updateAdminPwd(adminId, newPwd);
	}

	/**
	 * �޸Ĺ���Ա��Ϣ�����ݹ���Աid
	 * @param admin ����Ա������Ϣ
	 * @param adminId ����Աid
	 * @return ִ��update����Ӱ������
	 */
	public int updateAdminBase(Admin admin, Integer adminId) {
		return adminDao.updateAdminBase(admin, adminId);
	}

	/**
	 * ����Id��ѯ����Ա������Ϣ
	 * 
	 * @param adminId ����Աid
	 * @return ��ѯ���Ĺ���Ա����
	 */
	public Admin getAdminById(Integer adminId) {
		return adminDao.getAdminById(adminId);
	}
}
