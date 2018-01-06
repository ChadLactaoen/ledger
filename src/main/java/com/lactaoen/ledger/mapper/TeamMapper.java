package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Team;
import com.lactaoen.ledger.model.form.TeamForm;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamMapper {

    private final SqlSession sqlSession;

    public TeamMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Team> getTeamsByGameId(int id) {
        return sqlSession.selectList("TeamMapper.getTeamsByGameId", id);
    }

    public Team getTeamById(int id) {
        return sqlSession.selectOne("TeamMapper.getTeamById", id);
    }

    public Integer createTeam(TeamForm team) {
        return sqlSession.insert("TeamMapper.createTeam", team);
    }

    public Integer updateTeam(TeamForm team) {
        return sqlSession.update("TeamMapper.updateTeam", team);
    }

    public void deleteTeam(int id) {
        sqlSession.delete("TeamMapper.deleteTeam", id);
    }
}
