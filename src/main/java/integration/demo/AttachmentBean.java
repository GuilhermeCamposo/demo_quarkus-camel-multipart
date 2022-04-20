package integration.demo;

import io.quarkus.arc.Unremovable;
import org.apache.camel.Exchange;
import org.apache.camel.attachment.Attachment;
import org.apache.camel.attachment.AttachmentMessage;
import org.jboss.logging.Logger;

import javax.activation.DataHandler;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Unremovable
@Named("attachmentBean")
public class AttachmentBean  {

    private static final Logger LOG = Logger.getLogger(AttachmentBean.class);

    public void processAttachment(Exchange exchange) throws Exception {

        AttachmentMessage attMsg = exchange.getIn(AttachmentMessage.class);

            for (String key : attMsg.getAttachmentObjects().keySet()) {

                LOG.info("attachment found!");

                Attachment attachment = attMsg.getAttachmentObject(key);
                DataHandler dh = attachment.getDataHandler();

                exchange.getIn().setBody(dh.getInputStream());
            }
    }


}
