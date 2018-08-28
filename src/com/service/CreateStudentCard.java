package com.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.Student;
import com.util.ConfigManager;

import test.WordTest;

import javax.imageio.*;

public class CreateStudentCard {
	private static String studentCode = "201809103";

	public static void main(String[] args) {
		try {
			Properties prop = ConfigManager.loadProperties("config.properties");
			String excelPath = prop.getProperty("excel_path");
			String potoPath = prop.getProperty("poto_path");
			String potoTemplatePath = prop.getProperty("poto_template_path");
			String cardPotoOut = prop.getProperty("card_poto_out");
			List<Student> list = getXlsxExcelData(excelPath);
			List<String> imagePahtList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				if (true) {
					Student student = list.get(i);
					// 获取表格名字对应的学生照片
					File file = getStudentPoto(potoPath, student.getName());
					if (file == null) {
						continue;
					}
					if (i < 10) {
						exportImg(potoTemplatePath, cardPotoOut, student, file, studentCode + "0" + i, imagePahtList);
					} else {
						exportImg(potoTemplatePath, cardPotoOut, student, file, studentCode + i, imagePahtList);
					}
				}
			}
			// 将图片放到word中
			WordTest.exportDocByImage(imagePahtList);
		} catch (Exception e) {

		}
	}
	
	public static void createStudentCardMethod(String excelPath, String potoPath, String potoTemplatePath, String cardPotoOut){

		try {
			List<Student> list = getXlsxExcelData(excelPath);
			List<String> imagePahtList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				if (true) {
					Student student = list.get(i);
					// 获取表格名字对应的学生照片
					File file = getStudentPoto(potoPath, student.getName());
					if (file == null) {
						continue;
					}
					if (i < 10) {
						exportImg(potoTemplatePath, cardPotoOut, student, file, studentCode + "0" + i, imagePahtList);
					} else {
						exportImg(potoTemplatePath, cardPotoOut, student, file, studentCode + i, imagePahtList);
					}
				}
			}
			// 将图片放到word中
			WordTest.exportDocByImage(imagePahtList);
		} catch (Exception e) {

		}
	
	}

	public static void exportImg(String potoTemplatePath, String cardPotoOut, Student student, File studentPotoFile,
			String code, List<String> imagePathList) {
		try {
			// 读取原图片信息
			File srcImgFile = new File(potoTemplatePath);// 得到文件
			Image srcImg = ImageIO.read(srcImgFile);// 文件转化为图片
			int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
			int srcImgHeight = srcImg.getHeight(null);// 获取图片的高
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			g.setColor(new Color(255, 255, 255, 128)); // 根据图片的背景设置水印颜色
			g.setFont(new Font("微软雅黑", Font.PLAIN, 35)); // 设置字体
			g.setColor(Color.black);

			// 设置水印的坐�?
			g.drawString(student.getName(), srcImgWidth / 2 + 200, srcImgHeight / 2 + 40); // 画出水印
			g.drawString(student.get_class(), srcImgWidth / 2 + 200, srcImgHeight / 2 + 115); // 画出水印
			g.drawString(code, srcImgWidth / 2 + 200, srcImgHeight / 2 + 180); // 画出水印
			// 放置照片
			Image potoImg = ImageIO.read(studentPotoFile);// 文件转化为图片
			Image showImg = potoImg.getScaledInstance(potoImg.getWidth(null) - 38, potoImg.getHeight(null) - 81,
					Image.SCALE_DEFAULT);
			g.drawImage(showImg, 30, 170, null);
			g.dispose();
			// 输出图片
			String outPotoPath = cardPotoOut + student.getName() + ".jpg";
			FileOutputStream outImgStream = new FileOutputStream(outPotoPath);
			imagePathList.add(outPotoPath);
			ImageIO.write(bufImg, "jpg", outImgStream);
			System.out.println("添加水印完成");
			outImgStream.flush();
			outImgStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static List<Student> getXlsxExcelData(String excelPath) {
		List<Student> list = new ArrayList<Student>();
		InputStream is;
		try {
			is = new FileInputStream(new File(excelPath));
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// 取每�?个工作薄
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 获取当前工作薄的每一�?
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						// 这里�?要注�? 虽然是第�?列数�? 但是用这个输出的话得不到表格的数据�?�是返回 1.0 2.0
						// 这样是数�?
						// XSSFCell one = xssfRow.getCell(0);
						// System.out.println("�?" + rowNum + "�?" + "�?1�?" +
						// xssfRow.getCell(0));// 这样才能输出表格内的内容
						// 第二列数�?
						XSSFCell two = xssfRow.getCell(1);
						// System.out.println("�?" + rowNum + "�?" + "�?2�?" +
						// xssfRow.getCell(1));
						Student student = new Student();
						student.setName(String.valueOf(two));
						student.set_class("一(3)班");
						list.add(student);
						// 第三列数�?
						// XSSFCell three = xssfRow.getCell(2);
						// System.out.println("�?" + rowNum + "�?" + "�?3�?" +
						// xssfRow.getCell(2));
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		// �?
	}

	public static File getStudentPoto(String potoPath, String name) {
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(potoPath);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
				String fileName = tempList[i].getName();
				if (fileName.equals(name + ".JPG")) {
					return tempList[i];
				}
			}
			if (tempList[i].isDirectory()) {
				System.out.println("文件夹：" + tempList[i]);
			}
		}
		return null;
	}
}
