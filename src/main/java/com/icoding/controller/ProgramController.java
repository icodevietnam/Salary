package com.icoding.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icoding.domain.Faculty;
import com.icoding.domain.Program;
import com.icoding.domain.User;
import com.icoding.service.FacultyService;
import com.icoding.service.ProgramService;
import com.icoding.service.UserService;

@Controller
public class ProgramController {

	@Autowired
	private ProgramService programService;

	@Autowired
	private FacultyService facultyService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/admin/program", "/admin/program/list" }, method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@Secured("ROLE_ADMIN")
	public String displayPage(Model model) {
		List<Faculty> listFaculties = facultyService.getAll();
		List<User> listUserEE = userService.getListUserEE();
		List<User> listUserPL = userService.getListUserPL();
		model.addAttribute("listFaculties", listFaculties);
		model.addAttribute("listUsersEE", listUserEE);
		model.addAttribute("listUsersPL", listUserPL);
		model.addAttribute("listYear", listYearNow());
		model.addAttribute("pageName", "Manage Program");
		model.addAttribute("title", "Manage Program");
		return "program/index";
	}

	@RequestMapping(value = "/program/new", method = RequestMethod.POST)
	@ResponseBody
	public String addrole(@RequestParam(value = "name") String programName,
			@RequestParam(value = "description") String programDescription,
			@RequestParam(value = "typeOfGrade") String typeOfGrade,
			@RequestParam(value = "typeOfConduct") String typeOfConduct,
			@RequestParam(value = "academicYear") String academicYear) {
		Program program = new Program();
		try {
			programService.saveOrUpdate(program);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	private List<String> listYearNow() {
		List<String> listYear = new ArrayList<String>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);
		listYear.add(year);
		listYear.add((Integer.parseInt(year) + 1)+"");
		return listYear;
	}
}
