package Files;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Cooper E.
 *///*The inventory class that handles the selection and obtaining of items in the game. It uses a different JPanel because I didn't want the gameplay screen to be changed if the inventory was opened, and wanted to give players the challenge of having to navigate between the two panels to use/sell items. After all, it isn't an RPG without an inventory.*/

public class Inventory extends JPanel implements ActionListener, KeyListener { //Implements KeyListener as there's a navigation element to the inventory (when the player presses W/A/S/D, the inventory cursor moves up/left/down/right)
    int currentRow = 1; //Integer fields to track where the inventory cursor is, currently. 
    int currentColumn = 1;
    int r = 0, c = 0;
    ISU3 isu3; //Another instance of ISU3 is passed to the inventory class (using the same logic as the ExplodingEnemy class to do so); this.isu3 is also used here, and I included it in my constructor this time because creating a seperate method would simply add two lines of code to the total without contributing anything meaningful.
    Timer timer = new Timer(3, this);
    static boolean[][] isItemFound = new boolean[3][8]; //Tracks if an item is in the actual inventory itself
    ImageIcon question = new ImageIcon(getClass().getResource("/Images/ItemNotFound.png")), BaseChar = new ImageIcon (getClass().getResource("/Images/BaseChar.png")), CharArmour = new ImageIcon (getClass().getResource("/Images/CharArmour.png")), fullChar = new ImageIcon(getClass().getResource("/Images/CharFull.png")), healthPotion = new ImageIcon(getClass().getResource("/Images/healthPotion.png")), damagePotion = new ImageIcon(getClass().getResource("/Images/damagePotion.png")), speedPotion = new ImageIcon(getClass().getResource("/Images/speedPotion.jpg")), experiencePotion = new ImageIcon(getClass().getResource("/Images/experiencePotion.png")), necromancyPotion = new ImageIcon(getClass().getResource("/Images/necromancyPotion.jpg")), bronzeKey = new ImageIcon(getClass().getResource("/Images/bronzeKey.jpg")), silverKey = new ImageIcon(getClass().getResource("/Images/silverKey.jpg")), goldKey = new ImageIcon(getClass().getResource("/Images/goldKey.jpg")), forestPortal = new ImageIcon(getClass().getResource("/Images/forestPortal.jpg")), dungeonPortal = new ImageIcon(getClass().getResource("/Images/dungeonPortal.png")), escapePortal = new ImageIcon(getClass().getResource("/Images/escapePortal.jpg")), goldPouch = new ImageIcon(getClass().getResource("/Images/goldPouch.png")), largeGold = new ImageIcon(getClass().getResource("/Images/largeGold.jpg")), fireballSpell = new ImageIcon(getClass().getResource("/Images/fireballSpell.png")), lightningSpell = new ImageIcon(getClass().getResource("/Images/lightningSpell.jpg")), meteorSwarm = new ImageIcon(getClass().getResource("/Images/meteorSwarm.jpg")), poisonCloud = new ImageIcon(getClass().getResource("/Images/poisonCloud.png")), phaseShift = new ImageIcon(getClass().getResource("/Images/phaseShift.png")), doubleDamage = new ImageIcon(getClass().getResource("/Images/doubleDamage.png")), maxLevel = new ImageIcon(getClass().getResource("/Images/maxLevel.png")), instaDeath = new ImageIcon(getClass().getResource("/Images/instaDeath.jpg"));
    ImageIcon[][] list = {{BaseChar, CharArmour, fullChar, healthPotion, damagePotion, speedPotion, experiencePotion, necromancyPotion}, {bronzeKey, silverKey, goldKey, forestPortal, dungeonPortal, escapePortal, goldPouch, largeGold}, {fireballSpell, lightningSpell, meteorSwarm, poisonCloud, phaseShift, doubleDamage, maxLevel, instaDeath}};
    static JFrame window; //Uses a static JFrame as it needs to be accessible from the main method of ISU3 (which also is passed an instance of inventory so that the two class files can communicate back and forth.
    
