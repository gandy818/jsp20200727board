package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteFileService;
import article.service.WriteRequest;
import auth.service.User;
import mvc.controller.CommandHandler;

public class WriteArticleHandler implements CommandHandler{
	private static final String FORM_VIEW ="/WEB-INF/view/newArticleForm.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	private WriteFileService writeFile = new WriteFileService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		}else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res)  throws Exception{
		
		
		Part filePart = req.getPart("file1");
		String fileName = filePart.getSubmittedFileName();
		
		fileName = fileName == null ? "" : fileName; //null이면 빈string으로 변경
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		User user = (User)req.getSession(false).getAttribute("authUser");
		WriteRequest writeReq = createWriteRequest(user, req, fileName );
		writeReq.validate(errors);
		
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		int newArticleNo = writeService.write(writeReq);
		req.setAttribute("newArticleNo", newArticleNo);
		
		if(!(fileName == null || fileName.isEmpty() || filePart.getSize() == 0)) { //조건이 아니면 실행
			
			writeFile.write(filePart,newArticleNo);
		}
		
		return "/WEB-INF/view/newArticleSuccess.jsp";
	}
	
	
	
	private WriteRequest createWriteRequest(User user, HttpServletRequest req) {
		return createWriteRequest(user, req, "");
	}
	
	
	private WriteRequest createWriteRequest(User user, HttpServletRequest req, String fileName) {
		return new WriteRequest(new Writer(user.getId(), user.getName()), req.getParameter("title"), req.getParameter("content"), fileName);
	}

}
