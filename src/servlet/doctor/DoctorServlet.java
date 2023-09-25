package servlet.doctor;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.sun.deploy.uitoolkit.impl.fx.Utils;

import bean.Doctor;
import model.service.DoctorService;
import utils.ResultDate;
import utils.UploadResult;
import utils.Util;

/**
 * 
 * @instruction
 * ����Ա�ˣ�������ѯʦ��ز����Ŀ�����
 */
@MultipartConfig
public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DoctorService doctorService = new DoctorService();
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String m = request.getParameter("m");
		
		if("listDoctor".equals(m)) {
			
			//���ܲ�ѯ����
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String startAge = request.getParameter("startAge");
			String endAge = request.getParameter("endAge");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			
			//��������װ��search��
			Map<String, String> search = new HashMap<String,String>();
			search.put("name", name);
			search.put("startAge", startAge);
			search.put("endAge", endAge);
			search.put("phone", phone);
			search.put("email", email);
			search.put("sex", sex);
			
			//��ѯ������������ѯʦ
			List<Doctor> list =  doctorService.listSearch(search);
			
			
			
			request.setAttribute("search", search);
			
			request.setAttribute("doctorList", list);
			
			request.setAttribute("listSize", list.size());
			
			request.getRequestDispatcher("/admin/doctorList.jsp").forward(request, response);
			
		}else if("updateActive".equals(m)){
			
			//ͣ�ú������л�
			
			String doctorId = request.getParameter("id");
			
			String action = request.getParameter("action");
			
			
			doctorService.toggleDoctorActive(doctorId,action,response);
			
			
		}else if("deletDoctor".equals(m)) {
			
			//ɾ��
			
			//��ȡҪɾ����doctor
			String doctorId = request.getParameter("id");
			
			//����ɾ����Id
			String checkeds = request.getParameter("checkeds");
			
			//ɾ��������
			if(Util.isNotEmpty(doctorId)) {
				doctorService.deleteDoctor(Integer.parseInt(doctorId),response);
			}
			
			//����ɾ��
			if(Util.isNotEmpty(checkeds)) {
				doctorService.deleteDoctorCheckeds(checkeds,response);
			}
			
			
		}else if("selecteDoctor".equals(m)) {//ajax
			
			//�鿴����
			
			//Ҫ�鿴����
			String doctorId = request.getParameter("id");
			
			//��ѯ�����ҽ����ݷ��أ�JSON��ʽ��
			 doctorService.getDoctorByDoctorIdToResponse(Integer.parseInt(doctorId ),response);
			
			
			
		} else if("updateDoctor".equals(m)) {
			
			//���ӻ����޸�
		
			//id��ֵ�����޸ģ�ûֵ�������
			String doctorId = request.getParameter("id");
			
			
			//ȡ�ñ����ֵ
			String  name = request.getParameter("name");
			String  age = request.getParameter("age");
			String  sex = request.getParameter("sex");
			String  email = request.getParameter("email");
			String  phone = request.getParameter("phone");
			String  level = request.getParameter("level");
			String  place = request.getParameter("place");
			String  skill = request.getParameter("skill");
			String  isActive = request.getParameter("isActive");
			
			
			//�������ĸ�����Ƭ
			
			UploadResult uploadResult = Util.upload("img",request,Util.UPLOAD_TYPE_IMG);
			
			//�õ�ͼƬ���·��
			String imgPath = uploadResult.getLogicFileName();
			
			
			//���������װΪDoctor����
			Doctor doctor = new Doctor();
			doctor.setName(name);
			doctor.setAge(Integer.parseInt(age));
			doctor.setSex(Integer.parseInt(sex));
			doctor.setEmail(email);
			doctor.setPhone(phone);
			doctor.setLevel(level);
			doctor.setPlace(place);
			doctor.setSkill(skill);
			doctor.setIsActive(Integer.parseInt(isActive ));
			doctor.setImg(imgPath);
			
			
			int i = 0; //���
			
			if(Util.isNotEmpty(doctorId)) {
				//id��Ϊ�գ����޸�
				doctor.setDoctorId(Integer.parseInt(doctorId ));
				
				//����ļ����СΪ0����ô˵��û���ϴ���
				if(uploadResult.getSize() == 0) {
					
					//ͼƬ����ԭ����·��
					
					String  img = request.getParameter("imgPath"); //ȡ���������ͼƬ·��
					
					doctor.setImg(imgPath);
					
				}
				
				//�޸�
				i = doctorService.updateDoctor(doctor);
				
			}else {
				//����
				i = doctorService.addDoctor(doctor);
			}
			
			//response.sendRedirect(request.getContextPath() + "/doctor/DoctorServlet?m=listDoctor");
			
			response.setContentType("application/json;charset=utf-8");
			
			ResultDate resultDate = new ResultDate();
			if(i == 1) {//�ɹ�
				
				resultDate.setIsSuccess(true);
				resultDate.setMsg("�ɹ�!");
				
				
			}else {//ʧ��
				resultDate.setIsSuccess(true);
				resultDate.setMsg("ʧ�ܣ���ˢ�º�����!");
			}
			
			Writer writer = response.getWriter();
			
			try {
				JSONUtil.serialize(writer,resultDate);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//�ر���
			writer.close();
			
		}
	
	}

}
