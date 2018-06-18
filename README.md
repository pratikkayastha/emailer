# Emailer
An app written in Spring Boot that sends out emails for either Sendgrid or Mailgun. The app accepts a POST request and tries to send out email first from Sendgrid. If this fails, the app then tries to send out the mail from Mailgun.

## How to run
You can run the app by running the following command.
You will need JDK1.8 installed.
```
./gradlew bootRun
```
The application will run on port 8080.

Run the following to build an executable JAR.
```
./gradlew build
```

Run the following to run the JAR.
```
java -jar jar-name.jar --spring.config.location=classpath:/application.properties,classpath:/sender.properties
```

## API Codes
The codes does not contain API keys for Sendgrid and Mailgun, you will have to generate both API keys and place them in the following file

```
/src/main/resources/sender.properties
```

The content of this files looks like this

```
sender.sendgrid.accessKey=__SENDGRID_ACCESS_KEY__
sender.mailgun.key=__MAILGUN_KEY__
```

## Sample Request
Send the following JSON to http://localhost:8080/email

```json
{
	"recipients" : ["email1@address.com", "email4@address.com"],
	"subject": "Hey",
	"content":"Hey how are you?",
	"ccRecipients":["email2@address.com"],
	"bccRecipients":["email3@address.com"]
}
```

If successful, the app will return a 200 status code with following response
```json
{
	"status" : "OK",
	"messages": ["OK"],
	"sender":"Sendgrid"
}
```

Incase of failure, the app will return an either 4xx or 5xx status code with following response.

```json
{
	"status" : "FAILURE",
	"messages": ["...error message.."],
	"sender":"Sendgrid"
}
```

## Todo
Parse the response sent by each WS API
Current, I just check the response code; if response code = 2xx, its a success, otherwise its a failure. To parse the response, we will have to create POJO corresponding to the response and ask RestTemplate to parse the response

```
ResponseEntity<ResponsePOJO> response = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, ResponsePOJO.class);
```
RestTemplate throws HttpStatusCodeException if status code != 2xx, so we will have to catch it and parse the response string. The response is available as string if case of HttpStatusCodeException, so we will have to parse it to POJO using com.fasterxml.jackson.databind.ObjectMapper.
