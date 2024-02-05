package com.test.xraydemo1;
import java.net.URL;
import java.util.UUID;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.plugins.EC2Plugin;
import com.amazonaws.xray.plugins.ElasticBeanstalkPlugin;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;

@Configuration
public class WebConfig {
  private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);
  private static String uuid = UUID.randomUUID().toString();
  private static String[] uuidSplit = null;
  
  static {
	  uuidSplit = uuid.split("-");
  }
  

  @Bean
  public Filter TracingFilter() {
    return new AWSXRayServletFilter("app-in-ec2-"+uuidSplit[uuidSplit.length-1]);
  }

  static {
    AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard()
    		.withPlugin(new EC2Plugin());

    URL ruleFile = WebConfig.class.getResource("/sampling-rules.json");
    builder.withSamplingStrategy(new LocalizedSamplingStrategy(ruleFile));

    AWSXRay.setGlobalRecorder(builder.build());

    AWSXRay.beginSegment("app-in-ec2-init-"+uuidSplit[uuidSplit.length-1]);

    AWSXRay.endSegment();
  }
}