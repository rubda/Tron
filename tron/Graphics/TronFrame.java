package tron.Graphics;

import tron.Arena;
import tron.Directions;
import tron.Player;

import javax.swing.*;
//Obviously we use timer, so this import is not useless.
import javax.swing.Timer;
import java.awt.*;

import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA. User: RubenDas Date: 2013-09-22 Time: 15:51 To change this template use File | Settings | File
 * Templates.
 */
@SuppressWarnings("ALL")
public class TronFrame extends JFrame implements KeyListener
{

    private static final Pattern NULL = Pattern.compile("null");
    TronTextView textView = new TronTextView();

    //The inspector complaints about this since field is only used when
    //testing TronTextView
    JTextArea field;
    Arena arena;

    private int numPlayers = 0;
    private Color color = null;

     public TronComponent tronComp;

    final JButton startButton = new JButton("Start");
    final JButton quitButton = new JButton("Quit");
    final JButton instructionsButton = new JButton("Instructions");
    private Point positions = new Point();
    Random generator = new Random();
    private boolean isBreak = false;
    final static int BUTTON_SIZE = 50;
    final static int CHOOSE_KEY_BUTTON_WIDTH = 300;
    final static int CHOOSE_KEY_BUTTON_HEIGHT = 150;
    final static int SPACE_BETWEEN_BUTTONS = 15;
    private int num = 0;
    private int ifChooseKeys = 0;

    //-------------------------------------------------------------------------------------------------------------

    //Constructor for TronFrame
    public TronFrame(Arena arena) {
	super("Tron");

	this.arena = arena;

	arena.setPause(true);
	this.tronComp = new TronComponent(arena);
	arena.addArenaListener(tronComp);
	Container contents = this.getContentPane();
	contents.add(tronComp);

	//This is only for the TronTextView
/*	this.textView=new TronTextView();
	field = new JTextArea(arena.getRows(),arena.getColumns());
	field.setText(textView.convertToText(arena));
	this.setLayout(new BorderLayout());
	this.add(field, BorderLayout.CENTER);*/

	contents.setLayout(new FlowLayout());
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width / 2 - arena.getColumns() / 2, dim.height / 2 - arena.getRows() / 2);

	tronComp.setLayout(null);

	this.createButtons();

