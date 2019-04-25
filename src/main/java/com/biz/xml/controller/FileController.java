package com.biz.xml.controller;

import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.xml.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j //lombok
@MultipartConfig(maxFileSize = 1000000)
@Controller
public class FileController {

	@Autowired
	FileService fService;
	
	
	@ResponseBody
	@RequestMapping(value="file_up",method=RequestMethod.POST)
	public String file_up(MultipartFile file) {
		
		log.debug("업로드 파일" + file.getOriginalFilename());
		String fileName = fService.upload(file); //업로드라는 메서드에 전달을 해서 file을 fservice에 업로드 시킬것
		if(fileName == null) return "FAIL";
		else return fileName;
	}
	
	@ResponseBody
	@RequestMapping(value="files_up", method=RequestMethod.POST)
	public String files_up(MultipartHttpServletRequest files) {
		
		//fservice에 uploadfiles 메서드를 만들어서 files를 전달
		List<String> fileNames = fService.uploadFiles(files);
		return "OK";
		
	}
}
