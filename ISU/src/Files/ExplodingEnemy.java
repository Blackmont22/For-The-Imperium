package Files;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author Cooper E.
 *//*The supplementary ExplodingEnemy class to my main ISU3 one. This one contains all of the logic for spawning and controlling enemies throughout the game, including the final boss as I didn't have time to create another enemy model. Some of the key features that these enemies have include a health scaling system dependent on how far the player is in the game (and based off of how much damage their attacks do), a damage scaling system, a randomized spawn location, and a random number of enemies per area.*/

public class ExplodingEnemy extends JPanel implements ActionListener { //Although this file doesn't have a main method, it extends JPanel as these enemies were used in the ISU3 class's JPanel. Throughout my program, all 3 files were linked so that calculations and other logic could be executed in the "correct" spots; I didn't want to have to calculate enemy damage in the ISU3 class as that's solely for controlling the game, for example.
    boolean[][] enemyInitialized = new boolean[100][6], isEnemyAlive = new boolean[100][6]; //Boolean values have higher-than-needed indices accessible to them for the first dimension of their array as I didn't know how many areas I was going to program into the game, and decided to keep them at [100][6] because the loss in performance/storage space should be negligible. 
    ImageIcon[][] enemyImages = new ImageIcon[100][6]; 
    ISU3 isu3; //I create an instance of the ISU3 class here so that I can access its properties, most notably health (isu3.health) and EXP (isu3.EXP)
    int[][] upVel = new int[100][6], downVel = new int[100][6], leftVel = new int[100][6], rightVel = new int[100][6], deltaX = new int[100][6], deltaY = new int[100][6], enemyX = new int[100][6], enemyY = new int[100][6], enemyHealth = new int[100][6]; //A variety of integer arrays controlling variables to track the enemy's position and health. The first dimension of the array represents the scene/area the enemies are used in, and the second dimension is for tracking the individual enemies themselves
    Rectangle[][] enemies = new Rectangle[100][6];
    Timer movementTimer = new Timer(500, this); //I wanted the enemies to move sluggishly to play off of both the psychological aspects, and for gameplay reasons. The players have more time to adjust their position to one where they can actually attack the enemy when the enemy moves slower, and they have options to pick up items/use them or even avoid the enemies entirely when they move slower
    
