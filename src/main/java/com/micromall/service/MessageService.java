package com.micromall.service;

import com.micromall.entity.Message;
import com.micromall.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class MessageService {
	@Resource
	private MessageRepository messageRepository;

	public List<Message> list(int uid, int p) {
		return null;
	}
}
