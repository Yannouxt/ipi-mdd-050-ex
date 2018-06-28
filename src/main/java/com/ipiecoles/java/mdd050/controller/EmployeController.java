package com.ipiecoles.java.mdd050.controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.service.CommercialService;
import com.ipiecoles.java.mdd050.service.EmployeService;



@RestController
@RequestMapping("/employes")
public class EmployeController {

	@Autowired
	EmployeService empService;
	
	@Autowired
	CommercialService comService;
	
	@RequestMapping("/count")
	public Long count() {	
		Long employeNumber = empService.countAllEmploye();
		return employeNumber;
	}
	
    @RequestMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
    	Employe emp = empService.findById((Long) id);
    	if(emp == null) {
    		throw new EntityNotFoundException("L'employé "+id+ " n'a pas pu être trouvé");
    	}
        return new ResponseEntity<Employe>(emp, HttpStatus.OK);
    }
    
	@RequestMapping(params = {"matricule"})
	public ResponseEntity<Employe> getEmployeByMatricule(@RequestParam("matricule") String mat) {
		Employe emp = empService.findMyMatricule(mat);
		if (emp == null) {
    		throw new EntityNotFoundException("L'employé "+mat+ " n'a pas pu être trouvé");
		}
		return new ResponseEntity<Employe>(emp, HttpStatus.OK);
	}
	
	@RequestMapping(params = {"page","size"})
	public Page<Employe> listEmployes(@RequestParam("page") Integer page,
									  @RequestParam("size") Integer size,
									  @RequestParam("sortProperty") String sortProperty,
									  @RequestParam("sortDirection") String sortDirection) {
		if(page<0 || page > empService.countAllEmploye()/size) {
			throw new EntityNotFoundException("La page n'existe pas");

		}
		return empService.findAllEmployes(page, size, sortProperty, sortDirection);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Employe createEmploye(@RequestBody Employe employe){
		if (empService.findMyMatricule(employe.getMatricule())!= null){
	        throw new EntityExistsException("Le matricule "+ employe.getMatricule()+ " existe déjà");
		}
		return empService.creerEmploye(employe);

	}
	
	@RequestMapping(
			value="/{id}",
			method = RequestMethod.PUT
	)
	public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employe) throws Exception, EmployeException{
		Employe emp = empService.findById(id);
		if(emp == null) {
    		throw new EntityNotFoundException("L'employé "+id+ " n'a pas pu être trouvé");
		}

		
		empService.updateEmploye(id, employe);
		return new ResponseEntity<Employe>(emp, HttpStatus.OK);
	}
	
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable long id) throws EmployeException {
		Employe emp = empService.findById(id);
		if(emp == null) {
    		throw new EntityNotFoundException("L'employé "+id+ " n'a pas pu être trouvé");
		}
    	empService.deleteEmploye(id);
    }

}