    public ExplodingEnemy() { //A constructor where a random amount of enemies (between 0 and 5) are spawned per area and considered to be "alive". Only up to the 20th element is accessed in the first dimension of the array, but setting it to 100 has the added benefit of not having to modify that dimension if I were to add more areas to the game later
        movementTimer.start();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) { //Iterates through the array and essentially "initializes" the enemies. This is all done here so that I could ensure this only runs once, as enemies explicitly don't respawn after you kill them; that would result in a very unfair final boss fight (see below)
            for (int j = 0; j < rand.nextInt(7); j++) {
                isEnemyAlive[i][j] = true;
            } 
        }
    }
    
    public void setISU3(ISU3 isu3) { //I set an instance of ISU3 here for each enemy (see the ISU3 file), letting the ISU3 file and its properties become accessible to the ExplodingEnemy file. This was done so that enemy health calculations and damage calculations could be performed in this file, as opposed to in the ISU3 file.
        this.isu3 = isu3; //The keyword "this" is used to distinguish between the isu3 object in this particular method, and the field that's created above, also named isu3. It assigns the value of the isu3 that's passed to the method TO the isu3 field, linking the two files. I didn't want this to happen in the constructor, as I didn't want it to take in any parameters.  
    }
    
    public void setInitialPosition(Graphics g, int scene, int index, int x, int y) { //Initializes the enemy, taking in a Graphics variable, a given scene and index (for the two-dimensional array that's being accessed at a certain point), and the x and y values of a certain enemy
        Random rand = new Random();
        if (isEnemyAlive[scene][index] && enemies[scene][index] == null) { //If the enemy rectangle hasn't been assigned yet, I initialize the enemies. I use this as a criterion to prevent the program from crashing when it tries to access the enemies rectangle array before it's properly initialized
            if (!enemyInitialized[scene][index]) { //If the enemy at a given scene and index hasn't been initialized, I set up their health, assign them a default/base ImageIcon, create their collision rectangle, and set their spawn point/initial coordinates to the x and y values passed from the ISU3 file to ExplodingEnemy
                if (scene != 20) { //The area corresponding to where the final boss fight takes place is in scene 20; this scene variable is not to be confused with the one used in the ISU3 file, as this corresponds to strictly combat scenes, where enemies are spawned in. ISU3's scenes are used and incremented for any differentiable area/setting
                enemyHealth[scene][index] = rand.nextInt(70) + 150 + (isu3.scene * 5); //Scales enemy health dependant on the scene, gives them a base HP amount, and otherwise introduces an element of randomness into the calculations
                } else if (scene == 20 && isu3.necromancyRevival) { //If the boolean value necromancyRevival is true (the players didn't drink the potion to nullify/weaken the necromancer's abilities), I give the final boss much more health 
                    enemyHealth[scene][index] = rand.nextInt(500) + 750 + (isu3.killCount * 50); //Scales heavily off of kill count to balance the abilities of a player at a certain level with the health the boss has
                } else if (scene == 20 && !isu3.necromancyRevival) {
                    enemyHealth[scene][index] = rand.nextInt(500) + 500 + (isu3.killCount * 25); //Final boss is much weaker if the potion to nullify their abilities is consumed
                }
                enemyImages[scene][index] = new ImageIcon(getClass().getResource("/Images/enemyFront.png")); //Performs the rest of the initialization and sets the boolean enemyInitialized as true so as to avoid any of this code running again when it shouldn't
                enemies[scene][index] = new Rectangle(0, 0, 0, 0);
                enemyInitialized[scene][index] = true;
                enemyX[scene][index] = x;
                enemyY[scene][index] = y;
            }
        }
        enemies[scene][index] = new Rectangle(enemyX[scene][index], enemyY[scene][index], (int)(isu3.width() * 125), (int)(isu3.height() * 150)); //Outside of the if statement, I now assign the enemy's rectangle to their current position/width/height, and calculate how far away they are from the player (deltaY and deltaX)
        deltaX[scene][index] = (isu3.x + (isu3.player.width/2)) - enemies[scene][index].x;
        deltaY[scene][index] = isu3.y - enemies[scene][index].y;
        move(g, scene, index); //I trigger the enemies to move after they've been initialized
    }
    
    public void move(Graphics g, int scene, int index) {
        if (enemies[scene][index] != null) {
            Random rand = new Random();
            if (Math.abs(deltaX[scene][index]) > Math.abs(deltaY[scene][index])) { //If the enemies are further away horizontally than they are vertically, they move along the left/right axis
                if (deltaX[scene][index] < 0) { //If the enemies are to the right of the player's x-coordinate, they move left at a random velocity between 1 and 3
                    leftVel[scene][index] = rand.nextInt(3);
                    rightVel[scene][index] = 0;
                } else {
                    rightVel[scene][index] = rand.nextInt(3); //If those conditions aren't met, they move to the right
                    leftVel[scene][index] = 0;
                }
                upVel[scene][index] = 0; //I make sure they stop moving in the vertical direction as well
                downVel[scene][index] = 0;
            } else {
                if (deltaY[scene][index] < 0) { //If the enemies are further away vertically from the player than they are horizontally, they move on the y-axis
                    upVel[scene][index] = rand.nextInt(3);
                    downVel[scene][index] = 0;
                } else { //If the enemies are above the player, they move down, and vice versa
                    downVel[scene][index] = rand.nextInt(3);
                    upVel[scene][index] = 0;
                }
                leftVel[scene][index] = 0;
                rightVel[scene][index] = 0;
            }
            drawEnemy(g, scene, index); //When this is done, the enemy is drawn
        }
    }
    
    public void drawEnemy(Graphics g, int scene, int index) { //Used to draw the enemies at a given scene and index, determine what ImageIcon to use, and check for intersection between the enemy and player
        if (enemies[scene][index] != null) { //A lot of the methods check for if enemies[scene][index] != null because I want to make sure, with 100% certainty, that the rectangle for the enemies isn't being accessed before it is created; it's just a preventative measure to stop the program from crashing
            drawHealth(g, scene, index); //I draw the health bar for the enemy, check if the enemy intersects the player character's rectangle, and then check to see if the player's attacks intersect the enemy itself
            checkEnemyIntersection(g, scene, index);
            checkPlayerIntersection(scene, index);
            if (upVel[scene][index] > 0) { //I assign the corresponding ImageIcons to each enemy depending on which direction they're moving in, which is determined based on their current velocity
                enemyImages[scene][index] = new ImageIcon(getClass().getResource("/Images/enemyBack.png"));
            } else if (downVel[scene][index] > 0) {
                enemyImages[scene][index] = new ImageIcon(getClass().getResource("/Images/enemyFront.png"));
            } else if (leftVel[scene][index] > 0) { 
                enemyImages[scene][index] = new ImageIcon(getClass().getResource("/Images/enemyLeft.png"));
            } else if (rightVel[scene][index] > 0) {
                enemyImages[scene][index] = new ImageIcon(getClass().getResource("/Images/enemyRight.png"));
            }
            updateEnemyPosition(scene, index); //I update each enemy's position, and then finally draw their images at the corresponding coordinates. I use g.drawImage as this lets me be flexible with the width and height of the image in the actual IDE, rather than having to change it manually as I would have to do with g.paintIcon
            g.drawImage(enemyImages[scene][index].getImage(), enemyX[scene][index], enemyY[scene][index], (int)(isu3.width() * 125), (int)(isu3.height() * 150), this);
        }
    }
    
    public void drawHealth(Graphics g, int scene, int index) { //Draws the health bar above the enemy's head (essentially a red rectangle)
        g.setColor(Color.RED);
        if (enemies[scene][index] != null) {
            g.fillRect((enemies[scene][index].x + (int)(isu3.width() * 48)), (int)(enemies[scene][index].y - (isu3.height() * 10)), (enemyHealth[scene][index]/10), (int)(isu3.height() * 5)); //I don't draw the rectangle for the enemies 1-to-1 as that would simply be too large/long of a rectangle, instead using ratios of 5 and 10 to draw a scaled-down version of it 
        }
    }
    
    public void checkEnemyIntersection(Graphics g, int scene, int index) { //If a given enemy rectangle intersects the player character, the enemies explode.
        if (enemies[scene][index] != null) {
            if (enemies[scene][index].intersects(isu3.fullCharacter)) { //fullCharacter is simply the rectangle covering the whole player character, as opposed to the "player" rectangle that's used for collisions
                explode(g, scene, index);
            }
        }
    }
    
    public void checkPlayerIntersection(int scene, int index) { //Checks to see if the player's attack intersects the enemy's hitbox. If so, it determines how much health the enemy loses dependent on the attack the player uses. This is done to encourage players to switch between armour sets and vary up their attacks as what works in one area may not work in another
        if (enemies[scene][index] != null) {
            if (isu3.isAttacking) { //A boolean value that is triggered to false once the attack keys are released and vice versa. It was initialized to prevent the enemies from taking damage from a previous attackCollision rectangle
                if (isu3.attackCollision1.intersects(enemies[scene][index]) || isu3.attackCollision2.intersects(enemies[scene][index]) || isu3.attackCollision3.intersects(enemies[scene][index]) || isu3.attackCollision4.intersects(enemies[scene][index])) {
                    if (scene >= 0 && scene < 6) { //In the forest area, the enemies are resistant to piercing, bludgeoning, slashing, and poison-type attacks, are weak to fire, and take normal damage from everything else
                        if (isu3.damageType.equals("piercing") || isu3.damageType.equals("bludgeoning") || isu3.damageType.equals("slashing") || isu3.damageType.equals("poison")) {
                            enemyHealth[scene][index] -= (isu3.damage/2);
                        } else if (isu3.damageType.equals("fire")) {
                            enemyHealth[scene][index] -= (isu3.damage + 5);
                        } else {
                            enemyHealth[scene][index] -= isu3.damage; //The shortcut -= is used to save time (as opposed to enemyHealth[scene][index] = enemyHealth[scene][index] - isu3.damage)
                        }
                    } else if (scene > 5 && scene < 15) { //In the grasslands , the enemies are resistant to radiant and lightning damage, and take slightly reduced damage from everything else. 
                        if (!isu3.damageType.equals("radiant") && !isu3.damageType.equals("lightning")) {
                            enemyHealth[scene][index] -= isu3.damage/2;
                        } else {
                            enemyHealth[scene][index] -= isu3.damage - 2;
                        }
                    } else if (scene > 14 && scene < 20) { //In the dungeon, the enemies are very resistant to bludgeoning, and otherwise take reduced damage from everything else.
                        if (!isu3.damageType.equals("bludgeoning")) {
                            enemyHealth[scene][index] -= isu3.damage/4;
                        } else {
                            enemyHealth[scene][index] -= (isu3.damage - 2);
                        }
                    } else if (scene == 20) { //The final boss is extremely resistant to fire, and slightly weak to piercing, bludgeoning, slashing, and poison damage, taking reduced damage from everything else
                        if (isu3.damageType.equals("fire")) {
                            enemyHealth[scene][index] -= (isu3.damage/3);
                        } else if (isu3.damageType.equals("piercing") || isu3.damageType.equals("bludgeoning") || isu3.damageType.equals("slashing") || isu3.damageType.equals("poison")) {
                            enemyHealth[scene][index] -= (isu3.damage + 1);
                        } else {
                            enemyHealth[scene][index] -= isu3.damage - 1;
                        }
                    }
                } if (enemyHealth[scene][index] <= 0) { //If the enemy's health at a given scene and index reaches 0, the player is rewarded for killing them 
                    isEnemyAlive[scene][index] = false;
                    playerReward(scene);
                }
            }
        }
    }
    
    public void updateEnemyPosition(int scene, int index) { //Updates the enemy's position at a given scene and index; same movement logic that the player uses, except now applied to multiple enemies and with different hitbox dimensions
        if (enemies[scene][index] != null) {
            enemyX[scene][index] += (-leftVel[scene][index] + rightVel[scene][index]);
            enemyY[scene][index] += (-upVel[scene][index] + downVel[scene][index]);
            enemies[scene][index] = new Rectangle(enemyX[scene][index], enemyY[scene][index], (int)(isu3.width() * 125), (int)(isu3.height() * 150));
        } 
    }

    public void explode(Graphics g, int scene, int index) { //The method that determines how much damage the player takes when an enemy explodes.
        Random rand = new Random();
        if (enemies[scene][index] != null) {
            enemyImages[scene][index] = new ImageIcon(getClass().getResource("/Images/attack8.png")); //Uses the same, default explosion animation that the player has for their 8th attack
            g.drawImage(enemyImages[scene][index].getImage(), enemies[scene][index].x, enemies[scene][index].y, (int)(isu3.width() * 125), (int)(isu3.height() * 150), this);
            if (scene != 20) { //In any normal scene, once the enemies explode, they aren't alive anymore and their health is set to 0. This doesn't happen in the boss room because the fight would be over very quickly if the same logic was used.
                isEnemyAlive[scene][index] = false;
                enemyHealth[scene][index] = 0;
            }
            if (isu3.defense != 0) { //If the player's defense is not 0 (they're using the medium/heavy armour sets), the amount of damage the enemy deals is dependant on if they're a boss enemy or not
                if (scene != 20) {
                    isu3.health -= Math.ceil(isu3.scene/isu3.defense) - rand.nextInt(4); //In any normal scene, the damage the enemy deals is equal to the ISU3 scene (up to 37), divided by the amount of defense the player has (heavy armour = 2, medium = 1, and light = 0), and all of this is subtracted from a number from 0 through 3 to weaken the explosions a tiny bit
                } else {
                    isu3.health--; //If the scene is 20, whenever the boss intersects the player, their health goes down by 1. 
                }
            } else { //If the defense is 0, damage is equal to the scene minus a random integer from 0 through 3
                isu3.health -= isu3.scene - rand.nextInt(4);
            }
        }
    }
    
    public void playerReward(int scene) { //The player is given 1 experience point per enemy killed, a random amount of gold between 0 and 499, and their kill count increases by 1.
        Random rand = new Random();
        if (scene != 20) {
        isu3.killCount++;
        isu3.gold += rand.nextInt(500);
        isu3.EXP++;
        }
    }
  
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); 
    }
}