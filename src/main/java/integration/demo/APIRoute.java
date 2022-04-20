package integration.demo;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import javax.ws.rs.core.MediaType;

public class APIRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().bindingMode(RestBindingMode.off);

        onException(Exception.class)
        .handled(true)
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
        .log(LoggingLevel.ERROR,"${exception.message}")
        .setBody(simple("${exception.message} "));

        rest("/multipart")
        .consumes(MediaType.MULTIPART_FORM_DATA)
        .produces(MediaType.TEXT_PLAIN)
        .post()
            .route()
            .log("received payload -> ${body}")
            .to("bean:attachmentBean")
            .convertBodyTo(String.class);


    }

  

}
