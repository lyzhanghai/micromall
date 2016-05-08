package com.micromall.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: ChainMap
 * @Description: 
 *               使用链式编程模式对Map进行了一层封装，内层存储最终还是使用HashMap进行保存数据，里面所有以‘append’开头的方法调用后都会返回当前Map对象
 *               ， 使用方式:
 *               <code>new ChainMap<String, Object>("id","123").append("username", "admin").append("name", "张三")</code>
 * @author Zhang Zhongxiang<zhangzxiang91@gmail.com>
 * @date 2014年3月21日 下午2:08:20
 * 
 */
public class ChainMap<K, V> implements Map<K, V> {

	private Map<K, V>	map	= new HashMap<K, V>();

	/**********************************************************************************************/
	/**************** Map 扩展 **************************************************************/
	/**********************************************************************************************/

	public ChainMap() {
		map = new HashMap<K, V>();
	}

	public ChainMap(K key, V value) {
		this();
		map.put(key, value);
	}

	public ChainMap(Map<K, V> map) {
		this.map = map;
	}

	public ChainMap(Map<K, V> map, K key, V value) {
		this.map = map;
		this.map.put(key, value);
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public ChainMap<K, V> append(K key, V value) {
		map.put(key, value);
		return this;
	}

	public ChainMap<K, V> appendAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
		return this;
	}

	/**********************************************************************************************/
	/**************** Map 接口实现 **************************************************************/
	/**********************************************************************************************/

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> values() {
		return map.values();
	}

	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean containsKey(Object key) {

		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {

		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {

		return map.get(key);
	}

	@Override
	public V put(K key, V value) {

		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {

		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {

		map.putAll(m);
	}

	@Override
	public void clear() {

		map.clear();
	}
}
