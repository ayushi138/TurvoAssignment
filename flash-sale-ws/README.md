Assumptions

1) Java 8 or higher version to be installed
2) MySql Ver 14 to be installed


End points Exposed

1) Add user
2) Send email to users
3) Register users to enable them to purchase
4) Login registered users
5) Add watch
6) View the list of all watches
7) Purchase watch

Technology Used 

1) Java 8
2) Spring Boot
3) Spring Security
4) Spring Data JPA
5) Mysql 14
6) Json Web Token
7) Amazon Web Service Send Email Service
8) Apache commons lang

Steps for Running this App

1) Install Mysql database and set following credentials

username = root
pwd = password

2) execute the jar with the command

java -jar flash-sale-ws.jar

Execute the above command where this jar is placed

3) This will start the spring boot application, now the steps mentioned in the section how to use Flash Sale WS can be performed

4) Add the following dummy data to get started once the app is running

insert into flash_sale.company_users values(1,'ayushi.138@gmail.com','Ayushi','Srivastava',false,'ayushi'),
(2,'ayushi.cs3@gmail.com','Manu','Srivastava',false,'manu'),
(3,'arpitsrivastava89@gmail.com','Arpit','Srivastava',false,'arpit'),
(4,'ayushi.srivastava@imaginea.com','Ayushi','Srivastava',false,'ayushi'),
(5,'arpit.313@gmail.com','Arpit','Srivastava',false,'arpit'),
(6,'xyz@gmail.com','Xyz','Pqr',false,'xyz'),
(7,'abc@gmail.com','Abc','Pqr',false,'abc'),
(8,'def@gmail.com','Def','Pqr',false,'def'),
(9,'ghi@gmail.com','Ghi','Pqr',false,'ghi'),
(10,'mno@gmail.com','Mno','Srivastava',false,'mno');

insert into flash_sale.watch_entity values(1,2000,2,'Fossil');


Steps for locally executing the code from IDE


1) Take the checkout from Github with URL 

https://github.com/ayushi138/TurvoAssignment.git

2) Import the project in IDE

3) Perform maven clean and maven install

4) Start the application

Note: 
1) If the user wishes to change the parameter of database, then the same can be done in application.properties file eg, user and password

2) User can Also change the DB used by changing dependency for the DB connector at

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
3) Change the data sourec URL in application properties file

spring.datasource.url=jdbc:mysql://localhost:3306/flash_sale

4) After this perform maven clean and maven install and start the app


How to use Flash Sale WS

1) We can create users with the below call            

 POST request                 //Unsecured URL 

  http://localhost:8080/flash-sale-ws/company-user

{
	"firstName":"Ayushi",
	"lastName":"Srivastava",
	"emailAddress":"ayushi.138@gmail.com",
	"password":"ayushi"
}

2) We can send email to the users		
																											
GET request                     //Unsecured URL

 http://localhost:8080/flash-sale-ws/company-user/send-email


3) Open the mail box to check for the mail

Send a request to register
																										
PUT request                   //Unsecured URL

 http://localhost:8080/flash-sale-ws/company-user/register

{
	"emailAddress":"ayushi.138@gmail.com",
	"password":"ayushi"
}

remember this password as this is required to login to purchase

4) Send a request to login
 
POST                                                     //Unsecured URL but requires user to be registered

http://localhost:8080/flash-sale-ws/company-user/login     
 {
	"user":"ayushi.138@gmail.com",
	"password":"ayushi"
}

Once this is sent an Authorization header is returned, which is used in the subsequent calls
 
 This step is required as it generates an Authorization token which is required to proceed to purchase
 
 5) Send a request to purchase watch                                                               
 
 POST                                            //Secured URL

 http://localhost:8080/flash-sale-ws/watch/purchase

 {
	"email":"ayushi.138@gmail.com",
	"watch":"13",
	"address":"Lucknow"
}

In the Headers add  Authorization with the token which comes from the previous call

6) Watch can also be added 

POST            			 //Secured URL

  http://localhost:8080/flash-sale-ws/watch

{
	"name":"Fossil",
	"count":"2",
	"cost":"5000"
}


7) All the list of watches can also be viewed

GET 				  //Secured URL
	
http://localhost:8080/flash-sale-ws/watch

Note: For all the secured URLs please add the Authorization token as the Header parameter







