package com.graphicless.cricketapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.graphicless.cricketapp.model.Continent
import com.graphicless.cricketapp.model.Country
import com.graphicless.cricketapp.model.CountryWithContinent
import com.graphicless.cricketapp.model.League
import com.graphicless.cricketapp.temp.*
import com.graphicless.cricketapp.temp.joined.FixtureAndTeam
import com.graphicless.cricketapp.temp.map.FixtureDetails
import com.graphicless.cricketapp.temp.map.StageByLeague

@Dao
interface CricketDao {

    // ---------- INSERT ----------
    @Insert(onConflict = IGNORE)
    suspend fun insertContinent(continent: Continent)

    @Insert(onConflict = IGNORE)
    suspend fun insertCountry(country: Country)

    @Insert(onConflict = IGNORE)
    suspend fun insertLeague(leagues: League)

    @Insert(onConflict = IGNORE)
    suspend fun insertFixture(fixture: List<FixturesIncludeRuns.Data>)

    @Insert(onConflict = IGNORE)
    suspend fun insertRun(runs: List<FixturesIncludeRuns.Data.Run>)

    @Insert(onConflict = IGNORE)
    suspend fun insertTeam(teams: List<Teams.Data>)

    @Insert(onConflict = IGNORE)
    suspend fun insertVenue(venues: List<Venues.Data>)

    @Insert(onConflict = IGNORE)
    suspend fun insertStage(stages: List<Stages.Data>)

    @Insert(onConflict = IGNORE)
    suspend fun insertSeason(seasons: List<Seasons.Data>)

    @Insert(onConflict = IGNORE)
    suspend fun insertOfficials(officials: List<Officials.Data>)
    // ---------- INSERT ----------


    // ---------- GET ALL ----------
    @Query("SELECT * FROM continent")
    fun getContinents(): LiveData<List<Continent>>

    @Query("SELECT * FROM country")
    fun getCountries(): LiveData<List<Country>>

    @Query("SELECT * FROM league")
    fun getLeagues(): LiveData<List<League>>

    @Query("SELECT * FROM fixture")
    fun getFixtures(): LiveData<List<FixturesIncludeRuns.Data>>
    // ---------- GET ALL ----------


    // ---------- GET SINGLE ----------
//    @Query("SELECT name FROM continent WHERE id = :id")
//    fun getContinentName(id: Int): String

    @Transaction
    @Query("SELECT continent.name FROM continent INNER JOIN country ON continent.id = country.continent_id WHERE country.id = :countryId")
    fun getContinentName(countryId: Int): LiveData<String>
    // ---------- GET SINGLE ----------


    @Query("SELECT continent.id AS continent_id, continent.name AS continent_name, country.id AS country_id, country.name AS country_name, country.image_path AS country_flag FROM continent INNER JOIN country ON continent.id = country.continent_id")
    fun getCountryWithContinent(): LiveData<List<CountryWithContinent>>

//    @Query("SELECT fixture.id AS fixtureId, team.id AS teamOneId, team.imagePath AS teamOneFlag, fixture.note AS note FROM Fixture INNER JOIN team ON fixture.localteamId = team.id team.id AS teamTwoId, team.imagePath AS teamTwoFlag FROM Fixture INNER JOIN team ON fixture.visitorteamId = team.id")
//    fun getFixtureAndTeam(): LiveData<List<FixtureAndTeam>>

    /*@Query("SELECT fixture.id AS fixtureId, fixture.season_id AS seasonId, stage.id AS stageId, stage.name AS stageName, fixture.round AS round, venue.city AS venue, fixture.startingAt AS startingAT, localTeam.code AS teamOneCode, localTeam.imagePath AS teamOneFlag, visitorTeam.code AS teamTwoCode, visitorTeam.imagePath AS teamTwoFlag, fixture.note AS note " +
            "FROM Fixture " +
            "INNER JOIN team AS localTeam ON fixture.localteamId = localTeam.id " +
            "INNER JOIN team AS visitorTeam ON fixture.visitorteamId = visitorTeam.id " +
            "INNER JOIN stage ON fixture.stageId = stage.id " +
            "INNER JOIN venue ON fixture.venueId = venue.id " +
            "GROUP BY stageId " +
            "ORDER BY fixture.seasonId DESC, fixture.stageId DESC, fixture.startingAt DESC")
    fun getFixtureAndTeam(): LiveData<List<FixtureAndTeam>>*/

    /*@Query("SELECT stage.id AS id, fixture.id AS fixtureId, stage.id AS stageId, stage.name AS stageName, fixture.round AS round, venue.city AS venue, fixture.startingAt AS startingAT, localTeam.code AS teamOneCode, localTeam.imagePath AS teamOneFlag, visitorTeam.code AS teamTwoCode, visitorTeam.imagePath AS teamTwoFlag, fixture.note AS note " +
            "FROM Fixture " +
            "INNER JOIN team AS localTeam ON fixture.localteamId = localTeam.id " +
            "INNER JOIN team AS visitorTeam ON fixture.visitorteamId = visitorTeam.id " +
            "INNER JOIN stage ON fixture.stageId = stage.id " +
            "INNER JOIN venue ON fixture.venueId = venue.id " +
            "GROUP BY stageId, fixtureId " +
            "ORDER BY fixture.startingAt DESC, stageId")
    fun getFixtureAndTeam2(): LiveData<List<test>>*/

