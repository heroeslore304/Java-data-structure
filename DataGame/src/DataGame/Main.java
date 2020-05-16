package DataGame;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class Main extends Canvas{
	private static final int IMG_WIDTH = 1440;
	private static final int IMG_HEIGHT = 810;
	static BufferedImage img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,BufferedImage.TYPE_INT_ARGB);
	static Sceen sceen = new Sceen("Title");
	static Hand hand = new Hand();
	static Hand Enemyhand = new Hand();
	static Player player = new Player();
	static Enemy enemy = new Enemy();
	static Field field = new Field();
	static Boolean myturn = true;
	static LinkedList L= new LinkedList();
	static Minions m = new Minions();
	public static void main(String args[]) throws IOException{
	//선언
	enemy.hand = Enemyhand;
	player.controll="player";
	enemy.controll="enemy";
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Sound BGM = new Sound();
	BGM.BGM();
	//그래픽 생성
	for(int i = 0;i<10;i++) {
			Card card;
			if(Math.random()>0.3) {
			card = new Card(i,"Crusader");}
			else {card = new Card(i,"HighWayMan");}
			card.setState("Rope");
			card.add(sceen);
			card.gra.onAni=true;
			L.offer(card);
			}
	for(int i=0 ;i<18;i++) {
		Tile tile = new Tile((i/3)*100+420,(i%3)*50+425,sceen);
		field.Tile.offer(tile);
	}
	for(int i=0; i<field.Tile.size();i++) {
		BuildRoad(i);
	}
	Tile tile = (Tile)field.Tile.getFirst();
	//디버그
	for(int i = 0;i<L.size();i++) {
		Card temp=(Card) L.get(i);
		}
	//진행
	sceen.c.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
			switch(e.getKeyCode()) {
			case 10:turn();break;
			case 65:if(myturn){if(player.ActionNum>0)pull(L);}break;
			//case 68:if(myturn)push(L);break;
			//case 65:player.UpTug();break;
			}
			
		}
	});
	}
	/*public void StartSuffle() {
		Card card = new Card(i,"Crusader");
		card.setState("Rope");
		card.add(sceen);
		card.gra.onAni=false;
		card.gra.Type = "StartSuffle";
		L.offer(card);
	}*/
	static LinkedList pull(LinkedList L) {
		player.ActionNum--;
		Card card;
		if(Math.random()>0.3) {
			card = new Card(0,"Crusader");}
		else {card = new Card(0,"HighWayMan");}
		Card RemoveR=(Card) L.pop();
		if(hand.size<4) {
		hand.handadd(RemoveR);
		RemoveR.field=field;
		RemoveR.setState("Hand");
		RemoveR.minions=m;
		RemoveR.player=player;
		RemoveR.onHand();
		}
		else {
			RemoveR.destroy();
		}
		L.offerLast(card);
		card.setState("Rope");
		card.add(sceen);
		Setting(L);
		return L;
	}
	static LinkedList push(LinkedList L) {
		enemy.ActionNum--;
		Card card;
		if(Math.random()>0.3) {
			card = new Card(0,"Crusader");}
		else {card = new Card(0,"HighWayMan");}
		Card RemoveR=(Card) L.pollLast();
		if(Enemyhand.size<4) {
		Enemyhand.handadd(RemoveR);
		RemoveR.field=field;
		RemoveR.setState("Enemy");
		RemoveR.player=enemy;
		RemoveR.minions=m;
		RemoveR.add(sceen);
		}
		else {
			RemoveR.destroy();
		}
		L.offerFirst(card);
		card.setState("Rope");
		card.add(sceen);
		Setting(L);
		return L;
	}
	static LinkedList Setting(LinkedList L) {
		for(int i = 0;i<L.size();i++) {
			Card temp=(Card) L.get(i);
		temp.setting(i);
			}
		return L;
	}
	static void turn() {
		m.ActSet(m.head);
		if(myturn) {
			myturn = false;
			enemy.ActionNum=3;
			EnemyAI();
			}
		else {
			player.ActionNum=3;
			myturn = true;
		}
	}
	static void EnemyAI() {
		int temp=0;
		while(enemy.ActionNum>0) {
				if(Enemyhand.size<4) push(L);
				else Enemyhand.head.Effect();
				if(enemy.ActionNum==temp)break;
				else temp = enemy.ActionNum;
		}
		turn();
	}
	static void LinkTile(int n,int m) {
		field.TileLinked((Tile)field.Tile.get(n),(Tile) field.Tile.get(m));
	}
	static void BuildRoad(int i) {
		int m3=i-3;int m1=i-1;int p1=i+1;int p3=i+3;
		switch(i%3) {
		case 0:if(m3>=0) {LinkTile(i,m3);LinkTile(m3,i);}
		if(p1<=17) {LinkTile(i,p1);LinkTile(p1,i);}
		if(p3<=17) {LinkTile(i,p3);LinkTile(p3,i);}
		break;
		case 1:if(m3>=0) {LinkTile(i,m3);LinkTile(m3,i);}
		if(m1>=0) {LinkTile(i,m1);LinkTile(m1,i);}
		if(p1<=17) {LinkTile(i,p1);LinkTile(p1,i);}
		if(p3<=17) {LinkTile(i,p3);LinkTile(p3,i);}
		break;
		case 2:if(m3>=0) {LinkTile(i,m3);LinkTile(m3,i);}
		if(m1>=0) {LinkTile(i,m1);LinkTile(m1,i);}
		if(p3<=17) {LinkTile(i,p3);LinkTile(p3,i);}
		break;
		}
	}
}
class Player{
	String controll;
	int ActionNum;
	Player(){
		this.ActionNum = 3;
	}
}
class Enemy extends Player{
	Hand hand;
}
class Object{
	Graph gra = new Graph();;
	String name;
	String Type;
	Player player;
	Field field;
	Sceen sceen;
	Minions minions;
	void Reset() {}
	void AI() {}
}
class Card extends Object{
	int num;
	String state;
	String name="Attack";
	String Type="Card";
	Card link;
	Card pre;
	Hand hand;
	Card(int num,String name){
		this.num=num;
		this.name = name;
		switch(name){
		case "back":gra.Type=this.Type;break;
		case "Crusader":gra.Type="CruCard";break;
		case "HighWayMan":gra.Type="HigCard";break;
		}
		this.gra.object=this;
		this.gra.object.name=this.name;
	}
	void add(Sceen sceen) {
		this.sceen = sceen;
		switch (state) {
		case "Rope":sceen.setGraph(220+(num*100),10,gra);break;
		case "Hand":sceen.setGraph(10,32+(num*170),gra);break;
		case "Enemy":sceen.setGraph(1320,32+(num*170),gra);break;
		case "destroy":destroy();break;
		}
		this.gra.onAni=true;
	}
	void destroy() {
		setState("destroy");
		gra.sceen=sceen;
		gra.destroyAni();
		Card card = this;
		card = null;
	}
	void setting(int num) {
		this.num=num;
	}
	void Reset() {
		switch (state) {
		case "Rope":gra.Dx=220+(num*100);gra.Dy=10;break;
		case "Hand":gra.Dx=10;gra.Dy=32+170*num;break;
		case "Enemy":gra.Dx=1320;gra.Dy=32+(170*num);break;
		case "destroy":break;
		}
	}
	void setState(String state) {
		this.state=state;
	}
	void Effect() {
		if(minions.size<100) {
		if(player.ActionNum>0) {
			Tile tile = new Tile();
			if(player.controll=="player") {
				for(int i = 0 ; i<3; i++) {
					tile = (Tile)field.Tile.get(2-i);
					if(tile.onMinion==null)break;
					}
				SommonMinion(tile);
				}
			else{
				for(int i = 0 ; i<3; i++) {
					tile = (Tile)field.Tile.get(field.Tile.size()-3+i);
					if(tile.onMinion==null)break;
					}
			SommonMinion(tile);
			}
			}
		}
	}
	void SommonMinion(Tile tile) {
		if(tile.onMinion==null) {
		Card card= this;
		Minion minion = new Minion(this.name,field,sceen,tile,minions);
		minion.player = player;
		minion.add(sceen);
		System.out.println(minions.size);
		hand.handRemove(card);
		switch(this.name){
		case "Attack":break;
		}
	player.ActionNum--;
	destroy();
		}
	}
	void onHand() {
		gra.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(player.ActionNum>0) {
				Effect();
				}
			}
		});
	}
}
class Hand{
	Card head;
	int size=0;
	void handadd(Card card) {
		card.hand=this;
		if(head!=null) {
			card.link=head;
			head.pre=card;
			head = card;
		}
		else head = card;
		handSet(head);
		this.size++;
		return;
	}
	void handRemove(Card card) {
		if(card.pre!=null) {
			Card pre = card.pre;
			if(card.link!=null) {
			Card next =card.link;
			pre.link = next;
			next.pre = pre;
			}
			card.destroy();
		}
		else {
			if(card.link!=null) {
			head = card.link;
			head.pre=null;
			}
			card.destroy();
		}
		handSet(head);
		this.size--;
		return;
	}
	void handSet(Card card) {
		while(card!=null) {
			if(card.pre==null)card.num=0;
			else card.num=card.pre.num+1;
			card.setting(card.num);
			card=card.link;
		}
		return;
	}
}
class Band extends Card {
	Band(int num, String name) {
		super(num, name);
	}
	
}

