package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.ConfigManager;

public class StudentCard extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentCard() {
		System.out.println("HelloServlet's constructor...");
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String potoHeight = request.getParameter("potoHeight");
		String potoWidth = request.getParameter("potoWidth");
		String studentCode = request.getParameter("studentCode");
		String student_name_file_path = request.getParameter("student_name_file_path");
		student_name_file_path = new String(student_name_file_path.getBytes("ISO8859-1"),"UTF-8");
		String student_poto_file_path = request.getParameter("student_poto_file_path");
		String student_template_file_path = request.getParameter("student_template_file_path");
		Properties prop = ConfigManager.loadProperties("config.properties");
	    String filePath = prop.getProperty("file_path");
	    String cardPotoOut = prop.getProperty("card_poto_out");
	    
	    CreateStudentCard.createStudentCardMethod(student_name_file_path, student_poto_file_path, student_template_file_path, cardPotoOut);
	    
	    
		File file = new File(filePath);
		// ���һ�����content-type����Ϣͷ����������������ص�������͡�
		response.setContentType("application/octet-stream");
		//�ļ�������
		FileInputStream in = new FileInputStream(file);
		// ͨ����Ӧ������һ���������
		ServletOutputStream out = response.getOutputStream();
		byte[] b = new byte[1024];
		int n;
		while((n=in.read(b))!=-1){
		    out.write(b, 0, n);
		}
		// �ر��������رգ�Ҳû��ϵ�����������Զ��رա�
		in.close();
		out.close();
	}
	
}
