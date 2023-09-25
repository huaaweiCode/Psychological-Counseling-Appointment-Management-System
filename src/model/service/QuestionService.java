package model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Question;
import model.dao.QuestionDao;
import utils.ResultDate;
import utils.Util;

/**
 * 
 * @instruction
 * ����Service
 */
public class QuestionService {

	QuestionDao questionDao = new QuestionDao();
	
	
	/**
	 * ���һ��ԤԼ����
	 * @param question ����Ķ���
	 * @param response ��Ӧ����
	 */
	public void addQuestion(Question question, HttpServletResponse response) {

		int i = questionDao.addQuestion(question);		

		ResultDate rd = new ResultDate();
		if (i == 1) {
			// �޸ĳɹ�
			rd.setIsSuccess(true);
			rd.setMsg("��ӳɹ�");

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);
	}
	
	/**
	 * �鿴���е�����
	 * @param context ��ѯ������������������ݣ�
	 * @return ������󼯺�
	 */
	public ArrayList<Question> listQuestion(String context){
		
		return questionDao.listQuestion(context);		
	}

	
	/**
	 * �޸�һ�����⣬���ݽ����Ӧ���ͻ���
	 * @param question Ҫ���µ�����
	 * @param response ��Ӧ����
	 */
	public void updateQuestion(Question question, HttpServletResponse response) {

		int i = questionDao.updateQuestion(question);

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
	 * ��ѯһ�����⣬���ҷ�����Ӧ
	 * @param questionId �����id
	 * @param response ��Ӧ����
	 */
	public void getQuestionByQuestionIdToResponse(int questionId, HttpServletResponse response) {

		Question question = questionDao.getQuestionByQuestionId(questionId);

		ResultDate rd = new ResultDate();
		if (question != null) {
			// �ɹ�
			rd.setIsSuccess(true);
			rd.setMsg("��ѯ�ɹ�");
			rd.getDataList().add(question);

		} else {

			// �޸�ʧ��
			rd.setIsSuccess(false);
			rd.setMsg("ʧ�ܣ���ˢ��ҳ�������");

		}

		// ��Ӧ��JSON��ʽ����
		Util.responseJson(rd, response);

	}

	/**
	 *  ɾ��һ������
	 * @param questionId �����id
	 * @param response ��Ӧ����
	 */
	public void deleteQuestion(int questionId, HttpServletResponse response) {

		int i = questionDao.deleteQuestion(questionId);

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
	 * ɾ������ѡ�е�����
	 * @param checkeds Ҫɾ��������id(1,2,3)ʹ��","�ָ�
	 * @param response ��Ӧ����
	 */
	public void deleteQuestionCheckeds(String checkeds, HttpServletResponse response) {

		String[] checkedStrs = checkeds.split(",");

		int i = 0;

		for (String checked : checkedStrs) {

			if (questionDao.deleteQuestion(Integer.parseInt(checked)) == 1) {
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
	 * ͨ��questionIds������һ��json�ַ����������û��ش����ݣ���Ŀ��
	 * @param questionIds ����id
	 * @param request �������
	 * @return ����һ��map��������� "JSON"��key��valueΪ�����߻ش���ʾ����ݣ�����context,
	 * answer_yes_score,answer_no_score��json�ַ�����"level"��key��valueΪ��ݻش�ķ�ֵ
	 * 
	 * //��Ŀ����context���ǵķ�ֵanswer_yes_score����ķ�ֵanswer_no_score��
	   //clientSelected�û�ѡ��ķ�ֵ
	 */
	public HashMap<String,String> getJSON(String questionIds, HttpServletRequest request) {
		
		HashMap<String,String> mapJsonLevel = new HashMap<String,String>();
		
		String[] questions = questionIds.split(",");
		
		//�����ֵ
		Integer level = 0;
		
		//����JSON
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		
		for(String id : questions) {
			
			String questionId = "question" + id;
			
			String context = request.getParameter("context" + id);
			
			String answer_yes_score = request.getParameter("answerYesScore" + id);
			
			String answer_no_score = request.getParameter("answerNoScore" + id);
			
			String clientSelected = request.getParameter(id);
			
			level += Integer.parseInt(clientSelected);
			
			
			sb.append("\"" + questionId + "\":{");
			sb.append("\"context\":\"" + context + "\",");
			sb.append("\"answer_yes_score\":\"" + answer_yes_score + "\",");
			sb.append("\"answer_no_score\":\"" + answer_no_score + "\",");
			sb.append("\"clientSelected\":\"" + clientSelected + "\"");
			sb.append("},");
			
		}
		
		//ɾ�������","��
		sb.deleteCharAt(sb.length()-1);
		
		sb.append("}");
		
		mapJsonLevel.put("JSON", sb.toString());
		mapJsonLevel.put("level", level+"");
		
		return mapJsonLevel;
	}
	
	
	
}
