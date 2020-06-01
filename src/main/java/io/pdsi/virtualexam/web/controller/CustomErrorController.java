package io.pdsi.virtualexam.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {
	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errorMsg", response.getStatus());
		if(response.getStatus() == HttpStatus.NOT_FOUND.value()) {
			modelAndView.setViewName("pageNotFound");
		}
		else if(response.getStatus() == HttpStatus.FORBIDDEN.value()) {
			modelAndView.setViewName("accessDenied");
		}
		else {
			modelAndView.setViewName("errorPage");
		}
		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		return "/errorPage";
	}
}
