package com.turvo.flash.sale.ws.shared;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.turvo.flash.sale.ws.dto.CompanyUserDTO;

@Component
public class AmazonSES {
	

	final String FROM="ayushi.138@gmail.com";

	final String SUBJECT="Hurry Up Guys!! Flash Sale in 2 days ";
	
	final String HTMLBODY="<h1>Get to choose your favourite watches</h1>"
			+ "<p>We are happy to announce flash sale on watches which will happen in "+LocalDate.now().plusDays(2)+"</p>"
			+ "<a href='http://localhost:8080/registration-service/registration.html?emailAddress=$tokenValue1&name=$tokenValue2'>"
			+ "Click here to register"
			+ "</a><br/><br/>"
			+"<p>After you are registered click here to shop!!</p>"
			+ "<a href='http://localhost:8080/registration-service/login.html?emailAddress=$tokenValue1'>"
			+ "Click here to Shop"
			+ "</a><br/>"
			+ "Thank you!!!! We are waiting";
	
	public void sendFlassSaleEmail(CompanyUserDTO companyUserDto) {
		
		AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().
				withRegion(Regions.US_EAST_1).build();
		String htmlBodyWithToken = HTMLBODY.replace("$tokenValue1", companyUserDto.getEmailAddress())
				.replace("$tokenValue2", companyUserDto.getFirstName()+" "+companyUserDto.getLastName());
		SendEmailRequest request = new SendEmailRequest()
				.withDestination(new Destination().withToAddresses(companyUserDto.getEmailAddress()))
				.withMessage(new Message().
						withBody(new Body().
						withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken)))
				.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT))
				).withSource(FROM);
		client.sendEmail(request);
		System.out.println("Done");
	}

}
