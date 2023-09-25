package model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import bean.Doctor;
import model.dao.DoctorDao;
import utils.ResultDate;
import utils.Util;

/**
 * 
 * @instruction
 * ��ѯʦService
 */
public class DoctorService {

	DoctorDao doctorDao = new DoctorDao();

	/**
	 * �޸�doctor����is_active��ֵ���Ƿ�Ϊ����״̬
	 * 
	 * @param doctorId ��ѯʦid
	 * @param action Ҫ���µ�ֵ
	 */
	public void toggleDoctorActive(String doctorId, String action, HttpServletResponse response) {

		int i = doctorDao.toggleDoctorActive(doctorId, action);

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
	 * ��ѯ������������ѯʦ
	 * @param search ��ѯ����
	 * @return ��ѯʦ���󼯺�
	 */
	public List<Doctor> listSearch(Map<String, String> search) {

		return doctorDao.listSearch(search);
	}

	/**
	 * ����doctorId ɾ�� doctor
	 * 
	 * @param doctorId ��ѯʦid
	 * @param response ��Ӧ����
	 */
	public void deleteDoctor(Integer doctorId, HttpServletResponse response) {

		int i = doctorDao.deleteDoctor(doctorId);

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �޸ĳɹ�
			rd.setIsSuccess(true);
			rd.setMsg("ɾ���ɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * ͨ��doctor��doctorId ��ѯdoctor,��������Ӧ
	 * @param doctorId ��ѯʦid
	 * @param response ��Ӧ����
	 */
	public void getDoctorByDoctorIdToResponse(int doctorId, HttpServletResponse response) {

		Doctor doctor = doctorDao.getDoctorByDoctorId(doctorId);

		ResultDate rd = new ResultDate();
		if (doctor != null) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("��ѯ�ɹ�");
			rd.getDataList().add(doctor);

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * �޸�Doctor
	 * 
	 * @param doctor Ҫ���µ���ѯʦ����
	 * @return ��Ӱ������
	 */
	public int updateDoctor(Doctor doctor) {

		return doctorDao.updateDoctor(doctor);
	}

	/**
	 * ����һ��Doctor
	 * 
	 * @param doctor Ҫ��ӵ���ѯʦ����
	 * @return ��Ӱ������
	 */
	public int addDoctor(Doctor doctor) {

		// ���ӵ�ʱ����� �˺ź�����

		// ����˺�
		doctor.setDoctorName(Util.generateRandomNum(9));

		// Ĭ������123456
		doctor.setDoctorPwd("123456");

		return doctorDao.addDoctor(doctor);
	}

	/**
	 * ɾ�����Doctor
	 * 
	 * @param checkeds Ҫɾ����doctorId(1,2,3,4)ʹ��","�ָ�
	 * @param response ��Ӧ����
	 */
	public void deleteDoctorCheckeds(String checkeds, HttpServletResponse response) {

		String[] checkedStrs = checkeds.split(",");

		int i = 0;

		for (String checked : checkedStrs) {

			if (doctorDao.deleteDoctor(Integer.parseInt(checked)) == 1) {
				i++;
			}

		}

		ResultDate rd = new ResultDate();
		if (i == checkedStrs.length) {
			rd.setIsSuccess(true);
			rd.setMsg("ɾ���ɹ�");
		} else {

			rd.setIsSuccess(false);
			rd.setMsg("ɾ��ʧ��");
		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 * ��ѯ��ѯʦ����
	 * @return ��ѯʦ����
	 */
	public int getDoctorNum() {
		
		return doctorDao.getDoctorNum();
	}

	/**
	 * ����doctorId��ѯdoctor
	 * @param doctorId ��ѯʦid
	 * @return ��ѯʦ����
	 */
	public Doctor getDoctorById(int doctorId) {
		return doctorDao.getDoctorByDoctorId(doctorId);
	}

	/**
	 * ͨ��doctorName�����ѯʦ(֧�ֵ绰��¼)
	 * @param doctorName ��ѯʦ�˺�
	 * @return ��ѯʦ����
	 */
	public Doctor getDoctor(String doctorName) {
		return doctorDao.getDoctor(doctorName);
	}

	/**
	 * �޸���ѯʦ����
	 * @param doctorId ��ѯʦid
	 * @param newPwd ������
	 * @return ��Ӱ������
	 */
	public int updateDoctorPwd(Integer doctorId, String newPwd) {
		return doctorDao.updateDoctorPwd(doctorId,newPwd);
	}

}
