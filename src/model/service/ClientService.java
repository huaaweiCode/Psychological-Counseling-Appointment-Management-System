package model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import bean.Client;
import model.dao.ClientDao;
import utils.ResultDate;
import utils.Util;

/**
 * 
 * @instruction
 * �����ߵ�Service
 */
public class ClientService {
	
	ClientDao clientDao = new ClientDao();

	
	/**
	 * ��ѯ�����������û�
	 * @param search ��ѯ����
	 * @return �����߶��󼯺�
	 */
	public List<Client> listSearch(Map<String, String> search) {

		return clientDao.listSearch(search);
	}
	
	
	/**
	 * �޸�client����is_active��ֵ���Ƿ�Ϊ����״̬
	 * 
	 * @param clientId ������id
	 * @param action Ҫ���õ�״ֵ̬
	 */
	public void toggleClientActive(Integer clientId, Integer action, HttpServletResponse response) {

		int i = clientDao.toggleClientActive(clientId, action);

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
	 * ��ѯ ���ж���������
	 * @return ����������
	 */
	public int getClientNum() {
		return clientDao.getClientNum();
	}


	/**
	 * �����˺Ų�ѯ �����ߣ�֧�ֵ绰��
	 * @param clientName �˺�
	 * @return �����߶���
	 */
	public Client getClient(String clientName) {
		
		return clientDao.getClient(clientName);
	
	}


	/**
	 * �޸�����������
	 * @param clientId ������id
	 * @param newPwd ������
	 * @return ��Ӱ������
	 */
	public int updateClientPwd(Integer clientId, String newPwd) {

		return clientDao.updateClientPwd(clientId,newPwd);
	}


	/**
	 * �޸������ߵĸ�����Ϣ
	 * @param client �����߶���
	 * @param clientId ������id
	 * @return ��Ӱ������
	 */
	public int updateClientBase(Client client, Integer clientId) {
		return clientDao.updateClientBase(client,clientId);
	}


	/**
	 * �õ�������
	 * @param clientId ������id
	 * @return �����߶���
	 */
	public Client getClientByClientId(int clientId) {
		return clientDao.getClientByClientId(clientId);
	}


	/**
	 * �жϵ绰�Ƿ���ã��Ƿ�ע�����
	 * @param phone �绰
	 * @param response ��Ӧ����
	 */
	public void checkPhoneResponse(String phone, HttpServletResponse response) {

		Client client = clientDao.getClient(phone);

		ResultDate rd = new ResultDate();
		if (client == null) {
			// ����
			rd.setIsSuccess(true);
			rd.setMsg("�绰�������");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("�Ѿ���ע���");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}


	/**
	 * ���һ������
	 * @param client �����߶���
	 */
	public int addClient(Client client) {

		return clientDao.addClient(client);
		
	}

}
