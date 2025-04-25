/*package com.lonar.UserManagement.common;

import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.lonar.UserManagement.web.model.Mail;


@Component
@PropertySource(value = "classpath:mail.properties", ignoreResourceNotFound = true)
public class Mailer {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private Environment env;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void sendMail(Mail mail) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom(mail.getMailFrom().toString());
		smm.setTo(mail.getMailTo());
		smm.setSubject(mail.getMailSubject());
		smm.setText("dsds");
		mailSender.send(smm);

	}

	public int sendMail(Mail mail, VelocityContext velocityContext)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setFrom(new InternetAddress(env.getProperty("email.username").trim()));

				
					if (mail.getMailCc() != null && !mail.getMailCc().isEmpty()) {
						// message.setCc(InternetAddress.parse(mail.getMailCc()));
						message.setCc(StringUtils.split(mail.getMailCc(), ","));

					}
					if (mail.getAttachment() != null && !mail.getAttachment().isEmpty()) {
						File file = new File(mail.getAttachment());
						if (file.exists()) {
							FileSystemResource file1 = new FileSystemResource(file);
							message.addAttachment(file1.getFilename(), file);
						}
						// File file=new File(mail.getAttachment());
						// if(file.exists()){
						// message.addAttachment(file.getName(), file);
						// }
					}
				
				if (mail.getMailTo() != null && !mail.getMailTo().isEmpty())
					message.setTo(StringUtils.split(mail.getMailTo(), ","));
				message.setFrom(mail.getMailFrom().toString());
				message.setSubject(mail.getMailSubject());
				Template template = velocityEngine.getTemplate("./EmailTemplete/" + mail.getTemplateName());

				StringWriter stringWriter = new StringWriter();

				template.merge(velocityContext, stringWriter);
				message.setText(stringWriter.toString(), true);
			
			}

		};
		try{
		mailSender.send(preparator);
		return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public int sendHuhMail(Mail mail, VelocityContext velocityContext, Object object) {
		try
	    {
			 String smtpHostServer = "10.91.0.6";
			//String smtpHostServer = "smtp.gmail.com";

			 
			    //String emailID = "gajana.wayal@ppl.huhtamaki.com";
			    
			    Properties props = System.getProperties();

			    props.put("mail.smtp.host", smtpHostServer);

			    Session session = Session.getInstance(props, null);
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("no_reply@lexa.com", "NoReply-JD"));

	      msg.setReplyTo(InternetAddress.parse("no_reply@lexa.com", false));

	      msg.setSubject(mail.getMailSubject(), "UTF-8");

	      //-------------------------------------------------------------
	      Template template = velocityEngine.getTemplate("./EmailTemplete/" + mail.getTemplateName());

			StringWriter stringWriter = new StringWriter();

			template.merge(velocityContext, stringWriter);
			msg.setText(stringWriter.toString(), "UTF-8");
			 msg.setContent(stringWriter.toString(), "text/html; charset=utf-8");
	      //-------------------------------------------------------------
	     // msg.setText(mail., "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getMailTo(), false));
    	  Transport.send(msg);  

	      return 1;
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      return 0;
	    }
	}
}*/