package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Question;
import model.service.QuestionService;
import utils.Util;

/**
 * 
 * @instruction
 * ����ģ��Ŀ�����
 */
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	QuestionService questionService = new QuestionService();
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String m = request.getParameter("m");
		
		if ("listQuestion".equals(m)) {

			//�õ���ѯ����
			String context = request.getParameter("context");
			
			
			// ��ѯ������������Ŀ
			List<Question> list = questionService.listQuestion(context);

			
			request.setAttribute("questionList", list);

			request.setAttribute("listSize", list.size());

			request.getRequestDispatcher("/admin/question.jsp").forward(request, response);

		}else if("updateQuestion".equals(m)) {
			
			//���ӻ����޸�
		
			//id��ֵ�����޸ģ�ûֵ�������
			String questionId = request.getParameter("id");
			
			
			//ȡ�ñ����ֵ
			String  questionNum = request.getParameter("questionNum");
			String  answerYesScore = request.getParameter("answerYesScore");
			String  answerNoScore = request.getParameter("answerNoScore");
			String  context = request.getParameter("context");

			
			//���������װΪQuestion����
			Question question = new Question();
			question.setQuestionNum(Integer.parseInt(questionNum));
			question.setAnswerYesScore(Integer.parseInt(answerYesScore));
			question.setAnswerNoScore(Integer.parseInt(answerNoScore));
			question.setContext(context);
			
			
			if(Util.isNotEmpty(questionId)) {
				//id��Ϊ�գ����޸�
				question.setQuestionId(Integer.parseInt(questionId));
				
				//�޸�
				questionService.updateQuestion(question,response);
				
			}else {
				//����
				questionService.addQuestion(question,response);
			}
			
		}else if("deletQuestion".equals(m)) {
			
			//ɾ��
			
			//��ȡҪɾ����doctor
			String questionId = request.getParameter("id");
			
			//����ɾ����Id
			String checkeds = request.getParameter("checkeds");
			
			//ɾ��������
			if(Util.isNotEmpty(questionId)) {
				questionService.deleteQuestion(Integer.parseInt(questionId),response);
			}
			
			//����ɾ��
			if(Util.isNotEmpty(checkeds)) {
				questionService.deleteQuestionCheckeds(checkeds,response);
			}
			
			
		}else if("selecteQuestion".equals(m)) {//ajax
			
			//Ҫ�鿴������
			String questionId = request.getParameter("id");
			
			//��ѯ�����ҽ����ݷ��أ�JSON��ʽ��
			questionService.getQuestionByQuestionIdToResponse(Integer.parseInt(questionId ),response);
			
			
			
		}
	
	}

}
