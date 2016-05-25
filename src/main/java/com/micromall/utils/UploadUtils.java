package com.micromall.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by zhangzx on 16/3/28.
 */
public class UploadUtils {

	private static Logger logger = LoggerFactory.getLogger(UploadUtils.class);

	public static String _get_suffix(String name) {
		return name.substring(name.lastIndexOf(".") + 1).toLowerCase();
	}

	public static String _build_file_path(String parent, String filename) {
		Calendar calendar = Calendar.getInstance();
		StringBuffer buffer = new StringBuffer(parent);
		if (!parent.endsWith("/") || !parent.endsWith("\\")) {
			buffer.append(File.separator);
		}
		buffer.append(calendar.get(Calendar.YEAR)).append(File.separator).append(calendar.get(Calendar.MONTH) + 1).append(File.separator)
		      .append(UUID.randomUUID().toString().replaceAll("-", ""));
		if (StringUtils.isNotEmpty(filename) && filename.indexOf(".") != -1) {
			buffer.append(".").append(_get_suffix(filename));
		}
		return buffer.toString();
	}

	private static void _mkdirs(File file) {
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
	}

	public static String upload(String dir, MultipartFile multipartFile) {
		String path = _build_file_path(dir, multipartFile.getOriginalFilename());
		File file = new File(CommonEnvConstants.UPLOAD_ROOT_DIR, path);
		_mkdirs(file);
		try {
			multipartFile.transferTo(file);
		} catch (IOException e) {
			logger.error("保存文件出错：", e);
			return null;
		}
		return path;
	}

	public static String upload(String dir, String downloadUrl) {
		String path = _build_file_path(dir, null);
		File file = new File(CommonEnvConstants.UPLOAD_ROOT_DIR, path);
		_mkdirs(file);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		InputStream in = null;
		OutputStream out = null;
		try {
			HttpGet httpGet = new HttpGet(downloadUrl);
			HttpEntity httpEntity = httpclient.execute(httpGet).getEntity();
			if (httpEntity != null) {
				in = httpEntity.getContent();
				out = new FileOutputStream(file);

				byte[] data = new byte[1024];
				int len;
				while ((len = in.read(data)) != -1) {
					out.write(data, 0, len);
				}
			}
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
			logger.error("下载远程文件保存出错：", e);
			return null;
		} finally {
			IOUtils.closeQuietly(httpclient);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
		return path;
	}
}
