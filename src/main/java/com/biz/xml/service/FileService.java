package com.biz.xml.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class FileService {

	@Autowired
	ServletContext context;
	
	public String upload(MultipartFile file) {
		// TODO Auto-generated method stub
		
		String realPath = context.getRealPath("/files/");
		
		
		if(file.isEmpty()|| file ==null)
			return null;
		
		String fileName = file.getOriginalFilename();
		
		
		// 파일 저장할 디렉토리가 있는지 검사
		File dir = new File(realPath);
		if(!dir.exists())
			dir.mkdir();
		

		try {
			// 파일을 담을 byte 배열을 생성
			//...byte 배열을 생성하면서 ... 파일의 내용을 같이 가져와서 담아놓는다
			// DMA(Direct Memory Access)기능
			byte[] bytes = file.getBytes();
			
			File saveFile = new File(realPath,fileName);
			
			FileOutputStream fo = new FileOutputStream(saveFile);
			
			// 저장하기 위한 Stream(서버의 로컬디스크에 저장하기 위해 사용하는 클래스)
			// (로컬)디스크(스토리지)에 파일을 기록하기 위한 클래스(그래서 Output)
			// 파일저장할곳을 만들고 fileoutputstream을 만들고 그것을 저장하기위해
			// buffer...만들고
			// 웹브라우저로부터 날아온 파일은 bytes에 저장되있고 그 변수에 저장되어있는 내용을
			// bufferedoutputstream에게 보내줌.
			// 그 파일은 다시 fileoutputstream으로 가고 그 파일은 다시
			// file(realpath, filename)에 간다.
			BufferedOutputStream bo = new BufferedOutputStream(fo);
			
			// write를 실행하면 bytes에 담긴 내용을 buffer..stream으로 전달하고
			// buffer...stream은 file...stream에게 전달하여
			// 실제 로컬 디스크에 기록을 한다. <기록은 거꾸로>
			bo.write(bytes);
			// 여기까지 실행이되면 파일의 내용이 로컬디스크로 가기전
			// 임시 저장소에 보관된다.
			
			bo.close();
			// 임시저장소에 보관된 내용이 물리적 파일로 기록
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}

	public List<String> uploadFiles(MultipartHttpServletRequest files) {
		// TODO Auto-generated method stub
		
		// 파일을 여러개 보낼때 (다중파일 업로드)
		List<MultipartFile> fileList = files.getFiles("files");
		
		String realPath = context.getRealPath("/files/");
		// 디렉토리 만들기
		
		try {
		for(MultipartFile f : fileList) {
				File file = new File(realPath, f.getOriginalFilename());
				f.transferTo(file);
		}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return null;
	}

	
}
