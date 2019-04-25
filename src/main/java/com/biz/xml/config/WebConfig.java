package com.biz.xml.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/*
 * servlet-context.xml의 일을 대신한다.
 */


@Configuration //이 파일은 설정 파일이다, 이 클래스는 config를 담당할 클래스다
@EnableWebMvc // 이 프로젝트가 SpringMVC 프로젝트다 라는것을 선언하는것.
@ComponentScan(basePackages = {"com.biz.xml.controller", "com.biz.xml.service"} ) 
//sevlet에서 마지막줄 <context:component-scan base-package="com.biz.xml" />과 같은 일

public class WebConfig implements WebMvcConfigurer {
	/*
	 * @Bean Annotation은 
	 * @Controller, @Service와 유사한 역할을 하는데
	 * @Controller나 @Service는 사용자(개발자) 호출하여 사용할 클래스들에
	 * 		지정하는 방식이고
	 * 
	 * @Bean은 spring(Dispatcher)가 사용할 클래스들에 부여하는 Annotation
	 * 
	 * @Component Annotation은
	 *   구체적으로 Controller, Service를 명시하지 않고
	 *   두루뭉실하게 사용하는 Annotation 
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		
		/*
		 * 1
		 * MultipartResolver 인터페이스를 이용해서 
		 * mr객체를 선언하고,
		 * multi... 인터페이스를 implement한 Commons... 클래스의 생성자로
		 * 초기화를 한 후 
		 */
		MultipartResolver
			mr = new CommonsMultipartResolver();

		// 다시 mr 객체를 Commons... 클래스로 1차 형변환을 한 후 
		// setMaxUploadSize와 같은 method를 호출해서
		// 기능을 사용해야 한다.(스프링방식)
		((CommonsMultipartResolver)mr).setMaxUploadSize(1000000);

		// 2
		// Multi...인터페이스를 경유하지 않고
		// 직접 Commons 클래스를 이용해서 resolver 객체를 선언 및 생성하여
		// 기능을 수행하도록 사용한다.
		// spring에서 권하는 방식은 아니지만
		// 코드의 번잡함을 없애기 위해서 어쩔 수 없는 선택
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(1000000000); // 한번에 올릴 수 있는 파일 크기
		resolver.setMaxUploadSizePerFile(1000000); // 파일 1개의 크기
		
		return resolver;
	}
	
	private void test() {
		//ArrayList와 같은 클래스를 사용할때 일반적인 방식
		ArrayList<String> sList = new ArrayList<String>();
		
		// 권장하는 방식
		// List Interface를 사용해서 선언을 하고
		// ArrayList를 사용해서 초기화를 하는 방식
		List<String> lList = new ArrayList<String>();
		
		// 1. 혹시 코드를 작성하는 중에 ArrayList를 LinkedList로 변경하고 싶다 할때
		// 선언부에서 다음과 같이 변경을 해도 
		List<String> iList = new LinkedList<>();
		// 나머지 부분 코드를 변경하지 않아도 
		// 코드가 정상적으로 작동되는것을 보장한다.
		
	}
	@Bean// 컨트롤로아 서비스와 비슷하지만  우리가 사용하는게 아니라 스프링이 사용한것
	public InternalResourceViewResolver resolver() {
		/*
		 * Controller에서 view를 randering할때 
		 * 역할을 대신할 클래스
		 */
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
				
		return resolver;
	}

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		//files 폴더를 외부에 공개하하는 것
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
		
		registry.addResourceHandler("/files/**")
		.addResourceLocations("/files/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
}
