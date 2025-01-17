package br.com.ifsales.model;

public class Category {
    private Long id;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long categoryId) {
        this.id = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}