package com.tianer.util;
 
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tianer.entity.system.AppValidation;

public class XmlUtil {

	public static Map<String,HashMap<String, AppValidation>> initAppValidation() {
		String filePath = "/appvalidation/Validation.xml";
		return parseXMLData(filePath);
	}

	private static Document parse2Document(String xmlFilePath) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			InputStream in = XmlUtil.class.getResourceAsStream(xmlFilePath);
			document = reader.read(in);
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
			System.out
					.println("读取classpath下xmlFileName文件发生异常，请检查CLASSPATH和文件名是否存在！");
			e.printStackTrace();
		}
		return document;
	}

	private static Map<String,HashMap<String, AppValidation>> parseXMLData(String xmlFileName) {
		Map<String,HashMap<String, AppValidation>> appValidations = new HashMap<String,HashMap<String, AppValidation>>();
		HashMap<String, AppValidation> appValidationField = new HashMap<String, AppValidation>();
		// 产生一个解析器对象
		SAXReader reader = new SAXReader();
		// 将xml文档转换为Document的对象
		Document document = parse2Document(xmlFileName);
		// 获取文档的根元素
		Element root = document.getRootElement();
		// 遍历当前元素(在此是根元素)的子元素
		for (Iterator i_beans = root.elementIterator(); i_beans.hasNext();) {
			Element beans = (Element) i_beans.next();
			for (Iterator i_bean = beans.elementIterator(); i_bean.hasNext();) {
				AppValidation appValidation = new AppValidation();
				Element element = (Element) i_bean.next();
				String id = element.attributeValue("id");
				String required = element.attributeValue("required");
				String maxlength = element.attributeValue("maxlength");
				String minlength = element.attributeValue("minlength");
				String max = element.attributeValue("max");
				String min = element.attributeValue("min");
				String defaultValue = element.element("default").getText();
				appValidation.setId(id);
				appValidation.setRequired(required);
				appValidation.setMax(max);
				appValidation.setMin(min);
				appValidation.setDefaultValue(defaultValue);
				if (Tools.notEmpty(maxlength)) {
					appValidation.setMaxlength(Integer.parseInt(maxlength));
				}
				if (Tools.notEmpty(minlength)) {
					appValidation.setMinlength(Integer.parseInt(minlength));
				}

				// 获取当前元素element下的子元素enumerations
				Element e_enums = element.element("enumerations");
				Map<String, String> enums = new HashMap<String, String>();
				for (Iterator i_enums = e_enums.elementIterator(); i_enums
						.hasNext();) {
					Element e_enum = (Element) i_enums.next();
					String _enum = e_enum.getTextTrim();
					if (Tools.notEmpty(_enum)) {
						enums.put(_enum, "1");
					}
				}
				appValidation.setEnumeration(enums);
				appValidationField.put(appValidation.getId(), appValidation);
			}
			appValidations.put(beans.attributeValue("id"), appValidationField);
		}
		return appValidations;
	}

	/**
	 * 介绍Element中的element方法和elements方法的使用
	 * 
	 * @param node
	 */
	public void elementMethod(Element node) {
		Element e = node.element("bean");
		Element author = e.element("作者");
		System.out.println(e.getName() + "----" + author.getText());
		List<Element> authors = e.elements("作者");
		for (Element aut : authors) {
			System.out.println(aut.getText());
		}

		List<Element> elements = e.elements();
		for (Element el : elements) {
			System.out.println(el.getText());
		}

	}
}
