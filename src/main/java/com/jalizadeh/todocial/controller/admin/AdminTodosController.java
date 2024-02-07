package com.jalizadeh.todocial.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.todocial.repository.TodoRepository;
import com.jalizadeh.todocial.model.FlashMessage;

@Controller
public class AdminTodosController {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@GetMapping("/admin/settings/todos")
	public String ShowAP_Todos(ModelMap model) {
		model.put("PageTitle", "Admin > Todos");
		model.put("all_todos", todoRepository.findAll());
		return "admin/settings-todos";
	}
	

	@RequestMapping(value="/admin/delete_todo", method=RequestMethod.GET)
	public String deleteUser(@RequestParam Long id, 
			RedirectAttributes redirectAttributes) {
		
		//because of the relationships and cascade rules
		// all dependent entities will be removed automatically
		todoRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Todo deleted successfully", FlashMessage.Status.success));
		return "redirect:/admin/todos";
	}
	
}
