# TODO

## HIGH
1. set up DB access for 2 new users DONE
2. rework forAfterRound - IN PROGRESS

## MEDIUM
1. when opening standings, default to last round


# BUGS
- cannot remove a player once he has a match result

- [ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 3.879 s <<< FAILURE! - in com.romco.bracketeer.domain.tournament.TournamentImplTest
  [ERROR] generateRound  Time elapsed: 3.878 s  <<< FAILURE!
  org.opentest4j.AssertionFailedError: expected: <3> but was: <4>
  	at com.romco.bracketeer.domain.tournament.TournamentImplTest.generateRound(TournamentImplTest.java:32)