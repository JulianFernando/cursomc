package com.juliancambraia.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juliancambraia.cursomc.domain.Categoria;
import com.juliancambraia.cursomc.repositories.CategoriaRepository;
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
}
