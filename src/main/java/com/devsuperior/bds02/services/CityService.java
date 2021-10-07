package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.BadRequestException;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id); // criada no services.exceptions
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation"); // criada no services.exceptions
		} catch (BadRequestException e) {
			throw new BadRequestException("Bad Request");
		}
	}

	// Método para atualizar um dado nas entidades e trata exceção
	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		try {
			City entity = repository.getOne(id); // instancia com getOne antes de salvar
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CityDTO(entity);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id); // criada no services.exceptions
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation"); // criada no services.exceptions
		} catch (BadRequestException e) {
			throw new BadRequestException("Bad Request");
		}
	}

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName()); // daqui abaixo coloca os demais campos, caso tenha na entidade
		entity = repository.save(entity);
		return new CityDTO(entity);
	}
}
