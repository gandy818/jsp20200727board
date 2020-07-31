package article.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Part;

public class WriteFileService {
	public void write(Part part, int no) {
		String path = "c:/tempfiles/" + no;
		File file = new File(path);
		
		if(!file.exists()) { //존재하지 않으면
			file.mkdirs(); //디렉토리 생성
		}
		
		try {
			part.write(path +"/" + part.getSubmittedFileName()); //폴더명은 insert 된 다음에 알 수 있으므로
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