    public Inventory(ISU3 isu3) {
        this.isu3 = isu3;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        for (int row = 0; row < 3; row++) { //Initially sets all of the items to false in the inventory as they haven't been found yet
            for (int column = 0; column < 8; column++) {
                isItemFound[row][column] = false;
            }
        }
        timer.start();
    }
    
    public void start() { //A method responsible for creating the inventory's JPanel and JFrame, setting its size, and giving it a title
        Inventory panel = new Inventory(isu3); //The panel takes properties from the inventory constructor to function, and is then initialized much like the ISU3 panel is. 
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("INVENTORY");
        window.add(panel);
        window.pack();
        window.setSize(800, 450);
        window.setVisible(true);
    }
    
    public void itemDisplay(Graphics g, int rows, int columns, int width, int height) { //A method that controls the visibility of items in the inventory. If they're discovered, theyy are drawn at their respective slots in the inventory. If not, a placeholder question mark is drawn.
        isItemFound[0][0] = true; //Initializes the base player character model as "found" because the player will always revert to that character model if they can't access any other ones, and can't sell it off (see the ISU3 file).
        for (r = 0; r < rows; r++) { //Iterates over the entire inventory to check if an item is found at a given row and column, then adjusts accordingly
            for (c = 0; c < columns; c++) {
                if (isItemFound[r][c]) {
                    revealItemIcon(width, height, g, r, c); 
                } else {           
                    Image emptySlot = question.getImage();
                    g.drawImage(emptySlot, c * width, r * height, width, height, this);
                }
            }
        }
    }
   
    public void revealItemIcon(int width, int height, Graphics g, int r, int c) { //Obtains the respective image from the list field, and draws it in the correct slot
        Image image = list[r][c].getImage();
        g.drawImage(image, c * width, r * height, width, height, this);
    }
    
    public void openWindow() { //A method that is called whenever the key "I" is pressed, opening/enlarging the inventory window.
        window.setSize(800, 450);
    }
    
    public void closeWindow() { //A method that is called whenever the "C" key is pressed, closing/shrinking the inventory window.
        window.setSize(1, 1);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rows = 3, columns = 8; 
        int width = getWidth()/columns; //Width and height determine the measurements for the columns/rows in the inventory
        int height = getHeight()/rows;
        itemDisplay(g, rows, columns, width, height);
        for (r = 0; r < rows; r++) {
            for (c = 0; c < columns; c++) { //currentRow and currentColumn are both always 1 greater than the actual index of the column/row that's selected. As opposed to being coloured grey, the box is coloured yellow to make it clear what item the user currently has selected
                if (r == currentRow - 1 && c == currentColumn - 1) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(c * width, r * height, width, height);
                } else { //If the user isn't currently selecting the row/column, it reverts to a default grey colour
                    g.setColor(Color.GRAY);
                    g.drawRect(c * width, r * height, width, height); 
                }
            }
        }
    }
  
    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (window.getWidth() > 1 && window.getHeight() > 1) { //If the inventory is open, then all of these key presses go through. If not, they aren't effected (as I don't want key misclicks to change the current column/row the user is on in the closed inventory.
            if (c == KeyEvent.VK_W && currentRow > 1) { //Makes sure the current row is within bounds, and then moves the cursor (currentRow) up 1.
                currentRow--;
                isu3.currentRow--;
            } if (c == KeyEvent.VK_A && currentColumn > 1) { //Makes sure the current column is within bounds, then moves it to the left by 1.
                currentColumn--;
                isu3.currentColumn--;
            } if (c == KeyEvent.VK_S && currentRow < 3) { //Same deal as above, moving the current row down one.
                currentRow++;
                isu3.currentRow++;
            } if (c == KeyEvent.VK_D && currentColumn < 8) { //Same as above, moving the current column right by one.
                currentColumn++;
                isu3.currentColumn++;
            } if (c == KeyEvent.VK_C) {
                closeWindow();
            } if (c == KeyEvent.VK_I) { 
                openWindow();
            } 
            repaint();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override 
    public void keyReleased(KeyEvent e) {
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}