	this.createMenu();
	this.pack();
	this.setVisible(true);
	this.setFocusable(true);
	this.addKeyListener(this);
    }

    // Keeps the game alive
    public void runTimer() {
	final Action doOneStep = new AbstractAction()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (!arena.isPause()) {
		    if (!arena.isEndGame()) {
			arena.generateArena();
			arena.tick();
		    }
		    showWinningPlayer();

		}
	    }

	};

	final Timer clockTimer = new Timer(9, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

    //Shows the winning player in a dialog window.
    public void showWinningPlayer() {
	if (!NULL.matcher(arena.getShowWinningPlayer()).matches()) {
	    JOptionPane.showMessageDialog(null, arena.getShowWinningPlayer());
	    arena.setShowWinningPlayer("null");
	}
    }


    // Calls the restart function from arena
    public void restart() {
	arena.restart();
    }

//----------------------------------------------------------------------------------------------------------------

    // Sets the arena to text form.
    public void doText(Arena myArena) {
	field.setText(textView.convertToText(myArena));
    }

    // Shows our menu buttons on the frame.
    public void showMenuButtons() {
	startButton.setVisible(true);
	quitButton.setVisible(true);
	instructionsButton.setVisible(true);
    }

    // Hides our menu buttons on the frame.
    public void hideMenuButtons() {
	startButton.setVisible(false);
	quitButton.setVisible(false);
	instructionsButton.setVisible(false);
    }


    // Creates a menu with all possible things. Such as JBar that contains an Tron, menu, restart, about and quit options.
    private void createMenu() {

	// En Tron flik med det mest väsentliga
	final JMenu tron = new JMenu("Tron");

	final JMenuItem menu = new JMenuItem("Menu");
	tron.add(menu);

	menu.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		num = 0;
		arena.restart();
		arena.setPause(true);
		arena.getPlayers().clear();
		showMenuButtons();
		tronComp.arenaChanged();
	    }
	});

	final JMenuItem restart = new JMenuItem("Restart");

	tron.add(restart);

	restart.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (!arena.getPlayers().isEmpty() && arena.isReadyToPlay()) {
		    hideMenuButtons();
		    restart();
		}

	    }
	});


	final JMenuItem about = new JMenuItem("About");
	tron.add(about);

	about.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(about, "Hello!" + '\n' + '\n' +
						     "This is a game made by Ruben Das and Jakub Franciszek Binieda " + '\n' +
						     "in the course TDDC69 - Objective programming in Java at Linköpings University.");
	    }
	});


	// En options flik med möjligtheten att avsluta programmet
	final JMenu options = new JMenu("Options");
	final JMenuItem quit = new JMenuItem("Quit");
	options.add(quit);

	quit.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int answer = JOptionPane
			.showConfirmDialog(quit, "Do you really want to exit this brilliant game?", "Tron notifier",
					   JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
		    System.exit(0);
		}
	    }
	});


	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(tron);
	menuBar.add(options);
	this.setJMenuBar(menuBar);

    }

    // Here the user choose how many players the game should consist of.
    public void chooseNumPlayers() {
	String tempString = JOptionPane.showInputDialog("Number of players: ");
	if (isInteger(tempString)) {
	    if (Integer.parseInt(tempString) < 1 ||
		Integer.parseInt(tempString) > ((arena.getColumns() - 2) * (arena.getRows() - 2))) {
		chooseNumPlayers();
	    } else {
		numPlayers = Integer.parseInt(tempString);
		createAllPlayers();
	    }
	} else {
	    chooseNumPlayers();
	}
    }


