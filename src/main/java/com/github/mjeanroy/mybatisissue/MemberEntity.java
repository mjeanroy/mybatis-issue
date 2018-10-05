package com.github.mjeanroy.mybatisissue;

public class MemberEntity {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemberDto toMember() {
        return new MemberDto(id, name);
    }
}
