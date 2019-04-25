package com.biz.xml.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * web.xml의 일을 대신할 클래스
 * 
 */
public class AppInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		/*
		Class[] rootClass = new Class[] {RootConfig.class} ;
		
		return rootClass;
		*/
		
		return new Class[] {RootConfig.class}; // 한개가 아니라 여러개를 읽어 들이겠다.
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {WebConfig.class} ;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
}
}