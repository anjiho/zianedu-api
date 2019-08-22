package com.zianedu.api.dto;

import java.util.List;

import javax.mail.Address;

import lombok.Data;

@Data
public class EmailSendDto {

	private String subject;

	private String content;

	private String receiver;

	private List<String> toCc;

	public EmailSendDto() {}

	public EmailSendDto(String subject, String content, String receiver, List<String>toCC) {
		this.subject = subject;
		this.content = content;
		this.receiver = receiver;
		this.toCc = toCC;
	}

}
