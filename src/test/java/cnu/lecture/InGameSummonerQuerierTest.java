package cnu.lecture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import cnu.lecture.InGameInfo.Observer;
import cnu.lecture.InGameInfo.Participant;

/**
 * Created by tchi on 2016. 4. 25..
 */
public class InGameSummonerQuerierTest {
	private InGameSummonerQuerier querier;
	private InGameSummonerQuerier testableQurier;
	InGameInfo fakeInGameInfo;
	Observer observer;
	Participant[] fakeParticipants;

	@Before
	public void setup() throws ClientProtocolException, IOException {
		final String apiKey = "8242f154-342d-4b86-9642-dfa78cdb9d9c";
		GameParticipantListener dontCareListener = mock(GameParticipantListener.class);

		querier = new InGameSummonerQuerier(apiKey, dontCareListener);
		testableQurier = spy(querier);

		Participant fakeParticipant = mock(Participant.class);
		fakeParticipants = new Participant[] { fakeParticipant, fakeParticipant, fakeParticipant, fakeParticipant,
				fakeParticipant };

		fakeInGameInfo = mock(InGameInfo.class);
		observer = mock(Observer.class);

		when(fakeInGameInfo.getObservers()).thenReturn(observer);
		when(fakeInGameInfo.getParticipants()).thenReturn(fakeParticipants);
		when(fakeInGameInfo.getObservers().getEncryptionKey()).thenReturn("4/bl4DC8HBir8w7bGHq6hvuHluBd+3xM");
		when(testableQurier.inGameInfoRequest(anyString())).thenReturn(fakeInGameInfo);

	}

	@Test
	public void shouldQuerierIdentifyGameKeyWhenSpecificSummonerNameIsGiven() throws Exception {
		final String summonerName;

		GIVEN: {
			summonerName = "akane24";
		}

		final String actualGameKey;
		WHEN: {
			actualGameKey = testableQurier.queryGameKey(summonerName);

		}

		final String expectedGameKey = "4/bl4DC8HBir8w7bGHq6hvuHluBd+3xM";
		THEN: {
			assertThat(actualGameKey, is(expectedGameKey));
		}
	}

	@Test
	public void shouldQuerierReportMoreThen5Summoners() throws ClientProtocolException, IOException {
		final String summonerName;
		
		GIVEN: {
			summonerName = "akane24";
		}

		final String summonerId;
		final int actualNumberParticipants;
		WHEN: {
			summonerId = testableQurier.summonerRequest(summonerName);
			actualNumberParticipants = testableQurier.inGameInfoRequest(summonerId).getParticipants().length;
		}
		
		final int expectedGameKey = 4;
		THEN : {
			assertThat(actualNumberParticipants, is(6));
		}

	}
}
