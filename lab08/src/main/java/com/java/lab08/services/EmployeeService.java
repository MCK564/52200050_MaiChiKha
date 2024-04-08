package com.java.lab08.services;

import com.java.lab08.EmployeeDTO;

import com.java.lab08.models.Employee;
import com.java.lab08.repositories.EmployeeRepository;
import com.java.lab08.response.EmployeeResponse;
import com.java.lab08.response.ListEmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public Employee createOrUpdate(EmployeeDTO dto) {
        Employee newOrUpdateEmployee = modelMapper.map(dto,Employee.class);
        return employeeRepository.saveAndFlush(newOrUpdateEmployee);
    }

    @Override
    public ListEmployeeResponse findByPage(int page, int limit) {
        int totalPages = 0;
        PageRequest pageRequest = PageRequest.of(page,limit);
        Page<Employee> pages = employeeRepository.findAll(pageRequest);
        totalPages = pages.getTotalPages();
        List<Employee> employees = pages.getContent();
        List<EmployeeResponse> employeeResponses = employees.stream().map(EmployeeResponse::fromEmployee).toList();
        ListEmployeeResponse listEmployeeResponse =ListEmployeeResponse.builder()
                .employeeResponses(employeeResponses)
                .totalPages(totalPages)
                .message("")
                .build();
        if(listEmployeeResponse==null){
            listEmployeeResponse.setMessage("Get List unsuccessfully");
        }
        return listEmployeeResponse;
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        employeeRepository.deleteAllById(ids);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee existingEmployee = employeeRepository.findById(id).get();
        return EmployeeResponse.fromEmployee(existingEmployee);
    }

    @Override
    public void deleteById(Long id) {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        }
    }


}
