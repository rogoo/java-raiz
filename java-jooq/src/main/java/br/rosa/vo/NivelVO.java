package br.rosa.vo;

public class NivelVO {

    private Integer id;

    private String nome;

    public NivelVO() {
    }

    public NivelVO(String nome) {
        this.nome = nome;
    }

    public NivelVO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
