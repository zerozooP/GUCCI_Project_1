package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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
	public RedirectView edited(BBSVO vo, @RequestParam MultipartFile[] files) {
		svc.edit(vo);
		if (files != null) {
			svc.add(vo, files);
		}
		return new RedirectView("/bbs_detail/"+vo.getNum());
	}
	
	@GetMapping("/bbs_edit/{num}")
	public String edit(@PathVariable("num")int num, Model m) {
		if(login.isLogin()) {
			m.addAttribute("edit", svc.detail(num));
			List<FileVO> fileList = svc.getFileList(num);
			m.addAttribute("fileList", fileList);
			return "bbs_edit";
		} else {
			return "redirect:/login";
		}
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
	
	@GetMapping("/bbs_detail/{num}")
	public String detail(@PathVariable("num")int num, Model m) {
		m.addAttribute("detail", svc.detail(num));
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
		System.out.println(files);
		if (files != null) {
			svc.add(vo, files);
		}
		svc.add(vo);
		
		return new RedirectView("/bbs_list/1");
	}
	
	@GetMapping("/bbs_add")
	public String addWrite(Model m) {
		if(login.isLogin()) {
			return "bbs_add";
		} else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/bbs_list/{pgNum}")
	public String GetList(@PathVariable("pgNum")int pg, Model m) {
		PageInfo<BBSVO> pgInfo = svc.getList(pg, 8);
	    m.addAttribute("pageInfo", pgInfo);
	    return "bbs_list";
	}
}