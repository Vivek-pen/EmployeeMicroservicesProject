package com.employee.service.impl;

import com.employee.exception.BadRequestException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if(employeeDto.getId()!=null){
            throw new RuntimeException("Employee Already Exists");
        }
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if(id == null||employeeDto.getId()==null){
            throw new BadRequestException("Employee Id is required");
        }
        if(!Objects.equals(id,employeeDto.getId())){
            throw new BadRequestException("Employee Id is not matched");
        }
        Employee entity = modelMapper.map(employeeDto,Employee.class);
        Employee updatedEmployee = employeeRepository.save(entity);
        return modelMapper.map(updatedEmployee,EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id: "+id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not found with id: "+id));
        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if(employees.isEmpty()){
            throw new ResourceNotFoundException("Employees not found");
        }
        return employees.stream().map(emp->modelMapper.map(emp,EmployeeDto.class)).toList();
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndComapnyName(String empCode, String companyName) {
        Employee employee = employeeRepository.findByEmpCodeAndCompanyName(empCode,companyName).orElseThrow(()-> new ResourceNotFoundException("Employee not found with empCode "+empCode+" and companyCode "+companyName));
        return modelMapper.map(employee,EmployeeDto.class);
    }
}
