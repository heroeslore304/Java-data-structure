package DataGame;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	File cardDraw = new File("src\\sound\\Card_Draw.wav");
	File pistol = new File("src\\sound\\pistol.wav");
	File sword = new File("src\\sound\\Sword.wav");
	File bgm = new File("src\\sound\\BGM.wav");
	public void BGM() {
		{
			try {
		AudioInputStream Bgm = AudioSystem.getAudioInputStream(bgm);
		this.clip = AudioSystem.getClip();
		clip.open(Bgm);
		clip.start();
	}
			catch(Exception e){}
		}
	}
	public void CardDraw(){
	{
	try {
	AudioInputStream carddraw = AudioSystem.getAudioInputStream(cardDraw);
	this.clip = AudioSystem.getClip();
	clip.open(carddraw);
	clip.start();
	}
	catch(Exception e){}
		}
	}
	public void Pistol(){
		{
		try {
		AudioInputStream Pistol = AudioSystem.getAudioInputStream(pistol);
		this.clip = AudioSystem.getClip();
		clip.open(Pistol);
		clip.start();
		}
		catch(Exception e){}
			}
		}
	
	public void Sword(){
		{
		try {
		AudioInputStream Sword = AudioSystem.getAudioInputStream(sword);
		this.clip = AudioSystem.getClip();
		clip.open(Sword);
		clip.start();
		}
		catch(Exception e){}
			}
		}
}