    /*@Query("SELECT stage.id AS id, fixture.id AS fixtureId, stage.id AS stageId, stage.name AS stageName, fixture.round AS round, venue.city AS venue, fixture.startingAt AS startingAT, localTeam.code AS teamOneCode, localTeam.imagePath AS teamOneFlag, visitorTeam.code AS teamTwoCode, visitorTeam.imagePath AS teamTwoFlag, fixture.note AS note " +
            "FROM Fixture " +
            "INNER JOIN team AS localTeam ON fixture.localteamId = localTeam.id " +
            "INNER JOIN team AS visitorTeam ON fixture.visitorteamId = visitorTeam.id " +
            "INNER JOIN stage ON fixture.stageId = stage.id " +
            "INNER JOIN venue ON fixture.venueId = venue.id " +
            "GROUP BY stageId, fixtureId " +
            "ORDER BY fixture.startingAt DESC, stageId")
    fun getTest(): LiveData<List<test>>*/

    /*@Query("SELECT s.id, s.name, f.id as fixtureId, f.team1Id, f.team2Id, t1.name as team1Name, t2.name as team2Name " +
            "FROM stage s " +
            "LEFT JOIN fixture f ON s.id = f.stageId " +
            "LEFT JOIN team t1 ON f.team1Id = t1.id " +
            "LEFT JOIN team t2 ON f.team2Id = t2.id " +
            "ORDER BY s.id")
    fun getStagesWithFixtures(): List<FixtureAndStage>
*/
    @Query("SELECT fixture.id AS fixtureId, fixture.seasonId AS seasonId, stage.id AS stageId, stage.name AS stageName, " +
            "fixture.round AS round, venue.city AS venue, fixture.startingAt AS startingAT, localTeam.code AS teamOneCode, " +
            "localTeam.imagePath AS teamOneFlag, visitorTeam.code AS teamTwoCode, visitorTeam.imagePath AS teamTwoFlag, " +
            "fixture.note AS note, run.id AS runId, fixture.localTeamId AS teamOneId, fixture.visitorTeamId AS teamTwoId " +
            "FROM Fixture " +
            "LEFT JOIN stage ON fixture.stageId = stage.id " +
            "LEFT JOIN team AS localTeam ON fixture.localteamId = localTeam.id " +
            "LEFT JOIN team AS visitorTeam ON fixture.visitorteamId = visitorTeam.id " +
            "LEFT JOIN venue ON fixture.venueId = venue.id " +
            "LEFT JOIN run ON fixture.id = run.fixtureId " +
            "WHERE stageId = :stageId ")
    fun getFixturesByStageId(stageId: Int): LiveData<List<FixtureAndTeam>>

    @Query("SELECT fixture.id AS fixtureId, fixture.seasonId AS seasonId, stage.id AS stageId, stage.name AS stageName, " +
            "fixture.round AS round, venue.city AS venue, fixture.startingAt AS startingAT, localTeam.code AS teamOneCode, " +
            "localTeam.imagePath AS teamOneFlag, visitorTeam.code AS teamTwoCode, visitorTeam.imagePath AS teamTwoFlag, " +
            "fixture.note AS note, run.id AS runId, fixture.localTeamId AS teamOneId, fixture.visitorTeamId AS teamTwoId, " +
            "localTeam.name AS localTeamName, visitorTeam.name AS visitorTeamName, tossWinTeam.name As tossWinTeamName, " +
            "fixture.elected As elected, venue.name AS stadiumName, venue.capacity AS capacity, " +
            "venue.floodLight AS floodLight, firstUmpire.fullname As firstUmpire, secondUmpire.fullname AS secondUmpire," +
            "tvUmpire.fullname AS tvUmpire, referee.fullname AS referee, fixture.status AS status " +
            "FROM Fixture " +
            "INNER JOIN team AS localTeam ON fixture.localteamId = localTeam.id " +
            "INNER JOIN team AS visitorTeam ON fixture.visitorteamId = visitorTeam.id " +
            "INNER JOIN team As tossWinTeam on fixture.tossWonTeamId = tossWinTeam.id " +
            "INNER JOIN stage ON fixture.stageId = stage.id " +
            "INNER JOIN venue ON fixture.venueId = venue.id " +
            "INNER JOIN run ON fixture.id = run.fixtureId " +
            "INNER JOIN official AS firstUmpire ON fixture.firstUmpireId = firstUmpire.id " +
            "INNER JOIN official AS secondUmpire ON fixture.secondUmpireId = secondUmpire.id " +
            "INNER JOIN official AS tvUmpire ON fixture.tvUmpireId = tvUmpire.id " +
            "INNER JOIN official AS referee ON fixture.refereeId = referee.id " +
            "WHERE fixtureId = :fixtureId ")
    fun getFixtureDetails(fixtureId: Int): LiveData<FixtureDetails>