//----------------------------------------------------------------------------------------------------------------------


    //This function allows the player to choose it's own color, name and keys.
    public void chooseParameters(final Player player) {

	final JDialog pane = new JDialog(this, "Please enter your name, choose a color and keys");
	final JTextField name = new JTextField(5);

	//Knappen som väljer tangenter

	final JButton chooseButtons = new JButton("Choose Keys");
	chooseButtons.setSize(BUTTON_SIZE, BUTTON_SIZE);
	chooseButtons.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {


		final JDialog chooseKeysFrame = new JDialog();
		final JButton chooseKeysButton = new JButton(
			"<html>Choose the keys you want in the order<br />left, right, up and down. <br />Finish by clicking here.</html>");

		chooseKeysButton.setSize(CHOOSE_KEY_BUTTON_WIDTH, CHOOSE_KEY_BUTTON_HEIGHT);
		chooseKeysButton.addActionListener(new ActionListener()
		{
		    @Override
		    //The inspector complains about the e's,
		    //but this was added when we implemented ActionListeners.
		    public void actionPerformed(ActionEvent e) {
			chooseKeysFrame.setVisible(false);
		    }
		});
		chooseKeysFrame.setPreferredSize(new Dimension(CHOOSE_KEY_BUTTON_WIDTH, CHOOSE_KEY_BUTTON_HEIGHT));
		chooseKeysFrame.add(chooseKeysButton);
		chooseKeysFrame.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
		chooseKeysFrame.setLocationRelativeTo(null);
		chooseKeysFrame.pack();
		chooseKeysFrame.setVisible(true);
		chooseKeysFrame.setFocusable(true);
		chooseKeysFrame.addKeyListener(new KeyListener()
		{
		    int counter = 0;

		    //The inspector complains about the e's,
		    //but this was added when we implemented KeyListeners.
		    @Override public void keyTyped(final KeyEvent e) {
			//To change body of implemented methods use File | Settings | File Templates.
		    }

		    @Override public void keyPressed(final KeyEvent e) {
			//To change body of implemented methods use File | Settings | File Templates.
			if (counter == 0) {
			    int leftKey = e.getKeyCode();
			    player.setLeft(leftKey);
			    counter++;
			} else if (counter == 1) {
			    int rightKey = e.getKeyCode();
			    player.setRight(rightKey);
			    counter++;
			} else if (counter == 2) {
			    int upKey = e.getKeyCode();
			    player.setUp(upKey);
			    counter++;
			} else if (counter == 3) {
			    int downKey = e.getKeyCode();
			    player.setDown(downKey);
			    ifChooseKeys = downKey;
			    counter++;
			}
		    }

		    @Override public void keyReleased(final KeyEvent e) {
			//To change body of implemented methods use File | Settings | File Templates.
		    }
		});
	    }
	});

	// Ok button that makes sure that you have put a color and keys.

	final JButton okButton = new JButton("OK");
	okButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
	okButton.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (color == null || ifChooseKeys == 0) {
		    chooseParameters(player);

		} else {
		    player.setPlayerName(name.getText());
		    player.setDefaultPlayerColor(color);
		    player.setPlayerColorNow(color);
		    player.setReady(true);
		    color = null;
		    ifChooseKeys = 0;
		    num++;
		    if (num != arena.getPlayers().size()) {
			chooseParameters(arena.getPlayers().get(num));
		    }
		}
		pane.setVisible(false);
		if (!isBreak && arena.isReadyToPlay()) {
		    arena.setPause(false);
		    arena.setEndGame(false);
		}
	    }
	});

	//Cancel button

	final JButton cancelButton = new JButton("Cancel");
	cancelButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
	cancelButton.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		pane.setVisible(false);
		arena.getPlayers().clear();
		isBreak = true;
		num = 0;
		showMenuButtons();
	    }
	});

	//Button for chooosing colors

	final JButton chooseColor = new JButton("Choose color");
	chooseColor.setSize(BUTTON_SIZE, BUTTON_SIZE);
	chooseColor.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) {
		color = JColorChooser.showDialog(null, "Choose a color", Color.blue);

	    }
	});

	JPanel myPanel = new JPanel();
	myPanel.add(new JLabel("Name:"));
	myPanel.add(name);
	myPanel.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
	myPanel.add(chooseColor);
	myPanel.add(chooseButtons);
	myPanel.add(Box.createVerticalStrut(SPACE_BETWEEN_BUTTONS));
	myPanel.add(okButton);
	myPanel.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
	myPanel.add(cancelButton);

	pane.setModal(false);
	pane.add(myPanel);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	pane.setLocation(dim.width / 2 - arena.getColumns() / 2, dim.height / 2 - arena.getRows() / 2);
	pane.pack();
	pane.setVisible(true);


    }

