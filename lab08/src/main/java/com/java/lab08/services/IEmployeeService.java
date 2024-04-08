package com.java.lab08.services;

import com.java.lab08.EmployeeDTO;
import com.java.lab08.models.Employee;
import com.java.lab08.response.EmployeeResponse;
import com.java.lab08.response.ListEmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    Employee createOrUpdate(EmployeeDTO dto);
    ListEmployeeResponse findByPage(int page, int limit);

    void deleteByIds(List<Long> ids);
    EmployeeResponse findById(Long id);
    void deleteById(Long id);

}
