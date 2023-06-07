/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.demo.model.Empleado;
import org.uv.demo.model.RepositoryEmpleado;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.uv.demo.model.EmpleadoDTO;
/**
 *
 * @author loken
 */
@RestController
@RequestMapping("/api")
public class Controller {
    
    @Autowired
    private RepositoryEmpleado repositoryEmpleado;
    
    @GetMapping("/empleado")
    public List<Empleado> listEmpleados(){
        return repositoryEmpleado.findAll();
    }
    
    @GetMapping("empleado/{id}")
    public ResponseEntity<Empleado> byIdEmpleados(@PathVariable Long id) {
        Optional<Empleado> opt = repositoryEmpleado.findById(id);

        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/empleado")
    public ResponseEntity<Empleado> addEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = new Empleado(empleadoDTO);
        try {
            Empleado eMpleado = repositoryEmpleado.save(empleado);
            return new ResponseEntity<>(eMpleado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<HttpStatus> deleteVisit(@PathVariable Long id) {
        try {
            repositoryEmpleado.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/empleado/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable("id") long id, @RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = new Empleado(empleadoDTO);  
        Optional<Empleado> empleadoData = repositoryEmpleado.findById(id);

        if (empleadoData.isPresent()) {
            return new ResponseEntity<>(repositoryEmpleado.saveAndFlush(empleado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
