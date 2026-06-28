package com.employee.controller;

import com.employee.exception.MissingParameterException;
import com.employee.model.dto.EmployeeDto;
import com.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto saved = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,@RequestBody EmployeeDto employeeDto){
        EmployeeDto employeeDto1 = employeeService.updateEmployee(id,employeeDto);
        return new ResponseEntity<>(employeeDto1,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id){
        EmployeeDto employeeDto = employeeService.getEmployee(id);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<EmployeeDto>> getEmployees(){
        Iterable<EmployeeDto> employeeDtos = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeDtos,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("get-by-emp-code-and-company-name")
    public ResponseEntity<EmployeeDto> getEmployeeByEmpCodeAndCompanyName(@RequestParam(required = false) String empCode, @RequestParam(required = false) String companyName){
        List<String> missingParameters = new ArrayList<>();
        if(empCode == null || empCode.trim().isEmpty()){
            missingParameters.add("empCode");
        }
        if(companyName==null||companyName.trim().isEmpty()){
            missingParameters.add("companyName");
        }
        if(!missingParameters.isEmpty()){
            String finalMessage = missingParameters.stream().collect(Collectors.joining(","));
            throw new MissingParameterException("Please provide: "+finalMessage);
        }
        EmployeeDto response = employeeService.getEmployeeByEmpCodeAndComapnyName(empCode,companyName);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
