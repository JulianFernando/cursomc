package com.juliancambraia.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.juliancambraia.cursomc.domain.Categoria;
import com.juliancambraia.cursomc.repositories.CategoriaRepository;
import com.juliancambraia.cursomc.services.exceptions.DataIntegrityException;
import com.juliancambraia.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new  ObjectNotFoundException("Objeto não encontrado! Id " + id
					 + " Tipo: " + Categoria.class.getName())); 
	}
	
	public Categoria insert(Categoria obj) {
		// garante que o obj passado não possui Id, para que o método save não o trate como Update.
		obj.setId(null);
		obj = repo.save(obj);
		
		return obj;
	}
	
	public Categoria update(Categoria obj) {
		this.find(obj.getId());
		
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			
			repo.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			// TODO: Exceção tratada da camada de serviçe
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
	}
	
	public List<Categoria> findAll() {
		
		return repo.findAll();
		
	}
}
