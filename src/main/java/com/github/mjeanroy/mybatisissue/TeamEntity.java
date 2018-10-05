package com.github.mjeanroy.mybatisissue;

import java.util.List;

public class TeamEntity {
    private Long id;
    private String name;
    private List<MemberEntity> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(List<MemberEntity> members) {
        this.members = members;
    }

    public TeamDto toTeam() {
        return new TeamDto(id, name, members);
    }
}
