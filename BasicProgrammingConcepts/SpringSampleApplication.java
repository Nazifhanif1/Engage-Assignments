package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.answer.Answer;
import com.example.demo.appUser.AppUserRepository;
import com.example.demo.appUser.AppUserServiceImpl;
import com.example.demo.question.Question;

import lombok.extern.java.Log;

@Log
@SpringBootApplication
public class SpringSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSampleApplication.class, args);

		System.out.println("Spring Sample Application");
	}

	@Bean
	public CommandLineRunner demo(AppUserRepository appUserRepository, AppUserServiceImpl appUserServiceImpl) {
		return (args) -> {
			log.info("Demo is working");
			
//			Question dummyQuestion = new Question();
//			
//			for (Answer ans : dummyQuestion.getAnswers())
//			{
//				ans.getDetails();
//			}
			
		};
	}

}


//appUserRepository.deleteAll();
//
//log.info("======================================");
//for (AppUser tempAppUser : appUserRepository.findAll()) {
//	log.info(tempAppUser.toString());
//}
//
//for (int i = 0; i < 10; i++) {
//	AppUser tempAppUser = new AppUser();
//	tempAppUser.setName("Biraj");
//	tempAppUser.setAddress("India");
//	tempAppUser.setPhoneNumber(123456789L);
//	
//	appUserServiceImpl.createAppUser(tempAppUser);
//	
//	//appUserRepository.save(tempAppUser);
//}
//
//log.info("======================================");
//for (AppUser tempAppUser : appUserRepository.findAll()) {
//	log.info(tempAppUser.toString());
//}
//
//Iterable<AppUser> tempArr = appUserRepository.findAll();
//for (AppUser user : tempArr) {
//	user.setName("Test");
//	user.setAddress("UK");
//	user.setPhoneNumber(123456789L);
//	
//	appUserServiceImpl.createAppUser(user);
//	
//	//appUserRepository.save(user);
//}
//
//log.info("======================================");
//for (AppUser tempAppUser : appUserRepository.findAll()) {
//	log.info(tempAppUser.toString());
//}
//
//for (AppUser user : tempArr) {
//	
//	if (user.getId() % 2 == 0) {
//		appUserRepository.delete(user);
//	}
//	
//}

//AppUser testAppUser = new AppUser();
//testAppUser.setName("Encapsulated");
//testAppUser.setAddress("???");
//testAppUser.setPhoneNumber(123456789L);
//AppUserServiceImpl.createAppUser(testAppUser);

//log.info("======================================");
//for (AppUser tempAppUser : appUserRepository.findAll()) {
//	log.info(tempAppUser.toString());
//}