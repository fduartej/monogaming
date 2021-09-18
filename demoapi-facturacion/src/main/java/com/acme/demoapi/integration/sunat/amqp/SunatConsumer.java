package com.acme.demoapi.integration.sunat.amqp;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.acme.demoapi.integration.sunat.dto.Invoice;

import java.nio.charset.StandardCharsets;

@Service
public class SunatConsumer {
    
    @RabbitListener(queues = "${sunat.rabbitmq.queue.response}")
    public void receiveMessage(Message message) {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        try{
            ObjectMapper mapper = new ObjectMapper();
            Invoice invoice =mapper.readValue(json, Invoice.class);
            System.out.println("my invoice :"+invoice);
          }catch(Exception e){
            e.printStackTrace();
          }
    }
}
