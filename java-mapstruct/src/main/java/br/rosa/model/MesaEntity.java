package br.rosa.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MesaEntity {

	public static final String CAMPO_ID = "id";
	public static final String CAMPO_NOME = "nome";
	public static final String CAMPO_DATA_CRIACAO = "dataCriacao";
	public static final String CAMPO_LISTA_NUMEROS = "listaNumeros";

	private Long id;
	private String nome;
	private Date dataCriacao;
	private List<Integer> listaNumeros;

	public MesaEntity() {
		super();
	}

	public MesaEntity(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public MesaEntity(Long id, String nome, Date dataCriacao, List<Integer> listaNumeros) {
		this.id = id;
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.listaNumeros = listaNumeros;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<Integer> getListaNumeros() {
		return listaNumeros;
	}

	public void setListaNumeros(List<Integer> listaNumeros) {
		this.listaNumeros = listaNumeros;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
