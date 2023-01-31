package com.cjg.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
	
	private final KafkaProducer producer;
	
	@Autowired
	KafkaController(KafkaProducer producer){
		this.producer = producer;
	}
	
	
	@PostMapping("/produce")
	public String sendMessage(@RequestBody String message) {
		this.producer.sendMessage(message);
		return "success";
	}
	
	

}
