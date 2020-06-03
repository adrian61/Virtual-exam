package io.pdsi.virtualexam;

import io.pdsi.virtualexam.web.storage.StorageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfiguration.class)
public class VirtualExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualExamApplication.class, args);
	}

}
