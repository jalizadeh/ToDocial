package com.jalizadeh.springboot.web.highchart;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jalizadeh.springboot.web.model.Todo;
import com.jalizadeh.springboot.web.repository.TodoRepository;

@Controller
public class GraphController {
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	@Autowired
	private TodoRepository todoRepository;

	
	@GetMapping("/chart")
	public String chart(Model model) {
		List<TodoChartModel> list = new ArrayList<TodoChartModel>();
		
		for (Todo todo : todoRepository.getAllNotCompleted()) {
			int d1 = getDateDiff(new Date(),todo.getCreation_date());
			int d2 = getDateDiff(todo.getTarget_date(),todo.getCreation_date());
			float dif = (float)d1 / (float)d2;
			String progress = "";
			if(dif > 1)
				progress = df2.format(1);
			else
				progress = df2.format(dif);
		    
			list.add(new TodoChartModel(todo.getName(), 
					todo.getCreation_date(), todo.getTarget_date(), progress));
		}
				
		model.addAttribute("list",list);
		
		return "chart/over-time";
	}
	
	
	@GetMapping("/gantt")
	public String ghantt(Model model) {
		List<TodoChartModel> list = new ArrayList<TodoChartModel>();
		
		for (Todo todo : todoRepository.getAllNotCompleted()) {
			int d1 = getDateDiff(new Date(),todo.getCreation_date());
			int d2 = getDateDiff(todo.getTarget_date(),todo.getCreation_date());
			float dif = (float)d1 / (float)d2;
			String progress = "";
			if(dif > 1)
				progress = df2.format(1);
			else
				progress = df2.format(dif);
		    
			list.add(new TodoChartModel(todo.getName(), 
					todo.getCreation_date(), todo.getTarget_date(), progress));
		}
				
		model.addAttribute("list",list);
		
		return "chart/gantt";
	}

	
	
	public int getDateDiff(Date d1, Date d2) {
		return  (int)( (d1.getTime() - d2.getTime())  / (1000 * 60 * 60 * 24) );
	}
}
