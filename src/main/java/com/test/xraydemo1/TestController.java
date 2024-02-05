package com.test.xraydemo1;

import java.lang.management.ManagementFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("xraydemo1")
public class TestController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/api")
	public ResponseEntity<String> getMsg(@RequestParam("code") int code) {
		if(code==200) {
			//String msg = postMessage("test app").getBody();
			String msg = restTemplate.postForObject(
			         "http://localhost:8080/xraydemo1/postapi/v3", "test", String.class);
			return ResponseEntity.ok().body(String.format("SUCCESS [%s]",msg)) ;
		} else {
			if(code>=400 && code<499) {
				return ResponseEntity.badRequest().body("bad req code:"+code);
			} else {
				return ResponseEntity.internalServerError().body("error");
			}
		}
	}
	
	@GetMapping("/api/v2")
	public ResponseEntity<String> getMsgV2(@RequestParam("code") int code) {
		if(code==200) {
			String msg = restTemplate.postForObject(
			         "http://localhost:8081/xraydemo1/postapi/v3", "test", String.class);
			return ResponseEntity.ok().body("--SUCCESS "+msg);
		} else {
			if(code>=400 && code<499) {
				return ResponseEntity.badRequest().body("--bad req code:"+code);
			} else {
				return ResponseEntity.internalServerError().body("--error");
			}
		}
	}
	
	@GetMapping("/api/v3")
	public ResponseEntity<String> getMsgV3(@RequestParam("code") int code) {
		if(code==200) {
			return ResponseEntity.ok().body("--v3--SUCCESS");
		} else { 
			if(code>=400 && code<499) { 
				return ResponseEntity.badRequest().body("--v3--bad req code:"+code);
			} else {
				return ResponseEntity.internalServerError().body("--v3--error");
			}
		}
	}
	
	@PostMapping("/postapi/v3")
	public ResponseEntity<String> postMessage(@RequestBody String name) {
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        long pid = Long.parseLong(jvmName.split("@")[0]);
		return ResponseEntity.ok().body("Hello "+name+" [processid: "+pid+"] ["+jvmName+"]");
	}
	
	@GetMapping("/api2/v1")
	public ResponseEntity<String> getMsgApi2(@RequestParam("code") int code) {
		if(code==200) {
			return ResponseEntity.ok().body("--API2--SUCCESS");
		} else {
			if(code>=400 && code<499) {
				return ResponseEntity.badRequest().body("--API2--bad req code:"+code);
			} else {
				return ResponseEntity.internalServerError().body("--API2--error");
			}
		}
	}
}
