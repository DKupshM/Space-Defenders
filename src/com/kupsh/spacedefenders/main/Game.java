package com.kupsh.spacedefenders.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Random;

import com.kupsh.spacedefenders.main.PowerUp.Type;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private boolean running;
	private boolean powerUpActive;
	private boolean powerUpScreen = false;

	public static int Height = 480;
	public static int Width = 640;

	private Thread thread;
	private Random random;
	private Spawner spawner;
	private BufferedImageLoader loader;
	private LoadingScreen ls;
	private Handler handler;
	private SpriteSheet ss;
	private Menu menu;
	private FindingScreen fs;
	private HUD hud;
	private Type type;
	private Type otherType;
	private Session session;

	private State state = State.loadingScreen;

	private HashMap<String, AudioPlayer> audio;
	private HashMap<String, BufferedImage> image;

	private BufferedImage powerUpImage;

	private int ticks = 0;

	public PrintWriter output;
	private BufferedReader input;
	private Socket socket;
	private boolean server;
	private int port = 6440;
	//ip = 50.185.254.252
	private String ip = "localhost";
	private int ServerId;
	private boolean spaceActive = false;

	public void init() {
		System.out.println("Initialize Starting");

		// Load Random Utilities
		random = new Random();

		// Load Music and Sound Effects
		audio = new HashMap<String, AudioPlayer>();
		audio.put("PowerUp", new AudioPlayer("/Music/PowerUp.mp3"));

		// Load Images
		image = new HashMap<String, BufferedImage>();
		image.put("SpriteSheet", loader.loadImage("/Images/SpriteSheet.png"));
		image.put("PowerUpImage", loader.loadImage("/Images/PowerUp.jpg"));
		image.put("PowerUpDoublePointsImage",
				loader.loadImage("/Images/PowerUpDoublePoints.png"));
		image.put("PowerUpHealthImage",
				loader.loadImage("/Images/PowerUpHealth.png"));
		image.put("PowerUpUnlimitedHealthImage",
				loader.loadImage("/Images/PowerUpUnlimitedHealth.png"));
		image.put("PowerUpAmmoImage",
				loader.loadImage("/Images/PowerUpAmmo.png"));
		image.put("PowerUpUnlimitedAmmoImage",
				loader.loadImage("/Images/PowerUpUnlimitedAmmo.png"));
		image.put("PowerUpTripleShotImage",
				loader.loadImage("/Images/PowerUpTripleShot.png"));
		image.put("PowerUpClearScreenImage",
				loader.loadImage("/Images/PowerUpClearScreen.png"));
		image.put("PowerUpRegenShotImage",
				loader.loadImage("/Images/PowerUpRegenShot.png"));

		ss = new SpriteSheet(image.get("SpriteSheet"));

		// Load Screens and Various Elements
		hud = new HUD(this);
		menu = new Menu(this);
		fs = new FindingScreen(this);
		handler = new Handler(this);
		spawner = new Spawner(this, handler, random, ss);

		// LoadServer
		// Load Server
		this.server = server();
		if (server) {
			System.out.println("You have Connected and your id is " + ServerId);
			session = Session.waiting;
		} else {
			// Load Singleplayer Elements
			session = Session.waiting;
		}

		// Load Listeners for key and mouse
		this.addMouseListener(new MouseInput(this));
		this.addKeyListener(new KeyInput(this, handler, random, ss));
		System.out.println("Initialize Finished");
	}

	public void newGame() {
		// add player
		handler.addObject(new Player(random.nextInt(Game.Width), 0, 0, 0,
				ID.player, this, handler, ss));
		System.out.println("New Game");
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		if (state == State.loadingScreen)
			g.setColor(Color.white);
		else
			g.setColor(Color.black);
		g.fillRect(0, 0, Width, Height);
		g.setColor(Color.black);

		// render all screens here
		if (state == State.loadingScreen)
			ls.render(g);
		else if (state == State.menu)
			menu.render(g);
		else if (state == State.finding)
			fs.render(g);
		else if (state == State.Game) {
			if (powerUpScreen == true) {
				g.drawImage(powerUpImage, 0, 0, Game.Width, Game.Height, null);

			}
			hud.render(g);
			handler.render(g);
		}

		g.setColor(Color.white);
		g.drawString("Ticks: " + ticks, 40, Game.Height - 40);
		g.dispose();
		bs.show();
	}

	public void tick() {
		// tick all screens here
		if (state == State.loadingScreen)
			ls.tick();
		else if (state == State.Game) {
			hud.tick();
			if (session == Session.singleplayer)
				spawner.tick();
				handler.tick();
		}
	}

	public void start() {
		if (running) {
			return;
		}
		running = true;
		Window window = new Window();
		window.window("Space Defenders", this);
		loader = new BufferedImageLoader();
		ls = new LoadingScreen(this, loader);
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (!running)
			return;
		running = false;
		try {
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 40.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		RenderThread rt = new RenderThread(this, running);
		int tick = 0;
		new Thread(rt).start();
		// Game loop
		// tick is 20 a second

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				tick++;
				delta--;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				ticks = tick;
				tick = 0;
			}
		}
		stop();
	}

	private boolean server() {
		try {
			System.out.println("Trying to Connect to Server");
			InetAddress name = InetAddress.getByName(ip);
			SocketAddress address = new InetSocketAddress(name, port);
			socket = new Socket();
			// This limits the time allowed to establish a connection in the
			// case
			// that the connection is refused or server doesn't exist.\
			socket.connect(address, 100000);
			// This stops the request from dragging on after connection
			// succeeds.
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			System.out.println("Connection Made");

			// connection made

			String inputString = input.readLine();
			System.out.println(inputString);
			String idinput = input.readLine();
			ServerId = Integer.parseInt(idinput);
			InputThread it = new InputThread(this, input, handler, ss);
			new Thread(it).start();
			return true;
		} catch (Exception e) {
			System.out.println("Could not connect to server");
			output = new PrintWriter(System.out, true);
			System.out.println(e);
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getServerId() {
		return ServerId;
	}

	public void setServerId(int serverId) {
		ServerId = serverId;
	}

	public boolean isServer() {
		return server;
	}

	public void setServer(boolean server) {
		this.server = server;
	}

	public boolean isPowerUpActive() {
		return powerUpActive;
	}

	public void setPowerUpActive(boolean powerUpActive) {
		this.powerUpActive = powerUpActive;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public AudioPlayer getSound(String name) {
		return audio.get(name);
	}

	public boolean isPowerUpScreen() {
		return powerUpScreen;
	}

	public BufferedImage getImage(String name) {
		return image.get(name);
	}

	public void setPowerUpScreen(boolean powerUpScreen) {
		this.powerUpScreen = powerUpScreen;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Type getOtherType() {
		return otherType;
	}

	public void setOtherType(Type otherType) {
		this.otherType = otherType;
	}

	public BufferedImage getPowerUpImage() {
		return powerUpImage;
	}

	public void setPowerUpImage(BufferedImage powerUpImage) {
		this.powerUpImage = powerUpImage;
	}

	public boolean isSpaceActive() {
		return spaceActive;
	}

	public void setSpaceActive(boolean spaceActive) {
		this.spaceActive = spaceActive;
	}
}
