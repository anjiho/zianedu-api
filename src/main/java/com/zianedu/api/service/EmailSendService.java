package com.zianedu.api.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.dto.EmailSendDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class EmailSendService {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendService.class);

	@Autowired
	protected JavaMailSender mailSender;

	/**
	 *
	 * @param recipient 받는 사람의 메일주소
	 * @param subject 제목
	 * @param body 내용
	 * @throws Exception
	 */
	public void sendEmail(String recipient, String subject, String body) throws Exception {
		logger.info("이메일 발송 시작");
		String host = ConfigHolder.getMailSmtpHost();
		final String username = ConfigHolder.getMailUserId(); //네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.
		final String password = ConfigHolder.getMailUserPass(); //네이버 이메일 비밀번호를 입력해주세요.
		int port = ConfigHolder.getMailSmtpPort(); //포트번호

		Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성

		// SMTP 서버 정보 설정
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		//Session 생성
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String un = username;
			String pw = password;
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(un, pw);
			}
		});

		session.setDebug(false); //for debug
		Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
		mimeMessage.setFrom(new InternetAddress(ConfigHolder.getMailSendAddress())); //발신자 셋팅 , 보내는 사람의 이메일주소
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //수신자셋팅

		mimeMessage.setSubject(subject); //제목셋팅
		mimeMessage.setText(body); //내용셋팅
		Transport.send(mimeMessage); //javax.mail.Transport.send() 이용
	}

	public void test() throws Exception {
		String host = "smtp.daum.net";
		final String username = "anjo0070"; //네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.
		final String password = "c7684301-+"; //네이버 이메일 비밀번호를 입력해주세요.
		int port=465; //포트번호

		String recipient = "anjo0080@gmail.com"; //받는 사람의 메일주소를 입력해주세요.
		String subject = "메일테스트"; //메일 제목 입력해주세요.
		String body = "123422"; //메일 내용 입력해주세요.

		Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성

		// SMTP 서버 정보 설정
         props.put("mail.smtp.host", host);
         props.put("mail.smtp.port", port);
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.ssl.enable", "true");
         props.put("mail.smtp.ssl.trust", host);
        //Session 생성
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String un=username;
            String pw=password;
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(un, pw);
            }
        });

        session.setDebug(false); //for debug
        Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
        mimeMessage.setFrom(new InternetAddress("anjo0070@zianedu.com")); //발신자 셋팅 , 보내는 사람의 이메일주소
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //수신자셋팅

        mimeMessage.setSubject(subject); //제목셋팅
        mimeMessage.setText(body); //내용셋팅
        Transport.send(mimeMessage); //javax.mail.Transport.send() 이용
	}

}
