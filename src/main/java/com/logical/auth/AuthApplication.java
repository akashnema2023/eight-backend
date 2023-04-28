package com.logical.auth;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();

	}
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return null;
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return false;
			}
		};
	}


	@Bean
	public JavaMailSender javaMailSender(){
		return new JavaMailSenderImpl();
	}


//	@Bean
//	RestTemplate getObject(){
//		return new RestTemplate();
//	}
}
