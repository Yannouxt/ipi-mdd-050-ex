package com.ipiecoles.java.mdd050.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.service.EmployeService;
import com.ipiecoles.java.mdd050.service.ManagerService;



@RestController
@RequestMapping("/managers")
public class ManagerController {

	@Autowired
	EmployeService empService;
	@Autowired
	ManagerService manService;
	
    @RequestMapping("/{idManager}/equipe/{idTech}/remove")
    @ResponseStatus(value= HttpStatus.NO_CONTENT)
    public void removeTechFromManager(@PathVariable("idManager") Long idManager, @PathVariable("idTech") Long idTech) {
    		this.manService.deleteTechniciens(idManager, idTech);
    }
    
    @RequestMapping(value="/{idTech}/equipe/{matricule}/add", method= RequestMethod.GET)
    public Technicien addManagerToTech(@PathVariable("idTech") Long idTech, @PathVariable("matricule") String matricule) {
    		return this.manService.addTechniciens(idTech, matricule);
    }
}