//Richard Du
//04/23/12
//Game.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JApplet implements KeyListener,MouseListener,MouseMotionListener, ActionListener
{
	//Declaring variables
	private Finish endscreen;
	private Questions question;
	private Inventory inventory;
	private GameArea canvas;
	private MainMenu menu;
	private Timer timer = new Timer(100,this);
	private Timer shot = new Timer(30,new ShotTimer());
	private Timer moving = new Timer(20,new PMove());
	private Image map, image, imageg, tempimage,coin,title,eyes;
	private int xpos,ypos,xplayer,yplayer,xchange,ychange,shotx,shoty,mx,my;
	private int startx,starty,pheight,pwidth,sheight,swidth,mapx,mapy,mmx,mmy;
	private int x1=200,y1=300, x2=200,y2=300;
	private int xmouse, ymouse, temp;
	private int xitem, yitem, xdrag,ydrag,xequip,xrelease,yrelease,dragitem;
	private int bossx,bossy,sqxboss,sqyboss,cxboss,cyboss,sxboss,syboss,txboss,tyboss;
	private int bosshp,sqbossmaxhp,cbossmaxhp,sbossmaxhp,tbossmaxhp;
	private boolean shoot,ready,hit,edgex,edgey,sqfire,sqready,cfire,cready,sfire,sready,tfire,tready,answer;
	private boolean correct,movement,timeout,sqhit,chit,starhit,thit,pause,nogold,win,up,down,left,right,drawCoin,sq,cir,st,tri,boss;
	private int[] sqx, sqy, cx, cy, sx, sy, tx, ty,equip,sqhp,chp,shp,thp;
	private int sqxshot,sqyshot,cxshot,cyshot,sxshot,syshot,txshot,tyshot,bxchange,bychange,rTime,coinx,coiny;
	private int sqcount,ccount,scount,tcount,playerhp, pmaxhp, exp,maxexp, attack, lvl, gold,errortime,gTime,damagetotal,exptotal,gametime,damagetaken,canswer,tquestion,goldspent;
	private int sqxp,cxp,sxp,txp,sqbosshp,cbosshp,sbosshp,tbosshp,hpcost,atkcost,wepcost,afcost,goldtotal,bosscount,sqgold,cgold,sgold,tgold;
	private int maxsqhp,maxchp,maxshp,maxthp,timelimit,exitlocationx,exitlocationy,quesnum=6;//<html><sup></sup><sub></sub></html>
	private int[][] items;
	private String[][] education =
	//Questions
	/*1*/{{"Multiply (4x - 3)(x - 4)","<html>4x<sup>2</sup> - 19x - 12</html>","<html>4x<sup>2</sup> - 7</html>","<html>4x<sup>2</sup> - 12</html>","<html>4x<sup>2</sup> - 19x + 12</html>","d."},
	/*2*/{"<html>Multiply (a - b)(a<sup>2</sup> + ab + b<sup>2</sup>)</html>","<html>a<sup>3</sup> - b<sup>3</sup></html>","<html>a<sup>3</sup> + a<sup>2</sup>b + ab<sup>2</sup></html>","<html>a - a<sup>2</sup>b - ab<sup>2</sup> - b<sup>3</sup></html>","<html>a<sup>3</sup> + 2a<sup>2</sup>b + 2ab<sup>2</sup> - b<sup>3</sup><html>","a."},
	/*3*/{"<html>Factor 3x<sup>2</sup> - 4x - 4 completely</html>","(3x - 2)(x + 2)", "(3x + 2)(x - 2)", "(3x - 4)(x + 1)", "(3x - 1)(x + 4)","b."},
	/*4*/{"72% of 72 is what number?","100","1","<html><sup>18</sup>\u2044 <sub>25</sub></html>","51.84","d."},
	/*5*/{"<html>Factor 3x<sup>3</sup> - 27x completely</html>","<html>3x(x - 3)<sup>2</sup></html>","3x(x - 3)(x + 3)","<html>3(x - 2)(x<sup>2</sup> + 3x + 9)</html>","<html>3(x + 3)(x<sup>2</sup> - 3x + 9)</html>","b."},
	/*6*/{"<html>Solve x<sup>2</sup> = 1764</html>","420, -420","42, -42","4.2, -4.2","0.42, -0.42","b."},
	/*7*/{"<html>Solve x<sup>2</sup> = <sup>576</sup>/ <sub>400</sub></html>","<html><sup>12</sup>\u2044 <sub>5</sub> ,<sup>-12</sup>\u2044 <sub>5</sub></html>","<html><sup>3</sup>\u2044 <sub>4</sub> , <sup>-3</sup>\u2044 <sub>4</sub></html>","<html><sup>6</sup>\u2044 <sub>5</sub> , <sup>-6</sup>\u2044 <sub>5</sub></html>","<html><sup>8</sup>\u2044 <sub>5</sub> , <sup>-8</sup>\u2044 <sub>5</sub></html>","c."},
	/*8*/{"<html>5z<sup>2</sup> + 11z + 2</html>","<html>{-2,<sup>-1</sup>\u2044 <sub>5</sub>}</html>","<html>{2, <sup>1</sup>\u2044 <sub>5</sub>}</html>","<html>{2, <sup>-1</sup>\u2044 <sub>5</sub>}</html>","<html>{-2, <sup>1</sup>\u2044 <sub>5</sub>}</html>","a."},
	/*9*/{"<html>x<sup>2</sup> - 4x - 77</html>","{11, 7}","{-11, -7}","{11, -7}","{-11, 7}","c."},
	/*10*/{"Find the prime factorization of 1350","<html>(2<sup>3</sup>)(3)(5<sup>2</sup>)</html>","<html>(2<sup>2</sup>)(3<sup>4</sup>)(5)</html>","<html>(2)(3<sup>3</sup>)(5<sup>2</sup>)</html>","<html>(2<sup>2</sup>)(3<sup>2</sup>)(5<sup>2</sup>)</html>","c."},
	/*11*/{"<html>Simplify (-x<sup>3</sup>)<sup>2</sup>x<sup>4</sup></html>","<html>-x<sup>10</sup></html>","<html>x<sup>24</sup></html>","<html>-x<sup>9</sup></html>","<html>x<sup>10</sup></html>","d."},
	/*12*/{"<html>Factor x<sup>2</sup> - x - a - a<sup>2</sup></html>","(x + a)(x - a - 1)","x(x - 1) - a(1 + a)","(x - a)(x + a - 1)","(x - a)(x + a + 1)","a."},
	/*13*/{"<html>Factor 4t<sup>2</sup> + 4t + 1 completely</html>","4t(t + 1) + 1","<html>4(t<sup>2</sup> + t + 1)</html>","<html>(2t + 1)<sup>2</sup></html>","<html>(2t - 1)<sup>2</sup><html>","c."},
	/*14*/{"Simplify (2 + 3i)(1 - i)","-1 + i","5 + i","-1 + 5i","-1","b."},
	/*15*/{"<html>Find the midpoint of PQ.<br/>P(-4, 3) Q(-6, -1)</html>","(5, 1)","(-5, 1)","(-1, 1)","(-5, -1)","b."},
	/*16*/{"<html>Solve 25<sup>x + 2</sup> = 5<sup>3x - 3</sup></html>","2","3","4","7","d."},
	/*17*/{"<html>Simplify log<sub>4</sub> 64</html>","<html><sup>1</sup>\u2044 <sub>3</sub></html>","3","16","4","b."},
	/*18*/{"<html>Between which two numbers is log<sub>5</sub> 150?</html>","0 and 1","1 and 2","2 and 3","3 and 4","d."},
	/*19*/{"<html>Find the next term in the sequence<br/>8, 5, 2, -1...</html>","-2","-3","-4","-5","c."},
	/*20*/{"<html>Insert two arithmetic means between -2 and 13</html>","1, 10","2, 9","3, 8","4, 7","c."},
	/*21*/{"<html>Find the geometric mean of 10 and 40</html>","15","20","25","30","b."},
	/*22*/{"<html>Expand and simplify (x<sup>2</sup> - 2)<sup>4</sup></html>","<html>x<sup>8</sup> + 8x<sup>6</sup> + 24x<sup>4</sup> + 32x<sup>2</sup> + 16</html>","<html>x<sup>8</sup> + 4x<sup>6</sup> + 6x<sup>4</sup> + 4x<sup>2</sup> + 1</html>","<html>x<sup>8</sup> - 8x<sup>6</sup> + 24x<sup>4</sup> - 32x<sup>2</sup> + 16</html>","<html>x<sup>4</sup> - 8x<sup>3</sup> + 24x<sup>2</sup> - 32x + 16</html>","c."},
	/*23*/{"<html>Express cos 230<sup>o</sup> as a function of an acute angle</html>","<html>-cos 40<sup>o</sup></html>","<html>-sin 40<sup>o</sup></html>","<html>-cos 50<sup>o</sup></html>","<html>cos 50<sup>o</sup></html>","c."},
	/*24*/{"<html>Find tan \u03F4 if cos \u03F4 = <sup>8</sup>/<sub>17</sub><br/>and sin \u03F4 > 0</html>","<html><sup>15</sup>/<sub>8</sub></html>","<html><sup>-15</sup>/<sub>17</sub></html>","<html><sup>-8</sup>/<sub>15</sub></html>","<html><sup>-15</sup>/<sub>8</sub></html>","a."},
	/*25*/{"<html>In \u0394ABC, \u2220 B = 30<sup>o</sup> and c = 20. For what value(s) of b will the triangle have two solutions?</html>","0 < b < 10","10 < b < 20","b < 20","b = 10","b."},
	/*26*/{"<html>What is the median of 6, 7, 7, 8, 8, 9, 9, 10, 10?</html>","7","8","9","10","b."},
	/*27*/{"<html>How many positive integers less than 25 can be formed by using the digits 1, 2, and 3?</html>","3","6","9","12","c."},
	/*28*/{"<html>Express <sup>13\u03C0</sup>\u2044<sub> 4</sub> radians in degrees</html>","<html>495<sup>o</sup></html>","<html>225<sup>o</sup></html>","<html>585<sup>o</sup></html>","<html>135<sup>o</sup></html>","c."},
	/*29*/{"<html>Find the period of the function defined by y = 2 sec \u03C0x</html>","\u03C0","2\u03C0","1","2","d."},
	/*30*/{"<html>Simplify sec \u03F4 - tan \u03F4 sin \u03F4</html>","<html>sin<sup>2</sup> \u03F4</html>","<html>cos<sup>2</sup> \u03F4</html>","sec \u03F4","cos \u03F4","d."},
	};
	private boolean[] sqbar,cbar,sbar,tbar;
	private String res,error,bosshealth;
	private long curtime,starttime,gstime,gctime,mcTime,msTime,recordSTime,recordCTime,bsTime,bcTime;
	private JRadioButton c1,c2,c3,c4;
	private JButton contgamebutton,hpbutton,atkbutton,wepbutton,afbutton;
	private JLabel ques, time, result,a1,a2,a3,a4;
	private CardLayout cards;
	private Container c;
	private int shotnum,doneshots;
	private double degree,tan,dist;
	private boolean autofire,buyautofire,infinitegold,infinitehp;
	private int[][] shots;
	private boolean[] shotdone;
	//Constructor
	public Game()
	{
		//Sets default values for most of the variables
		damagetotal = exptotal = damagetaken = canswer = tquestion = goldspent =0;
		buyautofire = infinitegold = infinitehp = false;
		shots = new int[1][2];
		shotdone = new boolean[1];
		shotnum = 1;
		dist = tan = degree = 0.0;
		doneshots = 0;
		autofire = false;
		coinx = coiny = 0;
		answer = false;
		drawCoin = false;
		gTime = 0;
		sqcount = ccount = scount = tcount = bosscount = 0 ;
		win = pause = nogold = false;
		up = down = left = right = false;
		errortime = 2;
		res = "";
		error= "";
		bosshealth = "";
		hpcost = 20;
		atkcost = 10;
		wepcost = 30;
		afcost = 1000;
		gold = 0;
		exitlocationx = 4975;
		exitlocationy = 4975;
		sqxp = 1;
		cxp = 2;
		sxp = 4;
		txp = 5;
		sqbossmaxhp = 2000;
		cbossmaxhp = 4000;
		sbossmaxhp = 6000;
		tbossmaxhp = 10000;
		sqbosshp = sqbossmaxhp;
		cbosshp = cbossmaxhp;
		sbosshp = sbossmaxhp;
		tbosshp = tbossmaxhp;
		movement = true;
		quesnum = (int)(Math.random()*(education.length));
		attack = lvl = 1;
		starttime = curtime = gctime = gstime = mcTime = msTime =
		recordCTime = recordSTime = 0;
		bsTime = bcTime = 0;
		timelimit = 15;
		maxsqhp=2;
		maxchp=4;
		maxshp=6;
		maxthp=10;
		sqgold = maxsqhp*2;
		cgold = maxchp*2;
		sgold = maxshp*2;
		tgold = maxthp*2;
		playerhp = pmaxhp = 100;
		exp = 0;
		maxexp = 25;
		mapx = mapy = -4750;
		//Creates and sets default values for the enemy shapes
		sqx = new int[300];
		sqy = new int[300];
		cx = new int[300];
		cy = new int[300];
		sx = new int[300];
		sy = new int[300];
		tx = new int[300];
		ty = new int[300];
		sqhp = new int [300];
		chp = new int[300];
		shp = new int[300];
		thp = new int[300];
		sqbar = new boolean[300];
		cbar = new boolean[300];
		sbar = new boolean[300];
		tbar = new boolean[300];
		for (int i = 0; i < sqhp.length; i++)
			sqhp[i] = maxsqhp;
		for (int i = 0; i < chp.length; i++)
			chp[i] = maxchp;
		for (int i = 0; i < shp.length; i++)
			shp[i] = maxshp;
		for (int i = 0; i < thp.length; i++)
			thp[i] = maxthp;
		for (int i = 0; i < sqx.length; i++)
			switch((int)(Math.random()*3))
			{
				case 0: sqx[i]=(int)(Math.random()*(-1150)+(1370+mapx));
						sqy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
				case 1: sqx[i]=(int)(Math.random()*(-1380)+(2780+mapx));
						sqy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
				case 2: sqx[i]=(int)(Math.random()*(-1390)+(4210+mapx));
						sqy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
			}
		for (int i = 0; i < cx.length; i++)
			switch((int)(Math.random()*3))
			{
				case 0: cx[i]=(int)(Math.random()*(1400)+(5780+mapx));
						cy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
				case 1: cx[i]=(int)(Math.random()*(1380)+(7230+mapx));
						cy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
				case 2: cx[i]=(int)(Math.random()*(1390)+(8660+mapx));
						cy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
			}
		for (int i = 0; i < sx.length; i++)
			switch((int)(Math.random()*3))
			{
				case 0: sx[i]=(int)(Math.random()*(-1370)+(5630+mapx));
						sy[i]=(int)(Math.random()*(1400)+(5780+mapy));break;
				case 1: sx[i]=(int)(Math.random()*(-4220)+(7050+mapx));
						sy[i]=(int)(Math.random()*(1380)+(7130+mapy));break;
				case 2: sx[i]=(int)(Math.random()*(-7080)+(8480+mapx));
						sy[i]=(int)(Math.random()*(1390)+(8560+mapy));break;
			}
		for (int i = 0; i < tx.length; i++)
			switch((int)(Math.random()*3))
			{
				case 0: tx[i]=(int)(Math.random()*(-1370)+(5680+mapx));
						ty[i]=(int)(Math.random()*(-1390)+(4280+mapy));break;
				case 1: tx[i]=(int)(Math.random()*(-4220)+(7100+mapx));
						ty[i]=(int)(Math.random()*(-1380)+(2880+mapy));break;
				case 2: tx[i]=(int)(Math.random()*(-7080)+(8530+mapx));
						ty[i]=(int)(Math.random()*(-1400)+(1470+mapy));break;
			}
		items = new int[5][10];
		equip = new int[4];
		xplayer = yplayer = 250;
		mx = my = 300;
		ready = true;
		pheight = 30;
		pwidth = 30;
		sheight = swidth = 10;
		edgex = edgey = false;
		items[0][0] = 999;
		items[4][3] = 998;
		equip[0]=999;
		sqxboss = -4250;
		sqyboss = 250;
		sxboss = 250;
		syboss = 4250;
		cxboss = 4250;
		cyboss = 250;
		txboss = 250;
		tyboss = -4250;
	}
	//Initializes and adds the panels. Gets the images
	public void init()
	{
		//bossshot.start();
		map = getImage ( getDocumentBase ( ), "Map.jpg" );
		WaitForImage ( this, map );
		//imageg not used
		imageg = getImage ( getDocumentBase ( ), "MePicture4.jpg" );
		WaitForImage ( this, imageg );
		coin = getImage( getDocumentBase ( ), "Coin.png");
		WaitForImage ( this, coin );
		title = getImage( getDocumentBase ( ), "Title.png");
		WaitForImage ( this, title );
		eyes = getImage( getDocumentBase ( ), "Eyes.png");
		WaitForImage ( this, eyes );
		c = this.getContentPane();
		cards = new CardLayout();
		c.setLayout(cards);
		menu = new MainMenu();
		c.add(menu, "MainMenu");
		canvas = new GameArea();
		c.add(canvas, "GamePanel");
		inventory = new Inventory();
		c.add(inventory, "Inventory");
		endscreen = new Finish();
		c.add(endscreen, "EndScreen");
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.requestFocus();
	}
	public void WaitForImage ( JApplet component, Image image )
	{
		MediaTracker tracker = new MediaTracker ( component );
		try
		{
			tracker.addImage ( image, 0 );
			tracker.waitForID ( 0 );
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace ( );
		}
	}
	class GameArea extends JPanel implements ActionListener
	{
		//Inventory button (not used)
		private JButton ibutton;
		public GameArea()
		{
			this.setLayout(null);
			//Not used in the game
			ibutton = new JButton("Inventory");
			ibutton.setBounds(550,90,100,30);
			ibutton.addActionListener(this);
			//this.add(ibutton);
			//Create the upgrade buttons
			hpbutton = new JButton("Health + 5");
			hpbutton.setBounds(520,320,110,20);
			hpbutton.addActionListener(this);
			this.add(hpbutton);
			atkbutton = new JButton("Attack + 1");
			atkbutton.setBounds(520,365,110,20);
			atkbutton.addActionListener(this);
			this.add(atkbutton);
			wepbutton = new JButton("Weapon + 1");
			wepbutton.setBounds(520,410,110,20);
			wepbutton.addActionListener(this);
			this.add(wepbutton);
			afbutton = new JButton("Autofire");
			afbutton.setBounds(520,455,110,20);
			afbutton.addActionListener(this);
			this.add(afbutton);
			//Creates the question panel for the educational part
			question = new Questions();
			question.setVisible(false);
			this.add(question);
			question.setBounds(100,100,300,300);
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//Draws the map
			g.drawImage (map, mapx, mapy, 10000,10000, this);
			//Draws the exit location if true
			if(sqbosshp<=0&&cbosshp<=0&&sbosshp<=0&&tbosshp<=0)
			{
				g.setColor(Color.green);
				g.fillRect(mapx+exitlocationx,mapy+exitlocationy,50,50);
				g.setColor(Color.black);
				g.drawRect(mapx+exitlocationx,mapy+exitlocationy,50,50);
				g.drawString("EXIT",mapx+exitlocationx+12,mapy+exitlocationy+30);
			}
			g.setColor(Color.black);
			g.fillRect(xplayer,yplayer,pheight,pwidth);
			g.setColor(Color.red);
			//Checks for shot collision with the enemies
			for (int index = 0; index < shots.length; index++)
			{
				for (int i = 0; i < sqx.length; i++)
				{
					if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>sqx[i]&&shots[index][0]<sqx[i]+20)&&(shots[index][1]>sqy[i]&&shots[index][1]<sqy[i]+20))||((shots[index][0]<sqx[i]&shots[index][0]>sqx[i]+20)&&(shots[index][1]<sqy[i]&shots[index][1]>sqy[i]+20)))
					{
						hit = true;
						sqbar[i] = true;
						if(sqhp[i]>0)
						{
							sqhp[i]-=attack;
							damagetotal+=attack;
						}
						if(sqhp[i]<=0)
						{
							switch((int)(Math.random()*3))
							{
								case 0: sqx[i]=(int)(Math.random()*(-1150)+(1370+mapx));
										sqy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
								case 1: sqx[i]=(int)(Math.random()*(-1380)+(2780+mapx));
										sqy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
								case 2: sqx[i]=(int)(Math.random()*(-1390)+(4210+mapx));
										sqy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
							}
							sq = true;
							drawCoin=true;
							sqbar[i]=false;
							exp+=sqxp;
							exptotal+=sqxp;
							sqhp[i]=maxsqhp;
							gold+=sqgold;
							goldtotal+=sqgold;
							sqcount++;
						}
					}
				}
				for (int i = 0; i < cx.length; i++)
				{
					if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>cx[i]&&shots[index][0]<cx[i]+20)&&(shots[index][1]>cy[i]&&shots[index][1]<cy[i]+20))||((shots[index][0]<cx[i]&shots[index][0]>cx[i]+20)&&(shots[index][1]<cy[i]&shots[index][1]>cy[i]+20)))
					{
						hit = true;
						cbar[i] = true;
						if(chp[i]>0)
						{
							chp[i]-=attack;
							damagetotal+=attack;
						}
						if(chp[i]<=0)
						{
							switch((int)(Math.random()*3))
							{
								case 0: cx[i]=(int)(Math.random()*(1400)+(5780+mapx));
										cy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
								case 1: cx[i]=(int)(Math.random()*(1380)+(7230+mapx));
										cy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
								case 2: cx[i]=(int)(Math.random()*(1390)+(8660+mapx));
										cy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
							}
							cir = true;
							drawCoin=true;
							cbar[i]=false;
							exp+=cxp;
							exptotal+=cxp;
							chp[i]=maxchp;
							gold+=cgold;
							goldtotal+=cgold;
							ccount++;
						}
					}
				}
				for (int i = 0; i < sx.length; i++)
				{
					if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>sx[i]&&shots[index][0]<sx[i]+20)&&(shots[index][1]>sy[i]&&shots[index][1]<sy[i]+20))||((shots[index][0]<sx[i]&shots[index][0]>sx[i]+20)&&(shots[index][1]<sy[i]&shots[index][1]>sy[i]+20)))
					{
						hit = true;
						sbar[i] = true;
						if(shp[i]>0)
						{
							shp[i]-=attack;
							damagetotal+=attack;
						}
						
						if(shp[i]<=0)
						{
							switch((int)(Math.random()*3))
							{
								case 0: sx[i]=(int)(Math.random()*(-1370)+(5630+mapx));
										sy[i]=(int)(Math.random()*(1400)+(5780+mapy));break;
								case 1: sx[i]=(int)(Math.random()*(-4220)+(7050+mapx));
										sy[i]=(int)(Math.random()*(1380)+(7130+mapy));break;
								case 2: sx[i]=(int)(Math.random()*(-7080)+(8480+mapx));
										sy[i]=(int)(Math.random()*(1390)+(8560+mapy));break;
							}
							st = true;
							drawCoin=true;
							sbar[i]=false;
							exp+=sxp;
							exptotal+=sxp;
							shp[i]=maxshp;
							gold+=sgold;
							goldtotal+=sgold;
							scount++;
						}
					}
				}
				for (int i = 0; i < tx.length; i++)
				{
					if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>tx[i]&&shots[index][0]<tx[i]+20)&&(shots[index][1]>ty[i]&&shots[index][1]<ty[i]+20))||((shots[index][0]<tx[i]&shots[index][0]>tx[i]+20)&&(shots[index][1]<ty[i]&shots[index][1]>ty[i]+20)))
					{
						hit = true;
						tbar[i] = true;
						if(thp[i]>0)
						{
							thp[i]-=attack;
							damagetotal+=attack;
						}
						if(thp[i]<=0)
						{
							switch((int)(Math.random()*3))
							{
								case 0: tx[i]=(int)(Math.random()*(-1370)+(5680+mapx));
										ty[i]=(int)(Math.random()*(-1390)+(4280+mapy));break;
								case 1: tx[i]=(int)(Math.random()*(-4220)+(7100+mapx));
										ty[i]=(int)(Math.random()*(-1380)+(2880+mapy));break;
								case 2: tx[i]=(int)(Math.random()*(-7080)+(8530+mapx));
										ty[i]=(int)(Math.random()*(-1400)+(1470+mapy));break;
							}
							tri = true;
							drawCoin=true;
							tbar[i]=false;
							exp+=txp;
							exptotal+=txp;
							thp[i]=maxthp;
							gold+=tgold;
							goldtotal+=tgold;
							tcount++;
						}
					}
				}
				if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>sqxboss&&shots[index][0]<sqxboss+80)&&(shots[index][1]>sqyboss&&shots[index][1]<sqyboss+80))||((shots[index][0]<sqxboss&shots[index][0]>sqxboss+80)&&(shots[index][1]<sqyboss&shots[index][1]>sqyboss+80)))
				{
					hit = true;
					sqhit = true;
					if(sqbosshp>0)
					{
						bsTime = 0;
						sqbosshp-=attack;
						bosshealth = "Boss Health: "+sqbosshp+"/"+sqbossmaxhp;
						damagetotal+=attack;
					}
					if(sqbosshp<=0)
					{
						boss = true;
						drawCoin=true;
						sqxboss=100000;
						gold+=100*(10-lvl);
						goldtotal+=100*(10-lvl);
						bosscount++;
					}
				}
				if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>cxboss&&shots[index][0]<cxboss+80)&&(shots[index][1]>cyboss&&shots[index][1]<cyboss+80))||((shots[index][0]<cxboss&shots[index][0]>cxboss+80)&&(shots[index][1]<cyboss&shots[index][1]>cyboss+80)))
				{
					hit = true;
					chit = true;
					if(cbosshp>0)
					{
						bsTime = 0;
						cbosshp-=attack;
						bosshealth = "Boss Health: "+cbosshp+"/"+cbossmaxhp;
						damagetotal+=attack;
					}
					if(cbosshp<=0)
					{
						boss = true;
						drawCoin=true;
						cxboss=100000;
						gold+=100*(10-lvl);
						goldtotal+=100*(10-lvl);
						bosscount++;
					}
				}
				if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>sxboss&&shots[index][0]<sxboss+80)&&(shots[index][1]>syboss&&shots[index][1]<syboss+80))||((shots[index][0]<sxboss&shots[index][0]>sxboss+80)&&(shots[index][1]<syboss&shots[index][1]>syboss+80)))
				{
					hit = true;
					starhit = true;
					if(sbosshp>0)
					{
						bsTime = 0;
						sbosshp-=attack;
						bosshealth = "Boss Health: "+sbosshp+"/"+sbossmaxhp;
						damagetotal+=attack;
					}
					if(sbosshp<=0)
					{
						boss = true;
						drawCoin=true;
						sxboss=100000;
						gold+=100*(10-lvl);
						goldtotal+=100*(10-lvl);
						bosscount++;
					}
				}
				if((shoot||shots[index][0]!=xplayer+15&&shots[index][1]!=yplayer+15)&&((shots[index][0]>txboss&&shots[index][0]<txboss+80)&&(shots[index][1]>tyboss&&shots[index][1]<tyboss+80))||((shots[index][0]<txboss&shots[index][0]>txboss+80)&&(shots[index][1]<tyboss&shots[index][1]>tyboss+80)))
				{
					hit = true;
					thit = true;
					if(tbosshp>0)
					{
						bsTime = 0;
						tbosshp-=attack;
						bosshealth = "Boss Health: "+tbosshp+"/"+tbossmaxhp;
						damagetotal+=attack;
					}
					if(tbosshp<=0)
					{
						boss = true;
						drawCoin=true;
						txboss=100000;
						gold+=100*(10-lvl);
						goldtotal+=100*(10-lvl);
						bosscount++;
					}
				}
				//Draws the shot if true
				if((shots[index][0]>0&&shots[index][0]<500&&shots[index][1]>0&&shots[index][1]<500)&&!hit)
				{
					g.setColor(Color.blue);
					g.fillOval(shots[index][0]-5,shots[index][1]-5,sheight,swidth);
				}
				//Resets the value of the shot position to allow player to shoot again
				else if(shoot)
				{
					doneshots = 0;
					shotdone[index] = true;
					for (int q = 0; q < shotdone.length; q++)
						if(shotdone[q])
					doneshots++;
					hit = false;
					ready = true;
					{
						shots[index][0] = xplayer+15;
						shots[index][1] = yplayer+15;
					}
				}
				else if(autofire)
				{
					doneshots = 0;
					shotdone[index] = true;
					for (int q = 0; q < shotdone.length; q++)
							if(shotdone[q])
							doneshots++;
					hit = false;
					ready = true;
					{
						shots[index][0] = xplayer+15;
						shots[index][1] = yplayer+15;
					}
				}
				else
				{
					hit = false;
					shotdone[index] = true;
					ready = true;
					shots[index][0] = 1000000;
					shots[index][1] = 1000000;
					doneshots = 0;
					for (int q = 0; q < shotdone.length; q++)
						if(shotdone[q])
							doneshots++;
					if(doneshots==shotnum)
						shot.stop();
				}
			}
			g.setColor(Color.black);
			g.fillRect(xplayer,yplayer,pheight,pwidth);
			//Checks if a coin should be drawn
			if(drawCoin)
				goldIcon(g);
			//Draws the squares with hp bars if they are hit already
			for (int i = 0; i < sqx.length; i++)
			{
				g.setColor(Color.red);
				if(sqhp[i]>0)
					g.fillRect(sqx[i],sqy[i],20,20);
				if(sqbar[i])
				{
					g.setColor(Color.green);
					g.fillRect(sqx[i],sqy[i]-5,(int)(20-((double)20/maxsqhp*(maxsqhp-sqhp[i]))),3);
					g.setColor(Color.red);
					g.fillRect((int)(sqx[i]+(double)20/maxsqhp*sqhp[i]),sqy[i]-5,(int)(20-((double)20/maxsqhp*sqhp[i])),3);
					g.setColor(Color.black);
					g.drawRect(sqx[i],sqy[i]-5,20,3);
				}
			}
			//Draws the circles with hp bars if they are hit already
			for (int i = 0; i < cx.length; i++)
			{
				g.setColor(Color.red);
				if(chp[i]>0)
					g.fillOval(cx[i],cy[i],20,20);
				if(cbar[i])
				{
					g.setColor(Color.green);
					g.fillRect(cx[i],cy[i]-5,(int)(20-((double)20/maxchp*(maxchp-chp[i]))),3);
					g.setColor(Color.red);
					g.fillRect((int)(cx[i]+(double)20/maxchp*chp[i]),cy[i]-5,(int)(20-((double)20/maxchp*chp[i])),3);
					g.setColor(Color.black);
					g.drawRect(cx[i],cy[i]-5,20,3);
				}
			}
			//Draws the stars with hp bars if they are hit already
			for (int i = 0; i < sx.length; i++)
			{
				x1 = sx[i];
				y1 = sy[i];
				int[]starx={x1,x1+10,x1+15,x1+20,x1+30,x1+20,x1+25,x1+15,x1+5,x1+10};
				int[]stary={y1+10,y1+10,y1,y1+10,y1+10,y1+15,y1+25,y1+20,y1+25,y1+15};
				g.setColor(Color.blue);
				if(shp[i]>0)
					g.fillPolygon(starx,stary,10);
				if(sbar[i])
				{
					g.setColor(Color.green);
					g.fillRect(sx[i]+5,sy[i]-5,(int)(20-((double)20/maxshp*(maxshp-shp[i]))),3);
					g.setColor(Color.red);
					g.fillRect((int)(sx[i]+5+(double)20/maxshp*shp[i]),sy[i]-5,(int)(20-((double)20/maxshp*shp[i])),3);
					g.setColor(Color.black);
					g.drawRect(sx[i]+5,sy[i]-5,20,3);
				}
			}
			//Draws the triangles with hp bars if they are hit already
			for (int i = 0; i < tx.length; i++)
			{
				x2 = tx[i];
				y2 = ty[i];
				int[] trix={x2,x2+15,x2+30};
				int[] triy={y2+25,y2,y2+25};
				g.setColor(Color.blue);
				if(thp[i]>0)
					g.fillPolygon(trix,triy,3);
				if(tbar[i])
				{
					g.setColor(Color.green);
					g.fillRect(tx[i]+5,ty[i]-5,(int)(20-((double)20/maxthp*(maxthp-thp[i]))),3);
					g.setColor(Color.red);
					g.fillRect((int)(tx[i]+5+(double)20/maxthp*thp[i]),ty[i]-5,(int)(20-((double)20/maxthp*thp[i])),3);
					g.setColor(Color.black);
					g.drawRect(tx[i]+5,ty[i]-5,20,3);
				}
			}
			//Draws the square boss with hp bar if hit
			g.setColor(Color.red);
			if(sqbosshp>0)
				g.fillRect(sqxboss,sqyboss,80,80);
			if(sqhit)
			{
				g.setColor(Color.green);
				g.fillRect(sqxboss,sqyboss-5,(int)(80-((double)80/sqbossmaxhp*(sqbossmaxhp-sqbosshp))),3);
				g.setColor(Color.red);
				g.fillRect((int)(sqxboss+(double)80/sqbossmaxhp*sqbosshp),sqyboss-5,(int)(80-((double)80/sqbossmaxhp*sqbosshp)),3);
				g.setColor(Color.black);
				g.drawRect(sqxboss,sqyboss-5,80,3);
			}
			//Draws the circle boss with hp bar if hit
			g.setColor(Color.red);
			if(cbosshp>0)
				g.fillOval(cxboss,cyboss,80,80);
			if(chit)
			{
				g.setColor(Color.green);
				g.fillRect(cxboss,cyboss-5,(int)(80-((double)80/cbossmaxhp*(cbossmaxhp-cbosshp))),3);
				g.setColor(Color.red);
				g.fillRect((int)(cxboss+(double)80/cbossmaxhp*cbosshp),cyboss-5,(int)(80-((double)80/cbossmaxhp*cbosshp)),3);
				g.setColor(Color.black);
				g.drawRect(cxboss,cyboss-5,80,3);
			}
			//Draws the triangle boss with hp bar if hit
			g.setColor(Color.blue);
			x2 = txboss;
			y2 = tyboss;
			int[] trix={x2,x2+40,x2+80};
			int[] triy={y2+80,y2,y2+80};
			if(tbosshp>0)
				g.fillPolygon(trix,triy,3);
			if(thit)
			{
				g.setColor(Color.green);
				g.fillRect(txboss,tyboss-5,(int)(80-((double)80/tbossmaxhp*(tbossmaxhp-tbosshp))),3);
				g.setColor(Color.red);
				g.fillRect((int)(txboss+(double)80/tbossmaxhp*tbosshp),tyboss-5,(int)(80-((double)80/tbossmaxhp*tbosshp)),3);
				g.setColor(Color.black);
				g.drawRect(txboss,tyboss-5,80,3);
			}
			//Draws the star boss with hp bar if hit
			x1 = sxboss;
			y1 = syboss;
			int[]starx={x1,x1+30,x1+45,x1+60,x1+90,x1+60,x1+75,x1+45,x1+15,x1+30};
			int[]stary={y1+30,y1+30,y1,y1+30,y1+30,y1+45,y1+75,y1+60,y1+75,y1+45};
			g.setColor(Color.blue);
			if(sbosshp>0)
				g.fillPolygon(starx,stary,10);
			if(starhit)
			{
				g.setColor(Color.green);
				g.fillRect(sxboss+5,syboss-5,(int)(80-((double)80/sbossmaxhp*(sbossmaxhp-sbosshp))),3);
				g.setColor(Color.red);
				g.fillRect((int)(sxboss+5+(double)80/sbossmaxhp*sbosshp),syboss-5,(int)(80-((double)80/sbossmaxhp*sbosshp)),3);
				g.setColor(Color.black);
				g.drawRect(sxboss+5,syboss-5,80,3);
			}
			//Draws the stats on the right side of the applet
			g.setColor(Color.white);
			g.fillRect(505,0,195,500);
			g.setColor(Color.black);
			g.fillRect(500,0,5,500);
			g.setFont(new Font("Times New Roman",Font.PLAIN,12));
			g.drawString("Experience",570,65);
			g.drawString("Health",580,15);
			g.drawString("Map Position:("+Math.abs(mapx)+","+Math.abs(mapy)+")",520,120);
			g.drawString("Player Level: " + lvl, 520, 140);
			g.drawString("Weapon Level: " + shotnum, 520, 160);
			g.drawString("Attack: " + attack, 520, 180);
			g.drawString(bosshealth, 520,200);
			g.drawString("Gold: " + gold, 520, 280);
			g.drawString("Cost: " + hpcost, 520, 355);
			g.drawString("Cost: " + atkcost, 520, 400);
			g.drawString("Cost: " + wepcost, 520, 445);
			g.drawString("Cost: " + afcost, 520, 490);
			//Black border for hp bar
			g.fillRect(528,18,154,34);
			//Black border for xp bar
			g.fillRect(528,68,154,34);
			//Line under Upgrades
			g.fillRect(500,260,200,5);
			g.setColor(Color.white);
			//White background behind hp bar
			g.fillRect(530,20,150,30);
			//White background behind xp bar
			g.fillRect(530,70,150,30);
			g.setColor(Color.red);
			g.drawString(error, 520, 300);
			//Player hp bar
			if(playerhp>=0)
				g.fillRect(530,20,0+(int)(((double)150/pmaxhp)*playerhp),30);
			g.setColor(new Color(230,175,30));
			if(exp>=maxexp)
			{
				//Increases values of variables if player levels up
				int sqtemp,ctemp,stemp,ttemp;
				exp-=maxexp;
				maxexp+=25;
				lvl++;
				pmaxhp+=20;
				playerhp+=pmaxhp/2;
				if(playerhp>pmaxhp)
					playerhp=pmaxhp;
				maxsqhp=(int)(1.5*maxsqhp);
				maxchp=(int)(1.5*maxchp);
				maxshp=(int)(1.5*maxshp);
				maxthp=(int)(1.5*maxthp);
				sqtemp = sqbossmaxhp;
				ctemp = cbossmaxhp;
				stemp = sbossmaxhp;
				ttemp = tbossmaxhp;
				sqbossmaxhp+=maxsqhp*100;
				cbossmaxhp+=maxchp*100;
				sbossmaxhp+=maxshp*100;
				tbossmaxhp+=maxthp*100;
				//Increases hp of bosses
				if(sqbosshp>0)
					sqbosshp += sqbossmaxhp-sqtemp;
				if(cbosshp>0)
					cbosshp += cbossmaxhp-ctemp;
				if(sbosshp>0)
					sbosshp += sbossmaxhp-stemp;
				if(tbosshp>0)
					tbosshp += tbossmaxhp-ttemp;
				if(sqbosshp>sqbossmaxhp)
					sqbosshp=sqbossmaxhp;
				if(cbosshp>cbossmaxhp)
					cbosshp=cbossmaxhp;
				if(sbosshp>sbossmaxhp)
					sbosshp=sbossmaxhp;
				if(tbosshp>tbossmaxhp)
					tbosshp=tbossmaxhp;
				//Increases hp of small shapes
				for (int i = 0; i < sqhp.length; i++)
				{
					if(!sqbar[i])
						sqhp[i] = maxsqhp;
				}
				for (int i = 0; i < chp.length; i++)
				{
					if(!cbar[i])
						chp[i] = maxchp;
				}
				for (int i = 0; i < shp.length; i++)
				{
					if(!sbar[i])
						shp[i] = maxshp;
				}
				for (int i = 0; i < thp.length; i++)
				{
					if(!tbar[i])
						thp[i] = maxthp;
				}
				//Increase gold earned
				sqgold = maxsqhp*2;
				cgold = maxchp*2;
				sgold = maxshp*2;
				tgold = maxthp*2;
			}
			//Exp bar
			if(exp<=maxexp)
				g.fillRect(530,70,0+(int)(((double)150/maxexp)*exp),30);
			g.setColor(Color.black);
			g.drawString(""+playerhp+"/"+pmaxhp,580,40);
			g.drawString(""+exp +"/"+maxexp,580,90);
			//Draws upgrade information
			g.setFont(new Font("Times New Roman", Font.BOLD,20));
			g.drawString("Upgrades", 550, 250);
			//Draws pause box in center if game is paused
			if(pause)
			{
				g.fillRect(210,250,100,45);
				g.setColor(Color.red);
				g.drawString("PAUSED", 220, 280);
			}
			if((sqbosshp<=0&&cbosshp<=0&&sbosshp<=0&&tbosshp<=0)&&((xplayer>mapx+exitlocationx&&xplayer<mapx+exitlocationx+50&&yplayer>mapy+exitlocationy&&yplayer<mapy+exitlocationy+50)||(xplayer+30>mapx+exitlocationx&&xplayer+30<mapx+exitlocationx+50&&yplayer>mapy+exitlocationy&&yplayer<mapy+exitlocationy+50)||(xplayer+30>mapx+exitlocationx&&xplayer+30<mapx+exitlocationx+50&&yplayer+30>mapy+exitlocationy&&yplayer+30<mapy+exitlocationy+50)||(xplayer>mapx+exitlocationx&&xplayer<mapx+exitlocationx+50&&yplayer+30>mapy+exitlocationy&&yplayer+30<mapy+exitlocationy+50)))
			{
				win = true;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
			//Timer for showing boss hp for 3 seconds
			int bosstimer = (int)(bcTime-bsTime);
			if(3-bosstimer/1000==0)
				bosshealth="";
			//Boss Locators
			//Square
			g.setColor(Color.orange);
			if(sqbosshp>0)
			{
				if(sqxboss>=0&&sqxboss<=500&&sqyboss+80<=0)
					g.fillRect(sqxboss,0,10,10);
				else if(sqxboss>=0&&sqxboss<=500&&sqyboss>=500)
					g.fillRect(sqxboss,500-10,10,10);
				else if(sqyboss>=0&&sqyboss<=500&&sqxboss+80<=0)
					g.fillRect(0,sqyboss,10,10);
				else if(sqyboss>=0&&sqyboss<=500&&sqxboss>=500)
					g.fillRect(500-10,sqyboss,10,10);
				else if(sqxboss+80<0&&sqyboss+80<0)
					g.fillRect(0,0,10,10);
				else if(sqxboss+80<0&&sqyboss>0)
					g.fillRect(0,500-10,10,10);
				else if(sqxboss>0&&sqyboss+80<0)
					g.fillRect(500-10,0,10,10);
				else if(sqxboss>500&&sqyboss>500)
					g.fillRect(500-10,500-10,10,10);
			}
			//Circle
			g.setColor(Color.yellow);
			if(cbosshp>0)
			{
				if(cxboss>=0&&cxboss<=500&&cyboss+80<=0)
					g.fillRect(cxboss,0,10,10);
				else if(cxboss>=0&&cxboss<=500&&cyboss>=500)
					g.fillRect(cxboss,500-10,10,10);
				else if(cyboss>=0&&cyboss<=500&&cxboss+80<=0)
					g.fillRect(0,cyboss,10,10);
				else if(cyboss>=0&&cyboss<=500&&cxboss>=500)
					g.fillRect(500-10,cyboss,10,10);
				else if(cxboss+80<0&&cyboss+80<0)
					g.fillRect(0,0,10,10);
				else if(cxboss+80<0&&cyboss>0)
					g.fillRect(0,500-10,10,10);
				else if(cxboss>0&&cyboss+80<0)
					g.fillRect(500-10,0,10,10);
				else if(cxboss>500&&cyboss>500)
					g.fillRect(500-10,500-10,10,10);
			}
			//Star
			g.setColor(Color.cyan);
			if(sbosshp>0)
			{
				if(sxboss>=0&&sxboss<=500&&syboss+80<=0)
					g.fillRect(sxboss,0,10,10);
				else if(sxboss>=0&&sxboss<=500&&syboss>=500)
					g.fillRect(sxboss,500-10,10,10);
				else if(syboss>=0&&syboss<=500&&sxboss+80<=0)
					g.fillRect(0,syboss,10,10);
				else if(syboss>=0&&syboss<=500&&sxboss>=500)
					g.fillRect(500-10,syboss,10,10);
				else if(sxboss+80<0&&syboss+80<0)
					g.fillRect(0,0,10,10);
				else if(sxboss+80<0&&syboss>0)
					g.fillRect(0,500-10,10,10);
				else if(sxboss>0&&syboss+80<0)
					g.fillRect(500-10,0,10,10);
				else if(sxboss>500&&syboss>500)
					g.fillRect(500-10,500-10,10,10);
			}
			//Triangle
			g.setColor(Color.green);
			if(tbosshp>0)
			{
				if(txboss>=0&&txboss<=500&&tyboss+80<=0)
					g.fillRect(txboss,0,10,10);
				else if(txboss>=0&&txboss<=500&&tyboss>=500)
					g.fillRect(txboss,500-10,10,10);
				else if(tyboss>=0&&tyboss<=500&&txboss+80<=0)
					g.fillRect(0,tyboss,10,10);
				else if(tyboss>=0&&tyboss<=500&&txboss>=500)
					g.fillRect(500-10,tyboss,10,10);
				else if(txboss+80<0&&tyboss+80<0)
					g.fillRect(0,0,10,10);
				else if(txboss+80<0&&tyboss>0)
					g.fillRect(0,500-10,10,10);
				else if(txboss>0&&tyboss+80<0)
					g.fillRect(500-10,0,10,10);
				else if(txboss>500&&tyboss>500)
					g.fillRect(500-10,500-10,10,10);
			}
			//Marker Outlines
			//Square
			g.setColor(Color.black);
			if(sqbosshp>0)
			{
				if(sqxboss>=0&&sqxboss<=500&&sqyboss+80<=0)
					g.drawRect(sqxboss,0,10,10);
				else if(sqxboss>=0&&sqxboss<=500&&sqyboss>=500)
					g.drawRect(sqxboss,500-10,10,10);
				else if(sqyboss>=0&&sqyboss<=500&&sqxboss+80<=0)
					g.drawRect(0,sqyboss,10,10);
				else if(sqyboss>=0&&sqyboss<=500&&sqxboss>=500)
					g.drawRect(500-10,sqyboss,10,10);
				else if(sqxboss+80<0&&sqyboss+80<0)
					g.drawRect(0,0,10,10);
				else if(sqxboss+80<0&&sqyboss>0)
					g.drawRect(0,500-10,10,10);
				else if(sqxboss>0&&sqyboss+80<0)
					g.drawRect(500-10,0,10,10);
				else if(sqxboss>500&&sqyboss>500)
					g.drawRect(500-10,500-10,10,10);
			}
			//Circle
			if(cbosshp>0)
			{
				if(cxboss>=0&&cxboss<=500&&cyboss+80<=0)
					g.drawRect(cxboss,0,10,10);
				else if(cxboss>=0&&cxboss<=500&&cyboss>=500)
					g.drawRect(cxboss,500-10,10,10);
				else if(cyboss>=0&&cyboss<=500&&cxboss+80<=0)
					g.drawRect(0,cyboss,10,10);
				else if(cyboss>=0&&cyboss<=500&&cxboss>=500)
					g.drawRect(500-10,cyboss,10,10);
				else if(cxboss+80<0&&cyboss+80<0)
					g.drawRect(0,0,10,10);
				else if(cxboss+80<0&&cyboss>0)
					g.drawRect(0,500-10,10,10);
				else if(cxboss>0&&cyboss+80<0)
					g.drawRect(500-10,0,10,10);
				else if(cxboss>500&&cyboss>500)
					g.drawRect(500-10,500-10,10,10);
			}
			//Star
			if(sbosshp>0)
			{
				if(sxboss>=0&&sxboss<=500&&syboss+80<=0)
					g.drawRect(sxboss,0,10,10);
				else if(sxboss>=0&&sxboss<=500&&syboss>=500)
					g.drawRect(sxboss,500-10,10,10);
				else if(syboss>=0&&syboss<=500&&sxboss+80<=0)
					g.drawRect(0,syboss,10,10);
				else if(syboss>=0&&syboss<=500&&sxboss>=500)
					g.drawRect(500-10,syboss,10,10);
				else if(sxboss+80<0&&syboss+80<0)
					g.drawRect(0,0,10,10);
				else if(sxboss+80<0&&syboss>0)
					g.drawRect(0,500-10,10,10);
				else if(sxboss>0&&syboss+80<0)
					g.drawRect(500-10,0,10,10);
				else if(sxboss>500&&syboss>500)
					g.drawRect(500-10,500-10,10,10);
			}
			//Triangle
			if(tbosshp>0)
			{
				if(txboss>=0&&txboss<=500&&tyboss+80<=0)
					g.drawRect(txboss,0,10,10);
				else if(txboss>=0&&txboss<=500&&tyboss>=500)
					g.drawRect(txboss,500-10,10,10);
				else if(tyboss>=0&&tyboss<=500&&txboss+80<=0)
					g.drawRect(0,tyboss,10,10);
				else if(tyboss>=0&&tyboss<=500&&txboss>=500)
					g.drawRect(500-10,tyboss,10,10);
				else if(txboss+80<0&&tyboss+80<0)
					g.drawRect(0,0,10,10);
				else if(txboss+80<0&&tyboss>0)
					g.drawRect(0,500-10,10,10);
				else if(txboss>0&&tyboss+80<0)
					g.drawRect(500-10,0,10,10);
				else if(txboss>500&&tyboss>500)
					g.drawRect(500-10,500-10,10,10);
			}
			this.requestFocus();
		}
		//Draws a gold coin slowly rising from player && increases gold that player has
		public void goldIcon(Graphics g)
		{
			int temp = 0;
			int dx = xplayer+15 - (int)(50 * Math.cos(0.005 * Math.PI * gTime));
			int dy = yplayer+15 - (int)(25 * Math.sin(0.02 * Math.PI * gTime));
			int r = 15;
			dx = xplayer+25;
			if(gTime%25==0&&gTime!=0)
			{
				gTime = 0;
				drawCoin=false;
				sq = cir = st = tri = boss = false;
			}
			if(up)
				dy-=10;
			if(down)
				dy+=10;
			if(left)
				dx-=10;
			if(right)
				dx+=10;
			if(sq)
				temp = sqgold;
			if(cir)
				temp = cgold;
			if(st)
				temp = sgold;
			if(tri)
				temp = tgold;
			if(boss)
				temp = 100*(10-lvl);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.setColor(Color.black);
			if(drawCoin)
				g.drawString("+"+temp,dx - r + 15, dy - r + 15);
			if(drawCoin)
				g.drawImage(coin,dx - r, dy - r, 15, 15,this);
		}
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			/*if(command.equals("Inventory"))
			{
				timer.stop();
				cards.show(c,"Inventory");
			}*/
			//Checks if player wants to upgrade health
			if(command.equals("Health + 5")&&!pause&&(gold>=hpcost||infinitegold))
			{
				pmaxhp+=5;
				playerhp+=5;
				if(!infinitegold)
				{
					gold-=hpcost;
					goldspent+=hpcost;
				}
				//hpcost=(int)(1.5*hpcost);
				hpcost+=10;
			}
			else if(command.equals("Health + 5")&&gold<hpcost&&!pause)
			{
				error = "Not enough gold.";
				nogold = true;
			}
			//Checks if player wants to upgrade attack
			if(command.equals("Attack + 1")&&!pause&&(gold>=atkcost||infinitegold))
			{
				attack++;
				if(!infinitegold)
				{
					gold-=atkcost;
					goldspent+=atkcost;
				}
				//atkcost=(int)(1.5*atkcost);
				atkcost+=20;
			}
			else if(command.equals("Attack + 1")&&gold<atkcost&&!pause)
			{
				error = "Not enough gold.";
				nogold = true;
			}
			//Checks if player wants to upgrade weapon
			if(command.equals("Weapon + 1")&&!pause&&(gold>=wepcost||infinitegold))
			{
				shotnum++;
				if(!infinitegold)
				{
					gold-=wepcost;
					goldspent+=wepcost;
				}
				//wepcost=(int)(1.5*wepcost);
				wepcost+=25;
				if(shotnum==63)
				{
					wepcost=0;
					wepbutton.setEnabled(false);
				}
			}
			else if(command.equals("Weapon + 1")&&gold<wepcost&&!pause)
			{
				error = "Not enough gold.";
				nogold = true;
			}
			//Checks if player wants to purchase autofire
			if(command.equals("Autofire")&&!pause&&(gold>=afcost||infinitegold))
			{
				buyautofire = true;
				if(!infinitegold)
				{
					gold-=afcost;
					goldspent+=afcost;
				}
				afcost=0;
				afbutton.setEnabled(false);
			}
			else if(command.equals("Autofire")&&gold<afcost&&!pause)
			{
				error = "Not enough gold.";
				nogold = true;
			}
			repaint();
		}
	}
	class MainMenu extends JPanel implements ActionListener
	{
		//Declaring variables
		private JButton newgamebutton, instructionbutton, close;
		private JPanel instructions,loadingpanel;
		private JTextArea text;
		private JScrollPane i;
		private JProgressBar loadbar;
		private Timer movingshapes = new Timer(100,new MoveShape());
		private int[][] squares,circles,rsq,rc,rs,rt;
		private int[] triangles,stars;
		private int mtx,mty,msx,msy,time;
		private long loadstart = 0,currentloading = 0;
		private boolean trackloading;
		
		MainMenu()
		{
			//Sets default values
			this.setLayout(null);
			this.setBackground(Color.blue);
			trackloading = true;
			squares = new int[9][2];
			circles = new int[9][2];
			triangles = new int[13];
			stars = new int[13];
			rsq = new int[2][2];
			rc = new int[2][2];
			rs = new int[2][2];
			rt = new int[2][2];
			rt[0][0] = 80;
			rt[0][1] = 60;
			rt[1][0] = 580;
			rt[1][1] = 60;
			rsq[0][0] = 60;
			rsq[0][1] = 80;
			rsq[1][0] = 560;
			rsq[1][1] = 80;
			rs[0][0] = 100;
			rs[0][1] = 80;
			rs[1][0] = 600;
			rs[1][1] = 80;
			rc[0][0] = 80;
			rc[0][1] = 100;
			rc[1][0] = 580;
			rc[1][1] = 100;
			for (int i = 0; i < squares.length; i++)
			{
				squares[i][0]=10;
				squares[i][1]=440;
			}
			for (int i = 0; i < circles.length; i++)
			{
				circles[i][0]=670;
				circles[i][1]=40;
			}
			for (int i = 0; i < triangles.length; i++)
				triangles[i]=40;
			for (int i = 0; i < stars.length; i++)
				stars[i]=630;
			//Creates & adds components
			loadingpanel = new Loading();
			loadingpanel.setBounds(0,0,700,500);
			loadingpanel.setLayout(null);
			this.add(loadingpanel);
			loadbar = new JProgressBar(0,65);
			loadbar.setBounds(50,275,600,20);
			loadingpanel.add(loadbar);
			newgamebutton = new JButton("New Game");
			newgamebutton.addActionListener(this);
			newgamebutton.setBounds(150,400,100,30);
			newgamebutton.setVisible(false);
			this.add(newgamebutton);
			contgamebutton = new JButton("Continue Game");
			contgamebutton.addActionListener(this);
			contgamebutton.setBounds(270,400,130,30);
			contgamebutton.setEnabled(false);
			this.add(contgamebutton);
			instructionbutton = new JButton("Instructions");
			instructionbutton.addActionListener(this);
			instructionbutton.setBounds(420,400,120,30);
			instructionbutton.setVisible(false);
			this.add(instructionbutton);
			instructions = new JPanel();
			instructions.setLayout(new BorderLayout());
			instructions.setBounds(150,50,400,400);
			instructions.setVisible(false);
			this.add(instructions);
			//Instructions
			text = new JTextArea(
			"You are trapped in a world full of geometric shapes. Since it is a 2D world, you have "+
			"been transformed into a square. To escape, you have to defeat the 4 shape bosses located "+
			"at the north, south, east, and west ends of the map. The exit will appear where you started."+
			"\n\nAs you level up, the shapes will gain more health. Avoid running into the enemies. "+
			"The bosses kill you immediately if you run into them. When you are hit by an enemy, "+
			"you will be given a math problem to solve. You need to know Algebra I and Algebra II / "+
			"Trigonometry to correctly answer each question. A correct answer will regenerate 5 hp, "+
			"but an incorrect answer will deal 5 additional damage. You will also lose hp if the time "+
			"runs out for the question. Each enemy gives gold when you kill them, which can be used to "+
			"upgrade your health and damage. Every time you level up, you regenerate half your max hp."+
			"\n\nControls\nUse WASD to move around. "+
			"Shoot at the shapes with the mouse. If you are dragging the mouse when a shot hits an enemy, "+
			"only one of them will hurt the enemy and the rest will disappear. After you buy the "+
			"autofire upgrade, toggle it with control. If your weapon level is 2 or higher, the "+
			"shots can change direction when one of them hits an enemy or goes out of the screen."+"\nPress p to pause the game and escape to return to the main menu.\n\nMap Size: 9500x9500\n"+
			"Enemies(Initial Stats):\n\nSquare:\tHealth: 2\n\tGold: 4\n\tBoss Health:2000\n\nCircle:\t"+
			"Health: 4\n\tGold:8\n\tBoss Health:4000\n\nStar:\tHealth: 6\n\tGold:12\n\tBoss Health: 6000"+
			"\n\nTriangle:\tHealth: 10\n\tGold: 20\n\tBoss Health: 10000"
			);
			text.setMargin(new Insets(10,10,10,10));
			text.setEditable(false);
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			i = new JScrollPane(text);
			instructions.add(i, BorderLayout.CENTER);
			close = new JButton("Close");
			close.addActionListener(this);
			instructions.add(close, BorderLayout.SOUTH);
			movingshapes.start();
		}
		public void paintComponent(Graphics g)
		{
			//Draws graphics for main menu
			super.paintComponent(g);
			g.setColor(Color.black);
			g.fillRect(40,50,620,400);
			g.drawImage(title,130,70,this);
			g.setColor(Color.orange);
			for (int i = 0; i < squares.length; i++)
				g.fillRect(squares[i][0],squares[i][1],20,20);
			g.setColor(Color.yellow);
			for (int i = 0; i < circles.length; i++)
				g.fillOval(circles[i][0],circles[i][1],20,20);
			g.setColor(Color.green);
			for (int i = 0; i < triangles.length; i++)
			{
				mtx = triangles[i];
				mty = 10;
				int[] trix={mtx,mtx+15,mtx+30};
				int[] triy={mty+25,mty,mty+25};
				g.fillPolygon(trix,triy,3);
			}
			g.setColor(Color.cyan);
			for (int i = 0; i < stars.length; i++)
			{
				msx = stars[i];
				msy = 465;
				int[]starx={msx,msx+10,msx+15,msx+20,msx+30,msx+20,msx+25,msx+15,msx+5,msx+10};
				int[]stary={msy+10,msy+10,msy,msy+10,msy+10,msy+15,msy+25,msy+20,msy+25,msy+15};
				g.fillPolygon(starx,stary,10);
			}
			//Circling Triangle
			rt[0][0] = 80 - (int)(30 * Math.cos(0.05 * Math.PI * time+1));
			rt[0][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time+1));
			rt[1][0] = 595 - (int)(30 * -Math.cos(0.05 * Math.PI * time+1));
			rt[1][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time+1));
			//Circling Square
			rsq[0][0] = 80 - (int)(30 * Math.cos(0.05 * Math.PI * time-0.5));
			rsq[0][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time-0.5));
			rsq[1][0] = 595 - (int)(30 * -Math.cos(0.05 * Math.PI * time-0.5));
			rsq[1][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time-0.5));
			//Circling Star
			rs[0][0] = 80 - (int)(30 * Math.cos(0.05 * Math.PI * time-2));
			rs[0][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time-2));
			rs[1][0] = 595 - (int)(30 * -Math.cos(0.05 * Math.PI * time-2));
			rs[1][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time-2));
			//Circling Circle
			rc[0][0] = 80 - (int)(30 * Math.cos(0.05 * Math.PI * time-3.5));
			rc[0][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time-3.5));
			rc[1][0] = 595 - (int)(30 * -Math.cos(0.05 * Math.PI * time-3.5));
			rc[1][1] = 100 - (int)(30 * Math.sin(0.05 * Math.PI * time-3.5));
			g.setColor(Color.orange);
			g.fillRect(rsq[0][0],rsq[0][1],20,20);
			g.fillRect(rsq[1][0],rsq[1][1],20,20);
			g.setColor(Color.yellow);
			g.fillOval(rc[0][0],rc[0][1],20,20);
			g.fillOval(rc[1][0],rc[1][1],20,20);
			g.setColor(Color.green);
			mtx = rt[0][0];
			mty = rt[0][1];
			int[] trix={mtx,mtx+15,mtx+30};
			int[] triy={mty+25,mty,mty+25};
			g.fillPolygon(trix,triy,3);
			mtx = rt[1][0];
			mty = rt[1][1];
			int[] trix2={mtx,mtx+15,mtx+30};
			int[] triy2={mty+25,mty,mty+25};
			g.fillPolygon(trix2,triy2,3);
			g.setColor(Color.cyan);
			msx = rs[0][0];
			msy = rs[0][1];
			int[]starx={msx,msx+10,msx+15,msx+20,msx+30,msx+20,msx+25,msx+15,msx+5,msx+10};
			int[]stary={msy+10,msy+10,msy,msy+10,msy+10,msy+15,msy+25,msy+20,msy+25,msy+15};
			g.fillPolygon(starx,stary,10);
			msx = rs[1][0];
			msy = rs[1][1];
			int[]starx2={msx,msx+10,msx+15,msx+20,msx+30,msx+20,msx+25,msx+15,msx+5,msx+10};
			int[]stary2={msy+10,msy+10,msy,msy+10,msy+10,msy+15,msy+25,msy+20,msy+25,msy+15};
			g.fillPolygon(starx2,stary2,10);
			//Square + Circle Boss w/ eyes
			g.setColor(Color.red);
			g.fillRect(150,200,80,80);
			g.drawImage(eyes,160,210,60,40,this);
			g.fillOval(470,200,80,80);
			g.drawImage(eyes,480,210,60,40,this);
			//Triangle + Star Boss w/ eyes
			g.setColor(Color.blue);
			mtx = 260;
			mty = 200;
			int[] trix3={mtx,mtx+40,mtx+80};
			int[] triy3={mty+80,mty,mty+80};
			g.fillPolygon(trix3,triy3,3);
			g.drawImage(eyes,270,210,60,40,this);
			msx = 360;
			msy = 205;
			int[]starx3={msx,msx+30,msx+45,msx+60,msx+90,msx+60,msx+75,msx+45,msx+15,msx+30};
			int[]stary3={msy+30,msy+30,msy,msy+30,msy+30,msy+45,msy+75,msy+60,msy+75,msy+45};
			g.fillPolygon(starx3,stary3,10);
			g.drawImage(eyes,375,210,60,40,this);
			//Player
			g.setColor(Color.white);
			g.drawRect(344,340,31,31);
			g.drawLine(365,342,365,352);
			g.drawLine(367,344,367,354);
			g.drawLine(369,342,369,352);
		}
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			//Checks which button is pressed
			if(command.equals("New Game"))
			{
				//Resets everything to default values
				timer.start();
				moving.start();
				cards.show(c, "GamePanel");
				damagetotal = exptotal = damagetaken = canswer = tquestion = goldspent = 0;
				buyautofire = infinitegold = infinitehp = false;
				shots = new int[1][2];
				shotdone = new boolean[1];
				shotnum = 1;
				dist = tan = degree = 0.0;
				doneshots = 0;
				autofire = false;
				coinx = coiny = 0;
				answer = false;
				drawCoin = false;
				gTime = 0;
				sqcount = ccount = scount = tcount = bosscount = 0 ;
				win = pause = nogold = false;
				up = down = left = right = false;
				errortime = 2;
				res = "";
				error= "";
				bosshealth = "";
				hpcost = 20;
				atkcost = 10;
				wepcost = 30;
				afcost = 1000;
				gold = 0;
				exitlocationx = 4975;
				exitlocationy = 4975;
				sqxp = 1;
				cxp = 2;
				sxp = 4;
				txp = 5;
				sqbossmaxhp = 2000;
				cbossmaxhp = 4000;
				sbossmaxhp = 6000;
				tbossmaxhp = 10000;
				sqbosshp = sqbossmaxhp;
				cbosshp = cbossmaxhp;
				sbosshp = sbossmaxhp;
				tbosshp = tbossmaxhp;
				movement = true;
				quesnum = (int)(Math.random()*(education.length));
				attack = lvl = 1;
				starttime = curtime = gctime = gstime = mcTime = msTime = recordCTime = recordSTime = 0;
				bsTime = bcTime = 0;
				timelimit = 15;
				maxsqhp=2;
				maxchp=4;
				maxshp=6;
				maxthp=10;
				sqgold = maxsqhp*2;
				cgold = maxchp*2;
				sgold = maxshp*2;
				tgold = maxthp*2;
				playerhp = pmaxhp = 100;
				exp = 0;
				maxexp = 25;
				mapx = mapy = -4750;
				sqx = new int[300];
				sqy = new int[300];
				cx = new int[300];
				cy = new int[300];
				sx = new int[300];
				sy = new int[300];
				tx = new int[300];
				ty = new int[300];
				sqhp = new int [300];
				chp = new int[300];
				shp = new int[300];
				thp = new int[300];
				sqbar = new boolean[300];
				cbar = new boolean[300];
				sbar = new boolean[300];
				tbar = new boolean[300];
				for (int i = 0; i < sqhp.length; i++)
					sqhp[i] = maxsqhp;
				for (int i = 0; i < chp.length; i++)
					chp[i] = maxchp;
				for (int i = 0; i < shp.length; i++)
					shp[i] = maxshp;
				for (int i = 0; i < thp.length; i++)
					thp[i] = maxthp;
				for (int i = 0; i < sqx.length; i++)
					switch((int)(Math.random()*3))
					{
					case 0: sqx[i]=(int)(Math.random()*(-1150)+(1370+mapx));
							sqy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
					case 1: sqx[i]=(int)(Math.random()*(-1380)+(2780+mapx));
							sqy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
					case 2: sqx[i]=(int)(Math.random()*(-1390)+(4210+mapx));
							sqy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
					}
				for (int i = 0; i < cx.length; i++)
					switch((int)(Math.random()*3))
					{
						case 0: cx[i]=(int)(Math.random()*(1400)+(5780+mapx));
								cy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
						case 1: cx[i]=(int)(Math.random()*(1380)+(7230+mapx));
								cy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
						case 2: cx[i]=(int)(Math.random()*(1390)+(8660+mapx));
								cy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
					}
				for (int i = 0; i < sx.length; i++)
					switch((int)(Math.random()*3))
					{
						case 0: sx[i]=(int)(Math.random()*(-1370)+(5630+mapx));
								sy[i]=(int)(Math.random()*(1400)+(5780+mapy));break;
						case 1: sx[i]=(int)(Math.random()*(-4220)+(7050+mapx));
								sy[i]=(int)(Math.random()*(1380)+(7130+mapy));break;
						case 2: sx[i]=(int)(Math.random()*(-7080)+(8480+mapx));
								sy[i]=(int)(Math.random()*(1390)+(8560+mapy));break;
					}
				for (int i = 0; i < tx.length; i++)
					switch((int)(Math.random()*3))
					{
						case 0: tx[i]=(int)(Math.random()*(-1370)+(5680+mapx));
								ty[i]=(int)(Math.random()*(-1390)+(4280+mapy));break;
						case 1: tx[i]=(int)(Math.random()*(-4220)+(7100+mapx));
								ty[i]=(int)(Math.random()*(-1380)+(2880+mapy));break;
						case 2: tx[i]=(int)(Math.random()*(-7080)+(8530+mapx));
								ty[i]=(int)(Math.random()*(-1400)+(1470+mapy));break;
					}
				items = new int[5][10];
				equip = new int[4];
				xplayer = yplayer = 250;
				mx = my = 300;
				ready = true;
				pheight = 30;
				pwidth = 30;
				sheight = swidth = 10;
				edgex = edgey = false;
				items[0][0] = 999;
				items[4][3] = 998;
				equip[0]=999;
				sqxboss = -4250;
				sqyboss = 250;
				sxboss = 250;
				syboss = 4250;
				cxboss = 4250;
				cyboss = 250;
				txboss = 250;
				tyboss = -4250;
				atkbutton.setEnabled(true);
				hpbutton.setEnabled(true);
				afbutton.setEnabled(true);
				wepbutton.setEnabled(true);
				question.setVisible(false);
			}
			if(command.equals("Continue Game"))
			{
				timer.start();
				moving.start();
				movement = true;
				cards.show(c, "GamePanel");
				//cards.show(c, "EndScreen");
			}
			if(command.equals("Instructions"))
			{
				//Shows instructions
				instructions.setVisible(true);
				newgamebutton.setVisible(false);
				contgamebutton.setVisible(false);
				instructionbutton.setVisible(false);
			}
			if(command.equals("Close"))
			{
				instructions.setVisible(false);
				newgamebutton.setVisible(true);
				contgamebutton.setVisible(true);
				instructionbutton.setVisible(true);
			}
		}
		//Timer for moving shapes on front screen
		class MoveShape implements ActionListener
		{
			int pos = 0;
			public void actionPerformed(ActionEvent e)
			{
				loadbar.setValue(pos);
				loadbar.repaint();
				pos++;
				if(loadstart==0)
					loadstart = e.getWhen();
				currentloading = e.getWhen();
				if((double)(currentloading-loadstart)/1000>=7.1&&trackloading)
				{
					loadingpanel.setVisible(false);
					newgamebutton.setVisible(true);
					instructionbutton.setVisible(true);
					trackloading = false;
				}
				time++;
				for (int i = 0; i < squares.length; i++)
				{
					if(i==0)
						squares[i][1]-=10;
					else
						if((squares[i-1][1]<squares[i][1]-50)||(squares[i-1][1]>squares[squares.length-1][1]))
							squares[i][1]-=10;
					if(squares[i][1]+20<=0)
						squares[i][1]=440;
				}
				for (int i = 0; i < circles.length; i++)
				{
					if(i==0)
						circles[i][1]+=10;
					else
						if((circles[i-1][1]>circles[i][1]+50)||(circles[i-1][1]<circles[circles.length-1][1]))
							circles[i][1]+=10;
					if(circles[i][1]>=500)
						circles[i][1]=40;
				}
				for (int i = 0; i < triangles.length; i++)
				{
					if(i==0)
						triangles[i]+=10;
					else
						if((triangles[i-1]>triangles[i]+50)||(triangles[i-1]<triangles[triangles.length-1]))
							triangles[i]+=10;
					if(triangles[i]>=700)
						triangles[i]=40;
				}
				for (int i = 0; i < stars.length; i++)
				{
					if(i==0)
						stars[i]-=10;
					else
						if((stars[i-1]<stars[i]-50)||(stars[i-1]>stars[stars.length-1]))
							stars[i]-=10;
					if(stars[i]+30<=0)
						stars[i]=630;
				}
				loadingpanel.repaint();
				menu.repaint();
			}
		}
		//Loading panel before game starts
		class Loading extends JPanel
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.black);
				g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
				int loaddots = (int)((currentloading-loadstart)/1000);
				switch(loaddots%3)
				{
					case 0: g.drawString("Loading.",300,250);break;
					case 1: g.drawString("Loading..",300,250);break;
					case 2: g.drawString("Loading...",300,250);break;
				}
			}
		}
	}
	//Not implemented into the game, not used at all during game play
	class Inventory extends JPanel implements ActionListener,MouseListener,MouseMotionListener
	{
		JButton closex;
		JLabel itemname, atkarmor;
		boolean entered,release,drag,ipick, epick;
		public Inventory()
		{
			entered = true;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.setLayout(null);
			closex = new JButton("x");
			closex.addActionListener(this);
			closex.setBounds(659,0,41,20);
			this.add(closex);
			itemname = new JLabel("Item Name");
			itemname.setForeground(Color.black);
			itemname.setBounds(245,50,400,50);
			itemname.setFont(new Font("Arial", Font.PLAIN, 24));
			this.add(itemname);
			atkarmor = new JLabel("Attack: 100-200");
			atkarmor.setForeground(Color.black);
			atkarmor.setBounds(400,100,110,20);
			this.add(atkarmor);
		}
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			if(command.equals("X"))
				cards.show(c, "GamePanel");
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.black);
			g.fillRect(10,10,200,480);
			g.fillRect(220,10,470,480);
			g.setColor(Color.white);
			g.fillRect(230,20,450,460);
			g.fillRect(20,20,180,460);
			g.setColor(Color.red);
			g.fillRect(230,20,450,460);
			g.setColor(Color.black);
			g.fillRect(20,240,180,5);
			g.fillRect(230,240,450,5);
			if(!entered)
			{
				if(ipick)
					switch(items[yitem][xitem])
					{
						case 999: image = map;break;
						case 998: image = imageg;break;
						case 0: image = null; break;
					}
				if(epick)
					switch(equip[xequip])
					{
						case 999: image = map;break;
						case 998: image = imageg;break;
						case 0: image = null; break;
					}
			}
			g.drawImage(image,248,93,130,130,this);
			for (int r = 0; r < items.length; r++)
				for (int c = 0; c < items[r].length; c++)
				{
					g.setColor(Color.black);
					g.drawRect(235+c*45,255+r*45,35,35);
					g.drawRect(236+c*45,256+r*45,33,33);
					switch(items[r][c])
					{
						case 999: image = map;break;
						case 998: image = imageg;break;
						case 0: image = null; break;
					}
					g.drawImage(image, 237+c*45,257+r*45, 32,32, this);
				}
			g.setColor(Color.black);
			g.drawRect(245,90,135,135);
			g.drawRect(246,91,133,133);
			g.drawRect(247,92,131,131);
			g.drawString("Head",28,433);
			g.drawString("Body",75,433);
			g.drawString("Foot",120,433);
			g.drawString("Weapon",154,433);
			for (int r = 0; r < equip.length; r++)
			{
				g.drawRect(28+r*43,435,35,35);
				g.drawRect(29+r*43,436,33,33);
				switch(equip[r])
				{
					case 999: image = map; break;
					case 998: image = imageg; break;
					case 0: image = null; break;
				}
				g.drawImage (image, 30+r*43, 437,32,32,this);
			}
			if(!release)
			{
				if(ipick)
					switch(items[yitem][xitem])
					{
						case 999: image = map;break;
						case 998: image = imageg;break;
						case 0: image = null; break;
					}
				if(epick)
					switch(equip[xequip])
					{
						case 999: image = map;break;
						case 998: image = imageg;break;
						case 0: image = null; break;
					}
				if(!drag)
					tempimage = image;
				if(drag)
					image = tempimage;
				g.drawImage(image,xdrag-16,ydrag-16,32,32,this);
			}
		}
		public void mouseClicked (MouseEvent e) {}
		public void mousePressed (MouseEvent e)
		{
			entered = release = false;
			xmouse = e.getX();
			ymouse = e.getY();
			xdrag = xmouse;
			ydrag = ymouse;
			if(((double)(xmouse-235)/45>0&&(double)(xmouse-235)/45-(xmouse-235)/45<0.8)&&((double)(ymouse-255)/45>0&&(double)(ymouse-255)/45-(ymouse-255)/45<0.8))
			{
				if((xmouse-235)/45<5)
				{
					xitem = (xmouse-235)/45;
					ipick = true;
					epick = false;
				}
				if((ymouse-255)/45<5)
				{
					yitem = (ymouse-255)/45;
					ipick = true;
					epick = false;
				}
				switch(items[yitem][xitem])
				{
					case 999:atkarmor.setText("Attack: 100-200");break;
					case 998:atkarmor.setText("Armor: 100");break;
				}
				repaint();
			}
			if(((double)(xmouse-28)/43>0&&(double)(xmouse-28)/43-(xmouse-28)/43<0.8))
			{
				if((xmouse-28)/43<4)
				{
					epick = true;
					ipick = false;
					xequip = (xmouse-28)/43;
				}
				repaint();
			}
		}
		public void mouseReleased (MouseEvent e)
		{
			int ixtemp, iytemp;
			xrelease = e.getX();
			yrelease = e.getY();
			release = true;
			drag = false;
			if(((double)(xrelease-235)/45>0&&(double)(xrelease-235)/45-(xrelease-235)/45<0.8)&&((double)(yrelease-255)/45>0&&(double)(yrelease-255)/45-(yrelease-255)/45<0.8))
			{
				if(ipick)
				{
					ixtemp = xitem;
					iytemp = yitem;
					if((xrelease-235)/45<10)
						xitem = (xrelease-235)/45;
					if((yrelease-255)/45<5)
						yitem = (yrelease-255)/45;
					temp = items[yitem][xitem];
					items[yitem][xitem] = dragitem;
					items[iytemp][ixtemp] = temp;
					repaint();
				}
				if(epick)
				{
					if((xrelease-235)/45<10)
						xitem = (xrelease-235)/45;
					if((yrelease-255)/45<5)
						yitem = (yrelease-255)/45;
					temp = items[yitem][xitem];
					items[yitem][xitem] = dragitem;
					equip[xequip] = temp;
					repaint();
				}
			}
			if(((double)(xrelease-28)/43>0&&(double)(xrelease-28)/43-(xrelease-28)/43<0.8))
			{
				if((xrelease-28)/43<4)
				{
					if(ipick)
					{
						xequip = (xrelease-28)/43;
						temp = equip[xequip];
						equip[xequip] = dragitem;
						items[yitem][xitem] = temp;
						repaint();
					}
					if(epick)
					{
						ixtemp = xequip;
						xequip = (xrelease-28)/43;
						temp = equip[xequip];
						equip[xequip] = dragitem;
						equip[ixtemp] = temp;
						repaint();
					}
				}
			}
			repaint();
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mouseDragged(MouseEvent e)
		{
			if(!drag)
			{
				if((xmouse>230 && xmouse< 680)&&(ymouse > 245 && ymouse < 480))
				{
					ipick = true;
					epick = false;
					dragitem = items[yitem][xitem];
				}
				if (xmouse > 28 && xmouse < 192 && ymouse > 435 && ymouse < 470)
				{
					epick = true;
					ipick = false;
					dragitem = equip[xequip];
				}
				drag = true;
			}
			xdrag = e.getX();
			ydrag = e.getY();
			repaint();
		}
		public void mouseMoved(MouseEvent e) {}
	}
	public void mouseClicked (MouseEvent e) {}
	public void mousePressed (MouseEvent e)
	{
		if(movement)
		{
			//Gets mouse position for shooting the shot
			xpos = e.getX();
			ypos = e.getY();
			if(ready&&!autofire)
			{
				doneshots = 0;
				shots = new int[shotnum][2];
				shotdone =new boolean[shotnum];
				shot.start();
				shoot =true;
				ready =false;
				for (int i= 0; i < shots.length; i++)
				{
					shots[i][0] = xplayer+15;
					shots[i][1] = yplayer+15;
				}
				for (int i= 0; i < shotdone.length; i++)
					shotdone[i] = false;
				//Sets the values for shooting
				tan =(double)(ypos - yplayer + 15)/(xpos - xplayer + 15);
				degree =Math.atan(tan);
				if(tan<=0&&xpos<=xplayer+15&&ypos>=yplayer+15||tan<=0&&xpos<=xplayer+15&&(ypos<=yplayer+15&&ypos>yplayer-15))
					degree += Math.PI;
				if(tan>=0&&xpos<=xplayer+15&&ypos<=yplayer+15)
					degree += Math.PI;
				canvas.repaint();
			}
		}
	}
	public void mouseReleased (MouseEvent e)
	{
		//Stops shooting
		shoot = false;
	}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
	public void mouseMoved (MouseEvent e)
	{
		if((movement&&ready)||(movement&&autofire))
		{
			//Gets mouse position for autofire mode to shoot bullets
			xpos = e.getX();
			ypos = e.getY();
			canvas.repaint();
		}
	}
	public void mouseDragged (MouseEvent e)
	{
		if(movement)
		{
			//Gets mouse position
			xpos = e.getX();
			ypos = e.getY();
			if(ready&&!autofire)
			{
				doneshots = 0;
				shots = new int[shotnum][2];
				shotdone =new boolean[shotnum];
				shot.start();
				shoot =true;
				ready =false;
				for (int i = 0; i < shots.length; i++)
				{
					shots[i][0] = xplayer+15;
					shots[i][1] = yplayer+15;
				}
				for (int i= 0; i < shotdone.length; i++)
					shotdone[i] = false;
				//Sets the values for shooting
				tan = (double)(ypos - yplayer + 15)/(xpos - xplayer + 15);
				degree = Math.atan(tan);
				if(tan<=0&&xpos<=xplayer+15&&ypos>=yplayer+15||tan<=0&&xpos<=xplayer+15&&(ypos<=yplayer+15&&ypos>yplayer-15))
					degree += Math.PI;
				if(tan>=0&&xpos<=xplayer+15&&ypos<=yplayer+15)
					degree += Math.PI;
			}
			canvas.repaint();
		}
	}
	public void keyPressed (KeyEvent e)
	{
		if(movement)
		{
			//Returns to main menu
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			{
				timer.stop();
				shot.stop();
				cards.show(c,"MainMenu");
				contgamebutton.setEnabled(true);
				pause = false;
				movement = false;
			}
			//Toggles autofire
			if(e.getKeyCode()==KeyEvent.VK_CONTROL)
			{
				if(buyautofire)
				{
					autofire = !autofire;
					shot.start();
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_F12)
				infinitegold=!infinitegold;
			if(e.getKeyCode()==KeyEvent.VK_F11)
				infinitehp=!infinitehp;
		}
	}
	public void keyTyped (KeyEvent e)
	{
		char character = e.getKeyChar();
		if(movement)
		{
			switch(character)
			{
				case 's':
					down = true;
					//up = left = right = false;
					msTime = 0;
					break;
				case 'd':
					right = true;
					//up = down = left = false;
					msTime = 0;
					break;
				case 'w':
					up = true;
					//down = left = right = false;
					msTime = 0;
					break;
				case 'a':
					left = true;
					//up = down = right = false;
					msTime = 0;
					break;
			}
			canvas.repaint();
		}
		if(movement||pause)
		{
			if(character == 'p')
				pause = !pause;
			if(pause)
			{
				shot.stop();
				timer.stop();
				movement = false;
			}
			else if(!pause)
			{
				shot.start();
				timer.start();
				movement = true;
			}
		}
	}
	public void keyReleased (KeyEvent e) {
		//Checks which key to stop movement
		if(e.getKeyChar()=='w')
			up = false;
		if(e.getKeyChar()=='a')
			left = false;
		if(e.getKeyChar()=='s')
			down = false;
		if(e.getKeyChar()=='d')
			right = false;
	}
	public void actionPerformed(ActionEvent e)
	{
		//Keeps track of game timer
		if(recordSTime==0)
			recordSTime = e.getWhen();
		recordCTime = e.getWhen();
		int timedifference = (int)(recordCTime-recordSTime);
		gametime=timedifference/1000;
		if(bsTime ==0)
			bsTime = e.getWhen();
		bcTime = e.getWhen();
		//Randomely moves enemy shapes & bosses
		if(movement)
		{
			for (int i = 0; i < sqx.length; i++)
			{
				if(sqx[i]<10000)
					switch((int)(Math.random()*4))
					{
						case 0:
							sqx[i] += 20;
							break;
						case 1:
							sqx[i] -= 20;
							break;
						case 2:
							sqy[i] += 20;
							break;
						case 3:
							sqy[i] -= 20;
							break;
					}
				if(!infinitehp)
					checkHit(i);
				canvas.repaint();
			}
			for (int i = 0; i < cx.length; i++)
			{
				if(cx[i]<10000)
					switch((int)(Math.random()*4))
					{
						case 0:
							cx[i] += 20;
							break;
						case 1:
							cx[i] -= 20;
							break;
						case 2:
							cy[i] += 20;
							break;
						case 3:
							cy[i] -= 20;
							break;
					}
				if(!infinitehp)
					checkHit(i);
				canvas.repaint();
			}
			for (int i = 0; i < sx.length; i++)
			{
				if(sx[i]<10000)
					switch((int)(Math.random()*4))
					{
						case 0:
							sx[i] += 20;
							break;
						case 1:
							sx[i] -= 20;
							break;
						case 2:
							sy[i] += 20;
							break;
						case 3:
							sy[i] -= 20;
							break;
					}
				if(!infinitehp)
					checkHit(i);
				canvas.repaint();
			}
			for (int i = 0; i < tx.length; i++)
			{
				if(tx[i]<10000)
					switch((int)(Math.random()*4))
					{
						case 0:
							tx[i] += 20;
							break;
						case 1:
							tx[i] -= 20;
							break;
						case 2:
							ty[i] += 20;
							break;
						case 3:
							ty[i] -= 20;
							break;
					}
				if(!infinitehp)
					checkHit(i);
				canvas.repaint();
			}
			if(sqxboss<10000)
			{
				switch((int)(Math.random()*4))
				{
					case 0:
						if(sqxboss+20<mapx+1370)
							sqxboss+=20;
						break;
					case 1:
						if(sqxboss-20>mapx)
							sqxboss-=20;
						break;
					case 2:
						if(sqyboss+20<mapy+5650)
							sqyboss+=20;
						break;
					case 3:
						if(sqyboss-20>mapy+4290)
							sqyboss-=20;
						break;
				}
			}
			if(cxboss<10000)
			{
				switch((int)(Math.random()*4))
				{
					case 0:
						if(cxboss+20<mapx+9900)
							cxboss+=20;
						break;
					case 1:
						if(cxboss-20>mapx+8560)
							cxboss-=20;
						break;
					case 2:
						if(cyboss+20<mapy+5650)
							cyboss+=20;
						break;
					case 3:
						if(cyboss-20>mapy+4290)
							cyboss-=20;
						break;
				}
			}
			if(sxboss<10000)
			{
				switch((int)(Math.random()*4))
				{
					case 0:
						if(sxboss+20<mapx+5650)
							sxboss+=20;
						break;
					case 1:
						if(sxboss-20>mapy+4290)
							sxboss-=20;
						break;
					case 2:
						if(syboss+20<mapy+9900)
							syboss+=20;
						break;
					case 3:
						if(syboss-20>mapy+8560)
							syboss-=20;
						break;
				}
			}
			if(txboss<10000)
			{
				switch((int)(Math.random()*4))
				{
					case 0:
						if(txboss+20<mapx+5650)
							txboss+=20;
						break;
					case 1:
						if(txboss-20>mapx+4290)
							txboss-=20;
						break;
					case 2:
						if(tyboss+20<mapy+1450)
							tyboss+=20;
						break;
					case 3:
						if(tyboss-20>mapy)
							tyboss-=20;
						break;
				}
			}
			canvas.repaint();
		}
		//Timer for question panel
		if(starttime==0)
			starttime = e.getWhen();
		if(!movement)
		{
			curtime = e.getWhen();
			int t = (int)(curtime-starttime);
			if(!answer)
				rTime = timelimit-t/1000;
			else
				rTime -= t/1000;
			if(!answer&rTime>=0)
				time.setText("Time: " + rTime);
			//Checks if time is out with no answer
			else if(res.equals("")&&!timeout)
			{
				res = "You ran out of time. You take 5 extra damage.";
				playerhp-=5;
				damagetaken+=5;
				result.setText(res);
				result.setForeground(Color.red);
				c1.setEnabled(false);
				c2.setEnabled(false);
				c3.setEnabled(false);
				c4.setEnabled(false);
				res = "";
				quesnum = (int)(Math.random()*education.length);
				timeout=true;
				if(playerhp<=0)
				{
					win = false;
					cards.show(c, "EndScreen");
					shot.stop();
					moving.stop();
					timer.stop();
				}
			}
			//Returns to playing the game
			if(rTime==-3)
			{
				up = down = left = right = false;
				hpbutton.setEnabled(true);
				atkbutton.setEnabled(true);
				if(shotnum<63)
					wepbutton.setEnabled(true);
				if(!buyautofire)
					afbutton.setEnabled(true);
				movement = true;
				question.setVisible(false);
				shot.start();
			}
		}
		//Timer for showing gold message if not enough for buying
		if(nogold)
		{
			if(gstime == 0)
				gstime = e.getWhen();
			gctime = e.getWhen();
			int t = (int)(gctime-gstime);
			if(errortime-t/1000==0)
			{
				error = "";
				gstime = 0;
				nogold = false;
			}
		}
	}
	//Checks if an enemy collides with the player
	public void checkHit(int q)
	{
		//Square collision
		if((sqx[q]>xplayer && sqx[q]<xplayer+30&&sqy[q]>yplayer&&sqy[q]<yplayer+30)||(sqx[q]+20>xplayer&&sqx[q]+20<xplayer+30&&sqy[q]>yplayer&&sqy[q]<yplayer+30)||(sqx[q]+20>xplayer&&sqx[q]+20<xplayer+30&&sqy[q]+20>yplayer&&sqy[q]+20<yplayer+30)||(sqx[q]>xplayer&&sqx[q]<xplayer+30&&sqy[q]+20>yplayer &&sqy[q]+20<yplayer+30))
		{
			sqhp[q] = maxsqhp;
			switch((int)(Math.random()*3))
			{
				case 0: sqx[q]=(int)(Math.random()*(-1150)+(1370+mapx));
						sqy[q]=(int)(Math.random()*(-7080)+(8480+mapy));
						sqbar[q]=false;break;
				case 1: sqx[q]=(int)(Math.random()*(-1380)+(2780+mapx));
						sqy[q]=(int)(Math.random()*(-4220)+(7050+mapy));
						sqbar[q]=false;break;
				case 2: sqx[q]=(int)(Math.random()*(-1390)+(4210+mapx));
						sqy[q]=(int)(Math.random()*(-1370)+(5630+mapy));
						sqbar[q]=false;break;
			}
			playerhp-=maxsqhp;
			damagetaken+=maxsqhp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
			tquestion++;
			answer = ready = shoot = false;
			ques.setText(education[quesnum][0]);
			a1.setText(education[quesnum][1]);
			a2.setText(education[quesnum][2]);
			a3.setText(education[quesnum][3]);
			a4.setText(education[quesnum][4]);
			question.setVisible(true);
			movement = false;
			shot.stop();
			starttime=0;
			c1.setEnabled(true);
			c2.setEnabled(true);
			c3.setEnabled(true);
			c4.setEnabled(true);
			hpbutton.setEnabled(false);
			atkbutton.setEnabled(false);
			wepbutton.setEnabled(false);
			afbutton.setEnabled(false);
			c1.setSelected(false);
			c2.setSelected(false);
			c3.setSelected(false);
			c4.setSelected(false);
			res = "";
			result.setText(res);
			timeout=false;
		}
		//Circle collision
		if((cx[q]>xplayer && cx[q]<xplayer+30&&cy[q]>yplayer&&cy[q]<yplayer+30)||(cx[q]+20>xplayer&&cx[q]+20<xplayer+30&&cy[q]>yplayer&&cy[q]<yplayer+30)||(cx[q]+20>xplayer&&cx[q]+20<xplayer+30&&cy[q]+20>yplayer&&cy[q]+20<yplayer+30)||(cx[q]>xplayer&&cx[q]<xplayer+30&&cy[q]+20>yplayer&&cy[q]+20<yplayer+30))
		{
			chp[q] = maxchp;
			switch((int)(Math.random()*3))
			{
				case 0: cx[q]=(int)(Math.random()*(1400)+(5780+mapx));
						cy[q]=(int)(Math.random()*(-1370)+(5630+mapy));
						cbar[q]=false;break;
				case 1: cx[q]=(int)(Math.random()*(1380)+(7230+mapx));
						cy[q]=(int)(Math.random()*(-4220)+(7050+mapy));
						cbar[q]=false;break;
				case 2: cx[q]=(int)(Math.random()*(1390)+(8660+mapx));
						cy[q]=(int)(Math.random()*(-7080)+(8480+mapy));
						cbar[q]=false;break;
			}
			playerhp-=maxchp;
			damagetaken+=maxchp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
			tquestion++;
			answer = ready = shoot = false;
			ques.setText(education[quesnum][0]);
			a1.setText(education[quesnum][1]);
			a2.setText(education[quesnum][2]);
			a3.setText(education[quesnum][3]);
			a4.setText(education[quesnum][4]);
			question.setVisible(true);
			movement = false;
			shot.stop();
			starttime=0;
			c1.setEnabled(true);
			c2.setEnabled(true);
			c3.setEnabled(true);
			c4.setEnabled(true);
			hpbutton.setEnabled(false);
			atkbutton.setEnabled(false);
			wepbutton.setEnabled(false);
			afbutton.setEnabled(false);
			c1.setSelected(false);
			c2.setSelected(false);
			c3.setSelected(false);
			c4.setSelected(false);
			res = "";
			result.setText(res);
			timeout=false;
		}
		//Star collision
		if((sx[q]>xplayer && sx[q]<xplayer+30&&sy[q]>yplayer&&sy[q]<yplayer+30)||(sx[q]+20>xplayer&&sx[q]+20<xplayer+30&&sy[q]>yplayer&&sy[q]<yplayer+30)||(sx[q]+20>xplayer&&sx[q]+20<xplayer+30&&sy[q]+20>yplayer&&sy[q]+20<yplayer+30)||(sx[q]>xplayer&&sx[q]<xplayer+30&&sy[q]+20>yplayer&&sy[q]+20<yplayer+30))
		{
			shp[q] = maxshp;
			switch((int)(Math.random()*3))
			{
				case 0: sx[q]=(int)(Math.random()*(-1370)+(5630+mapx));
						sy[q]=(int)(Math.random()*(1400)+(5780+mapy));
						sbar[q]=false;break;
				case 1: sx[q]=(int)(Math.random()*(-4220)+(7050+mapx));
						sy[q]=(int)(Math.random()*(1380)+(7130+mapy));
						sbar[q]=false;break;
				case 2: sx[q]=(int)(Math.random()*(-7080)+(8480+mapx));
						sy[q]=(int)(Math.random()*(1390)+(8560+mapy));
						sbar[q]=false;break;
			}
			playerhp-=maxshp;
			damagetaken+=maxshp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
			tquestion++;
			answer = ready = shoot = false;
			ques.setText(education[quesnum][0]);
			a1.setText(education[quesnum][1]);
			a2.setText(education[quesnum][2]);
			a3.setText(education[quesnum][3]);
			a4.setText(education[quesnum][4]);
			question.setVisible(true);
			movement = false;
			shot.stop();
			starttime=0;
			c1.setEnabled(true);
			c2.setEnabled(true);
			c3.setEnabled(true);
			c4.setEnabled(true);
			hpbutton.setEnabled(false);
			atkbutton.setEnabled(false);
			wepbutton.setEnabled(false);
			afbutton.setEnabled(false);
			c1.setSelected(false);
			c2.setSelected(false);
			c3.setSelected(false);
			c4.setSelected(false);
			res = "";
			result.setText(res);
			timeout=false;
		}
		//Triangle collision
		if((tx[q]>xplayer && tx[q]<xplayer+30&&ty[q]>yplayer&&ty[q]<yplayer+30)||(tx[q]+20>xplayer&&tx[q]+20<xplayer+30&&ty[q]>yplayer&&ty[q]<yplayer+30)||(tx[q]+20>xplayer&&tx[q]+20<xplayer+30&&ty[q]+20>yplayer&&ty[q]+20<yplayer+30)||(tx[q]>xplayer&&tx[q]<xplayer+30&&ty[q]+20>yplayer&& ty[q]+20<yplayer+30))
		{
			thp[q] = maxthp;
			switch((int)(Math.random()*3))
			{
				case 0: tx[q]=(int)(Math.random()*(-1370)+(5680+mapx));
						ty[q]=(int)(Math.random()*(-1390)+(4280+mapy));
						tbar[q]=false;break;
				case 1: tx[q]=(int)(Math.random()*(-4220)+(7100+mapx));
						ty[q]=(int)(Math.random()*(-1380)+(2880+mapy));
						tbar[q]=false;break;
				case 2: tx[q]=(int)(Math.random()*(-7080)+(8530+mapx));
						ty[q]=(int)(Math.random()*(-1400)+(1470+mapy));
						tbar[q]=false;break;
			}
			playerhp-=maxthp;
			damagetaken+=maxthp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
			tquestion++;
			answer = ready = shoot = false;
			ques.setText(education[quesnum][0]);
			a1.setText(education[quesnum][1]);
			a2.setText(education[quesnum][2]);
			a3.setText(education[quesnum][3]);
			a4.setText(education[quesnum][4]);
			question.setVisible(true);
			movement = false;
			shot.stop();
			starttime=0;
			c1.setEnabled(true);
			c2.setEnabled(true);
			c3.setEnabled(true);
			c4.setEnabled(true);
			hpbutton.setEnabled(false);
			atkbutton.setEnabled(false);
			wepbutton.setEnabled(false);
			afbutton.setEnabled(false);
			c1.setSelected(false);
			c2.setSelected(false);
			c3.setSelected(false);
			c4.setSelected(false);
			res = "";
			result.setText(res);
			timeout=false;
		}
		//Boss collisions
		//Square
		if((xplayer>sqxboss&&xplayer<sqxboss+80&&yplayer>sqyboss&&yplayer<sqyboss+80)||(xplayer+30>sqxboss&&xplayer+30<sqxboss+80&&yplayer>sqyboss&&yplayer<sqyboss+80)||(xplayer+30>sqxboss&&xplayer+30<sqxboss+80&&yplayer+30>sqyboss&&yplayer+30<sqyboss+80)||(xplayer>sqxboss&&xplayer<sqxboss+80&&yplayer+30>sqyboss&&yplayer+30<sqyboss+80))
		{
			playerhp -= pmaxhp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
		}
		//Circle
		if((xplayer>cxboss&&xplayer<cxboss+80&&yplayer>cyboss&&yplayer<cyboss+80)||(xplayer+30>cxboss&&xplayer+30<cxboss+80&&yplayer>cyboss&&yplayer<cyboss+80)||(xplayer+30>cxboss&&xplayer+30<cxboss+80&&yplayer+30>cyboss&&yplayer+30<cyboss+80)||(xplayer>cxboss&&xplayer<cxboss+80&&yplayer+30>cyboss&&yplayer+30<cyboss+80))
		{
			playerhp -= pmaxhp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
		}
		//Star
		if((xplayer>sxboss&&xplayer<sxboss+80&&yplayer>syboss&&yplayer<syboss+80)||(xplayer+30>sxboss&&xplayer+30<sxboss+80&&yplayer>syboss&&yplayer<syboss+80)||(xplayer+30>sxboss&&xplayer+30<sxboss+80&&yplayer+30>syboss&&yplayer+30<syboss+80)||(xplayer>sxboss&&xplayer<sxboss+80&&yplayer+30>syboss&&yplayer+30<syboss+80))
		{
			playerhp -= pmaxhp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
		}
		//Triangle
		if((xplayer>txboss&&xplayer<txboss+80&&yplayer>tyboss&&yplayer<tyboss+80)||(xplayer+30>txboss&&xplayer+30<txboss+80&&yplayer>tyboss&&yplayer<tyboss+80)||(xplayer+30>txboss&&xplayer+30<txboss+80&&yplayer+30>tyboss&&yplayer+30<tyboss+80)||(xplayer>txboss&&xplayer<txboss+80&&yplayer+30>tyboss&&yplayer+30<tyboss+80))
		{
			playerhp -= pmaxhp;
			if(playerhp<=0)
			{
				win = false;
				cards.show(c, "EndScreen");
				shot.stop();
				moving.stop();
				timer.stop();
			}
		}
	}
	//Questions for education part
	class Questions extends JPanel implements ActionListener
	{
		//constructor
		public Questions()
		{
			this.setBackground(Color.white);
			this.setLayout(null);
			result = new JLabel("",JLabel.CENTER);
			result.setBounds(0,195,300,50);
			result.setBackground(Color.black);
			result.setOpaque(true);
			result.setFont(new Font("Times New Roman",Font.PLAIN, 14));
			this.add(result);
			ques = new JLabel(education[quesnum][0]);
			ques.setBounds(5,0,200,70);
			this.add(ques);
			time = new JLabel("Time: ");
			time.setBounds(230,0,70,30);
			this.add(time);
			c1 = new JRadioButton("a.");
			c1.addActionListener(this);
			this.add(c1);
			c1.setBounds(0,75,40,30);
			c2 = new JRadioButton("b.");
			c2.addActionListener(this);
			this.add(c2);
			c2.setBounds(0,105,40,30);
			c3 = new JRadioButton("c.");
			c3.addActionListener(this);
			this.add(c3);
			c3.setBounds(0,135,40,30);
			c4 = new JRadioButton("d.");
			c4.addActionListener(this);
			this.add(c4);
			c4.setBounds(0,165,40,30);
			a1 = new JLabel(education[quesnum][1]);
			a1.setBounds(45,75,300,30);
			this.add(a1);
			a2 = new JLabel(education[quesnum][2]);
			a2.setBounds(45,105,300,30);
			this.add(a2);
			a3 = new JLabel(education[quesnum][3]);
			a3.setBounds(45,135,300,30);
			this.add(a3);
			a4 = new JLabel(education[quesnum][4]);
			a4.setBounds(45,165,300,30);
			this.add(a4);
		}
		public void actionPerformed(ActionEvent e)
		{
			//checks if player chooses the correct button
			String command = e.getActionCommand();
			if(command.equals(education[quesnum][5]))
			{
				canswer++;
				res = "Correct. You gain 5 hp.";
				if(playerhp+5<=pmaxhp)
					playerhp+=5;
				else
					playerhp=pmaxhp;
				result.setForeground(Color.green);
			}
			else
			{
				res = "Incorrect. You take 5 extra damage.";
				playerhp-=5;
				damagetaken+=5;
				result.setForeground(Color.red);
				if(playerhp<=0)
				{
					win = false;
					cards.show(c, "EndScreen");
					shot.stop();
					moving.stop();
					timer.stop();
				}
			}
			//Doesn't allow for another button to be chosen
			result.setText(res);
			quesnum = (int)(Math.random()*(education.length));
			c1.setEnabled(false);
			c2.setEnabled(false);
			c3.setEnabled(false);
			c4.setEnabled(false);
			answer = true;
			starttime = 0;
			rTime = 3;
		}
	}
	//End screen panel
	class Finish extends JPanel implements ActionListener
	{
		private JButton menureturn, playagain;
		public Finish()
		{
			this.setLayout(null);
			this.setBackground(Color.white);
			menureturn = new JButton("Return to Main Menu");
			menureturn.addActionListener(this);
			menureturn.setBounds(100,455,200,30);
			this.add(menureturn);
			playagain = new JButton("Play Again");
			playagain.addActionListener(this);
			playagain.setBounds(400,455,200,30);
			this.add(playagain);
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setFont(new Font("Times New Roman", Font.BOLD,20));
			//Draws stats if won
			if(win)
			{
				g.setColor(Color.green);
				g.fillRect(0,25,700,35);
				g.setColor(Color.black);
				g.fillRect(0,155,700,5);
				g.drawString("Congratulations",250,50);
				g.drawString("You have escaped from ShapeWorld",10,100);
				g.drawString("Stats", 300,150);
				g.drawString("Level: " + lvl, 10,180);
				g.drawString("Damage Dealt: " +	damagetotal, 10,210);
				g.drawString("Damage Taken: " +	damagetaken, 10,240);
				g.drawString("Experience Earned: " + exptotal, 10,270);
				g.drawString("Correct Answers: "+canswer+"/"+tquestion,10,300);
				g.drawString("Gold Earned: "+goldtotal,10,330);
				g.drawString("Gold Spent: "+goldspent,10,360);
				g.drawString("Time Played: "+gametime/60+" minutes "+gametime%60+" seconds", 10,450);
				g.drawString("Squares Killed: "+sqcount,400,180);
				g.drawString("Circles Killed: "+ccount,400,210);
				g.drawString("Stars Killed: "+scount,400,240);
				g.drawString("Triangles Killed: "+tcount,400,270);
				g.drawString("Bosses Defeated: "+bosscount,400,300);
			}
			//Draws stats if lost
			else
			{
				g.setColor(Color.red);
				g.fillRect(0,25,700,35);
				g.setColor(Color.black);
				g.fillRect(0,155,700,5);
				g.drawString("Game Over",250,50);
				g.drawString("You have died.",10,100);
				g.drawString("Stats", 300,150);
				g.drawString("Level: " + lvl, 10,180);
				g.drawString("Damage Dealt: " +damagetotal, 10,210);
				g.drawString("Damage Taken: " +damagetaken, 10,240);
				g.drawString("Experience Earned: " +exptotal, 10,270);
				g.drawString("Correct Answers: "+canswer+"/"+tquestion,10,300);
				g.drawString("Gold Earned: "+goldtotal,10,330);
				g.drawString("Gold Spent: "+goldspent,10,360);
				g.drawString("Time Played: "+gametime/60+" minutes "+gametime%60+" seconds", 10,420);
				g.drawString("Squares Killed: "+sqcount,400,180);
				g.drawString("Circles Killed: "+ccount,400,210);
				g.drawString("Stars Killed: "+scount,400,240);
				g.drawString("Triangles Killed: "+tcount,400,270);
				g.drawString("Bosses Defeated: "+bosscount,400,300);
			}
		}
		public void actionPerformed(ActionEvent e)
		{
			//Checks for button pressed
			String action = e.getActionCommand();
			if(action.equals("Return to Main Menu"))
			{
				//Return to mainmenu
				contgamebutton.setEnabled(false);
				cards.show(c, "MainMenu");
				question.setVisible(false);
				canvas.repaint();
			}
			if(action.equals("Play Again"))
			{
				//Goes to game area and resets values
				cards.show(c, "GamePanel");
				damagetotal = exptotal = damagetaken = canswer = tquestion = goldspent = 0;
				buyautofire = infinitegold = infinitehp = false;
				shots = new int[1][2];
				shotdone = new boolean[1];
				shotnum = 1;
				dist = tan = degree = 0.0;
				doneshots = 0;
				autofire = false;
				coinx = coiny = 0;
				answer = false;
				drawCoin = false;
				gTime = 0;
				sqcount = ccount = scount = tcount = bosscount = 0;
				win = pause = nogold = false;
				up = down = left = right = false;
				errortime = 2;
				res = "";
				error= "";
				bosshealth = "";
				hpcost = 20;
				atkcost = 10;
				wepcost = 30;
				afcost = 1000;
				gold = 0;
				exitlocationx = 4975;
				exitlocationy = 4975;
				sqxp = 1;
				cxp = 2;
				sxp = 4;
				txp = 5;
				sqbossmaxhp = 2000;
				cbossmaxhp = 4000;
				sbossmaxhp = 6000;
				tbossmaxhp = 10000;
				sqbosshp = sqbossmaxhp;
				cbosshp = cbossmaxhp;
				sbosshp = sbossmaxhp;
				tbosshp = tbossmaxhp;
				movement = true;
				quesnum = (int)(Math.random()*(education.length));
				attack = lvl = 1;
				starttime = curtime = gctime = gstime = mcTime = msTime = recordCTime = recordSTime = 0;
				bsTime = bcTime = 0;
				timelimit = 15;
				maxsqhp=2;
				maxchp=4;
				maxshp=6;
				maxthp=10;
				sqgold = maxsqhp*2;
				cgold = maxchp*2;
				sgold = maxshp*2;
				tgold = maxthp*2;
				playerhp = pmaxhp = 100;
				exp = 0;
				maxexp = 25;
				mapx = mapy = -4750;
				sqx = new int[300];
				sqy = new int[300];
				cx = new int[300];
				cy = new int[300];
				sx = new int[300];
				sy = new int[300];
				tx = new int[300];
				ty = new int[300];
				sqhp = new int [300];
				chp = new int[300];
				shp = new int[300];
				thp = new int[300];
				sqbar = new boolean[300];
				cbar = new boolean[300];
				sbar = new boolean[300];
				tbar = new boolean[300];
				for (int i = 0; i < sqhp.length; i++)
					sqhp[i] = maxsqhp;
				for (int i = 0; i < chp.length; i++)
					chp[i] = maxchp;
				for (int i = 0; i < shp.length; i++)
					shp[i] = maxshp;
				for (int i = 0; i < thp.length; i++)
					thp[i] = maxthp;
				for (int i = 0; i < sqx.length; i++)
					switch((int)(Math.random()*3))
					{
						case 0: sqx[i]=(int)(Math.random()*(-1150)+(1370+mapx));
								sqy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
						case 1: sqx[i]=(int)(Math.random()*(-1380)+(2780+mapx));
								sqy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
						case 2: sqx[i]=(int)(Math.random()*(-1390)+(4210+mapx));
								sqy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
					}
				for (int i = 0; i < cx.length; i++)
					switch((int)(Math.random()*3))
					{
						case 0: cx[i]=(int)(Math.random()*(1400)+(5780+mapx));
								cy[i]=(int)(Math.random()*(-1370)+(5630+mapy));break;
						case 1: cx[i]=(int)(Math.random()*(1380)+(7230+mapx));
								cy[i]=(int)(Math.random()*(-4220)+(7050+mapy));break;
						case 2: cx[i]=(int)(Math.random()*(1390)+(8660+mapx));
								cy[i]=(int)(Math.random()*(-7080)+(8480+mapy));break;
					}
				for (int i = 0; i < sx.length; i++)
					switch((int)(Math.random()*3))
					{
						case 0: sx[i]=(int)(Math.random()*(-1370)+(5630+mapx));
								sy[i]=(int)(Math.random()*(1400)+(5780+mapy));break;
						case 1: sx[i]=(int)(Math.random()*(-4220)+(7050+mapx));
								sy[i]=(int)(Math.random()*(1380)+(7130+mapy));break;
						case 2: sx[i]=(int)(Math.random()*(-7080)+(8480+mapx));
								sy[i]=(int)(Math.random()*(1390)+(8560+mapy));break;
					}
				for (int i = 0; i < tx.length; i++)
					switch((int)(Math.random()*3))
					{
						case 0: tx[i]=(int)(Math.random()*(-1370)+(5680+mapx));
								ty[i]=(int)(Math.random()*(-1390)+(4280+mapy));break;
						case 1: tx[i]=(int)(Math.random()*(-4220)+(7100+mapx));
								ty[i]=(int)(Math.random()*(-1380)+(2880+mapy));break;
						case 2: tx[i]=(int)(Math.random()*(-7080)+(8530+mapx));
								ty[i]=(int)(Math.random()*(-1400)+(1470+mapy));break;
					}
				items = new int[5][10];
				equip = new int[4];
				xplayer = yplayer = 250;
				mx = my = 300;
				ready = true;
				pheight = 30;
				pwidth = 30;
				sheight = swidth = 10;
				edgex = edgey = false;
				items[0][0] = 999;
				items[4][3] = 998;
				equip[0]=999;
				sqxboss = -4250;
				sqyboss = 250;
				sxboss = 250;
				syboss = 4250;
				cxboss = 4250;
				cyboss = 250;
				txboss = 250;
				tyboss = -4250;
				atkbutton.setEnabled(true);
				hpbutton.setEnabled(true);
				afbutton.setEnabled(true);
				wepbutton.setEnabled(true);
				question.setVisible(false);
				timer.start();
				moving.start();
				canvas.repaint();
			}
		}
	}
	class PMove implements ActionListener
	{
		//Moves the player
		public void actionPerformed(ActionEvent e)
		{
			if(movement)
			{
				if(msTime==0)
					msTime=e.getWhen();
				mcTime=e.getWhen();
				long t = mcTime-msTime;
				//if(1-t/600>0)
				{
					//Move up
					if(up)
					{
						if(ready)
						{
							xchange = xpos-5-(xplayer+10);
							ychange = ypos-5-(yplayer+10);
						}
						if(mapy+10<0&&yplayer==250)
						{
							shoty+=10;
							mapy+=10;
							for (int i = 0; i < sqy.length; i++)
								sqy[i] += 10;
							for (int i = 0; i < cy.length; i++)
								cy[i] += 10;
							for (int i = 0; i < sy.length; i++)
								sy[i] += 10;
							for (int i = 0; i < ty.length; i++)
								ty[i] += 10;
							edgey = false;
							sqyboss+=10;
							cyboss+=10;
							syboss+=10;
							tyboss+=10;
						}
						else
							edgey = true;
						if(edgey)
						{
							if(ready)
							{
								xchange=xpos-5-(xplayer+10);
								ychange=ypos-5-(yplayer+10);
							}
							if(yplayer-10>=0)
								yplayer-=10;
						}
						canvas.repaint();
					}
					//Move down
					if(down)
					{
						if(ready)
						{
							xchange = xpos-5-(xplayer+10);
							ychange = ypos-5-(yplayer+10);
						}
						if(mapy-10>-9500&&yplayer==250)
						{
							shoty-=10;
							mapy-=10;
							edgey = false;
							for (int i = 0; i < sqy.length; i++)
								sqy[i] -= 10;
							for (int i = 0; i < cy.length; i++)
								cy[i] -= 10;
							for (int i = 0; i < sy.length; i++)
								sy[i] -= 10;
							for (int i = 0; i < ty.length; i++)
								ty[i] -= 10;
							sqyboss-=10;
							cyboss-=10;
							syboss-=10;
							tyboss-=10;
						}
						else
							edgey = true;
						if(edgey)
						{
							if(ready)
							{
								xchange=xpos-5-(xplayer+10);
								ychange=ypos-5-(yplayer+10);
							}
							if(yplayer+pheight-10<=500)
								yplayer+=10;
						}
						canvas.repaint();
					}
					//Move left
					if(left)
					{
						if(ready)
						{
							xchange = xpos-5-(xplayer+10);
							ychange = ypos-5-(yplayer+10);
						}
						if(mapx+10<0&&xplayer==250)
						{
							shotx+=10;
							mapx+=10;
							for (int i = 0; i < sqx.length; i++)
								sqx[i] += 10;
							for (int i = 0; i < cy.length; i++)
								cx[i] += 10;
							for (int i = 0; i < sy.length; i++)
								sx[i] += 10;
							for (int i = 0; i < ty.length; i++)
								tx[i] += 10;
							edgex = false;
							sqxboss+=10;
							cxboss+=10;
							sxboss+=10;
							txboss+=10;
						}
						else
							edgex = true;
						if(edgex)
						{
							if(ready)
							{
								xchange=xpos-5-(xplayer+10);
								ychange=ypos-5-(yplayer+10);
							}
							if(xplayer-10>=0)
								xplayer-=10;
						}
						canvas.repaint();
					}
					//Move right
					if(right)
					{
						if(ready)
						{
							xchange = xpos-5-(xplayer+10);
							ychange = ypos-5-(yplayer+10);
						}
						if(mapx-10>-9500&&xplayer==250)
						{
							shotx-=10;
							mapx-=10;
							for (int i = 0; i < sqy.length; i++)
								sqx[i] -= 10;
							for (int i = 0; i < cy.length; i++)
								cx[i] -= 10;
							for (int i = 0; i < sy.length; i++)
								sx[i] -= 10;
							for (int i = 0; i < ty.length; i++)
								tx[i] -= 10;
							edgex = false;
							sqxboss-=10;
							cxboss-=10;
							sxboss-=10;
							txboss-=10;
						}
						else
							edgex = true;
						if(edgex)
						{
							if(ready)
							{
								xchange=xpos-5-(xplayer+10);
								ychange=ypos-5-(yplayer+10);
							}
							if(xplayer+pwidth-10<=500)
								xplayer+=10;
						}
						canvas.repaint();
					}
					//Checks if collision with enemy
					for(int i = 0; i < sqx.length; i++)
						if(!infinitehp)
							checkHit(i);
				}
				//Timer for drawing coin
				if(drawCoin)
					gTime++;
			}
		}
	}
	//Timer for shooting
	class ShotTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Calculates the spread of the bullets
			dist = (shotnum/2)*-0.1;
			//Resets the shots if autofire is on
			if((ready&&autofire))
			{
				doneshots = 0;
				ready = false;
				if(shotnum!=shots.length)
				{
					shots = new int[shotnum][2];
					shotdone = new boolean[shotnum];
					for (int i = 0; i < shots.length; i++)
					{
						shots[i][0] = xplayer+15;
						shots[i][1] = yplayer+15;
					}
				}
				for (int i = 0; i < shotdone.length; i++)
					shotdone[i] = false;
				tan = (double)(ypos - yplayer + 15)/(xpos - xplayer + 15);
				degree = Math.atan(tan);
				if(tan<=0&&xpos<=xplayer+15&&ypos>=yplayer+15||tan<=0&&xpos<=xplayer+15&&(ypos<=yplayer+15&&ypos>yplayer-15))
					degree += Math.PI;
				if(tan>=0&&xpos<=xplayer+15&&ypos<=yplayer+15)
					degree += Math.PI;
			}
			//Increases the position of the shots using sine & cosine
			double tempdist = dist;
			shotdone = new boolean[shotnum];
			for (int i = 0; i < shots.length; i++)
			{
				shots[i][0] += Math.round(40*Math.cos(degree+tempdist));
				shots[i][1] += Math.round(40*Math.sin(degree+tempdist));
				if(tempdist+dist==0&&shotnum%2==0)
					tempdist+=dist*2;
				else if(dist!=0)
					tempdist+=0.1;	
			}
			canvas.repaint();
		}
	}
}