class Minion extends Object{
	int HP,Atk,Def;
	String Type;
	String name;
	Tile ontile;
	Boolean act=false;
	MinionNode node = new MinionNode();
	Minion target;
	Minion(String name,Field field,Sceen sceen,Tile tile,Minions minions){
		this.name=name;
		gra.object=this;
		this.field = field;
		this.ontile = tile;
		this.sceen = sceen;
		this.minions = minions;
		Minion me = this;
		this.gra.object.name=this.name;
		ontile.onMinion=me;
		switch(name){
		case "Crusader":this.HP=10;this.Atk=3;this.Def=0;break;
		case "HighWayMan":this.HP=6;this.Atk=4;this.Def=0;break;
		}
	}
	void AI() {
		if(!act) {
			if(ontile!=null) {
		for(int i=ontile.Road.size();i>0;i--) {
			Road road =(Road) ontile.Road.get(i-1);
			if(road.link.onMinion==null) {
				ontile.onMinion=null;
				this.ontile = road.link;
				ontile.onMinion=this;
				this.act=true;break;}
			else { 
				Minion min = road.link.onMinion;
				Minion attacker = this;
				if(min.player.controll!=player.controll) {
					target=min;
					target.HP=target.HP-(this.Atk-target.Def);
					sceen.AttackAni(attacker, min);
					if(target.HP<=0) {target.die();target=null;}
					this.act=true;break;
				}
			}
		}
		switch(name) {
		case "Minion": break;
		}
		}
	}
		this.act=true;
	}
	void setAblity() {
		switch(name) {
		case "Woker": this.HP=15; this.Atk=0; this.Def=0;break;
		}
	}
	void setTile(Tile tile) {
		this.ontile =tile;
	}
	void add(Sceen sceen) {
		node.minion=this;
		minions.minionsadd(node);
		gra.Type=player.controll;
		sceen.setGraph(ontile.x,ontile.y,gra);
		
	}
	void die() {
		Minion die = this;
		this.minions.minionsRemove(this.node);
		this.gra.removethis(sceen.c);
		this.ontile.onMinion=null;
		this.ontile = null;
		die = null;
	}
	void Reset() {
		if(ontile!=null) {
		gra.Dx=ontile.x;
		gra.Dy=ontile.y;
		}
	}
	void nextMinion() {
		this.minions.ActSet(node.link);
	}
}
class MinionNode{
	MinionNode pre;
	MinionNode link;
	Minion minion;
	Minions minions;
}

