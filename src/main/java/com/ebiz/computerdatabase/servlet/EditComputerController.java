package com.ebiz.computerdatabase.servlet;

import com.ebiz.computerdatabase.dto.ComputerDTO;

import com.ebiz.computerdatabase.log.LoggerSing;
import com.ebiz.computerdatabase.mapper.CompanyDTOMapper;
import com.ebiz.computerdatabase.mapper.ComputerDTOMapper;
import com.ebiz.computerdatabase.service.CompanyService;
import com.ebiz.computerdatabase.service.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.servlet.view.RedirectView;


import java.util.Map;

@RequestMapping("/")
@Controller
public class EditComputerController {


	@Autowired
	ComputerService computerService;
	@Autowired
	CompanyService companyService;

	@Autowired
	private Validator computerValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(computerValidator);
	}


	@RequestMapping(value = "editComputer", method = RequestMethod.GET)
	protected String getEditComputer(Model model,@RequestParam Map<String,String> requestParams) {


		String computerId = requestParams.get("id");
		ComputerDTO computer = ComputerDTOMapper.createComputerDTO(computerService.getComputerById(computerId));

		model.addAttribute("id", computerId);

		if (computer == null) {
			return "404";
		} else {

			model.addAttribute("companyList", CompanyDTOMapper.createCompanyDtoList(companyService.getCompanies()));
			model.addAttribute("computer", computer);
			return "editComputer";

		}

	}

	@RequestMapping(value = "editComputer", method = RequestMethod.POST)
	public String editComputer(@ModelAttribute("computer") @Validated ComputerDTO computerDTO, BindingResult bindingResult, Model model){

		model.addAttribute("computer", computerDTO);
		model.addAttribute("companyList", CompanyDTOMapper.createCompanyDtoList(companyService.getCompanies()));

		LoggerSing.logger.error("ComputerDTO CHANGE "+ computerDTO.getId());
		if (bindingResult.hasErrors()) {

			return "editComputer";
		}

		boolean success = computerService.update(computerDTO);

		if (!success) {
			return "404";
		} else {
			return "editComputer";
		}

	}



}
