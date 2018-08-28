package com.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.util.ConfigManager;

public class UploadFile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static int BUFFER_SIZE = 1024;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		try {
			// request.getParameter("");
			// 建本地磁盘工厂
			long currentTimeMills = System.currentTimeMillis();
			Properties prop = ConfigManager.loadProperties("config.properties");
			String fileSavePath = prop.getProperty("data_path") + currentTimeMills;
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			// ���������ļ���
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			// fileUpload.setHeaderEncoding("utf-8");
			fileUpload.setHeaderEncoding("UTF-8");// ��������ļ����ϴ�����.
			// �����ϴ���
			List<FileItem> list = fileUpload.parseRequest(request);
			Map<String, String> map = new HashMap<String, String>();
			File file = null;
			String fileName = null;
			JSONObject json = new JSONObject();
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					// ����Ǳ?��
					String name = fileItem.getFieldName();
					String string = fileItem.getString("utf-8");
					// �?��ļ���
					map.put(name, string);
				} else {
					// �ϴ���
					fileName = fileItem.getName();
					// ����ļ�Ҫ�ϴ���·���������·�������Զ��壩:
					File directory = new File(fileSavePath);
					if (!directory.exists()) {
						boolean flag = directory.mkdirs();
						System.out.println(flag);
					}
					file = new File(directory + "/" + fileName);
					fileItem.write(file);
					json.put("path", directory + "/" + fileName);
				}
			}
			if (map.get("type").equals("2")) {
				json.put("path", fileSavePath + "/" + fileName.substring(0, fileName.lastIndexOf(".")));
				// 解压
				boolean flag = unZipFiles(file, fileSavePath + "/");
				if (!flag) {
					System.out.println("解压异常");
				}
			}
			// BeanUtils.populate(); //��ʵ���Ӧ�����Ը���ʵ�壨�ռ���ݣ�
			if (!fileName.equals(null) && !fileName.equals("")) {
				// ��ͼƬ·������ʵ���ĳ������
			}

			json.put("status", 1);
			json.put("msg", "ok");
			PrintWriter writer = response.getWriter();
			writer.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压文件到指定目录 解压后的文件名，和之前一致
	 * 
	 * @param zipFile
	 *            待解压的zip文件
	 * @param descDir
	 *            指定目录
	 */
	public static boolean unZipFiles(File srcFile, String destDirPath) throws IOException {

		long start = System.currentTimeMillis();

	     // 判断源文件是否存在

	     if (!srcFile.exists()) {

	         throw new RuntimeException(srcFile.getPath() + "所指文件不存在");

	     }

	     // 开始解压

	     ZipFile zipFile = null;

	     try {

	         zipFile = new ZipFile(srcFile, Charset.forName("GBK"));

	         Enumeration<?> entries = zipFile.entries();

	         while (entries.hasMoreElements()) {

	             ZipEntry entry = (ZipEntry) entries.nextElement();

	             System.out.println("解压" + entry.getName());

	             // 如果是文件夹，就创建个文件夹

	             if (entry.isDirectory()) {

	                 String dirPath = destDirPath + "/" + entry.getName();

	                 File dir = new File(dirPath);

	                 dir.mkdirs();

	             } else {

	                 // 如果是文件，就先创建一个文件，然后用io流把内容copy过去

	                 File targetFile = new File(destDirPath + "/" + entry.getName());

	                 // 保证这个文件的父文件夹必须要存在

	                 if(!targetFile.getParentFile().exists()){

	                     targetFile.getParentFile().mkdirs();

	                 }

	                 targetFile.createNewFile();

	                 // 将压缩文件内容写入到这个文件中

	                 InputStream is = zipFile.getInputStream(entry);

	                 FileOutputStream fos = new FileOutputStream(targetFile);

	                 int len;

	                 byte[] buf = new byte[BUFFER_SIZE];

	                 while ((len = is.read(buf)) != -1) {

	                     fos.write(buf, 0, len);

	                 }

	                 // 关流顺序，先打开的后关闭

	                 fos.close();

	                 is.close();

	             }

	         }

	         long end = System.currentTimeMillis();

	         System.out.println("解压完成，耗时：" + (end - start) +" ms");

	     } catch (Exception e) {
	    	 
	         throw new RuntimeException("unzip error from ZipUtils", e);

	     } finally {

	         if(zipFile != null){

	             try {

	                 zipFile.close();

	             } catch (IOException e) {

	                 e.printStackTrace();

	             }

	         }

	     }
	     return true;
	 }
}
