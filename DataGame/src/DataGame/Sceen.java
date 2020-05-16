package DataGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
class Graph extends JLabel{
	String Type;
	int x,y,Dx,Dy;
	ImageIcon img;
	ImageIcon stay;
	Card card;
	Object object;
	Sceen sceen;
	Boolean onDestroy;
	Boolean onAni;
	//Boolean StartSuffle=false;
	Sound sound = new Sound();
	int onAttack=0;
	int speed=5;
	Graph(){
		this.onDestroy=false;
		this.speed=5;
		this.onAttack=0;
		sound.CardDraw();
		onAni=false;
	}
	void removethis(Container c) {
		this.setSize(0, 0);
		c.remove(this);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch(Type){
		case "Tile":break;
		case "Card":this.setIcon(img);break;
		case "back":this.setIcon(img);break;
		case "player":this.setIcon(img);break;
		case "enemy":this.setIcon(img);break;
		case "CruCard":this.setIcon(img);break;
		case "HigCard":this.setIcon(img);break;
		//case "StartSuffle":this.setIcon(img);break;
		}
	}
	synchronized void FrameRun() {
		if(onAttack==0) {
		if(Type!="Tile") {
		this.img=this.stay;
		this.setSize(img.getIconWidth(),img.getIconHeight());
		object.Reset();
		setLocation(Dx,Dy);}
		}
		else {
			this.onAttack--;
		}
		if(onAni) {
		Ani();
		setLocation(x,y);}
		repaint();
		notify();
	}
	void Ani() {
		if(x<Dx&&x>Dx-speed)x++;
		else if(x<Dx)this.x+=speed;
		else if(x>Dx&&x<Dx+speed)x--;
		else if(x>Dx)this.x-=speed;
		if(y<Dy&&y>Dy-speed)y++;
		else if(y<Dy)this.y+=speed;
		else if(y>Dy&&y<Dy+speed)y--;
		else if(y>Dy)this.y-=speed;
		if(onDestroy) {
			if(x==Dx&&y==Dy)removethis(sceen.c);
		}
		if(Type=="player"||Type=="enemy") {
			if(x==Dx&&y==Dy) {
				Minion min= (Minion)this.object;
				min.nextMinion();
				this.onAni=false;
			}
		}
		if(Type=="StartSuffle") {
			if(x==Dx&&y==Dy) {
				this.Type="Card";
				this.onAni=true;
			}
		}
	}
	void destroyAni() {
		if(this.x<720) {
			this.Dx=this.Dx-45;
		}
		else {this.Dx=this.Dx+45;}
		this.onDestroy=true;
	}
	void EndAni() {
		
	}
}
class ConsumerThread extends Thread{
	Graph gra;
	ConsumerThread(Graph gra){
		this.gra = gra;
	}
	public void run() {
		while(true) {
			try {
				sleep(17);
				gra.FrameRun();
			}
			catch(InterruptedException e) {
				return;
			}
		}
	}
	
}
public class Sceen extends JFrame {
	Container c = getContentPane();
	ImageIcon background= new ImageIcon("src\\image\\Ruins.png");
	ImageIcon soilder= new ImageIcon("src\\image\\soilder.png");
	ImageIcon manti= new ImageIcon("src\\image\\manti.png");
	ImageIcon cardBack = new ImageIcon("src\\image\\card.png");
	ImageIcon banner = new ImageIcon("src\\image\\Banner.png");
	ImageIcon WAtk = new ImageIcon("src\\image\\WAtk.png");
	ImageIcon WHit = new ImageIcon("src\\image\\WHit.png");
	ImageIcon WStay = new ImageIcon("src\\image\\WStay.png");
	ImageIcon RAtk = new ImageIcon("src\\image\\RAtk.png");
	ImageIcon RHit = new ImageIcon("src\\image\\RHit.png");
	ImageIcon RStay = new ImageIcon("src\\image\\RStay.png");
	ImageIcon EWAtk = new ImageIcon("src\\image\\EWAtk.png");
	ImageIcon EWHit = new ImageIcon("src\\image\\EWHit.png");
	ImageIcon EWStay = new ImageIcon("src\\image\\EWStay.png");
	ImageIcon ERAtk = new ImageIcon("src\\image\\ERAtk.png");
	ImageIcon ERHit = new ImageIcon("src\\image\\ERHit.png");
	ImageIcon ERStay = new ImageIcon("src\\image\\ERStay.png");
	ImageIcon CruCard = new ImageIcon("src\\image\\CruCard.png");
	ImageIcon HigCard = new ImageIcon("src\\image\\HigCard.png");
	ImageIcon HowlCard = new ImageIcon("src\\image\\HowlCard.png");
	//static JLabel Watk = new JLabel(WAtk);
	//static JLabel eWhit = new JLabel(EWHit);
	static BufferedImage img = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_ARGB);
	static Graph back = new Graph();
	Sceen(String title){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440,810);
		setResizable(false);
		setVisible(true);
		c.setLayout(null);
		//Watk.setBounds(0, 0, WAtk.getIconWidth(), WAtk.getIconHeight());
		//Watk.setLocation(600, 500);
		//eWhit.setBounds(0, 0, EWHit.getIconWidth(), EWHit.getIconHeight());
		//eWhit.setLocation(800, 500);
		back.img=background;
		back.Type="back";
		back.x=0;back.y=0;
		back.sceen=this;
		back.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		back.setLocation(0,0);
		JLabel player = new JLabel(soilder);
		player.setBounds(0, 0, soilder.getIconWidth(), soilder.getIconHeight());
		player.setLocation(200, 500);
		c.requestFocus();
		//c.add(Watk);
		//Watk.setVisible(false);
		//c.add(eWhit);
		//eWhit.setVisible(false);
		//ConsumerThread th = new ConsumerThread(back);
		//th.start();
	}
	void setGraph(int x,int y,Graph gra){
		c.remove(back);
		gra.Dx=x;
		gra.Dy=y;
		switch(gra.Type) {
		case "Tile":break;
		case "Card":gra.x=675;gra.y=200;gra.setSize(90,160);gra.img=cardBack;gra.stay=gra.img;break;
		case "CruCard":gra.x=675;gra.y=200;gra.setSize(110,177);gra.img=CruCard;gra.stay=gra.img;break;
		case "HigCard":gra.x=675;gra.y=200;gra.setSize(110,177);gra.img=HigCard;gra.stay=gra.img;break;
		case "HowlCard":gra.x=675;gra.y=200;gra.setSize(110,177);gra.img=HowlCard;gra.stay=gra.img;break;
		case "player":gra.x=gra.Dx;gra.y=gra.Dy;System.out.println(gra.object);
				if(gra.object.name=="Crusader") {gra.img=WStay;gra.setSize(139,256);}
				else if(gra.object.name=="HighWayMan"){gra.img=RStay;gra.setSize(110,256);}gra.stay=gra.img;break;
		case "enemy":gra.x=gra.Dx;gra.y=gra.Dy;
				if(gra.object.name=="Crusader") {gra.img=EWStay;gra.setSize(139,256);}
				else if(gra.object.name=="HighWayMan"){gra.img=ERStay;gra.setSize(110,256);}gra.stay=gra.img;break;
		}
		gra.setLocation(gra.x,gra.y);
		//gra.setOpaque(false);
		//gra.setBackground(Color.BLACK);
		c.add(gra);
		c.add(back);
		ConsumerThread th = new ConsumerThread(gra);
		th.start();
	}
	void AttackAni(Minion attacker, Minion target) {
		Sound sound = new Sound();
		if(attacker.player.controll=="player") {
			switch(attacker.name) {
			case "Crusader":attacker.gra.img=WAtk;sound.Sword();
			attacker.gra.setSize(WAtk.getIconWidth(),WAtk.getIconHeight());break;
			case "HighWayMan":attacker.gra.img=RAtk;sound.Pistol();
			attacker.gra.setSize(RAtk.getIconWidth(),RAtk.getIconHeight());break;
			}
			attacker.gra.x=600;attacker.gra.Dx=600;
			attacker.gra.y=300;attacker.gra.Dy=300;
			switch(target.name) {
			case "Crusader":target.gra.img=EWHit;
			target.gra.setSize(EWHit.getIconWidth(),EWHit.getIconHeight());break;
			case "HighWayMan":target.gra.img=ERHit;
			target.gra.setSize(ERHit.getIconWidth(),ERHit.getIconHeight());break;
			}
			target.gra.x=800;target.gra.Dx=800;
			target.gra.y=300;target.gra.Dy=300;target.gra.setLocation(target.gra.x,target.gra.y);
		}
		else {
			switch(attacker.name) {
			case "Crusader":attacker.gra.img=EWAtk;sound.Sword();
			attacker.gra.setSize(EWAtk.getIconWidth(),EWAtk.getIconHeight());break;
			case "HighWayMan":attacker.gra.img=ERAtk;sound.Pistol();
			attacker.gra.setSize(ERAtk.getIconWidth(),ERAtk.getIconHeight());break;
			}
			attacker.gra.x=800;attacker.gra.Dx=800;
			attacker.gra.y=300;attacker.gra.Dy=300;
			switch(target.name) {
			case "Crusader":target.gra.img=WHit;
			target.gra.setSize(WHit.getIconWidth(),WHit.getIconHeight());break;
			case "HighWayMan":target.gra.img=RHit;
			target.gra.setSize(RHit.getIconWidth(),RHit.getIconHeight());break;
			}
			target.gra.x=600;target.gra.Dx=600;
			target.gra.y=300;target.gra.Dy=300;target.gra.setLocation(target.gra.x,target.gra.y);
		}
		attacker.gra.onAttack=50;attacker.gra.onAni=true;
		target.gra.onAttack=50;target.gra.onAni=true;
	}
}
