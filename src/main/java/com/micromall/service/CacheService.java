package com.micromall.service;

import java.io.Serializable;

/**
 * <pre>
 * cache 服务接口，过期时间以秒为单位
 *
 * @author Zhang Zhongxiang<an_huai@sina.cn>
 * @ClassName: CacheService
 * @date 2014年5月6日 下午3:48:05
 */
@Deprecated
public interface CacheService extends Serializable {

	int SECOND = 1;
	int MINUTE = 60 * SECOND;
	int HOUR   = 60 * MINUTE;
	int DAY    = 24 * HOUR;
	int WEEK   = 7 * DAY;

	<T> T get(final String key);

	<T> T get(final String namespace, final String key);

	<T> void set(final String key, final T value);

	<T> void set(final String key, final T value, final int expried);

	<T> void set(final String namespace, final String key, final T value);

	<T> void set(final String namespace, final String key, final T value, final int expried);

	boolean del(final String key);

	boolean del(final String namespace, final String key);
}
