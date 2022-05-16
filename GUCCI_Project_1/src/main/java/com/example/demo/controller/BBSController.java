package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.service.BBSSVC;
import com.example.demo.service.UserSVC;
import com.example.demo.vo.BBSVO;
import com.example.demo.vo.FileVO;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;

@Controller
public class BBSController {

	@Autowired
	HttpSession session;
	
	@Autowired
	private UserSVC login;
	
	@Autowired
	private BBSSVC svc;
	
	
	@PostMapping("/bbs_delete/{num}")
	@ResponseBody
	public String delete(@PathVariable("num")int num) {
		return String.format("{\"deleted\":%b}", svc.delete(num));
	}
	
	@PostMapping("/bbs_edit")
	@ResponseBody
	public RedirectView edited(BBSVO vo, @RequestParam String ctgr, @RequestParam MultipartFile[] files) {
		svc.edit(vo);
		if (files != null) {
			svc.add(vo, files);
		}
		return new RedirectView("/bbs_detail/"+vo.getNum()+"/"+ctgr);
	}
	
	@GetMapping("/bbs_edit/{num}/{ctgr}")
	public String edit(@PathVariable("num")int num, Model m, @PathVariable("ctgr")String ctgr) {
		if(login.isLogin()) {
			m.addAttribute("edit", svc.detail(num));
			m.addAttribute("category", ctgr);
			List<FileVO> fileList = svc.getFileList(num);
			m.addAttribute("fileList", fileList);
			return "bbs_edit";
		} else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
	@ResponseBody
	public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
		
		JsonObject jsonObject = new JsonObject();
		
		String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
				
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		
		File targetFile = new File(fileRoot + savedFileName);	
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	@GetMapping("/bbs_download/{idx}")
	public void downloadAttachFile(@PathVariable("idx")long idx, Model model, HttpServletResponse response) {

		FileVO fileInfo = svc.getAttachDetail(idx);
		if (fileInfo == null || "Y".equals(fileInfo.getDeleteYn())) {
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
		}

		String uploadDate = fileInfo.getInsertTime().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadPath = Paths.get("C:", "develop", "upload", uploadDate).toString();

		String filename = fileInfo.getOriginalName();
		File file = new File(uploadPath, fileInfo.getSaveName());

		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");

			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (IOException e) {
			throw new RuntimeException("파일 다운로드에 실패하였습니다.");

		} catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}
	}
	
	@GetMapping("/bbs_detail/{num}/{ctgr}")
	public String detail(@PathVariable("num")int num, Model m, @PathVariable("ctgr")String ctgr,
			final HttpServletRequest request, final HttpServletResponse response) {
		
		// 저장된 쿠키 불러오기
	    Cookie cookies[] = request.getCookies();
	    Map mapCookie = new HashMap();
	    if(request.getCookies() != null){
	    	for (int i = 0; i < cookies.length; i++) {
		        Cookie obj = cookies[i];
		        mapCookie.put(obj.getName(),obj.getValue());
	      	}
	    }

	    // 저장된 쿠키중에 read_count 만 불러오기
	    String cookie_read_count = (String) mapCookie.get("read_count");
	    // 저장될 새로운 쿠키값 생성
	    String new_cookie_read_count = "|" + num;

	    // 저장된 쿠키에 새로운 쿠키값이 존재하는 지 검사
	    if ( StringUtils.indexOfIgnoreCase(cookie_read_count, new_cookie_read_count) == -1 ) {
	    	// 없을 경우 쿠키 생성
	        Cookie cookie = new Cookie("read_count", cookie_read_count + new_cookie_read_count);
	        //cookie.setMaxAge(1000); // 초단위
	        response.addCookie(cookie);

	        // 조회수 업데이트
	        svc.incrementCnt(num);
	    }
		
		m.addAttribute("detail", svc.detail(num));
		m.addAttribute("category", ctgr);
		String uid = (String)session.getAttribute("uid");
    	m.addAttribute("uid", uid);
		m.addAttribute("reply", svc.reply(num));
		
		List<FileVO> fileList = svc.getFileList(num);
		m.addAttribute("fileList", fileList);
		return "bbs_detail";
	}
	
	@PostMapping("/bbs_add")
	@ResponseBody
	public RedirectView add(BBSVO vo, @RequestParam MultipartFile[] files) {
		if (files != null) {
			svc.add(vo, files);
		}
		svc.add(vo);
		
		return new RedirectView("/bbs_list/1/all");
	}
	
	@GetMapping("/bbs_add")
	public String addWrite(Model m) {
		if(login.isLogin()) {
			return "bbs_add";
		} else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/bbs_list/{pgNum}/{ctgr}")
	public String GetList(@PathVariable("pgNum")int pg, Model m, @PathVariable("ctgr")String ctgr) {
		PageInfo<BBSVO> pgInfo = svc.getList(pg, 8, ctgr);
		int startPage = pg - 4;
		int lastPage  = pgInfo.getNavigateLastPage();
		System.out.println(lastPage);
		m.addAttribute("category", ctgr);
		m.addAttribute("pageInfo", pgInfo);
		m.addAttribute("startPage", startPage);
		m.addAttribute("lastPage", lastPage);
		
	    return "bbs_list";
	}
}