package com.github.mjeanroy.mybatisissue;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.InputStream;
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
                    .map(team -> team.toTeam())
                    .collect(Collectors.toList());
        }
    }

    List<TeamEntity> findAllEntities() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return findAsStream(session)
                    .collect(Collectors.toList());
        }
    }

    private Stream<TeamEntity> findAsStream(SqlSession sqlSession) {
        Cursor<TeamEntity> cursor = sqlSession.selectCursor("com.github.mjeanroy.mybatisissue.TeamEntity.findAll");
        return StreamSupport.stream(cursor.spliterator(), false);
    }
}
