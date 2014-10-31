/***********************************************************************
 Program Name: GuessGame.java
 Programmer's Name: Steven Bennett
 Program Description: The Guess Game program generates a random number
 from 0 to 1000. The player continues until they guess the magic number.
 The player has the option of generating a new game during play.  The 
 message panel indicates if player is getting closer or farther from the
 magic number, and indicates if guess it above or below magic number.
 ***********************************************************************/ 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.Random;

public class GuessGame extends JFrame {
	
	//declare variables
	private JFrame mainFrame;
	private JLabel gameInstruction;
	private JLabel guessLabel;
	private JTextField guessField;
	private JTextField messageField;
	private JButton guessButton;
	private JButton newGameButton;
	private JButton exitGameButton;
	private Random randomNumberGenerator;
	private int magicNumber;
	private int guessCounter;
	private int guess; //integer variable to hold player's guess.
	private int lastDistance = 0; //holds distance of last guess from magicNumber.
	private int currentDistance; //holds distance of current guess from magicNumber.
	private boolean isNotCorrect = true; //boolean to determine if distance of guess should be checked.
	
	//GuessGame constructor
	public GuessGame(){
		
		//instantiate Random object to generate for game to begin
		randomNumberGenerator = new Random();
		magicNumber = randomNumberGenerator.nextInt(1001); //generates random integer from 0 (inclusive) to 1001 (exclusive).
		
		//instantiate mainFrame, label, text field, and button objects
		mainFrame = new JFrame("Guessing Game");
		gameInstruction = new JLabel("I have a number between 0 and 1000--Can you guess my number?");
		guessLabel = new JLabel("Enter guess: ");
		guessField = new JTextField(4);
		messageField = new JTextField(33);
		guessButton = new JButton("Submit Guess");
		newGameButton = new JButton("New Game");
		exitGameButton = new JButton("Exit");
		
		//create mainframe, set layout for program, add event to close program with "X" window button.
		Container c = mainFrame.getContentPane();
		c.setLayout(new FlowLayout());
		mainFrame.setBounds(800, 300, 400, 200);
		mainFrame.setVisible(true);
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		//add components to container
		c.add(gameInstruction);
		c.add(guessLabel);
		c.add(guessField);
		c.add(guessButton);
		c.add(messageField);
		c.add(newGameButton);
		c.add(exitGameButton);
		
		//set Mnemonics for each Button
		guessButton.setMnemonic('S');
		newGameButton.setMnemonic('N');
		exitGameButton.setMnemonic('X');
		
		//disable messageField so guess hints can not be altered.
		messageField.setEditable(false);
		
		//add action listeners to buttons
		GuessButtonHandler gbHandler = new GuessButtonHandler();
		guessButton.addActionListener(gbHandler);
		
		ExitGameButtonHandler egbHandler = new ExitGameButtonHandler();
		exitGameButton.addActionListener(egbHandler);
		
		NewGameButtonHandler ngbHandler = new NewGameButtonHandler();
		newGameButton.addActionListener(ngbHandler);
	}
		
		
	/*Section of event handlers*/
	
	//submitGuessButton handler
	class GuessButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			boolean validGuess = true; //if false will not execute check of player guess against magicNumber.
			
			try{
				guessCounter++;
				guess = Integer.parseInt(guessField.getText());
				if(guess < 0 || guess > 1000){
					validGuess = false;
					JOptionPane.showMessageDialog(null, "Enter a digit from 0 to 1000.", "INPUT ERROR!", JOptionPane.ERROR_MESSAGE);
					guessField.setText("");
					guessCounter--;
				}
			}
			catch(Exception ex){
				validGuess = false;
				JOptionPane.showMessageDialog(null, "Enter a digit from 0 to 1000.", "INPUT ERROR!", JOptionPane.ERROR_MESSAGE);
				guessField.setText("");
				guessCounter--;
			}
			
			if(validGuess == true){
				//check if guess equals magicNumber.
				if(guess == magicNumber){
					messageField.setText("Congratulations! You guessed the Magic Number in " + guessCounter + " tries!");
					messageField.setBackground(Color.green);
					messageField.setForeground(Color.black);
					guessField.setEditable(false);
					guessButton.setEnabled(false);
					isNotCorrect = false;
				}
				else{
					if(guess > magicNumber){
						messageField.setText("Too High. Guess Again.");
						guessField.setText("");
					}	
					else{
						messageField.setText("Too Low. Guess Again.");
						guessField.setText("");
					}
				}
				
				//Determine what color to change messageField to indicate if player is getting closer
				//or farther away from magic number.
				if(isNotCorrect == true){
					currentDistance = Math.abs(guess - magicNumber);
					if(guessCounter != 1){
						if (currentDistance < lastDistance){
							messageField.setBackground(Color.red);
							messageField.setForeground(Color.white);
						}
						else{
							messageField.setBackground(Color.blue);
							messageField.setForeground(Color.white);
						}
						lastDistance = currentDistance;
					}
					else{
						lastDistance = currentDistance;
					}
				}
			}
		}
		
	}//end GuessButtonHandler.
		
	//exitButton handler
	class ExitGameButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
	
	//newGameButton handler
	class NewGameButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			magicNumber = randomNumberGenerator.nextInt(1001);
			guessField.setText("");
			guessField.setEditable(true);
			guessButton.setEnabled(true);
			messageField.setText("");
			messageField.setBackground(Color.white);
			messageField.setForeground(Color.black);
			guessCounter = 0;
			isNotCorrect = true;
		}
	}

}
