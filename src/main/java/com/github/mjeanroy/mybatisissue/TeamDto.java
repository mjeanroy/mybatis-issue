package com.github.mjeanroy.mybatisissue;

import java.util.*;
import java.util.stream.Collectors;

class TeamDto {

    private final Long id;
    private final String name;
    private final Set<MemberDto> members;

    TeamDto(Long id, String name, Collection<MemberEntity> members) {
        this.id = id;
        this.name = name;
        this.members = new LinkedHashSet<>();
        this.members.addAll(members.stream().map(MemberEntity::toMember).collect(Collectors.toSet()));
    }

    Long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    Set<MemberDto> getMembers() {
        return members;
    }
}
