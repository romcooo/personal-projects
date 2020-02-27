package com.romco.persistence.daoimpl;

import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;
import com.romco.domain.participant.Player;
import com.romco.domain.tournament.Tournament;
import com.romco.domain.tournament.TournamentImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantRowMapper implements RowMapper<Participant> {
    @Override
    public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participant participant = new Player(rs.getString("name"));
    
        participant.setId(rs.getLong("id"));
        participant.setCode(rs.getInt("code"));
//        participant.setName(rs.getString("name"));
//        participant.setOfTournament(rs.getLong("tournament_id"));
        
        return participant;
    }
}
