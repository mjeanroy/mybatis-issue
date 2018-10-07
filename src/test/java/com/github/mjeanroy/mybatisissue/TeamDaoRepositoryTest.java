package com.github.mjeanroy.mybatisissue;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

class TeamDaoRepositoryTest {

    private static TeamDaoRepository teamDaoRepository;

    @BeforeAll
    static void setUp() throws Exception {
        teamDaoRepository = new TeamDaoRepository();
        initData();
    }

    @Test
    void it_should_get_all_teams() {
        List<TeamDto> teams = teamDaoRepository.findAll();

        Assertions.assertNotNull(teams);
        Assertions.assertEquals(teams.size(), 1);
        Assertions.assertEquals(teams.get(0).getMembers().size(), 10);
    }

    @Test
    void it_should_get_all_teams_as_entities() {
        List<TeamEntity> teams = teamDaoRepository.findAllEntities();

        Assertions.assertNotNull(teams);
        Assertions.assertEquals(teams.size(), 2);
        Assertions.assertEquals(teams.get(0).getMembers().size(), 10);
        Assertions.assertEquals(teams.get(1).getMembers().size(), 5);
    }

    private static void initData() throws Exception {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
        createTableTeam(connection);
        createTableMember(connection);
        createFirstTeam(connection);
        createSecondTeam(connection);
    }

    private static void createTableTeam(Connection connection) throws Exception {
        // @formatter:off
        String sql =
                "CREATE TABLE team (" +
                        "id INTEGER not NULL, " +
                        "name VARCHAR(255), " +
                        "PRIMARY KEY (id)" +
                ")";
        // @formatter:on

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    private static void createTableMember(Connection connection) throws Exception {
        // @formatter:off
        String sql =
                "CREATE TABLE member (" +
                        "id INTEGER not NULL, " +
                        "name VARCHAR(255), " +
                        "team_id INTEGER not null, " +
                        "PRIMARY KEY (id)" +
                ")";
        // @formatter:on

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    private static void createFirstTeam(Connection connection) throws Exception {
        String insertTeamSql = "INSERT INTO team(id, name) VALUES(1, 'The Avengers')";
        Statement insertTeamStmt = connection.createStatement();
        insertTeamStmt.executeUpdate(insertTeamSql);

        for (int i = 1; i <= 10; ++i) {
            String insertMemberSql = "INSERT INTO member(id, name, team_id) VALUES(" + i + ", 'John Doe #" + i + "', 1)";
            Statement insertMemberStatement = connection.createStatement();
            insertMemberStatement.executeUpdate(insertMemberSql);
        }
    }

    private static void createSecondTeam(Connection connection) throws Exception {
        String insertTeamSql = "INSERT INTO team(id, name) VALUES(2, 'The Suicide Squad')";
        Statement insertTeamStmt = connection.createStatement();
        insertTeamStmt.executeUpdate(insertTeamSql);

        for (int i = 21; i <= 25; ++i) {
            String insertMemberSql = "INSERT INTO member(id, name, team_id) VALUES(" + i + ", 'John Doe #" + i + "', 2)";
            Statement insertMemberStatement = connection.createStatement();
            insertMemberStatement.executeUpdate(insertMemberSql);
        }
    }
}
