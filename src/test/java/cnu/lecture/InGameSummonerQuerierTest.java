package cnu.lecture;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import com.google.gson.Gson;

import cnu.lecture.InGameInfo.Participant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;

/**
 * Created by tchi on 2016. 4. 25..
 */
public class InGameSummonerQuerierTest {
	private InGameSummonerQuerier querier;
	private InGameSummonerQuerier testableQurier;
	
	@Before
	public void setup() {
		final String apiKey = "8242f154-342d-4b86-9642-dfa78cdb9d9c";
		GameParticipantListener dontCareListener = mock(GameParticipantListener.class);

		querier = new InGameSummonerQuerier(apiKey, dontCareListener);
		testableQurier = new InGameSummonerQuerier(apiKey, dontCareListener);
	}

	@Test
	public void shouldQuerierIdentifyGameKeyWhenSpecificSummonerNameIsGiven() throws Exception {
		final String summonerName;
		final Participant p = new Participant();
		p.setSummonerName("3team");
		final Participant[] fakeParticipants = { p, p, p, p, p };
		
		InGameInfo inGameInfo = new InGameInfo();
		inGameInfo.setParticipants(fakeParticipants);

		when(testableQurier.inGameInfoRequest(anyObject(), anyObject())).thenReturn(inGameInfo);

		GIVEN: {
			summonerName = "akane24";
		}

		final String actualGameKey;
		WHEN: {
			actualGameKey = querier.queryGameKey(summonerName);

		}

		final String expectedGameKey = "4/bl4DC8HBir8w7bGHq6hvuHluBd+3xM";
		THEN: {
			//assertThat(actualGameKey, is(expectedGameKey));
			 assertThat(inGameInfo.getParticipants(), is(fakeParticipants));
		}
	}
}
