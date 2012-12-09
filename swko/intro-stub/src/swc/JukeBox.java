package swc;

import java.util.List;

public interface JukeBox {
	/**
	 * Adds given song to the playlist.
	 * 
	 * @param song to add
	 */
	public void addSong(Song song);

	/**
	 * Plays given song.
	 * 
	 * @param songTitle song title to play
	 * @throws JukeBoxException in case if song is just playing 
	 */
	public void playTitle(String songTitle) throws JukeBoxException;

	/**
	 * Returns song which is playing actually.
	 * 
	 * @return song object
	 */
	public Song getActualSong();
	
	/**
	 * Returns the playlist.
	 * 
	 * @return list of all song objects added to this jukebox
	 */
	public List<Song> getPlayList();
}
