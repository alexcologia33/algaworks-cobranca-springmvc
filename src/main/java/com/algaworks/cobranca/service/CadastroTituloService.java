package com.algaworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;	//Repositorio de Titulos
	
	public void salvar(Titulo titulo){
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalida"); 
		}
	}

	public void excluir(long codigo) {
			titulos.delete(codigo);
	}
	
	public void receber(long codigo){
		 Titulo titulo = titulos.findOne(codigo);
		 titulo.setStatus(StatusTitulo.RECEBIDO);
		 titulos.save(titulo);
	}
	
	
	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao() ==null ? "%" : filtro.getDescricao();
		
		return titulos.findByDescricaoContaining(descricao);
	}
}
