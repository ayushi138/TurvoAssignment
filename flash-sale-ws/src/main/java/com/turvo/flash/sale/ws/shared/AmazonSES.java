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
			+ "<p>We are happy to announce flash sale on watches which will happen on "+LocalDate.now().plusDays(2)+"</p>"
			+"Perform the following steps to register<br/>"
			+ "1) Open postman and set to PUT request http://localhost:8080/flash-sale-ws/company-user/register\n" + 
			"<br/>"
			+ "2) Fill in the body with the json<br/>"
			+ " {\n" + 
			"	\"emailAddress\":\"your id\",\n" + 
			"	\"password\":\"your password\"\n" + 
			"} <br/>"
			+ "3) Press enter to send the request"
			+ "<br/><br/>"
			+"<p>After you are registered follow these steps to shop!!</p>"
			+ "1) Open postman and set to POST request http://localhost:8080/flash-sale-ws/company-user/login <br/>"
			+ "2) Fill in the body with the json<br/>"
			+ " {\n" + 
			"	\"user\":\"your id\",\n" + 
			"	\"password\":\"your password\"\n" + 
			"} <br/>"
			+ "3) Press enter to send the request<br/>"+
			"4) Copy the value of Authorization header"+
			"<br/><br/>"
			+ "1) Open postman and set to POST request http://localhost:8080/flash-sale-ws/watch/purchase <br/>"
			+"2) Add Authorization value in the request header as the one copied from previous call<br/>"
			+ "3) Fill in the body with the json<br/>"
			+ " {\n" + 
			"	\"email\":\"your id\",\n" + 
			"	\"watch\":\"watch id\",\n" + 
			"	\"address\":\"your address\"\n" + 
			"}<br/>"
			+ "4) Press enter to send the request"+
			"<br/><br/>"
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
