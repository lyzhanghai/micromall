package com.micromall.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class IPUtils {

	public static String getIp(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			params.put(key, request.getParameter(key));
		}
		return params;
	}

	/**
	 * 将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
	 * 
	 * @author Zhang Zhongxiang<an_huai@sina.cn>
	 * @date 2014年5月6日 上午10:16:37
	 * @param strIP
	 * @return long
	 */
	public static long ipToLong(String strIP) {
		long[] ip = new long[4];
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 将10进制整数形式转换成127.0.0.1形式的IP地址
	 * 
	 * @author Zhang Zhongxiang<an_huai@sina.cn>
	 * @date 2014年5月6日 上午10:16:52
	 * @param longIP
	 *            IP地址的10进制整数表示
	 * @return String
	 */
	public static String longToIP(long longIP) {
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf(longIP >>> 24));
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longIP & 0x000000FF));
		return sb.toString();
	}

	/**
	 * 判断IP是否在指定范围；
	 * 
	 * @author Zhang Zhongxiang<an_huai@sina.cn>
	 * @date 2014年5月6日 下午1:51:24
	 * @param ipSection
	 * @param ip
	 * @return boolean
	 */
	private static final String	REGX_IP		= "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
	private static final String	REGX_IPB	= REGX_IP + "\\-" + REGX_IP;

	public static boolean ipIsValid(String ipSection, String ip) {
		if (ipSection == null)
			throw new NullPointerException("IP段不能为空！");
		if (ip == null)
			throw new NullPointerException("IP不能为空！");
		ipSection = ipSection.trim();
		ip = ip.trim();
		if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))
			return false;
		int idx = ipSection.indexOf('-');
		String[] sips = ipSection.substring(0, idx).split("\\.");
		String[] sipe = ipSection.substring(idx + 1).split("\\.");
		String[] sipt = ip.split("\\.");
		long ips = 0L, ipe = 0L, ipt = 0L;
		for (int i = 0; i < 4; ++i) {
			ips = ips << 8 | Integer.parseInt(sips[i]);
			ipe = ipe << 8 | Integer.parseInt(sipe[i]);
			ipt = ipt << 8 | Integer.parseInt(sipt[i]);
		}
		if (ips > ipe) {
			long t = ips;
			ips = ipe;
			ipe = t;
		}
		return ips <= ipt && ipt <= ipe;
	}

	// 格式：127.0.0.1-127.0.0.10;127.0.2.1
	public boolean accessAllowedIP(String requestIP, String trustIp) {
		Set<String> ipRanges = new HashSet<String>();
		Set<String> ips = new HashSet<String>();
		StringTokenizer tokenizer = new StringTokenizer(trustIp, ";");
		while (tokenizer.hasMoreTokens()) {
			String ip = tokenizer.nextToken().trim();
			if (ip.indexOf("-") > 0) {
				ipRanges.add(ip);
			} else {
				ips.add(ip);
			}
		}
		if (ips.isEmpty() && ipRanges.isEmpty()) {
			return true;
		}
		if (ips.contains(requestIP)) {
			return true;
		}
		for (String ipSection : ipRanges) {
			if (ipIsValid(ipSection, requestIP)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		if (ipIsValid("192.168.1.1-192.168.1.10", "192.168.1.2")) {
			System.out.println("ip属于该网段");
		} else
			System.out.println("ip不属于该网段");
	}
}
