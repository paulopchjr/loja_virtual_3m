package loja_virtual_3m.com.services;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServiceSendoEmail {

	private String userName = "pchjr.dev2@gmail.com";
	private String senha = "hnlt pxlz wkzo nkig";

	@Async
	public void enviarEmailHtml(String assunto, String msg, String emailDestino) throws UnsupportedEncodingException, MessagingException {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFacotry");

		Session session = Session.getDefaultInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(userName, senha);
			}

		});
		
		
		session.setDebug(true);
	
		Address[] toUser = InternetAddress.parse(emailDestino);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName,"Paulo Henrique - Softium", "utf-8"));
	    message.setRecipients(Message.RecipientType.TO, toUser);
	    message.setSubject(assunto);
	    message.setContent(msg,"text/html; charset=utf-8");
	    
	    Transport.send(message);
	}

}
