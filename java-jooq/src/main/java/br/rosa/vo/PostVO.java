package br.rosa.vo;

public class PostVO {

    private Integer id;

    private String title;

    private String description;

    private Integer idAuthor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Integer idAuthor) {
        this.idAuthor = idAuthor;
    }

    @Override
    public String toString() {
        return "id: " + id + " - " + "title: " + title + " - description: " + description +
                " - idAuthor:" + idAuthor;
    }
}
