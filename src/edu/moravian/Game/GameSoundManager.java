package edu.moravian.Game;

import java.util.ArrayList;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class GameSoundManager {
	
	//private ArrayList<Music> playlist = new ArrayList<Music>();
        private Music music;
        private Sound sound;
        
	private int playlistCount;
	private Boolean isPlaying;
	
	public GameSoundManager() throws SlickException {
            music = new Music("res/pokemonBattleTheme.wav");
            music.loop();
//            sound = new Sound("res/pokemonItem.wav");
	    
	}
	/*
	public void playNextSong() {
		if (playlistCount >= 2) {
			playlistCount = -1;
		}
		playlistCount++;
		playlist.get(playlistCount).play();
	}*/
	
	public void pauseSong() {
            music.pause();
		//playlist.get(playlistCount).pause();
	}
	
	public void resumeSong() {
            music.resume();
		//playlist.get(playlistCount).resume();
	}
	
	public void playSound() {
		sound.play();
	}
	/*
	public Boolean isPlaying() {
		if(playlist.get(playlistCount).playing()) {
			isPlaying = true;
		}
		else {
			isPlaying = false;
		}
		return isPlaying;
	}*/

}
