package com.java.lab08.controllers;

import com.java.lab08.EmployeeDTO;
import com.java.lab08.models.Employee;
import com.java.lab08.response.EmployeeResponse;
import com.java.lab08.response.ListEmployeeResponse;
import com.java.lab08.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;

    @GetMapping()
    public String show(){
        return "employee/list";
    }
    @GetMapping("/list")
    public String showEmployeeList(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "100") int limit) {
        ListEmployeeResponse response = employeeService.findByPage(page, limit);
        model.addAttribute("employees", response.getEmployeeResponses());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", response.getTotalPages());
        return "employee";
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTO) {
        employeeService.createOrUpdate(employeeDTO);
        return "redirect:/employee/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        EmployeeResponse employeeResponse = employeeService.findById(id);
        model.addAttribute("employee", employeeResponse);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, @ModelAttribute("employee") EmployeeDTO employeeDTO) {
        employeeService.createOrUpdate(employeeDTO);
        return "redirect:/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id")Long id) {
        employeeService.deleteById(id);
        return "redirect:/employee/list";
    }
}