    @Query("SELECT * FROM run WHERE fixtureId = :runId")
    fun getRunByRunId(runId: Int): LiveData<List<FixturesIncludeRuns.Data.Run>>

    @Query("SELECT DISTINCT stage.name AS stageName, fixture.startingAt AS startingAt " +
            "FROM Fixture " +
            "INNER JOIN stage ON fixture.stageId = stage.id GROUP BY stageName ORDER BY fixture.startingAt ASC" )
    fun getDistinctStageName(): LiveData<List<StageName>>

//    @Query("SELECT name FROM ")

    /*@Query("SELECT stage.id As stageId, stage.name AS stageName, fixture.startingAt AS startingAt " +
            "FROM Fixture " +
            "INNER JOIN stage ON fixture.stageId = stage.id " +
            "WHERE stage.id = :stageId GROUP BY stageName ORDER BY fixture.startingAt ASC" )
    fun getStageNameById(stageId: Int): DistinctStages*/

    @Query("SELECT name FROM stage WHERE id = :stageId" )
    fun getStageNameById(stageId: Int): LiveData<String>

    @Query("SELECT * FROM team WHERE id = :teamId")
    fun getTeamNameByFixtureId(teamId: Int): LiveData<Teams.Data>

    @Query("SELECT stage.id As stageId, stage.name AS stageName, fixture.startingAt AS startingAt " +
            "FROM Fixture " +
            "INNER JOIN stage ON fixture.stageId = stage.id " )
    fun getDistinctStages():  LiveData<List<DistinctStages>>

    /*@Query("SELECT DISTINCT stage.id AS stageId, stage.name AS stageName, fixture.id AS fixtureId, fixture.leagueId AS leagueId, fixture.seasonId AS seasonId, fixture.startingAt AS startingAt " +
            "FROM fixture " +
            "INNER JOIN stage ON fixture.stageId = stage.id " +
            "WHERE fixture.leagueId = :leagueId ORDER BY fixture.seasonId, fixture.stageId, fixture.startingAt")
    fun getStageByLeagueId(leagueId: Int): LiveData<List<StageByLeague>>*/

    @Query("SELECT DISTINCT stage.id AS stageId, stage.name AS stageName, fixture.seasonId AS seasonId, season.name AS seasonName " +
            "FROM fixture " +
            "LEFT JOIN stage ON fixture.stageId = stage.id " +
            "LEFT JOIN season ON fixture.seasonId = season.id " +
            "WHERE fixture.leagueId = :leagueId AND fixture.status LIKE 'NS' ORDER BY fixture.seasonId DESC, fixture.stageId, fixture.startingAt DESC")
    fun getStageByLeagueId(leagueId: Int): LiveData<List<StageByLeague>>

    @Query("SELECT DISTINCT stage.id AS stageId, stage.name AS stageName, fixture.seasonId AS seasonId, season.name AS seasonName " +
            "FROM fixture " +
            "LEFT JOIN stage ON fixture.stageId = stage.id " +
            "LEFT JOIN season ON fixture.seasonId = season.id " +
            "WHERE fixture.leagueId = :leagueId AND fixture.status LIKE 'NS' ORDER BY fixture.seasonId DESC, fixture.stageId, fixture.startingAt DESC")
    fun getUpcomingMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>>

    @Query("SELECT DISTINCT stage.id AS stageId, stage.name AS stageName, fixture.seasonId AS seasonId, season.name AS seasonName " +
            "FROM fixture " +
            "LEFT JOIN stage ON fixture.stageId = stage.id " +
            "LEFT JOIN season ON fixture.seasonId = season.id " +
            "WHERE fixture.leagueId = :leagueId AND NOT fixture.status LIKE 'NS' ORDER BY fixture.seasonId DESC, fixture.stageId, fixture.startingAt DESC")
    fun getPreviousMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>>


    /*@Query("SELECT DISTINCT stage.id AS stageId" +
            "FROM Fixture " +
            "INNER JOIN stage ON fixture.stageId = stage.id" )
    fun getDistinctStages(): LiveData<List<Int>>*/

    /*@Query("SELECT fixture.id AS fixtureId, stage.id AS stageId, stage.name AS stageName, fixture.round AS round, venue.city AS venue, fixture.startingAt AS startingAT, localTeam.code AS teamOneCode, localTeam.imagePath AS teamOneFlag, visitorTeam.code AS teamTwoCode, visitorTeam.imagePath AS teamTwoFlag, fixture.note AS note " +
            "FROM Fixture " +
            "INNER JOIN team AS localTeam ON fixture.localteamId = localTeam.id " +
            "INNER JOIN team AS visitorTeam ON fixture.visitorteamId = visitorTeam.id " +
            "INNER JOIN stage ON fixture.stageId = stage.id " +
            "INNER JOIN venue ON fixture.venueId = venue.id " +
            "GROUP BY stageId" +
            "ORDER BY startingAt" +
            "WHERE stage.name =:stageName")
    fun getMatchesWithStageName(stageName: String): LiveData<List<FixtureAndTeam>>*/



}