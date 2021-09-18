package gob.sunat.apisunat.integration;

import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gob.sunat.apisunat.model.Invoice;

@Service
public class FacturaProducer {

    private AmqpTemplate amqpTemplate;
    @Value("${sunat.rabbitmq.exchange.response}")
    private String EXCHANGE_NAME;

    public FacturaProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void send(Invoice invoice){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(invoice);
            amqpTemplate.convertSendAndReceive(EXCHANGE_NAME, "", json);
          }catch(JsonProcessingException e){
            e.printStackTrace();
          }

    }
    
}