//----------------------------------------------------------------------------------------------------------------------



    //When the player is created this gives the player a random position
    public void randomPosition() {
	positions.x = generator.nextInt(arena.getColumns());
	positions.y = generator.nextInt(arena.getRows());
	if (arena.checkStartColision(positions.y, positions.x)) {
	    randomPosition();
	}

    }

    //Creates all the players
    public void createAllPlayers() {
	isBreak = false;

	if (numPlayers == 1) {
	    arena.setPause(true);
	    arena.createPlayers(arena.getRows() / 2, arena.getColumns() / 2, Directions.EAST);
	    for (Player player : arena.getPlayers()) {
		chooseParameters(player);
	    }


	} else if (numPlayers == 2) {
	    arena.setPause(true);
	    arena.createPlayers(arena.getRows() / 2, Arena.getDistanceFromLeft(), Directions.EAST);
	    arena.createPlayers(arena.getRows() / 2, Arena.getDistanceFromRight(), Directions.WEST);
	    chooseParameters(arena.getPlayers().get(num));
	} else {
	    for (int i = 0; i < numPlayers; i++) {
		arena.setPause(true);
		randomPosition();
		Directions dir = Directions.values()[generator.nextInt(4)];
		arena.createPlayers(positions.y, positions.x, dir);
	    }
	    chooseParameters(arena.getPlayers().get(num));
	}

	if (!isBreak && arena.isReadyToPlay()) {
	    arena.setPause(false);
	    arena.setEndGame(false);
	}

    }


    //Checks if a string is an integer or not. We use this method cause we could not
    //find the method in the java library and we have done this method before in our
    //algorithm course in java.
    public static boolean isInteger(String str) {
	if (str == null) {
	    return false;
	}
	int length = str.length();
	if (length == 0) {
	    return false;
	}
	int i = 0;
	if (str.charAt(0) == '-') {
	    if (length == 1) {
		return false;
	    }
	    i = 1;
	}
	//The inspector complaints about an initializer, but it is not needed.
	for (; i < length; i++) {
	    char c = str.charAt(i);
	    if (c <= '/' || c >= ':') {
		return false;
	    }
	}
	return true;
    }

    //Creates buttons for our main menu.
    public void createButtons() {

	final int buttonWidth = arena.getColumns() / 5;
	final int buttonHeight = arena.getRows() / 5;
	final int centreOfScreenWidth = arena.getColumns() / 2 - buttonWidth / 2;
	final int centreOfScreenHeight = arena.getRows() / 2 - buttonHeight / 2;
	final int textSize = buttonWidth / 8;
	final int distanceFromMidButton = buttonHeight + 10;

	startButton.setLocation(centreOfScreenWidth, centreOfScreenHeight - distanceFromMidButton);
	startButton.setSize(new Dimension(buttonWidth, buttonHeight));
	startButton.setFont(new Font("Century Gothic", Font.ITALIC, textSize));
	startButton.setVisible(true);
	tronComp.add(startButton);

	quitButton.setLocation(centreOfScreenWidth, centreOfScreenHeight);
	quitButton.setSize(new Dimension(buttonWidth, buttonHeight));
	quitButton.setFont(new Font("Century Gothic", Font.ITALIC, textSize));
	quitButton.setVisible(true);
	tronComp.add(quitButton);

	instructionsButton.setLocation(centreOfScreenWidth, centreOfScreenHeight + distanceFromMidButton);
	instructionsButton.setSize(new Dimension(buttonWidth, buttonHeight));
	instructionsButton.setFont(new Font("Century Gothic", Font.ITALIC, textSize));
	instructionsButton.setVisible(true);
	tronComp.add(instructionsButton);

	startButton.addActionListener(new ActionListener()
	{

	    public void actionPerformed(ActionEvent e) {
		num = 0;
		hideMenuButtons();
		tronComp.arenaChanged();
		chooseNumPlayers();

	    }
	});

	quitButton.addActionListener(new ActionListener()
	{

	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});

	instructionsButton.addActionListener(new ActionListener()
	{

	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Welcome to the game Tron." + '\n' + '\n' +
						    "The objective of this game is to make your opponent collide with the boundaries of the wall" +
						    "or the trail of your own path." + '\n' +
						    "The players controls consists of whatever you like except the 'p' and 'r' button, " +
						    "you can even choose your own color, just click start and you will be fine. " +
						    '\n' +
						    "To pause the game simply press 'p' and to restart the game press 'r'." +
						    '\n' + '\n' + "Enjoy!");
	    }
	});


    }


    @Override public void keyTyped(final KeyEvent e) {
	//To change body of implemented methods use File | Settings | File Templates.

    }

    @Override public void keyPressed(final KeyEvent e) {
	//To change body of implemented methods use File | Settings | File Templates.
	for (Player player : arena.getPlayers()) {
	    player.keyPressed(e);
	}
    }

    @Override public void keyReleased(final KeyEvent e) {
	//To change body of implemented methods use File | Settings | File Templates.
    }
}
