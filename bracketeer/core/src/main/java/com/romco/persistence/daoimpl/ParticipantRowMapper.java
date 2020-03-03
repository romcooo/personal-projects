package com.romco.persistence.daoimpl;

import com.romco.domain.participant.Participant;
import com.romco.domain.participant.Player;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantRowMapper implements RowMapper<Participant> {
    @Override
    public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participant participant = new Player(rs.getString("name"));
    
        participant.setId(rs.getLong("id"));
        participant.setCode(rs.getString("code"));
//        participant.setName(rs.getString("name"));
//        participant.setOfTournament(rs.getLong("tournament_id"));
        
        return participant;
    }
}
