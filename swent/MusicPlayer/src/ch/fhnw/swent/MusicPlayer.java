package ch.fhnw.swent;

import java.util.LinkedList;

import ch.fhnw.swent.Framework.Filter;

public class MusicPlayer {

	LinkedList<Item> items;
	
	public static void main(String[] args) {
		MusicPlayer mp = new MusicPlayer();
		
		Framework fr = new Framework();
		Filter f1 = new TitleContainsFilter("2");
		
		
		
	}
	
	public MusicPlayer() {
		
		for (int i = 0; i < 10; i++) {
			items.add(new Item("Titel"+i,"Album"+i,"Band"+i));
		}
	}
	
	public void printItems() {
		for (Item i : items){
			System.out.println(i.getArtist()+" - "+i.getTitle()+" ("+i.getAlbum()+")");
		}
	}
	
}
