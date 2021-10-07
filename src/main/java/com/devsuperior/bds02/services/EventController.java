package com.devsuperior.bds02.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.EventService;

@RestController                           //estruturas predesenvolvidas
@RequestMapping(value = "/events")
public class EventController {
	
	@Autowired
	private EventService service;
	
	@GetMapping
	public ResponseEntity<List<EventDTO>> findAll() {
		List<EventDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);		
	}
	
	@PostMapping
	public ResponseEntity<EventDTO> insert(@ RequestBody EventDTO dto) {
		dto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();            
		return ResponseEntity.created(uri).body(dto);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);		
		return ResponseEntity.noContent().build();	    //retorna um cod 204,  referente ao body vazio.   
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id, @ RequestBody EventDTO dto) {
		dto = service.update(id, dto);		
		return ResponseEntity.ok().body(dto);       
	}
	

}