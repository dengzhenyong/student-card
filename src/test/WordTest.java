package test;
 
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.util.ConfigManager;
 
public class WordTest {
	public static void main(String[] args) {
		//exportDoc();
	}
 
	/**
	 *
	 * @Description: ����ҳ���ݵ���Ϊword @param @param file @param @throws
	 *               DocumentException @param @throws IOException �趨�ļ� @return void
	 *               �������� @throws
	 */
	public static String exportDoc33() {
		try {
			// ����ֽ�Ŵ�С
			Document document = new Document(PageSize.A4);
			// ����һ����д��(Writer)��document���������ͨ����д��(Writer)���Խ��ĵ�д�뵽������
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// C:\\Users\\orion\\Desktop\\home.jpg
			File file = new File("C:\\Users\\orion\\Desktop\\qwe.doc");
			RtfWriter2.getInstance(document, new FileOutputStream(file));
			document.open();
			// ������������
			BaseFont bfChinese = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			// ����������
			// Font titleFont = new Font(bfChinese, 12, Font.BOLD);
			// Paragraph title = new Paragraph("���Խ��");
			// ���ñ����ʽ���뷽ʽ
			// title.setAlignment(Element.ALIGN_CENTER);
			// title.setFont(titleFont);
			// document.add(title);
			// ����������
			Font contextFont = new Font(bfChinese, 12, Font.BOLD);
			// Font contextFont = new Font(bfChinese, 11, Font.NORMAL);
			for (int i = 0 ; i < 10 ; i++) {
				// code
				String code = "code ��  ";
				Paragraph codeStyle = new Paragraph(code);
				// ���ĸ�ʽ�����
				codeStyle.setAlignment(Element.ALIGN_LEFT);
				codeStyle.setFont(contextFont);
				// ����һ���䣨���⣩�յ�����
				codeStyle.setSpacingBefore(20);
				// ���õ�һ�пյ�����
				// context.setFirstLineIndent(0);
				document.add(codeStyle);
				// ����
				String codeContent = "���";
				Paragraph codeContentStyle = new Paragraph(codeContent, FontFactory
						.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 11, Font.UNDERLINE, new Color(0, 0, 255)));
				// ����һ���䣨���⣩�յ�����
				codeContentStyle.setSpacingBefore(5);
				document.add(codeContentStyle);
				// result
				String result = "result ��";
				Paragraph resultStyle = new Paragraph(result);
				// ���ĸ�ʽ�����
				resultStyle.setAlignment(Element.ALIGN_LEFT);
				resultStyle.setFont(contextFont);
				// ����һ����յ�����
				resultStyle.setSpacingBefore(10);
				// ���õ�һ�пյ�����
				// context.setFirstLineIndent(0);
				document.add(resultStyle);
				// ������FontFactory���Font��Color�������ø��ָ���������ʽ
				// ���
				String resultContent = "�ɹ�";
				Paragraph resultContentStyle = null;
				if (resultContent.equals("�ɹ�")) {
					resultContentStyle = new Paragraph(resultContent, FontFactory
							.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 11, Font.UNDERLINE, new Color(0, 255, 0)));
				} else {
					resultContentStyle = new Paragraph(resultContent, FontFactory
							.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 11, Font.UNDERLINE, new Color(255, 0, 0)));
				}
				// ����һ����յ�����
				resultContentStyle.setSpacingBefore(5);
				document.add(resultContentStyle);
				// ���ͼƬ Image.getInstance�����Է�·���ֿ��ԷŶ������ֽ���
				String imgPath = "C:\\Users\\orion\\Desktop\\home.jpg";
				Image img = Image.getInstance(imgPath);
				img.setAbsolutePosition(0, 0);
				img.setAlignment(Image.ALIGN_CENTER);// ����ͼƬ��ʾλ��
				img.scalePercent(30);// ��ʾ��ʾ�Ĵ�СΪԭ�ߴ��50%
				// img.scaleAbsolute(60, 60);// ֱ���趨��ʾ�ߴ�
				// img.scalePercent(25, 12);//ͼ��߿����ʾ����
				// img.setRotation(30);//ͼ����תһ���Ƕ�
				document.add(img);
				String log = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
				if (log != null && !"".equals(log)) {
					Paragraph exceptionStyle = new Paragraph("�쳣��Ϣ ��");
					// ���ĸ�ʽ�����
					exceptionStyle.setAlignment(Element.ALIGN_LEFT);
					exceptionStyle.setFont(contextFont);
					// ����һ����յ�����
					exceptionStyle.setSpacingBefore(20);
					document.add(exceptionStyle);
					document.add(new Paragraph(log,
							FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.NORMAL)));
				}
			}
 
			document.close();
			// �õ�������
			// wordFile = new ByteArrayInputStream(baos.toByteArray());
			// baos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
 
	}
	
	/**
	 *
	 * @Description: ����ҳ���ݵ���Ϊword @param @param file @param @throws
	 *               DocumentException @param @throws IOException �趨�ļ� @return void
	 *               �������� @throws
	 */
	public static String exportDocByImage(List<String> list) {
		try {
			// ����ֽ�Ŵ�С
			Document document = new Document(PageSize.A4);
			// ����һ����д��(Writer)��document���������ͨ����д��(Writer)���Խ��ĵ�д�뵽������
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// C:\\Users\\orion\\Desktop\\home.jpg
			//File file = new File("C:\\Users\\orion\\Desktop\\qwe.doc");
			Properties prop = ConfigManager.loadProperties("config.properties");
			String excelPath = prop.getProperty("doc_file_path");
			File file = new File(excelPath);
			
			RtfWriter2.getInstance(document, new FileOutputStream(file));
			document.open();
			// ������������
			BaseFont bfChinese = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			// ����������
			// Font titleFont = new Font(bfChinese, 12, Font.BOLD);
			// Paragraph title = new Paragraph("���Խ��");
			// ���ñ����ʽ���뷽ʽ
			// title.setAlignment(Element.ALIGN_CENTER);
			// title.setFont(titleFont);
			// document.add(title);
			// ����������
			Font contextFont = new Font(bfChinese, 12, Font.BOLD);
			// Font contextFont = new Font(bfChinese, 11, Font.NORMAL);
			for (int i = 0 ; i < list.size() ; i++) {
				// ���ͼƬ Image.getInstance�����Է�·���ֿ��ԷŶ������ֽ���
				//String imgPath = "C:\\Users\\orion\\Desktop\\home.jpg";
				//String imgPath = "D:\\test\\333\\������.jpg";
				String imgPath = list.get(i);
				Image img = Image.getInstance(imgPath);
				img.setAbsolutePosition(0, 0);
				img.setAlignment(Image.ALIGN_CENTER);// ����ͼƬ��ʾλ��
				img.scalePercent(30);// ��ʾ��ʾ�Ĵ�СΪԭ�ߴ��50%
				// img.scaleAbsolute(60, 60);// ֱ���趨��ʾ�ߴ�
				// img.scalePercent(25, 12);//ͼ��߿����ʾ����
				// img.setRotation(30);//ͼ����תһ���Ƕ�
				document.add(img);
				// code
				String code = "\n";
				Paragraph codeStyle = new Paragraph(code);
				// ���ĸ�ʽ�����
				codeStyle.setAlignment(Element.ALIGN_LEFT);
				codeStyle.setFont(contextFont);
				// ����һ���䣨���⣩�յ�����
				codeStyle.setSpacingBefore(200);
				// ���õ�һ�пյ�����
				// context.setFirstLineIndent(0);
				document.add(codeStyle);
			}

			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}
}
