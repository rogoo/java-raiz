package br.rosa.vo;

public class TopicoVO {

    private Integer id;

    private String nome;

    private String descricao;

    private Integer idDisciplina;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
    public String toString() {
        return "id: " + id + " - " + "nome: " + nome + " - descricao: " + descricao +
                " - idDisciplina:" + idDisciplina;
    }
}
