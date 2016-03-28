package com.micromall.service;

import com.micromall.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class MessageService {
	@Resource
	private MessageRepository messageRepository;
}