class Minions{
	MinionNode head;
	int size=0;
	void minionsadd(MinionNode node) {
		node.minions=this;
		if(head!=null) {
			node.link=head;
			head.pre=node;
			head = node;
		}
		else head = node;
		this.size++;
		return;
	}
	void minionsRemove(MinionNode node) {
		if(node.pre!=null) {
			MinionNode pre = node.pre;
			if(node.link!=null) {
			MinionNode next =node.link;
			pre.link = next;
			next.pre = pre;
			node=null;
			}
			else node=null;
		}
		else {
			if(node.link!=null) {
			head = node.link;
			head.pre=null;
			}
			node=null;
		}
		this.size--;
		return;
	}
	void ActSet(MinionNode node) {
		if(node!=null) {
			if(node.minion.act)node.minion.act=false;
			else node.minion.AI();node.minion.gra.onAni=true;
			//ActSet(node.link);
		return;
		}
	}
}

class Road{
	Graph gra;
	Tile tile;
	String Type;
	Tile link;
}
class Tile{
	String Type="Tile";
	Minion onMinion;
	LinkedList Road = new LinkedList();
	int x,y;
	Tile(){}
	Tile(int x,int y,Sceen sceen){
		this.x=x;
		this.y=y;
	}
}
class Field{
	LinkedList Road = new LinkedList();
	LinkedList Tile = new LinkedList();
	Field(){
		
	}
	void TileAdd(Tile tile) {
		Tile.add(tile);
	}
	void TileLinked(Tile t1,Tile t2) {
			Road RoadTile = new Road();
			RoadTile.Type = "Road";
			RoadTile.tile = t2;
			RoadTile.link = t1;
			Road.offer(RoadTile);
			RoadTile.tile.Road.offer(RoadTile);
	}
}