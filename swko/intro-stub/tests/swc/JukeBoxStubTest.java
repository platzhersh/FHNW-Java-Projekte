package swc;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import swc.StubSong;

public class JukeBoxStubTest {
	private static String songTitle = "Coldplay - Talk";
	private JukeBox jukeBox;
	private Song stubSong;

	@Before
	public void setUp() throws Exception {
		stubSong = new StubSong(songTitle);
		jukeBox = new MusicJukeBox();
	}

	@Test(expected = JukeBoxException.class)
	public void testPlayOfNonexistingSong() {
		jukeBox.playTitle("not existing");
	}

	@Test
	public void testGetPlayList() {
		jukeBox.addSong(stubSong);
		List<Song> list = jukeBox.getPlayList();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testPlay() {
		jukeBox.addSong(stubSong);
		jukeBox.playTitle(songTitle);
		Song mySong = jukeBox.getActualSong();
		Assert.assertNotNull(mySong);
		Assert.assertTrue(mySong.isPlaying());
	}

	@Test(expected = JukeBoxException.class)
	public void testPlayOfAlreadyPlayingSong() {
		jukeBox.addSong(stubSong);
		jukeBox.playTitle(songTitle);
		jukeBox.playTitle(songTitle);		
	}
	
}
