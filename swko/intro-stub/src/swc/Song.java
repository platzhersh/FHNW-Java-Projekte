package swc;

public interface Song {
	public String getTitle();
	public float getPlayTime();
	public boolean isPlaying();
	public void start() throws JukeBoxException;
}
