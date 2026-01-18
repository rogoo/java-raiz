package br.rosa.dto;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MesaDTO {

	public static final String CAMPO_CODIGO = "codigo";
	public static final String CAMPO_NOME_MESA = "nomeMesa";
	public static final String CAMPO_DATA_CRIACAO = "dataCriacao";
	public static final String CAMPO_LISTA = "lista";

	private Long codigo;
	private String nomeMesa;
	private Date dataCriacao;
	private List<Integer> lista;

	public MesaDTO() {
		super();
	}

	public MesaDTO(Long codigo, String nomeMesa, Date dataCriacao, List<Integer> lista) {
		this.codigo = codigo;
		this.nomeMesa = nomeMesa;
		this.dataCriacao = dataCriacao;
		this.lista = lista;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNomeMesa() {
		return nomeMesa;
	}

	public void setNomeMesa(String nomeMesa) {
		this.nomeMesa = nomeMesa;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<Integer> getLista() {
		return lista;
	}

	public void setLista(List<Integer> lista) {
		this.lista = lista;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
