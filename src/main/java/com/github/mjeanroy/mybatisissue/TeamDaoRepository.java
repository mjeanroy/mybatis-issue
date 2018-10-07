package com.github.mjeanroy.mybatisissue;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

class TeamDaoRepository {

    private final SqlSessionFactory sqlSessionFactory;

    TeamDaoRepository() throws Exception {
        String resource = "com/github/mjeanroy/mybatisissue/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    List<TeamDto> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return findAsStream(session)
                    .map(TeamEntity::toTeam)
                    .collect(Collectors.toList());
        }
    }

    List<TeamDto> findAllUsingForLoop() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<TeamDto> results = new ArrayList<>();
            for (TeamEntity team : findAsIterable(session)) {
                results.add(team.toTeam());
            }

            return results;
        }
    }

    List<TeamEntity> findAllEntities() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return findAsStream(session)
                    .collect(Collectors.toList());
        }
    }

    private static Stream<TeamEntity> findAsStream(SqlSession sqlSession) {
        return StreamSupport.stream(findAsIterable(sqlSession).spliterator(), false);
    }

    private static Iterable<TeamEntity> findAsIterable(SqlSession sqlSession) {
        return sqlSession.selectCursor("com.github.mjeanroy.mybatisissue.TeamEntity.findAll");
    }
}
