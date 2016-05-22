package com.micromall.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;

import java.util.*;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
public class XmlUtils {

	public static String convertToXml(Map<String, String> params) {
		return convertToXml(params, true);
	}

	public static String convertToXml(Map<String, String> params, boolean ignoreEmptyValue) {
		StringBuilder builder = new StringBuilder();
		builder.append("<xml>");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (StringUtils.isEmpty(entry.getValue()) && ignoreEmptyValue) {
				continue;
			}
			builder.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
		}
		builder.append("</xml>");
		return builder.toString();
	}

	public static Map<String, Object> convertToMap(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			return (Map<String, Object>)_xml_to_map(doc.getRootElement());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Object _xml_to_map(Element element) {
		Map<String, Object> map = new HashMap<>();
		List<Element> elements = element.elements();
		if (elements.size() == 0 && !element.isRootElement()) {
			return element.getText();
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(), _xml_to_map(elements.get(0)));
		} else if (elements.size() > 1) {
			Map<String, Element> tempMap = new HashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string, namespace));
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(_xml_to_map(ele));
					}
					map.put(string, list);
				} else {
					map.put(string, _xml_to_map(elements2.get(0)));
				}
			}
		}
		return map;
	}
}
