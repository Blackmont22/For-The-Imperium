package Files;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.Random;

/**
 *
 **
 *///*ISU - Cooper Edwards - January 22nd, 2023 - A 2D Role-Playing Game called "For The Imperium," whereby the player assumes control of a 70-year-old, heroic paladin named Grondheim Stormborn who was resurrected by a faction known as the Nightfall Imperium, and whose sole purpose is to put an end to a necromancer hiding out in a dungeon. The game's story is largely optional, and is only hinted at through dialogue, quests, and cutscenes. Notable features that the game contains includes: 1. A title screen consisting of static text over changing images; 2. An intro (and technically outro) cutscene with scrolling, centred text over a static image that changes size depending on how large the JPanel is. 3. A controls screen, guide screen (that changes depending on the area the player is in) and a death screen; 4. An inventory system with 24 items able to be obtained, ranging from attack boosts to different armour sets, potions, keys, and teleportation portals; 5. A buying/selling mechanism with merchants, where items can be exchanged for gold and vice versa; 6. Randomized enemy placements, amounts, damage, and health throughout all 5 main areas that scales with how far the player is in the game; 7. A randomized chest system where the player can obtain items from chests on the ground; 8. An intricate combat system with enemies being resistent to certain attacks and the player obtaining different attacks at different levels depending on what suit of armour they wear; 9. A level-up system, based on EXP progression; when the player levels up, their health increases, and they gain access to new abilities; 10. `A final boss (although it uses the same logic as the enemies, it has varying amounts of health dependent on how many enemies the player kills, and if they consume a certain potion to nullify the necromancer's abilities; 11. A quest system (where 3 NPC's have completable quests that affect the game's overall narrative, but are ultimately optional); 12. An option to talk to NPC's (there are 7 NPCs and they cycle through 9 dialog options each); 13. Interaction with miscellaneous items in the world such as obelisks (attempt to kill the player) and bonfires (a healing mechanism), where the bonfires also serve as strategically-placed checkpoints; 14. 22 minutes of music, spread out across 14 tracks that I composed myself as another extra feature; the tracks change based on the area of the game that the player is in; and 15. 30 different endings, based on a variety of conditions that the player meets at the end of the game (ex: whether they kill the final boss or join him, whether they complete all, some, or no quests offered, whether they have a certain amount of gold in their inventory or no gold, whether they drank the potion to nullify the necromancer's abilities or not, and based on their death count)*/

public class ISU3 extends JPanel implements ActionListener, KeyListener {
    boolean isAttacking, phrasesLoaded, guideOpened, controlsOpened, inventoryOpen, doubleDamageExchanged, necromancyRevival = true, noCollisions, keyInserted, silverKeyInserted, goldKeyInserted, townDiscovered, refugeFound, dungeonDelved, questAccepted = false, quest2Accepted = false, quest3Accepted = false, questCompleted, quest2Completed, quest3Completed, rewardGiven, reward2Given, reward3Given, battleUndecided = true, battleConfirmed, requestShown, bossDead; //Multiple self-explanatory boolean values that are used to track the state of the inventory, final boss, checkpoints, and other elements of the game.
    Clip clip; //An audio clip; not used in any other context than playing and loading audio files. 
    Color color1 = new Color(255, 255, 255, 150); //A slightly-grey colour that I use to increase the transparency of the images in the title screen, so that the text overtop can still be seen
    File audioFile; //A variable representing the file itself that is played when audio is needed 
    FontMetrics fontMetrics; //A variable that determines the different properties of a font in java, such as its width and height; I use this to centre the font to the screen in the title screen and during the intro/epilogue cutscenes, by determining the width of a given string and positioning it at a certain (x, y) value based on that
    ImageIcon cavern = new ImageIcon(getClass().getResource("/Images/Cavern.png")), mountain = new ImageIcon(getClass().getResource("/Images/Area1a.png")), snow = new ImageIcon(getClass().getResource("/Images/Area1b.jpg")), guardTower = new ImageIcon(getClass().getResource("/Images/Area2a.jpg")), merchants = new ImageIcon(getClass().getResource("/Images/Area2b.jpg")), camps = new ImageIcon(getClass().getResource("/Images/Area2c.jpg")), hole = new ImageIcon(getClass().getResource("/Images/Area2d.jpg")), innerPit = new ImageIcon(getClass().getResource("/Images/Area2e.jpg")), forestEntrance = new ImageIcon(getClass().getResource("/Images/Area3a.jpg")), forestRight = new ImageIcon(getClass().getResource("/Images/Area3b.jpg")), forestSouth = new ImageIcon(getClass().getResource("/Images/Area3c.jpg")), forestLeft = new ImageIcon(getClass().getResource("/Images/Area3d.jpg")), forestPortal = new ImageIcon(getClass().getResource("/Images/Area3e.jpg")), grasslandsEntrance = new ImageIcon(getClass().getResource("/Images/Area4a.jpg")), clearing = new ImageIcon(getClass().getResource("/Images/Area4b.jpg")), refuge = new ImageIcon(getClass().getResource("/Images/Area4c.jpg")), grasslandExpanse = new ImageIcon(getClass().getResource("/Images/Area4d.jpg")), dungeonExterior = new ImageIcon(getClass().getResource("/Images/Area4l.jpg")), dungeon1 = new ImageIcon(getClass().getResource("/Images/Area5a.jpg")), dungeon2 = new ImageIcon(getClass().getResource("/Images/Area5b.jpg")), dungeon3 = new ImageIcon(getClass().getResource("/Images/Area5c.jpg")), dungeon4 = new ImageIcon(getClass().getResource("/Images/Area5d.jpg")), dungeon5 = new ImageIcon(getClass().getResource("/Images/Area5e.jpg")), dungeon6 = new ImageIcon(getClass().getResource("/Images/Area5f.jpg")), dungeon7 = new ImageIcon(getClass().getResource("/Images/Area5g.jpg")), dungeon8 = new ImageIcon(getClass().getResource("/Images/Area5h.jpg")), bossRoom = new ImageIcon(getClass().getResource("/Images/Area5i.jpg")), chest = new ImageIcon(getClass().getResource("/Images/Chest1.png")), guide = new ImageIcon(getClass().getResource("/Images/Instructions.png")), guide2 = new ImageIcon(getClass().getResource("/Images/guide2.png")), guide3 = new ImageIcon(getClass().getResource("/Images/guide3.png")), guide4 = new ImageIcon(getClass().getResource("/Images/guide4.png")), guide5 = new ImageIcon(getClass().getResource("/Images/guide5.png")), BaseChar = new ImageIcon(getClass().getResource("/Images/BaseChar.png")), CharArmour = new ImageIcon(getClass().getResource("/Images/CharArmour.png")), CharFull = new ImageIcon(getClass().getResource("/Images/CharFull.png")), BaseCharRight = new ImageIcon(getClass().getResource("/Images/BaseCharRight.png")), BaseCharLeft = new ImageIcon(getClass().getResource("/Images/BaseCharLeft.png")), BaseCharBack = new ImageIcon(getClass().getResource("/Images/BaseCharBack.png")), CharArmourRight = new ImageIcon(getClass().getResource("/Images/CharArmourRight.png")), CharArmourLeft = new ImageIcon(getClass().getResource("/Images/CharArmourLeft.png")), CharArmourBack = new ImageIcon(getClass().getResource("/Images/CharArmourBack.png")), CharFullBack = new ImageIcon(getClass().getResource("/Images/CharFullBack.png")), CharFullRight = new ImageIcon(getClass().getResource("/Images/CharFullRight.png")), CharFullLeft = new ImageIcon(getClass().getResource("/Images/CharFullLeft.png")), attack1 = new ImageIcon(getClass().getResource("/Images/attack1.png")), attack2 = new ImageIcon(getClass().getResource("/Images/attack2.png")), attack3 = new ImageIcon(getClass().getResource("/Images/attack3.png")), attack4 = new ImageIcon(getClass().getResource("/Images/attack4.png")), attack5 = new ImageIcon(getClass().getResource("/Images/attack5.png")), attack6 = new ImageIcon(getClass().getResource("/Images/attack6.png")), attack7 = new ImageIcon(getClass().getResource("/Images/attack7.png")), attack8 = new ImageIcon(getClass().getResource("/Images/attack8.png")), healthPotion = new ImageIcon(getClass().getResource("/Images/healthPotion.png")), speedPotion = new ImageIcon(getClass().getResource("/Images/speedPotion.jpg")), damagePotion = new ImageIcon(getClass().getResource("/Images/damagePotion.png")), portal1 = new ImageIcon(getClass().getResource("/Images/forestPortal.jpg")), shinyKey = new ImageIcon(getClass().getResource("/Images/silverKey.jpg")), fireball = new ImageIcon(getClass().getResource("/Images/fireballSpell.png")), lightning = new ImageIcon(getClass().getResource("/Images/lightningSpell.jpg")), goldBars = new ImageIcon(getClass().getResource("/Images/largeGold.jpg")), moreGold = new ImageIcon(getClass().getResource("/Images/largeGold.jpg")), evenMoreGold = new ImageIcon(getClass().getResource("/Images/largeGold.jpg")), surplusGold = new ImageIcon(getClass().getResource("/Images/largeGold.jpg")), tooMuchGold = new ImageIcon(getClass().getResource("/Images/largeGold.jpg")), finalGold = new ImageIcon(getClass().getResource("/Images/largeGold.jpg")), expPotion = new ImageIcon(getClass().getResource("/Images/experiencePotion.png")), phaseShift = new ImageIcon(getClass().getResource("/Images/phaseShift.png")), necromancyPotion = new ImageIcon(getClass().getResource("/Images/necromancyPotion.jpg")), death = new ImageIcon(getClass().getResource("/Images/deathScreen.png")), controls = new ImageIcon(getClass().getResource("/Images/Controls.png")), cursedScreen = new ImageIcon(getClass().getResource("/Images/corruptedScreen.jpg")), character1 = BaseChar; //Initializing all of the necessary ImageIcons in advance
    ImageIcon[] imageLoop = new ImageIcon[13]; //An array used in the title screen to determine what image to display at a given time
    Image player1 = character1.getImage(), chest1 = character1.getImage(), chest2 = character1.getImage(), chest3 = healthPotion.getImage(), chest4 = speedPotion.getImage(), chest5 = damagePotion.getImage(), chest6 = portal1.getImage(), chest7 = shinyKey.getImage(), chest8 = fireball.getImage(), chest9 = lightning.getImage(), chest10 = phaseShift.getImage(), chest11 = expPotion.getImage(), chest12 = goldBars.getImage(), chest13 = necromancyPotion.getImage(), chest14 = goldBars.getImage(), chest15 = healthPotion.getImage(), chest16 = speedPotion.getImage(), chest17 = damagePotion.getImage(), chest18 = expPotion.getImage(), chest19 = lightning.getImage(), chest20 = goldBars.getImage(), chest21 = moreGold.getImage(), chest22 = evenMoreGold.getImage(), chest23 = surplusGold.getImage(), chest24 = tooMuchGold.getImage(), chest25 = finalGold.getImage(), note, controlScreen, attack = null; //Again, I use images here rather than ImageIcons by getting the ImageIcon's respective image; this allows me to resize the images as I see fit in the IDE rather than adjusting them manually in the photos app or some equivalent
    int scene = 1, currentScene = 1, alpha = 0, textWidth = 0, currentSentenceIndex = 0, currentCharIndex = 0, imageX, imageY, textY, x = 0, y = 0, leftVel = 0, rightVel = 0, upVel = 0, downVel = 0, health = 1, level = 1, EXP = 0, defense = 0, damage = 0, experienceWidth = 15, currentRow = 1, currentColumn = 1, chosenVel = 0, gold = 0, cost = 0, extraDamage = 0, extraSpeed = 0, fireballDamage = 0, lightningDamage = 0, explosionDamage = 0, poisonDamage = 0, damageModifier = 1, killCount = 0, deathCount = 0, futureDeathCount = 1, rowNum = 0, columnNum = 0, response2 = 0; //Some self-explanatory fields used in all different aspects of the game
    JOptionPane optionPane; //A JOptionPane (that gives the player a choice) where the player can select what response they want to give. I took this code and idea from the dialog boxes lesson posted under the Graphics unit
    Rectangle player = new Rectangle((int)(x + (width() * 40)), (int)(y + (height() * 105)), (int)(width() * 48), (int)(height() * 28)), fullCharacter = new Rectangle(0,0,0,0), bonfire1 = new Rectangle((int)(width() * 730), (int)(height() * 365), (int)(width() * 75), (int)(height() * 75)), bonfire2 = new Rectangle((int)(width() * 665), (int)(height() * 350), (int)(width() * 200), (int)(height() * 90)), cursedBonfire = new Rectangle((int)(width() * 200), (int)(height() * 200), (int)(width() * 150), (int)(height() * 130)), bonfire3 = new Rectangle((int)(width() * 50), (int)(height() * 350), (int)(width() * 150), (int)(height() * 75)), obelisk = new Rectangle((int)(width() * 785), (int)(height() * 75), (int)(width() * 125), (int)(height() * 125)), obelisk2 = new Rectangle((int)(width() * 1320), (int)(height() * 600), (int)(width() * 100), (int)(height() * 200)), boostMerchant = new Rectangle((int)(width() * 440), (int)(height() * 720), (int)(width() * 50), (int)(height() * 60)), magicMerchant = new Rectangle((int)(width() * 745), (int)(height() * 50), (int)(width() * 40), (int)(height() * 150)), potionMerchant = new Rectangle((int)(width() * 745), (int)(height() * 200), (int)(width() * 40), (int)(height() * 150)), guard1 = new Rectangle((int)(width() * 60), (int)(height() * 100), (int)(width() * 90), (int)(height() * 120)), guard2 = new Rectangle((int)(width() * 60), (int)(height() * 620), (int)(width() * 75), (int)(height() * 100)), cleric = new Rectangle((int)(width() * 70), (int)(height() * 500), (int)(width() * 100), (int)(height() * 100)), axeDwarf = new Rectangle((int)(width() * 310), (int)(height() * 50), (int)(width() * 100), (int)(height() * 100)), wizard = new Rectangle((int)(width() * 1295), (int)(height() * 50), (int)(width() * 100), (int)(height() * 100)), grasslandGate = new Rectangle((int)(width() * 1370), (int)(height() * 300), (int)(width() * 125), (int)(height() * 200)), dungeonDoor = new Rectangle ((int)(width() * 575), (int)(height() * 100), (int)(width() * 250), (int)(height() * 200)), bossDoor = new Rectangle((int)(width() * 600), 0, (int)(width() * 300), (int)(height() * 300)), attackCollision1 = new Rectangle(0, 0, 0, 0), attackCollision2 = new Rectangle(0, 0, 0, 0), attackCollision3 = new Rectangle(0, 0, 0, 0), attackCollision4 = new Rectangle(0, 0, 0, 0); //I initialize all of the important rectangles (for merchants, bonfires, and obelisks, etc.) in advance so that they don't return null and the program doesn't crash when they're referenced later on
    String[] phrases = new String[12]; //A string array that contains each individual line for the opening and epilogue cutscenes
    String filePath, fileName, fileNom = "src/Files/openingPhrases.txt", paragraph, damageType, string1, string2; //Strings used mostly for loading/playing audio files, or in the intro/epilogue cutscenes
    Timer timer = new Timer(10000, this), transitionTimer = new Timer(3, this); //Two seperate timers, one used for the title screen, and the other used for the transition from white to black that happens in between cutscenes
    Inventory inventory = new Inventory(this); //I initialize an array of exploding enemies at a given scene and index, and also give the ISU3 class an instance of the inventory file, so that they can communicate back and forth. 
    ExplodingEnemy[][] explodingEnemies = new ExplodingEnemy[21][5]; 
    
    public ISU3() { //The constructor for ISU3, which essentially sets up the explodingEnemies 2D array, loads images for the title screen in, loads the text for the intro/epilogue cutscenes, and opens/minimizes the inventory
        for (int i = 0; i < explodingEnemies.length; i++) { //Iterates through the explodingEnemies 2D array and makes it so that the ExplodingEnemy file and the ISU3 file can communicate back and forth (initializes an instance of ISU3 for every enemy in the explodingEnemies array)
            for (int j = 0; j < explodingEnemies[i].length; j++) {
                explodingEnemies[i][j] = new ExplodingEnemy();
                explodingEnemies[i][j].setISU3(this);
            }
        } 
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        loadImages(); 
        loadPhrases();
        fileName = "A World Apart.wav"; //First song that plays when the game is opened, set here so that it doesn't run more than it has to.
        createFile(); 
        timer.start();
        inventory.start(); //Initializes the inventory, sets it to open, and then minimizes it to get it out of the player's way.
        inventoryOpen = true;
        inventory.closeWindow();
    }

    public static void main(String[] args) { //The main method that initializes/creates the JPanel and JFrame for the main game, including setting the title, setting the size, and setting the visibility.
        ISU3 panel = new ISU3();
        JFrame window = new JFrame();
        window.setTitle("FOR THE IMPERIUM");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(panel);
        window.pack();
        window.setSize(800, 450);
        window.setVisible(true);
    }
    
    public void paintComponent(Graphics g) { //Rather than using paintComponent to do ALL of the drawing, I found it easier to use paintComponent as a way to transition between areas of the game, and passed each corresponding area method a Graphics g variable instead, as a lot of drawing is going on specific to said area/method, which I didn't want to be lumped in here.
        super.paintComponent(g);
        int width = getWidth(); //Width and height correspond to the entire width/height of the screen, used if I don't want to bother specifying the coordinates where a collision rectangle stops/starts and just want it to cover the entire screen in that direction. Alternatively, width and height are also used when determining the x- and y-coordinates of the character as he transitions between scenes (as they're only used when I need an approximate value, not when I need a specific coordinate like width() * 650, for instance)
        int height = getHeight();
        if (currentScene != scene) { //currentScene is always one less than scene, except for if the phaseShift item is used in the inventory, whereupon it will be equal to scene for that one area only. Then, collisions will be treated as normal.
            noCollisions = false;
        }
        currentScene = scene;
        switch (scene) { //A switch-case statement that essentially changes areas depending on what integer value the scene is at any given point. Numbers are generally in chronological order of when the areas will be discovered, except for the death scenes, which are extremely high numbers on purpose to distinguish them and their function from the rest of the areas.
            case 1, 38, 1001 -> drawTitleScreen(width, height, g); //Called three times: once when the game starts; another when the final boss "crashes" the game; and another when one specific ending is achieved out of the 30 that beats the game
            case 2, 4 -> drawTransition(width, height, g); //Called twice: once when transitioning from the title screen to the cutscene, and another when transitioning from the cutscene to the game's first playable area
            case 3, 39, 40 -> drawIntroText(width, height, g);
            case 5 -> Area1a(width, height, g); //Areas are subdivided into a number followed by a letter. The number is the region itself, while the letter is the actual area within that region. 
            case 6 -> Area1b(width, height, g); //The number 1 corresponds to the Snow-Capped Mountains, where the main character wakes up at the beginning of the game.
            case 7 -> Area2a(width, height, g); //The number 2 corresponds to the Town of Winterveil, where the player can explore the game's different mechanics and buy/sell items with merchants.
            case 8 -> Area2b(width, height, g);
            case 9 -> Area2c(width, height, g);
            case 10 -> Area2d(width, height, g);
            case 11 -> Area2e(width, height, g); 
            case 12 -> Area3a(width, height, g); //The number 3 corresponds to the Mistwood Forest, the game's first combat area. The enemies here are scaled down to reflect this. 
            case 13 -> Area3b(width, height, g); 
            case 14 -> Area3c(width, height, g); 
            case 15 -> Area3d(width, height, g); 
            case 16 -> Area3e(width, height, g); 
            case 17 -> Area4a(width, height, g); //The number 4 corresponds to the Grasslands, the region after the forest that requires the bronze key to access. There are no enemies in areas 4a (entrance), 4b (clearing), or 4c (refuge).
            case 18 -> Area4b(width, height, g);
            case 19 -> Area4c(width, height, g);
            case 20 -> Area4d(width, height, g); //The enemies start spawning in this method, and the grasslands has an intentionally-confusing layout (see below)
            case 21 -> Area4e(width, height, g); 
            case 22 -> Area4f(width, height, g); 
            case 23 -> Area4g(width, height, g); 
            case 24 -> Area4h(width, height, g); 
            case 25 -> Area4i(width, height, g); 
            case 26 -> Area4j(width, height, g); 
            case 27 -> Area4k(width, height, g); 
            case 28 -> Area4l(width, height, g); //Where the entrance to the necromancer's dungeon is located
            case 29 -> Area5a(width, height, g); //The number 5 corresponds to the dungeon, where the toughest enemies and the final bonfire/checkpoint can be found along with the final boss. There are traps in these areas that go against the conventional understanding of items/objects prior in the game (ex: a bonfire that reduces your health, and an item chest that kills you)
            case 30 -> Area5b(width, height, g); 
            case 31 -> Area5c(width, height, g); 
            case 32 -> Area5d(width, height, g); 
            case 33 -> Area5e(width, height, g); 
            case 34 -> Area5f(width, height, g); 
            case 35 -> Area5g(width, height, g);
            case 36 -> Area5h(width, height, g);
            case 37 -> Area5i(width, height, g); //The final boss arena
            case 999, 1000 -> deathScreen(g); //The death screen, where 999 is what typically draws during the game (with the "press enter to begin again" text), and 1000 is what draws at the end of the game if the player gets endings 1 through 29 that they ultimately die in. 
        } if (guideOpened) { //If the guide is opened, the image "note" is drawn, which is just one of the 4 guide ImageIcons but converted to an image instead
            if (note != null) {
                g.drawImage(note, 0, 0, getWidth(), getHeight(), this);
            }
        } if (controlsOpened) { //If the control screen is opened, the same logic is followed for drawing it as that of the guide screen.
            if (controlScreen != null) {
                g.drawImage(controlScreen, 0, 0, getWidth(), getHeight(), this);
            }
        }
        if (scene > 4 && scene < 38 && attack != null) { //The character's attacks are drawn to the screen if the scene is greater than 4 (in a playable area) and is not one where the player character isn't drawn (scene has to be less than 38 for the player to attack)
            drawAttack(g); //The attack is drawn, the attack's base damage is calculated, then attack is set back to null as the attack isn't happening.
            attackDamage();
            attack = null;
        }
        checkHealth(); //The player's health is checked. If it is above zero, the game continues as normal. If it is at or below 0, the player dies. 
    }

    public void loadImages() { //Iterates over the imageLoop array, which contains images named under the format "cropped-Background1.jpeg", or "cropped-Background2.jpeg" hence why lines of code can be saved there. The image is later drawn to the screen 
        for (int i = 0; i < 13; i++) {
            String imagePath = "/Images/cropped-Background" + (i + 1) + ".jpeg";
            imageLoop[i] = new ImageIcon(getClass().getResource(imagePath));
        }
    }

    public void loadPhrases() { //A method responsible for loading and displaying the text used in the intro and epilogue cutscenes.
        if (scene == 3) { //The file loaded here never changes. 
            fileNom = "src/Files/openingPhrases.txt";
        } if (scene == 39) { //There are several conditions to what file is loaded here, marked by whether certain boolean values are true or false. Scene 39 is if the player joins the necromancer.
            if (deathCount > 0 && deathCount < 20) { //The death count is the first determining factor used. If it is within this range, endings are limited to endings 1 through 18
                if (gold == 0) { //Then, the amount of gold the player has is taken into account
                    if (necromancyRevival) { //If the player didn't consume the potion to weaken the necromancer, then they're limited to endings 1, 2, or 3
                        if (questCompleted && quest2Completed && quest3Completed) { //The specific ending is ultimately determined by whether the player completed 0, 1/2, or all 3 quests offered to them in the game.
                            fileNom = "src/Files/ending1.txt";
                        } else if (questCompleted || quest2Completed || quest3Completed) {
                            fileNom = "src/Files/ending2.txt";
                        } else if (!questCompleted && !quest2Completed && !quest3Completed) {
                            fileNom = "src/Files/ending3.txt";
                        } 
                    } else if (!necromancyRevival) { //If the player drank the potion to weaken the final boss, they're limited to endings 4, 5, and 6
                        if (questCompleted && quest2Completed && quest3Completed) {
                            fileNom = "src/Files/ending4.txt";
                        } else if (questCompleted || quest2Completed || quest3Completed) {
                            fileNom = "src/Files/ending5.txt";
                        } else if (!questCompleted && !quest2Completed && !quest3Completed) {
                            fileNom = "src/Files/ending6.txt";
                        }
                    }
                } else if (gold > 0 && gold < 10000) { //If the player had a moderate amount of gold, they're limited to endings 7 through 12
                    if (necromancyRevival) {
                        if (questCompleted && quest2Completed && quest3Completed) {
                            fileNom = "src/Files/ending7.txt";
                        } else if (questCompleted || quest2Completed || quest3Completed) {
                            fileNom = "src/Files/ending8.txt";
                        } else if (!questCompleted && !quest2Completed && !quest3Completed) {
                            fileNom = "src/Files/ending9.txt";
                        }
                    } else if (!necromancyRevival) {
                        if (questCompleted && quest2Completed && quest3Completed) {
                            fileNom = "src/Files/ending10.txt";
                        } else if (questCompleted || quest2Completed || quest3Completed) {
                            fileNom = "src/Files/ending11.txt";
                        } else if (!questCompleted && !quest2Completed && !quest3Completed) {
                            fileNom = "src/Files/ending12.txt";
                        }
                    }
                } else if (gold > 10000) { //If players have over 10000 gold, they can only obtain endings 13 through 18 (that comment on the conditions the player met)
                    if (necromancyRevival) {
                        if (questCompleted && quest2Completed && quest3Completed) {
                            fileNom = "src/Files/ending13.txt";
                        } else if (questCompleted || quest2Completed || quest3Completed) {
                            fileNom = "src/Files/ending14.txt";
                        } else if (!questCompleted && !quest2Completed && !quest3Completed) {
                            fileNom = "src/Files/ending15.txt";
                        }
                    } else if (!necromancyRevival) {
                        if (questCompleted && quest2Completed && quest3Completed) {
                            fileNom = "src/Files/ending16.txt";
                        } else if (questCompleted || quest2Completed || quest3Completed) {
                            fileNom = "src/Files/ending17.txt";
                        } else if (!questCompleted && !quest2Completed && !quest3Completed) {
                            fileNom = "src/Files/ending18.txt";
                        }
                    }
                }
            } else if (deathCount == 0) { //Two special endings that only trigger based on certain death counts.
                fileNom = "src/Files/ending19.txt";
            } else if (deathCount > 20) {
                fileNom = "src/Files/ending20.txt";
            }
        } else if (scene == 40) { //Scene 40 is called if the player instead fights and kills the necromancer, and determines the endings based on the same criteria as above.
            if (deathCount > 0 && deathCount < 20) {
                if (gold == 0) {
                    fileNom = "src/Files/ending21.txt";
                } else if (gold > 0 && gold < 10000) {
                    fileNom = "src/Files/ending22.txt";
                } else if (gold > 10000) {
                    fileNom = "src/Files/ending23.txt";
                }
            } else if (deathCount > 20) {
                if (gold == 0) {
                    fileNom = "src/Files/ending24.txt";
                } else if (gold > 0 && gold < 10000) {
                    fileNom = "src/Files/ending25.txt";
                } else if (gold > 10000) {
                    fileNom = "src/Files/ending26.txt";
                }
            } else if (deathCount == 0) {
                if (gold == 0) {
                    fileNom = "src/Files/ending27.txt";
                } else if (gold > 0 && gold < 10000) {
                    fileNom = "src/Files/ending28.txt";
                } else if (gold > 10000) {
                    fileNom = "src/Files/ending29.txt";
                }
            } else if (deathCount == 0 && questCompleted && quest2Completed && quest3Completed && !necromancyRevival) { //This is the one true ending to the game, where the player is allowed to live and emerges victorious. It's also the hardest (almost an impossible) ending to obtain
                fileNom = "src/Files/ending30.txt";
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileNom))) { //Code for reading in a text file, with BufferedReader, FileReader, and a for loop iterating over the length of each line in the text file as well as the length of each individual word
            for (int i = 0; i < phrases.length; i++) {
                String line = br.readLine(); 
                if (line != null) { //If all of the content within the text file hasn't been entirely read yet, the phrase (at a given index) is equal to the String line 
                    phrases[i] = line;
                }
            }
        } catch (Exception e) {
            System.out.println(); //Doesn't print anything so as to not disturb the user's experience.
        }
    }

    public void createFile() { //Method responsible for "creating" audio files, that assigns the string filePath (based on the fileName, which is just a shorter section of the filePath string) to an actual audio file. Audio is then loaded in the loadAudio method.
        filePath = "./src/Files/" + fileName;
        audioFile = new File(filePath);
        loadAudio();
    }

    public void loadAudio() { //A method that uses an AudioInputStream obtained from the audioFile itself, and then opens the clip for the file (gets the clip ready to play)
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Error loading audio file.");
        }
    }

    public void playAudio() { //A method that sets the clip's frame position to 0 (reverts back to the beginning) and then starts the clip (plays the audio).
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
    
    public void changeClip(String string) { //A method used for transitioning between audio files and songs as the area that the player character is in changes. 
        if (fileName != string) { //Takes in one string, which is the file that the audio should change to. If the fileName and the string don't match up, the previous clip is stopped, the fileName is reassigned, and the new clip is played.
            clip.stop();
            fileName = string;
            createFile();
            playAudio();
        } if (fileName != "Peril.wav" && fileName != "Start.wav" && fileName != "All Hail The Imperium.wav" && fileName != "Convergence.wav") { //If the fileName isn't specifically one of the songs that doesn't repeat (the intro cutscene song, the start sound effect, and the two epilogue cutscene songs), it loops indefinitely.
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }
    }

    public void obtainCoordinates() {  //A method used to determine where text should be positioned on the screen, dependent on the width and height of the image (when I want text to be centred at the bottom of the screen)
        imageX = (getWidth() - textWidth)/2; //textWidth is a field that's modified in drawTitleScreen (and similar methods), equal to the width of a given string. 
        imageY = getHeight() - 50;
    }

    public void stringWidth(String phrase) { //A method that takes in a string, and determines the exact width of said string, using the fontMetrics feature.
        textWidth = fontMetrics.stringWidth(phrase);
    }

    public void drawTitleScreen(int width, int height, Graphics g) { //A method responsible for drawing the title screen and the text/images associated with it
        Random rand = new Random();
        String title = "null";
        String instructions = "Some random words"; //Initialized to some placeholder words first, before being assigned to what title and instructions actaully are
        if (scene == 1001) {
            title = "You Won"; //This is what the text reads on the top-center and bottom-center for the good/true ending
            instructions = "Good Job";
        } else if (scene == 1) { //This is the text for when the game is just beginning
            title = "For The Imperium";
            instructions = "Press Enter To Start";
        } else { //For scene 38 when the boss crashes the game, all music is stopped and ominous warnings appear on the screen
            clip.stop();
            title = "You Made The Wrong Choice";
            instructions = "THERE'S NO GOING BACK NOW.";
        } if (scene == 1 || scene == 1001) { //If the scene isn't 38 here, an image is randomly chosen to be displayed beneath the text every 10 seconds, and is accordingly drawn to the screen. 
            timer.setDelay(10000);
            int chooseImage = rand.nextInt(13);
            Image titleImage = imageLoop[chooseImage].getImage();
            g.drawImage(titleImage, 0, 0, width, height, this);
            g.setFont(new Font("Garamond", Font.PLAIN, 50));
        } else if (scene == 38) {
            g.drawImage(cursedScreen.getImage(), 0, 0, width, height, this);
            g.setFont(new Font("Garamond", Font.PLAIN, 35));
        }
        if (scene == 1 || scene == 1001) { //The colour for the text at the top of the screen is set
            g.setColor(Color.YELLOW);    
        } else {
            g.setColor(Color.BLACK);
        }
        fontMetrics = g.getFontMetrics(); //Another instance of fontMetrics is obtained and the width of both strings as well as the location of where to center the text (title and instructions) is obtained
        stringWidth(title);
        obtainCoordinates();
        g.drawString(title, imageX, 75); //Draws the title centred to the top
        if (scene == 1 || scene == 1001) {
            g.setColor(Color.WHITE); //Changes the colour to white then repeats the process for the "instructions" string at the bottom
        }
        obtainCoordinates();
        stringWidth(instructions);
        g.drawString(instructions, imageX, imageY);
        g.setColor(color1);
        g.fillRect(0, 0, width, height);
        if (!clip.isRunning() && scene == 1 || scene == 1001) { //Changes the clip for those scenes just in case they're accessed from different scenes where a different music/audio file is playing
            changeClip("A World Apart.wav");
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void drawTransition(int width, int height, Graphics g) { //Responsible for drawing the transition between scenes from white to black.
        if (!clip.isRunning() && alpha == 0 && scene == 2) { //The alpha integer represents the transparency of the colour, starting from 0 transparency for black (which draws as white) then slowly increasing it, making the colour go more and more opaque
            playAudio(); //Plays the "Start.wav" file if the scene is 2. This is, notably, the only instance of sound effect usage in the game. I was potentially going to include more sound effects until I ran out of time, but also wanted the player to primarily focus on the music and gameplay so that they weren't distracted by too many sounds playing simultaneously
        }
        g.setColor(new Color(0, 0, 0, alpha)); //Colour is set initially to black with 0 opacity, which then increases
        g.fillRect(0, 0, width, height); //Fills the entire screen with the colour
    }

    public void drawIntroText(int width, int height, Graphics g) {
        alpha = 0; //Resets the alpha value to 0, in preparation for the next transition after the cutscene
        if (!phrasesLoaded) { 
            loadPhrases(); //Initializes and assigns values to the indices of the phrases array, from the different text files.
            phrasesLoaded = true;
        }
        paragraph = "empty"; 
        timer.setDelay(40); //A new letter is revealed every 40 ms.
        if (scene == 3) { //Determines the audio for the different scenes
        fileName = "Peril.wav";
            if (!filePath.equals("./src/Files/Peril.wav")) {
                createFile();
            }
        } else if (scene == 39) {
            changeClip("Convergence.wav");
        } else if (scene == 40) {
            changeClip("All Hail The Imperium.wav");
        }
        Image cavernImage = cavern.getImage(); //A default cavern image that goes beneath the text, covering the whole screen.
        g.drawImage(cavernImage, 0, 0, width, height, this);
        if (currentSentenceIndex < phrases.length) { //Essentially iterates through each phrase/sentence of the file until the indices are the same 
            paragraph = phrases[currentSentenceIndex]; //Each "paragraph" is one phrase in the phrases array (one line in the file)
            if (paragraph != null) { //If there still is text to read from a given line in the file, the font is set depending on the scene 
                if (scene == 3) { 
                    g.setFont(new Font("Times New Roman", Font.PLAIN, (int) (15 * (width()) + (15 * height()))));
                } else {
                    g.setFont(new Font("Garamond", Font.PLAIN, (int)(10 * (width()) + (10 * height()))));
                    timer.setDelay(60); //Text is revealed slightly slower than in the intro cutscene, as there's less ground to cover and the songs are longer (trying to minimize time that the user has to listen to the epilogue songs)
                }
                String sentence = paragraph.substring(0, currentCharIndex); //Takes a substring of each phrase, meaning that "sentence" is one character long; the text is then printed to the screen character by character
                fontMetrics = g.getFontMetrics();
                obtainCoordinates();
                stringWidth(sentence); //Each character's width is then determined, and the text is centred to the screen 
                textY = height - (int)(height/2); //The height of the text is equal to the height of the screen, minus itself/2
                imageX = (width - textWidth)/2; //Where the text should be relative to the image (the imageX) is equal to the width of the screen, minus the width of the character, divided by 2. That's how a scrolling effect is achieved from side to side as the text is drawn to the screen.
                g.setColor(Color.WHITE);
                if (scene == 3) {
                    g.drawString("Press Enter To Skip", (int) (width - (width() * 235)), (int) (height - (height() * 50))); //Instructions for the player are drawn at the bottom right of the screen
                }
                g.drawString(sentence, imageX, textY); //This is where each character is drawn in, whereupon the character index will increase 
                currentCharIndex++; 
                if (currentCharIndex > paragraph.length()) { //If the last character in the phrase is printed to the screen, everything sleeps/stops for 400 ms and then the next phrase is printed to the screen (the currentSentenceIndex is incremented) and the currentChar index reverts back to 0.
                    try {
                        Thread.sleep(400);
                    } catch (Exception e) {
                        System.out.println("Error pausing.");
                    }
                    currentSentenceIndex++;
                    currentCharIndex = 0;
                    textY = height - (int)(height/2); //The Y-coordinate of the text relative to the image is reverted back to what it once was.
                } 
            }
        } if (!clip.isRunning() && currentCharIndex == 1 && currentSentenceIndex == 0) { //If the clip isn't running and the first character has been printed to the screen, the audio starts
            playAudio();
        } else if (paragraph == null && !clip.isRunning()) { //If the intro cutscene ends, the next song ("Mountaintops.wav") is loaded and the transition occurs
            if (scene == 3) {
                changeClip("Mountaintops.wav");
                transitionTimer.start();
                scene++;
            } else if (scene == 39 || scene == 40) { //After the epilogue text is read, either the death screen or the title screen pops up again, depending on the ending that the player got.
                if (fileNom.equals("ending30.txt")) {
                    scene = 1001;
                } else {
                    scene = 1000;
                }
            }
        }
    }

    public void Area1a(int width, int height, Graphics g) { //The logic surrounding the first area of the game. 
        phrasesLoaded = false; //This boolean is set to false so that once the game ends, a new text file can be read from.
        timer.setDelay(1); //Delay is adjusted up to increase performance
        if (x == 0 && y == 0) { //The x and y values are initialized here, to specifically be at (350 * height()), etc. 
            x = (int) (350 * height());
            y = (int) (650 * width());
        }
        initializeArea(mountain.getImage(), g); //The image is drawn, instructions on how to access the controls screen are given, and two item chests are drawn to the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Garamond", Font.PLAIN, 25));
        stringWidth("Press K for the Controls Screen");
        obtainCoordinates();
        g.drawString("Press K for the Controls Screen", imageX, (int)(height() * 100));
        g.setColor(Color.WHITE);
        objectDrawing(g, chest1, chest, (int)(width() * 360), (int)(height() * 570), (int)(width() * 64), (int)(height() * 64));
        objectDrawing(g, chest2, chest, (int)(width() * 800), (int)(height() * 800), (int)(width() * 64), (int)(height() * 64));
        if (y > height * 0.92 && scene == 5) { //Logic for transitioning between scenes, which uses the int height and int width, because it doesn't need to place the player at a specific coordinate. Generally, if the player is down far enough, their y-coordinate reverts back to the top and the scene advances
            scene = 6;
            y = (int)(height * 0.04);
        }
    }

    public void Area1b(int width, int height, Graphics g) { //Logic for the 2nd area of the game.
        initializeArea(snow.getImage(), g); //Does everything in the same order. The background image is drawn, the music is determined, and the scene changes based on the player's coordinates.
        changeClip("Mountaintops.wav");
        if (player.y < height() * 1 && y != (int)(height * 0.97)) {
            scene = 5;
            y = (int)(height * 0.8);
        } if (x > width * 0.97) {
            scene = 7;
            x = (int) (width * 0.03);
            y = (int) (height * 0.33);
        } if (y > height * 0.98) {
            scene = 12;
            y = (int)(height * 0.01);
        }
    }

    public void Area2a(int width, int height, Graphics g) { //Logic for the third area in the game, and the first section of Winterveil.
        townDiscovered = true; //A checkpoint is set here, such as that if the player dies when adventuring, they spawn back here.
        initializeArea(guardTower.getImage(), g); //The image is drawn and then the music is determined, and if the player's y or x-coordinates are within a certain range, the scene changes
        changeClip("Snowfall.wav");
        if (x < width * 0.01) {
            scene = 6;
            x = (int) (width * 0.96);
            changeClip("Mountaintops.wav");
        } if (x > width * 0.98) {
            scene = 10;
            x = (int) (width * 0.03);
            y = (int) (height * 0.20);
        } if (y > height * 0.98) {
            scene = 8;
            y = (int) (height * 0.03);
        }
    }

    public void Area2b(int width, int height, Graphics g) { //Logic for the fourth area in the game, and the second section of Winterveil.
        initializeArea(merchants.getImage(), g); //The ImageIcons typically have names describing the general area; this scene has three merchants in it, so the ImageIcon is called merchants
        if (y < height * 0.02) { //Same pattern as above for adjusting player coordinates. Rather than resorting to another method to determine coordinate changes, I found it easier to execute within the actual scene method itself. The logic is (in my opinion) easier to follow when scenes are numbered chronologically
            scene = 7;
            y = (int) (height * 0.97);
        } if (x > width * 0.97) {
            scene = 9;
            x = (int) (width * 0.03);
        }
    }

    public void Area2c(int width, int height, Graphics g) { //Logic for the fifth area in the game, and the third section of Winterveil.
        Image campsites = camps.getImage(); //Same pattern as above.
        initializeArea(camps.getImage(), g);
        if (x < width * 0.02) {
            scene = 8;
            x = (int) (width * 0.96);
        } if (y < height * 0.02) {
            scene = 10;
            y = (int) (height * 0.97);
        }
    } 

    public void Area2d(int width, int height, Graphics g) { //Logic for the sixth area in the game, and the fourth section of Winterveil
        initializeArea(hole.getImage(), g); //This area contains a chest and a pit leading down to the player's (ideally) first enemy encounter
         objectDrawing(g, chest3, chest, (int)(width() * 1100), (int)(height() * 350), (int)(width() * 64), (int)(height() * 64));
        changeClip("Snowfall.wav");
        if (y > height * 0.98) {
            scene = 9;
            x = (int)(width() * 1125);
            y = (int) (height * 0.03);
        } if (x < width * 0.02) {
            scene = 7;
            x = (int) (width * 0.97);
            y = (int) (height * 0.33);
        }
    }

    public void Area2e(int width, int height, Graphics g) { //Logic for the seventh area of the game, and the fifth section of Winterveil
        initializeArea(innerPit.getImage(), g); //A chest is drawn, the music is changed to something much more tense, and the first group of explodingEnemies is initialized. 
        objectDrawing(g, chest4, chest, (int)(width() * 500), (int)(height() * 450), (int)(width() * 64), (int)(height() * 64));
        changeClip("CQC.wav");
        initializeExplodingEnemy(g, 0);
    }
    
    public void Area3a(int width, int height, Graphics g) { //Logic for the eighth area of the game, and the first section of the Mistwood Forest.
        initializeArea(forestEntrance.getImage(), g); //These areas all follow the same pattern as above, where chests are drawn in, the area image is initialized and drawn, and scenes/areas change depending on the player's coordinates. Enemies are always drawn after everything else has been completed
        objectDrawing(g, chest5, chest, (int)(width() * 450), (int)(height() * 400), (int)(width() * 64), (int)(height() * 64));
        objectDrawing(g, chest6, chest, (int)(width() * 260), (int)(height() * 575), (int)(width() * 64), (int)(height() * 64));
        objectDrawing(g, chest8, chest, (int)(width() * 1215), (int)(height() * 270), (int)(width() * 64), (int)(height() * 64));
        changeClip("The Mistwood.wav"); 
        if (player.y < height() * 1) {
            y = (int)(height * 0.97);
            scene = 6;
        } if (x > width * 0.98) {
            x = (int)(width * 0.03);
            scene = 13;
        } if (y > height * 0.98) {
            y = (int)(height * 0.02);
            scene = 14;
        } if (x < width * 0.02) {
            x = (int)(width * 0.97);
            scene = 15;
        }
        initializeExplodingEnemy(g, 1);
    }
    
    public void Area3b(int width, int height, Graphics g) { //Logic for the ninth area of the game, and the second section of the Mistwood Forest.
        initializeArea(forestRight.getImage(), g); //This is again, the exact same as above.
        objectDrawing(g, chest7, chest, (int)(width() * 900), (int)(height() * 400), (int)(width() * 64), (int)(height() * 64));
        if (x < width * 0.01) {
            scene = 12;
            x = (int)(width * 0.97);
        } if (y > height * 0.99) {
            y = (int)(height * 0.98);
        }
        initializeExplodingEnemy(g, 2);
    }
    
    public void Area3c(int width, int height, Graphics g) { //Logic for the tenth area of the game, and the third section of the Mistwood Forest.
        initializeArea(forestSouth.getImage(), g); //Same logic as above; the chests merely draw in at different spots for the different areas
        objectDrawing(g, chest9, chest, (int)(width() * 700), (int)(height() * 500), (int)(width() * 64), (int)(height() * 64));
        if (y < height * 0.01) { 
            y = (int)(height * 0.97);
            scene = 12;
        }
        initializeExplodingEnemy(g, 3);
    }
    
    public void Area3d(int width, int height, Graphics g) { //Logic for the eleventh area of the game, and the fourth section of the Mistwood Forest
        initializeArea(forestLeft.getImage(), g); //This area has 3 chests and even more enemies. See below for the explanations of objectDrawing and initializeArea
        objectDrawing(g, chest10, chest, (int)(width() * 100), (int)(height() * 650), (int)(width() * 64), (int)(height() * 64));
        objectDrawing(g, chest11, chest, (int)(width() * 200), (int)(height() * 300), (int)(width() * 64), (int)(height() * 64));
        objectDrawing(g, chest12, chest, (int)(width() * 75), (int)(height() * 100), (int)(width() * 64), (int)(height() * 64));
        if (x > width * 0.99) {
            x = (int)(width * 0.03);
            scene = 12;
        } if (player.y < height * 0.01) {
            y = (int)(height * 0.98);
            scene = 16;
        }
        initializeExplodingEnemy(g, 4);
    }
    
    public void Area3e(int width, int height, Graphics g) { //Logic for the twelfth area of the game and the fifth section of the Mistwood Forest.
        initializeArea(forestPortal.getImage(), g); //This area contains a portal that, if intersected with, will teleport the player back to the beginning of the forest. The music is changed to the forest music as after this area, there's only the Grasslands and Dungeon to go to.
        changeClip("The Mistwood.wav");
        if (y > height * 0.99) {
            y = (int)(height * 0.02);
            scene = 15;
        } if (x > width * 0.98) {
            x = (int)(width * 0.02);
            scene = 17;
        }
        initializeExplodingEnemy(g, 5);
    }
    
    public void Area4a(int width, int height, Graphics g) { //Logic for the thirteenth area of the game and the first section of the Grasslands.
        initializeArea(grasslandsEntrance.getImage(), g); //The audio clip is changed, and although the coordinate system and changing scenes remains the same, this scene introduces a few new elements.
        changeClip("Grasslands.wav");
        grasslandGate = new Rectangle((int)(width() * 1370), (int)(height() * 300), (int)(width() * 125), (int)(height() * 200)); //A rectangle for the gate leading to the rest of the grasslands is drawn in
        if (x < width * 0.01) {
            x = (int)(width * 0.97);
            scene = 16;
        } if (y < height * 0.01) {
            y = (int)(height * 0.98);
            scene = 18;
        } if (y > height * 0.99) {
            y = (int)(height * 0.03);
            scene = 19;
        } if (keyInserted && player.intersects(grasslandGate)) { //keyInserted is a boolean that tracks whether the key from the inventory is used on the gate or not. To use the key, the player has to navigate into their inventory, move the cursor over to the rustyKey item, click back onto the main game, and then click U to use the item on the door. Once this is accomplished, the gate is always open for the player to pass through.
            scene = 20;
        }
    }
    
    public void Area4b(int width, int height, Graphics g) { //Logic for the fourteenth area of the game and the second section of the Grasslands.
        changeClip("Hidden Clearing.wav"); //This clearing has different music as it's where the player obtains the item that significantly weakens the necromancer.
        initializeArea(clearing.getImage(), g); //The logic is otherwise the same as any previous area.
        objectDrawing(g, chest13, chest, (int)(width() * 750), (int)(height() * 650), (int)(width() * 64), (int)(height() * 64));
        if (y > height * 0.99) {
            y = (int)(height * 0.02);
            scene = 17;
        }
    }
    
    public void Area4c(int width, int height, Graphics g) { //Logic for the fifteenth area of the game and the third section of the Grasslands.
        refugeFound = true; //This is the second checkpoint in the game, so if the player dies from now on, they respawn here. The music is once again changed, and the area is initialized.
        changeClip("Grasslands.wav");
        initializeArea(refuge.getImage(), g);
        if (player.y < height * 0.02) {
            y = (int)(height * 0.98);
            scene = 17;
        }
    }
    
    public void Area4d(int width, int height, Graphics g) { //Logic for the sixteenth area of the game and the fourth section of the grasslands
        initializeGrasslandArea(g); //This area uses another default method, initializeGrasslandArea, to draw the image. This is due to the fact that this part of the grasslands consists of a 3 x 3 square series of areas that look exactly the same except for the one in the top right housing the entrance to the necromancer's dungeon (the last area of the game).
        objectDrawing(g, chest14, chest, (int)(width() * 1200), (int)(height() * 400), (int)(width() * 64), (int)(height() * 64));
        changeClip("The Sum of All Fears.wav"); //Chests spawn at set locations, and the music is again changed to be far more tense. 
        if (x < width * 0.01) { //Leads the player back to before the gate
            x = (int)(width * 0.7);
            scene = 17;
        } if (y < height * 0.02) { //Leads to the 2nd combat area in the grasslands(left-top)
            y = (int)(height * 0.98);
            scene = 22;
        } if (y > height * 0.98) { //To the 3rd combat area in the grasslands (left-bottom)
            y = (int)(height * 0.02);
            scene = 21;
        } if (x > width * 0.99) { //To the 5th combat area in the grasslands (centre-centre)
            x = (int)(width * 0.02);
            scene = 24;
        }
        initializeExplodingEnemy(g, 6); //Enemies are also initialized here up until the first area of the dungeon
    }
    
    public void Area4e(int width, int height, Graphics g) { //Logic for the seventeenth area of the game and the fifth section of the grasslands. This is the 2nd combat grasslands area (situated in the left-bottom)
        initializeGrasslandArea(g);
        objectDrawing(g, chest15, chest, (int)(width() * 180), (int)(height() * 700), (int)(width() * 64), (int)(height() * 64));
        if (y < height * 0.01) { //To the 1st grasslands combat area (left-centre)
            y = (int)(height * 0.99);
            scene = 20;
        } if (x > width * 0.99) { //To the 4th combat area in the grasslands (centre-top)
            x = (int)(width * 0.02);
            scene = 23;
        }
        initializeExplodingEnemy(g, 7);
    }
    
    public void Area4f(int width, int height, Graphics g) { //Logic for the eighteenth area of the game and the sixth section of the grasslands. This is also the 3rd grasslands combat area (left-top).
        initializeGrasslandArea(g);
        if (y > height * 0.99) { //To the 1st grasslands combat area (left-centre)
            y = (int)(height * 0.02);
            scene = 20;
        } if (x > width * 0.99) { //To the 6th grasslands combat area (centre-top)
            x = (int)(width * 0.02);
            scene = 25;
        }
        initializeExplodingEnemy(g, 8);
    }
    
    public void Area4g(int width, int height, Graphics g) { //Logic for the nineteenth area of the game and the seventh section of the grasslands. This is also the 4th combat area in the grasslands (centre-bottom)
        initializeGrasslandArea(g);
        objectDrawing(g, chest16, chest, (int)(width() * 1350), (int)(height() * 180), (int)(width() * 64), (int)(height() * 64));
        if (x < width * 0.01) { //To the 2nd grasslands combat area (left-top)
            x = (int)(width * 0.98);
            scene = 21;
        } if (y < height * 0.01) { //To the 5th combat area in the grasslands (centre-centre)
            y = (int)(height * 0.98);
            scene = 24;
        } if (x > width * 0.99) {
            x = (int)(width * 0.02);
            scene = 26; //To the 7th combat area in the grasslands (right-bottom)
        }
        initializeExplodingEnemy(g, 9);
    }
    
    public void Area4h(int width, int height, Graphics g) { //Logic for the twentieth area of the game and the eighth section of the grasslands. This is also the 5th grasslands combat area (centre-centre)
        initializeGrasslandArea(g);
        objectDrawing(g, chest17, chest, (int)(width() * 600), (int)(height() * 250), (int)(width() * 64), (int)(height() * 64));
        if (y < height * 0.01) { //To the 6th grasslands combat area (centre-top)
            y = (int)(height * 0.98);
            scene = 25;
        } if (y > height * 0.99) { //To the 4th grasslands combat area (centre-bottom)
            y = (int)(height * 0.02);
            scene = 23;
        } if (x < width * 0.01) { //To the 1st grasslands combat area (left-centre)
            x = (int)(width * 0.98);
            scene = 20;
        } if (x > width * 0.99) {
            x = (int)(width * 0.02);
            scene = 27;
        }
        initializeExplodingEnemy(g, 10);
    }
    
    public void Area4i(int width, int height, Graphics g) { //Logic for the twenty-first area of the game, and the ninth section of the grasslands. This is also the 6th grasslands combat area (centre-top)
        initializeGrasslandArea(g);
        objectDrawing(g, chest18, chest, (int)(width() * 1050), (int)(height() * 300), (int)(width() * 64), (int)(height() * 64));
        if (y > height * 0.99) {
            y = (int)(height * 0.02);
            scene = 24;
        } if (x < width * 0.02) {
            x = (int)(width * 0.98);
            scene = 22;
        } if (x > width * 0.99) {
            x = (int)(width * 0.02);
            scene = 28;
        }
        initializeExplodingEnemy(g, 11);
    }
    
    public void Area4j(int width, int height, Graphics g) { //Logic for the 22nd area of the game, and the tenth section of the grasslands. This is also the 7th grasslands combat area (right-bottom)
        initializeGrasslandArea(g);
        objectDrawing(g, chest19, chest, (int)(width() * 565), (int)(height() * 700), (int)(width() * 64), (int)(height() * 64));
        if (x < width * 0.01) {
            x = (int)(width * 0.98);
            scene = 23;
        } if (y < height * 0.01) {
            y = (int)(height * 0.98);
            scene = 27;
        }
        initializeExplodingEnemy(g, 12);
    }
    
    public void Area4k(int width, int height, Graphics g) { //Logic for the 23rd area of the game, and the eleventh section of the grasslands. This is also the 8th grasslands combat area (right-centre)
        initializeGrasslandArea(g); //Still exact same as above, with minor variations in item placement and scene changes
        objectDrawing(g, chest20, chest, (int)(width() * 140), (int)(height() * 530), (int)(width() * 64), (int)(height() * 64));
        if (y > height * 0.99) {
            y = (int)(height * 0.02);
            scene = 26;
        } if (x < width * 0.01) {
            x = (int)(width * 0.98);
            scene = 24;
        } if (y < height * 0.01) {
            y = (int)(height * 0.98);
            scene = 28;
        }
        initializeExplodingEnemy(g, 13);
    }
    
    public void Area4l(int width, int height, Graphics g) { //Logic for the 24th area of the game, and the twelfth section of the grasslands. This is also the 9th grasslands combat area (right-top)
        changeClip("The Sum of All Fears.wav"); //The clip is changed to the base Grasslands combat audio, just in case the player exits the dungeon to backtrack.
        initializeArea(dungeonExterior.getImage(), g);
        if (y > height * 0.99) {
            y = (int)(height * 0.02);
            scene = 27;
        } if (x < width * 0.01) {
            x = (int)(width * 0.98);
            scene = 25;
        } if (silverKeyInserted && player.intersects(dungeonDoor)) { //If the player uses the silverKey item while intersecting the dungeonDoor rectangle, the scene changes to the first dungeon area. This is the same logic used in the rustyKey portion above.
            scene = 29;
            y = (int)(height * 0.98);
        }
        initializeExplodingEnemy(g, 14);
    }
    
    public void Area5a(int width, int height, Graphics g) { //Logic for the 25th area of the game and the first section of the Dungeon.
        initializeArea(dungeon1.getImage(), g);
        changeClip("Unease.wav"); //There's a new song for this portion of the game, but otherwise the code remains the same and follows the same pattern as it did before.
        objectDrawing(g, chest21, chest, (int)(width() * 300), (int)(height() * 600), (int)(width() * 64), (int)(height() * 64));
        if (y + (player.y + player.height + (height() * 50)) < (int)(height() * 2)) {
            y = (int)(height * 0.98);
            scene++;
        } else if (y > (int)(height * 0.99)) {
            y = (int)(height * 0.2);
            scene--;
        }
    }
    
    public void Area5b(int width, int height, Graphics g) { //Logic for the 26th area of the game and the second section of the dungeon
        initializeArea(dungeon2.getImage(), g);
        changeClip("Nyctophobia.wav"); //The clip is again changed to the dungeon combat theme. See below for how coordinates are determined and scenes are changed from Area5b onward, as the dungeon is linear so a method was created to save code when transitioning from area to area
        initializeExplodingEnemy(g, 15);
    }
    
    public void Area5c(int width, int height, Graphics g) { //Logic for the 27th area of the game and the third section of the dungeon.
        initializeArea(dungeon3.getImage(), g);
        objectDrawing(g, chest22, chest, (int)(width() * 700), (int)(height() * 200), (int)(width() * 64), (int)(height() * 64));
        initializeExplodingEnemy(g, 16);
    }
    
    public void Area5d(int width, int height, Graphics g) { //Logic for the 28th area of the game and the fourth section of the dungeon.
        initializeArea(dungeon4.getImage(), g);
        objectDrawing(g, chest23, chest, (int)(width() * 1300), (int)(height() * 100), (int)(width() * 64), (int)(height() * 64));
        initializeExplodingEnemy(g, 17);
    }
    
    public void Area5e(int width, int height, Graphics g) { //Logic for the 29th area of the game and the fifth section of the dungeon.
        initializeArea(dungeon5.getImage(), g);
        initializeExplodingEnemy(g, 18);
    }
    
    public void Area5f(int width, int height, Graphics g) { //Logic for the 30th area of the game and the sixth section of the dungeon
        changeClip("Nyctophobia.wav"); //Clip is changed as the next area reverts back to the ambient music because there's a bonfire and no enemies there.
        initializeArea(dungeon6.getImage(), g);
        objectDrawing(g, chest24, chest, (int)(width() * 200), (int)(height() * 500), (int)(width() * 64), (int)(height() * 64));
        initializeExplodingEnemy(g, 19);
    }
    
    public void Area5g(int width, int height, Graphics g) { //Logic for the 31st area of the game and the seventh section of the dungeon
        changeClip("Unease.wav"); //Music changes to the ambient dungeon audio.
        initializeArea(dungeon7.getImage(), g);
        objectDrawing(g, chest25, chest, (int)(width() * 650), (int)(height() * 700), (int)(width() * 64), (int)(height() * 64));
        dungeonDelved = true; //The player, after going through the entirety of the grasslands and dungeon proper, reaches the last checkpoint of the game, right before the final boss
    } 
    
    public void Area5h(int width, int height, Graphics g) { //Logic for the 32nd area of the game and the eighth section of the dungeon
        changeClip("Unease.wav");
        initializeArea(dungeon8.getImage(), g);
        bossDoor = new Rectangle((int)(width() * 600), 0, (int)(width() * 300), (int)(height() * 150)); //When the player intersects this rectangle and uses the gold key to open the door to the boss room, they are first prompted on whether or not they want to join the boss. If they hit no, from then on, they can access the boss arena without having to use the key again. If they hit yes, they're sent right to one of the two ending paths.
        if (player.intersects(bossDoor) && goldKeyInserted && battleConfirmed) {
            y = (int)(height * 0.7);
            scene = 37;
        }
    }
    
    public void Area5i(int width, int height, Graphics g) { //Logic for the 33rd area of the game and the last section of the dungeon.
        changeClip("Culmination.wav"); //Music changes to the boss music, and the area is initialized along with enemies. 
        Image finalBossRoom = bossRoom.getImage();
        initializeArea(finalBossRoom, g);
        initializeExplodingEnemy(g, 20); //Boss splits into different enemies, a random amount between 0 and 5 with random amounts of health as well depending on whether you drank the potion to nullify the necromancer's abilities or not.
        for (int i = 0; i < explodingEnemies[20].length; i++) { //A for loop is used to check if the boss is dead (if no more enemies are alive). If so, the scene advances to scene 40 and the game ends.
            if (explodingEnemies[20][i].isEnemyAlive[20][i]) {
                bossDead = false;
                break;
            }
            bossDead = true;
        }
        if (bossDead) {
            scene = 40;
        }
    }
    
    public void initializeGrasslandArea(Graphics g) { //A method used to initialize the 8 identical grasslands areas which simply calls initializeArea but with the same grasslands image for all 8 areas
        Image grasslands = grasslandExpanse.getImage();
        initializeArea(grasslands, g);
    }
    
    public void initializeArea(Image image, Graphics g) { //A method that draws the image given to it, and makes it take up the whole screen
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        if (scene == 30 || scene == 31 || scene == 32 || scene == 33 || scene == 34 || scene == 35 || scene == 36) {
           checkCoordinates(); //If the scene is a dungeon scene, this is where the player's coordinates are checked and the scene is adjusted based on them.
        }
        drawBars(g); //The player's health and EXP bars are drawn in initializeArea.
    }
    
    public void checkCoordinates() { //A method used to determine how scenes switch in the dungeon.
        if (y + (player.y + player.height + (height() * 50)) < (int)(height() * 2)) { //Since the dungeon is linear, I can simply check for if the player's y-coordinate is above or below a certain value, changing the scene based on that.
            y = (int)(getHeight() * 0.98);
            scene++;
        } else if (y > (int)(getHeight() * 0.99)) {
            y = (int)(getHeight() * 0.03);
            x = (int)(width() * 650); //The player's x-coordinate is adjusted to match up with the exit and entrance drawn to the screen. 
            scene--;
        }
    }
    
    public void health(Graphics g) { //A method that draws the player's health bar to the screen, positioned above the player by a certain amount and that gets longer to the right as the player levels up
        g.setColor(Color.RED);
        g.drawRect((x + (int)(width() * 48) - (int)(width() * (level * 2.5))), (int)(y - (height() * 10)), (level * 10), (int)(height() * 5)); //Draws equal to the max amount of health the player can have at a given level, which is level * 10
        g.fillRect((x + (int)(width() * 48) - (int)(width() * (level * 2.5))), (int)(y - (height() * 10)), (health), (int)(height() * 5)); //Fills equal to the amount of health the player currently has (somewhere between the maximum and 0)
    }

    public void experience(Graphics g) { //A method that draws the player's experience bar to the screen, positioned above the health bar.
        g.setColor(Color.BLUE); //Colour is different to distinguish the two bars
        g.drawRect((x + (int) (width() * 48) - (int) (width() * (level * 2.5))), (int) (y - (height() * 20)), experienceWidth, (int) (height() * 5)); //Is drawn equal to the experienceWidth; that is, the amount of EXP needed to advance to the next level.
        g.fillRect((x + (int) (width() * 48) - (int) (width() * (level * 2.5))), (int) (y - (height() * 20)), EXP, (int) (height() * 5)); //Fills equal to the player's current amount of EXP.
        if (EXP >= experienceWidth) { 
            levelUp();
        }
    }

    public void levelUp() { //A method responsible for controlling the level-up logic of the game
        if (EXP >= 15 && level == 1) { //Experience needed to get to the next level is equal to 15, then 30, then 45, then 60, meaning that a total of 160 kills is needed. However, the presence of experience potions and the Level 5 boost that the dwarf gives after you become level 4 reduces the amount of kills needed significantly. Players will most likely face the final boss at level 4 due to this reason, but have a chance of becoming level 5 if they explore.
            level = 2;
            health = 20;
            experienceWidth = 30;
        } if (EXP >= 30 && level == 2) {
            level = 3;
            health = 30;
            experienceWidth = 45;
        } if (EXP >= 45 && level == 3) {
            level = 4;
            health = 40;
            experienceWidth = 60;
        } if (EXP >= 60 && level == 4) {
            level = 5;
            health = 10000000;
            experienceWidth = 75; //I adjust the experience width, health, and EXP amount after every level up. At level 5, expWidth doesn't matter as much because even if the 75 were to be filled, the player wouldn't level up.
        } if (level != 5) {
            EXP = 0;
        } else {
            EXP = experienceWidth;
        }
    }

    public void drawBars(Graphics g) { //A method that is responsible for drawing the player character, health, and experience to the screen
        player1 = character1.getImage();
        drawCharacter(g);
        health(g);
        experience(g);
    }
    
    public void objectDrawing(Graphics g, Image image, ImageIcon imageIcon, int xPosition, int yPosition, int length, int verticality) { // A method that draws a given image (item chest) to the screen, if it isn't null (this is how I spawn and despawn items when they're picked up)
        if (image != null) {
            image = imageIcon.getImage();
            g.drawImage(image, xPosition, yPosition, length, verticality, this);
        }
    }
       
    public void drawCharacter(Graphics g) { //A method that draws the character and assigns them a corresponding rectangle, adjusted to match the dimensions of each character type
        if (character1.equals(BaseCharBack) || character1.equals(BaseCharLeft) || character1.equals(BaseCharRight) || character1.equals(CharArmourLeft) || character1.equals(CharArmourRight)) {
            g.drawImage(player1, x + (int)(width() * 32), y, (int)(width() * 64), (int) (height() * 128), this);
            fullCharacter = new Rectangle(x + (int)(width() * 32), y, (int)(width() * 64), (int)(height() * 128));
        } else {
            g.drawImage(player1, x, y, (int)(width() * 128), (int)(height() * 128), this);
            fullCharacter = new Rectangle(x, y, (int)(width() * 128), (int)(height() * 128));
        }
    }
    
    public void obelisk() { //A method that handles interactions with the two obelisks in the game
        JDialog.setDefaultLookAndFeelDecorated(true); //This makes the JDialog look a bit better than it normally does
        int response = 0;
        if (player.intersects(obelisk)) { //Uses an int ("response") which is typically 0 for yes, 1 for no, and 2 for cancel. I set the response equal to the JOptionPane first (so that it displays on the screen for the user and waits for their choice).
            response = JOptionPane.showConfirmDialog(null, "Do you want to skip to the end of the game right away?", "REWARD", JOptionPane.YES_NO_OPTION);
        } if (player.intersects(obelisk2)) { //This is the same as above, although with different messages and titles.
            response = JOptionPane.showConfirmDialog(null, deathCount + ". Would you like to increase this number for a future return-on-investment?", "KEEP IT UP!", JOptionPane.YES_NO_OPTION);
        } if (response == JOptionPane.NO_OPTION) { //If the player clicks no, the JOptionPane disappears and can be interacted with again.
            JOptionPane.getRootFrame().dispose(); //Command to make the pane disappear
        } else if (response == JOptionPane.YES_OPTION) { //If the player clicks yes, they are killed and their deathCount goes up by one (futureDeathCount is normally equal to deathCount, so it's increased once and then it stops incrementing). The scene is also switched to 999.
            if (futureDeathCount == deathCount) {
                futureDeathCount++;
            }
            scene = 999;
        }
    }
    
    public void inventorySelection(Object selection, String item, int amount, int arrayDimension1, int arrayDimension2) { //If an item in a merchant's drop-down menu is clicked on and the player hits OK to purchase the item, the item appears in their inventory and another method handles their finances
        if (selection.equals(item) && gold >= amount) {
            inventory.isItemFound[arrayDimension1][arrayDimension2] = true;
            cost = amount;
        }
    }
    
    public void boostShop() { //This method handles one type of merchant shop (the boost shop, centred around giving the player items that are either very powerful or give them access to other locations), which shows up as a JDialog that has a variety of options for the player to choose from (a drop-down menu). The different options for selection are set, the player is prompted to buy an item, and their response determines what occurs.
        JDialog.setDefaultLookAndFeelDecorated(true);
        Object[] selectionValues = {"Dungeon Portal (1000 gold)", "Rusty Key (200 gold)", "Gold Pouch (1250 gold)", "Gold Bar (3500 gold)", "Double Damage (10000 gold)", "Insta-Kill (1000 gold)"};
        String initialSelection = "Dungeon Portal (1000 gold)";
        Object selection = JOptionPane.showInputDialog(null, "What item(s) do you want to buy?", "Boost Shop", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        if (selection != null) { //If the player hits OK to purchase AND they have enough gold, the corresponding item slot in their inventory reveals the image, and they now have that item in their inventory.
            inventorySelection(selection, "Dungeon Portal (1000 gold)", 1000, 1, 4);
            inventorySelection(selection, "Rusty Key (200 gold)", 200, 1, 0);
            inventorySelection(selection, "Gold Pouch (1250 gold)", 1250, 1, 6);
            inventorySelection(selection, "Gold Bar (3500 gold)", 3500, 1, 7);
            inventorySelection(selection, "Double Damage (10000 gold)", 10000, 2, 5);
            inventorySelection(selection, "Insta-Kill (1000 gold)", 1000, 2, 7);
        }
        checkDisplay(selection); //The player's total amount of gold is checked after they select an item to buy.
    }
    
    public void magicShop() { //This method handles the second type of shop, a magic shop where the player can purchase spells from the merchant for a certain amount of gold. It follows the exact same logic as above (see boostShop)
        JDialog.setDefaultLookAndFeelDecorated(true);
        Object[] selectionValues = {"Enhanced Fireball (2000 gold)", "Enhanced Lightning (3000 gold)", "Meteor Swarm (3500 gold)", "Poison Cloud (3000 gold)", "Phase Shift (4000 gold)"};
        String initialSelection = "Enhanced Fireball (2000 gold)";
        Object selection = JOptionPane.showInputDialog(null, "What item(s) do you want to buy?", "Magic Shop", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        if (selection != null) {
            inventorySelection(selection, "Enhanced Fireball (2000 gold)", 2000, 2, 0);
            inventorySelection(selection, "Enhanced Lightning (3000 gold)", 3000, 2, 1);
            inventorySelection(selection, "Meteor Swarm (3500 gold)", 3500, 2, 2);
            inventorySelection(selection, "Poison Cloud (3000 gold)", 3000, 2, 3);
            inventorySelection(selection, "Phase Shift (4000 gold)", 4000, 2, 4);
        }
        checkDisplay(selection);
    }
    
    public void potionShop() { //This method concerns itself with the third type of shop, the potionShop, which sells items that permanently boost the player's stats (other than the health potion, which restores health)
        JDialog.setDefaultLookAndFeelDecorated(true); //The logic is the same as above.
        Object[] selectionValues = {"Health Potion (4000 gold)", "Damage Potion (5000 gold)", "Speed Potion (2000 gold)", "Experience Potion (5000 gold)"};
        String initialSelection = "Health Potion (4000 gold)";
        Object selection = JOptionPane.showInputDialog(null, "What item(s) do you want to buy?", "Potion Shop", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        if (selection != null) {
            inventorySelection(selection, "Health Potion (4000 gold)", 4000, 0, 3);
            inventorySelection(selection, "Damage Potion (5000 gold)", 5000, 0, 4);
            inventorySelection(selection, "Speed Potion (2000 gold)", 2000, 0, 5);
            inventorySelection(selection, "Experience Potion (5000 gold)", 5000, 0, 6);
        }
        checkDisplay(selection);
    }
    
    public void checkDisplay(Object selection) { //If the player doesn't select an item, or they don't have enough gold to pay for the item, the dialog box closes/disperses. Otherwise, the cost is subtracted from their total, and they obtain the item. The amount of gold they have after completing this transaction is also listed.
        if (gold < cost || selection == null) {
            JOptionPane.getRootFrame().dispose();
        } else {
            gold -= cost;
        }
        displayGold();
    }
    
    public void displayGold() { //Displays a JDialog with only a cancel option that shows the player how much gold they have left.
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(null, "You have " + gold + " gold remaining.");
    }
    
    public void sellItems() { //A method concerned with the selling of items in the inventory.
        switch(currentRow - 1) { //The inventory is divided up into three general sections, hence the 3 methods. The first row is dedicated to equipment (and potions) while the second row has all the keys and portals. The third row has all of the powerful boosts the game has to offer.
            case 0 -> sellEquipment();
            case 1 -> sellKeyItems();
            case 2 -> sellBoosts();
        }
    }
    
    public void sellEquipment() { //A method that contains the logic for selling equipment.
        if ((currentColumn - 1) != 0) { //If the player's cursor in the inventory isn't over the base character model, and if the item is found at the given currentRow - 1 and currentColumn - 1, the item is sold.
            if (inventory.isItemFound[currentRow - 1][currentColumn - 1]) {
                switch(currentColumn - 1) { //The player recieves an amount of gold dependent on the item they sell, and the item is removed from their inventory.
                    case 1 -> gold += 7500;  
                    case 2 -> gold += 10000;
                    case 4 -> gold += 3000;
                    case 3, 5 -> gold += 1500;
                    case 6 -> gold += 4000;
                }
                inventory.isItemFound[currentRow - 1][currentColumn - 1] = false;
            }
        }
    }
    
    public void sellKeyItems() { //A method responsible for the logic surrounding selling key items. Note that certain key items can be sold, whereupon a workaround will have to be found by the player
        if (inventory.isItemFound[currentRow - 1][currentColumn - 1]) { //This follows much of the same logic as above.
            switch(currentColumn - 1) {
                case 0 -> gold += 100;
                case 1, 2, 3 -> gold += 5000;
                case 4 -> gold += 900;
                case 5 -> gold += 1000;
                case 6 -> gold += 1000;
                case 7 -> gold += 3000;
            }
            inventory.isItemFound[currentRow - 1][currentColumn - 1] = false;
        }
    }
    
    public void sellBoosts() { //A method surrounding the logic for selling boosts. 
        if (inventory.isItemFound[currentRow - 1][currentColumn - 1]) { //Follows exactly the same logic as above, and instaKill sets the gold to 0 as a bit of a joke here.
            switch(currentColumn - 1) {
                case 0 -> gold += 1000;
                case 1, 3 -> gold += 2000;
                case 2, 4 -> gold += 3000;
                case 5 -> gold += 9500; 
                case 6 -> gold += 15000;
                case 7 -> gold = 0;
            }
            inventory.isItemFound[currentRow - 1][currentColumn - 1] = false;
        }
    }
    
    public void decorateJOptionPane(String message, String title, int response) { //A method used for decorating the JOptionPane for quests, and determining the player's response.
        JDialog.setDefaultLookAndFeelDecorated(true);
        determineResponse(message, title, response);
    }
    
    public void showRequest() { //This request is shown in a JDialog, with the player's final response determining what route of the game they go down. 
        if (optionPane == null) {
            response2 = optionPane.showConfirmDialog(null, "You've been lied to for far too long.\nI'm only trying to restore order and freedom to the realm.\nA world without the Imperium is a world where millions can thrive rather than being forced to comply to the Imperium's demands\nSo, what do you say?", "Would you like to join me and put an end to the Imperium?", JOptionPane.YES_NO_OPTION);
            determineFinalResponse(response2);
        }
    }
    
    public void determineFinalResponse(int response2) { //If the player tries to click out/cancel, or hits no, the scene goes to 38. Else, the scene increases to 39 and the game ends.
        if (response2 == JOptionPane.NO_OPTION || response2 == JOptionPane.CANCEL_OPTION) {
            battleConfirmed = true;
            scene = 38; //A scene where it appears that the necromancer crashes the game. In reality, the player can just hit enter to progress.
        } else if (response2 == JOptionPane.YES_OPTION) {
            scene = 39; //If the player hits yes, they go right to the epilogue cutscene.
        }
        JOptionPane.getRootFrame().dispose(); //Regardless of what the player clicks, the JOptionPane disappears and they're locked into their route. 
        battleUndecided = false;
    }
    
    public void quest() { //The initial quest offers for the 3 quest-giving NPC's are displayed here.
        if (player.intersects(guard1) && !questAccepted) {
            displayQuest("I know we're supposed to be the town guards and all, but we really don't feel like doing our jobs right now. Could you please kill 10 creatures for us? We'll give you 5000 gold as a reward.", "Quest Offer");
        } else if (player.intersects(axeDwarf) && !quest2Accepted) {
            displayQuest("In case you need an extra strength boost before taking on that necromancer, I have just the item for you. \nYou need to be strong enough to wield it, however...", "BECOME LEVEL 4 OR ELSE");
        } else if (player.intersects(wizard) && !quest3Accepted) {
            displayQuest("I'm looking for the double damage boost to help myself become stronger, and will give you some cash as a reward. \nPress E to exchange the item when near me.", "ACCEPT THIS QUEST PLEASE");
        }
    }
    
    public void displayQuest(String message, String title) { //The player's response is determined for each one of the quests. If the player hits no, the JDialog disposes and they don't have to complete the quest, but can click Q again to accept the quest if they wish.
        int response = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        determineResponse(message, title, response);
    }
    
    public void questNotComplete() { //Boolean values are used to track whether the quests offered by NPCs are completed, accepted, or if the reward is given by them. Based on this, they give a response which only lets the player hit cancel to exit out of the JDialog. After the player hits cancel, it disposes.
        if (player.intersects(guard1) && questAccepted && !questCompleted) {
            decorateJOptionPane("You should be out doing your quest. If you don't complete it fast enough, the leaders of the Nightfall Imperium will be displeased with you.", "Quest Undertaken", 0);
        } else if (player.intersects(axeDwarf) && quest2Accepted && !quest2Completed) {
            decorateJOptionPane("Yer not powerful enough yet. \nGet more EXP points from the enemies and then come back.", "Not Competent Enough", 0);
        } else if (player.intersects(wizard) && quest3Accepted && !quest3Completed) {
            decorateJOptionPane("Get me the item shortly, or else I will contact your leaders and they will come here themselves to deal with you. \nPress E to exchange the item when near me.", "Item Not Found", 0);
        }
    }
    
    public void questCompleted() { //Method that follows the same logic as above; the player gets a reward after completing each quest, and the quest is flagged as completed.
        if (player.intersects(guard1)) {
            decorateJOptionPane("Good work, fellow subject of the Imperium. Aren't you quite the murderer.", "Quest Completed", 0);
            gold += 5000;
            rewardGiven = true;
        } else if (player.intersects(axeDwarf)) {
            decorateJOptionPane("Good job, you're powerful enough now. \nKill that necromancer.", "EXP Recieved", 0);
            inventory.isItemFound[2][6] = true;
            reward2Given = true;
        } else if (player.intersects(wizard)) {
            decorateJOptionPane("I already gave you the gold. \nThanks for handing over the most powerful boost in the game. You played yourself.", "Item Given", 0);
            gold += 10000;
            reward3Given = true;
        }
    }
    
    public void rewardAlreadyGiven() { //Default dialogue that the NPCs say after the quest they offer is already completed. 
        if (player.intersects(guard1)) {
            decorateJOptionPane("We already gave you your payment. That's more than enough for you to go and spend it on whatever exorbitant thing you want. All hail the Imperium.", "Reward Given", 0);
        } else if (player.intersects(axeDwarf)) {
            decorateJOptionPane("There's nothing new for me to offer. Move along.", "NOTHING NEW", 0);
        } else if (player.intersects(wizard)) {
            decorateJOptionPane("Thanks for giving me the item. \nI really appreciate it. Also, my condolences for your loss.", "THANKS", 0);
        }
    }
    
    public void determineResponse(String message, String title, int response) { //A method used to determine what happens when the player clicks no, yes, or cancel for any of the given quests. 
        if (response == JOptionPane.YES_OPTION) {
            if ("Quest Offer".equals(title) && !questAccepted) {
                questAccepted = true;
            } else if ("BECOME LEVEL 4 OR ELSE".equals(title) && !quest2Accepted) {
                quest2Accepted = true;
            } else if ("ACCEPT THIS QUEST PLEASE".equals(title) && !quest3Accepted) {
                quest3Accepted = true;
            }
        } else if (response == JOptionPane.CANCEL_OPTION || response == JOptionPane.NO_OPTION) { 
            if ("Quest Offer".equals(title) || "BECOME LEVEL 4 OR ELSE".equals(title) || "ACCEPT THIS QUEST PLEASE".equals(title)) {
                JOptionPane.getRootFrame().dispose();
                return; 
            }
        }
        if (response == 0 && ("Quest Undertaken".equals(title) || "Quest Completed".equals(title) || "Reward Given".equals(title) || "Not Competent Enough".equals(title) || "EXP Recieved".equals(title) || "NOTHING NEW".equals(title) || "Item Not Found".equals(title) || "Item Given".equals(title) || "THANKS".equals(title))) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
    public void guardDialogue(String dialogue, int randomInt) { //A dialogue method for the guards that cycles through different random dialog choices and displays one of them.
        switch(randomInt) {
            case 1 -> dialogue = "The leader of the Nightfall Imperium, Sirus, personally had us stationed up here, on duty 24/7 while you complete your little quest.\nIt's been a while since I've got any sleep thanks to them. You better be fast with this whole killing-the-necromancer thing.";
            case 2 -> dialogue = "Once, I heard from a guy that the Nightfall Imperium actually came to power through fear and total conquest of those who opposed them.\nI had him executed for that. As the days go by, I'm starting to believe what he said may have been true after all.";
            case 3 -> dialogue = "If you haven't noticed already, it's always bright over here.\nI think someone might've cast some magic to make it eternally day in this area.";
            case 4 -> dialogue = "Y'know, I used to be an adventurer like you, then I took an arrow to the knee.";
            case 5 -> dialogue = "I heard that the necromancer, for all he's worth, can easily be killed with the magic available to you.\nJust get the Insta-Kill boost and use it right away to reap the benefits for the rest of your time here.";
            case 6 -> dialogue = "The Imperium now has control over almost every part of the continent of Feigrstad that we're on.\nSeems like rebelling against them is pointless now, but the necromancer just didn't listen.";
            case 7 -> dialogue = "I think you should just take over guard duty yourself.\nAfter all, you could probably wait around in this area without killing the necromancer and nothing'll change anyways";
            case 8 -> dialogue = "Someone told me that the Nightfall Imperium gave you a guide.\nDon't bother reading it, they're lying to you anyways";
            case 9 -> dialogue = "The merchants have an infinite stock of speed potions available.\nIf I were you, I'd trade in my starting equipment for gold and then zoom around for the rest of the time.";
        }
        displayDialogue(dialogue);
    }
    
    public void boostMerchantDialogue(String dialogue, int randomInt) { //A dialogue method for the boost merchant that cycles through different random dialog choices and displays one of them.
        switch(randomInt) {
            case 1 -> dialogue = "There are a variety of boosts you can get out in the world that we don't sell here.";
            case 2 -> dialogue = "I heard the best bonus that you could obtain is one that lets you jump right to level 5.\nOf course, it's probably in the final area of the game.";
            case 3 -> dialogue = "The Imperium stationed us out here in the freezing cold and made it so that we couldn't leave.\nHow nice of them.";
            case 4 -> dialogue = "Even though the Imperium may not be the kindest, they've restored order to an entire continent.\nIf I need to endure hardship for millions of people to enjoy their lives, that's a sacrifice I'm willing to take.";
            case 5 -> dialogue = "Since the guards in the guard towers over there won't give you their names, one of them's named John Armshound and the other's named John Doe.\nThat second name seems very generic to me.";
            case 6 -> dialogue = "I heard you were brought back just for this one mission. What was the other side like? \nThe Imperium'll probably give you immortality after you do this mission anyways.";
            case 7 -> dialogue = "You may notice that after a while, we all say lines you've heard before. \nThe person who programmed us must not've been very creative.";
            case 8 -> dialogue = "A small piece of advice, since the guards and the Imperium are here to laugh at your expense: do not use the Insta-Kill boost. \nIt doesn't do what you think it will.";
            case 9 -> dialogue = "Any boosts you apply will last for the rest of the game, minus one of them that lets you walk through walls and other structures. \nThat one only lasts for 1 area.";
        }
        displayDialogue(dialogue);
    }
    
    public void magicMerchantDialogue(String dialogue, int randomInt) { //A dialogue method for the magic merchant that cycles through different random dialog choices and displays one of them.
        switch(randomInt) {
            case 1 -> dialogue = "The magic may be a bit tricky to get the hang of at first, as it fires off at different distances depending on what direction you look. \nThat's part of the fun.";
            case 2 -> dialogue = "Since you have to multitask when using items, my advice is to use them before getting into a fight, or after.";
            case 3 -> dialogue = "Bonfires are a great tool to use to restore health. \nThere's only a few of them, but if you die, the mission fails and they have to rewind time back to when you were first ressurrected.";
            case 4 -> dialogue = "I have no idea where the music's coming from. Yes, we can all hear it in-world too. \nThe loop gets really predictable when you hear it nonstop.";
            case 5 -> dialogue = "The magic that I sell isn't anything new, but it simply boosts your attack damage with those magic spells. \nYou should stack it alongside a double damage boost you get from the other merchant over there.";
            case 6 -> dialogue = "There are three merchants here, I'm named Bartholemew Wizard, the one opposite me is Karen Potionseller, and the boost guy is Lancelot Armorwearer. \nI have no idea who came up with those names but I'm being serious here.";
            case 7 -> dialogue = "There's a sixth tent in the area to the right. \nThat's for if the leader of the Imperium, Sirus, wants to make sure that we're doing our jobs and not slacking off. \nIf he catches us sleeping in one of the 5 tents, he'll probably fire us";
            case 8 -> dialogue = "I heard there's a clearing in the forest to the southwest somewhere. \nYou may want to look for it when you go down there.";
            case 9 -> dialogue = "The necromancer's dungeon's quite a ways away. \nI don't know why he'd make it obvious where he operates, but that doesn't surprise me. \nHe just wants to look imposing.";
        }
        displayDialogue(dialogue);
    }
    
    public void potionMerchantDialogue(String dialogue, int randomInt) { //A dialogue method for the potion merchant that cycles through different random dialog choices and displays one of them.
        switch(randomInt) {
            case 1 -> dialogue = "There are a total of 5 potions in the game. \nThe one I don't sell is one that's meant to cancel out a necromancer's abilities and was crafted by my ancestors. \nSome say it's deep within the grasslands";
            case 2 -> dialogue = "The experience, speed, and damage potions that I sell should be used right away. \nThere's no point holding onto them as you can only have one at a time.";
            case 3 -> dialogue = "If you feel like your health is getting low and you're near the end of an area, it may be worth it to keep a health potion on hand and not use it. \nYou may find a bonfire, after all.";
            case 4 -> dialogue = "We could have got an entire army to face this necromancer, or even Sirus himself, but I think he was just too lazy so he outsourced the work. \nHe's a god beyond time, and could've saved us all from having to do this if he just used a wish spell. \nBut of course he didn't.";
            case 5 -> dialogue = "There are different ways you can approach the task of facing down the necromancer. \nHeavier armour will give you more defense, but will be slower to move in, and vice versa for lighter armour.";
            case 6 -> dialogue = "As you get more experienced, you obtain different moves, some of which are dependent on the type of armour you wear. \nIt's worthwhile to change up your armour during a fight as an enemy may be weak to one type of damage and strong against another.";
            case 7 -> dialogue = "Experience potions may be expensive, but they're worthwhile. \nThey basically give you the equivalent of hours worth of training, without you having to kill a single enemy.";
            case 8 -> dialogue = "You may want to be careful around here as the way you approach situations may affect your fight with the necromancer. \nHe can revive the dead, after all.";
            case 9 -> dialogue = "Good luck out there. You may want to consider coming back here and healing up as you get further along. \nEnemies won't respawn after you kill them, thankfully.";
        }
        displayDialogue(dialogue);
    }
    
    public void wizardDialogue(String dialogue, int randomInt) { //Dialogue for the wizard
        switch(randomInt) {
            case 1 -> dialogue = "Winterveil and this part of the grasslands haven't been around very long. They were just built several days ago to help you on your adventure.";
            case 2 -> dialogue = "There's a bronze door to the northeast that...YOU SHALL NOT PASS...through...unless you have the key";
            case 3 -> dialogue = "The Mistwood Forest was a natural old-growth forest that predates much of the Imperium's influence on this continent.\nIt has since been corrupted by the necromancer ever since he moved to his dungeon 3 months ago.";
            case 4 -> dialogue = "This used to be all unoccupied land, as the locals were driven away by sightings of people resurrecting from the cemetery over in the mountains.\nNo, you're not the first.\nThe necromancer always finds ways to cheat death.";
            case 5 -> dialogue = "If you're wondering what you did before your demise, you were one hell of a soldier in your prime.\nStormborn isn't your real last name, just an inherited title because of how you led the Imperium to victory in most of their major conquests\nYou really matter to them, which is why they brought you back.";
            case 6 -> dialogue = "By now you may have seen some of the other things I said.\nI know this much because I've lived far longer than you can comprehend.\nI know about all the local gods, places of interest, and people.";
            case 7 -> dialogue = "Those enemies you encountered in the forest were servants of the necromancer. \nThey were people, but they unfortunately chose the wrong side.";
            case 8 -> dialogue = "For some reason the cleric and the dwarf haven't moved since I've got here. \nThey're both just staring out into space.\nYou do know that the quests you take from them won't have any real impact, do you?";
            case 9 -> dialogue = "A wizard is never late. He arrives precisely when he means to.";
        }
        displayDialogue(dialogue);
    }
    
    public void clericDialogue(String dialogue, int randomInt) { //Dialog for the cleric (sometimes meta, sometimes dark, gives lore info to curious players)
        switch(randomInt) {
            case 1 -> dialogue = "Hopefully you've stocked up on potions by now. If not, you'll have to backtrack.";
            case 2 -> dialogue = "You should interact with the bonfire and obelisk while you're here.\nThey'll help you on your journey.";
            case 3 -> dialogue = "Sometimes, there's places that you can't access when you should be able to access them, and vice versa.\nAttribute that to magic, it definitely isn't a bug in the code.";
            case 4 -> dialogue = "Other adventurers who've ventured through here have reported that the chests contain different items every time.\nInteresting.";
            case 5 -> dialogue = "There are different ways to approach combat. \nFor instance, you don't always have to stay in the combat zone, you can heal up then come back.";
            case 6 -> dialogue = "We are all servants of the Imperium here. We do as they say, and practice what they preach.\nNon-Compliance comes with a heavy cost.";
            case 7 -> dialogue = "I heard the necromancer, Frazun Lonefall, was actually a really decent person.\nBut I'm sure that's just a lie anyways.";
            case 8 -> dialogue = "Whenever someone's done killing the necromancer and comes through here again, I feel like everything's been reset.\nThe two over there give their quests again, and the necromancer comes back.";
            case 9 -> dialogue = "If you're having trouble figuring out the inventory system by now, here's a few pointers:\nYou have to click on the inventory tab to navigate around. \nThen, click back to use the item that's selected in the inventory.\nAll items can be sold. \nBut don't give away some of them as they'll softlock you out of the rest of the game.";
        }
        displayDialogue(dialogue);
    }
    
    public void dwarfDialogue(String dialogue, int randomInt) { //Dialogue for the dwarf in the Grasslands
        switch(randomInt) {
            case 1 -> dialogue = "If you've picked up your starting equipment, you should have a giant flaming greatsword in your inventory.\nWeird that you can't kill enemies with it, but it was probably too hard to animate anyways.";
            case 2 -> dialogue = "In the forest and grasslands, the music always scares the heck out of me! \nIt comes out of nowhere, and is so tense for no reason! \nAll that I've seen in those places are unmoving statues, kind of unsettling but not enough to justify how loud the music is!";
            case 3 -> dialogue = "The Nightfall Imperium, the faction we all serve, is virtually everywhere in Feigrstad and Tenaebris. \nWho knows how those names should actually be pronounced.";
            case 4 -> dialogue = "You should exhaust every line of dialogue from every NPC. They may have important info to say.";
            case 5 -> dialogue = "if you die, don't get discouraged. \nDeath may come at the end, but it's just a plot device to make it easier for you to finish the game, for now.";
            case 6 -> dialogue = "The body of water to the north is called Whiteraft Bay.\nIt's very peaceful to go out to the clearing and simply stand there";
            case 7 -> dialogue = "I'm not sure why you're hovering above the ground when you walk. \nI guess that's how powerful you were before they resurrected you. \nOr are you just a ghost all along?";
            case 8 -> dialogue = "What's good is that it's a straight sprint from here to the dungeon to the necromancer. \nHowever, you should really save your items before facing him.";
            case 9 -> dialogue = "The necromancer's minions seem to just spawn in. \nI'm not sure how they get there, maybe they materialize out of thin air? \nIt's very strange.";
        }
        displayDialogue(dialogue);
    }
    
    public void displayDialogue(String dialogue) { //Displays the chosen piece of dialogue as a JDialog that the player can click to cancel out of 
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showMessageDialog(null, dialogue);
    }
    
    public void obtainItem(Image image1, int arrayDimension1, int arrayDimension2) { //A method used for determining what item(s) the player picks up when they intersect a chest. If it isn't one of the fixed chests (see below), they get a random item from a random row and random column. 
        boolean isRandomChest = (image1 == chest3 || image1 == chest6 || image1 == chest8 || image1 == chest9 || image1 == chest10 || image1 == chest11 || image1 == chest12 || image1 == chest14 || image1 == chest15 || image1 == chest16 || image1 == chest17 || image1 == chest18 || image1 == chest19 || image1 == chest20 || image1 == chest21 || image1 == chest22);
        if (isRandomChest) { //The boolean value isRandomChest is true if image1 equals any of those item chests. If the value is true, a random row and column is selected to be revealed in the inventory.
            randomRow();
            randomColumn();
        }
        inventory.isItemFound[arrayDimension1][arrayDimension2] = true;
        inventory.repaint(); //The inventory is immediately repainted to adjust, and the chest is set to null, disappearing. 
        chestDisappearance(image1);
    } 
    
    public void randomRow() { //Selects a random row out of 0, 1, or 2
        Random rand = new Random();
        rowNum = rand.nextInt(3);
    }
    
    public void randomColumn() { //Selects a "random" column based on what items should only be found in certain locations (ex: keys) and which ones can be found anywhere (ex: health potions)
        Random rand = new Random();
        if (rowNum == 0) {
            columnNum = rand.nextInt(4) + 3;
        } else if (rowNum == 1) {
            columnNum = rand.nextInt(2) + 6;
        } else if (rowNum == 2) {
            columnNum = rand.nextInt(5);
        }
    }
    
    public void chestDisappearance(Image image) { //If the image equals a given chest, it  sets said chest to null, making it not draw or function anymore. I couldn't find any way to optimize this as I wasn't sure how to convert from string to image in a for loop (ex: if imageName = chest + "i"...) 
        if (image == chest1) {
            chest1 = null;
        } else if (image == chest2) {
            chest2 = null;
        } else if (image == chest3) {
            chest3 = null;
        } else if (image == chest4) {
            chest4 = null;
        } else if (image == chest5) {
            chest5 = null;
        } else if (image == chest6) {
            chest6 = null;
        } else if (image == chest7) {
            chest7 = null;
        } else if (image == chest8) {
            chest8 = null;
        } else if (image == chest9) {
            chest9 = null;
        } else if (image == chest10) {
            chest10 = null;
        } else if (image == chest11) {
            chest11 = null;
        } else if (image == chest12) {
            chest12 = null;
        } else if (image == chest13) {
            chest13 = null;
        } else if (image == chest14) {
            chest14 = null;
        } else if (image == chest15) {
            chest15 = null;
        } else if (image == chest16) {
            chest16 = null;
        } else if (image == chest17) {
            chest17 = null;
        } else if (image == chest18) {
            chest18 = null;
        } else if (image == chest19) {
            chest19 = null;
        } else if (image == chest20) {
            chest20 = null;
        } else if (image == chest21) {
            chest21 = null;
        } else if (image == chest22) {
            chest22 = null;
        } else if (image == chest23) {
            chest23 = null;
        } else if (image == chest24) {
            chest24 = null;
        } else if (image == chest25) {
            chest25 = null;
        }
    }
    
    public void handleCollisionArray(Rectangle[] collisionArray, int endIndex) { //The first of the MANY collision methods, which were very painstakingly done manually as I didn't adhere to the tile system (would rather design the map with as many assets as I want rather than be forced to import and place each one); see the dungeon for why I did that.
        for (int i = 0; i < endIndex; i++) { //Takes in a value from one of the collision methods below representing the last index of the collisionArray, and if the player intersects one of those rectangles while noCollisions is false, the collision calculation is handled 
            if (player.intersects(collisionArray[i]) && !noCollisions) {
                handleCollisions(collisionArray[i]);
            }
        }
    }

    public void collision1() { //The first collision method, in which a rectangle array of length 6 is declared with specific dimensions for each rectangle, chest rectangles are drawn in, and collision is handled.
        Rectangle[] collisions = new Rectangle[6];
        collisions[0] = new Rectangle(0, 0, getWidth(), (int) (getHeight() * 0.7));
        collisions[1] = new Rectangle(0, (int) (getHeight() * 0.7), (int) (width() * 300), getHeight());
        collisions[2] = new Rectangle((int) (width() * 800), (int) (getHeight() * 0.7), getWidth(), getHeight());
        collisions[3] = new Rectangle((int) (width() * 488), (int) (getHeight() * 0.89), (int) (width() * 86), (int) (height() * 54));
        collisions[4] = new Rectangle((int) (width() * 690), (int) (getHeight() * 0.86), (int) (width() * 87), (int) (height() * 52));
        collisions[5] = new Rectangle((int) (width() * 600), (int) (getHeight() * 0.974), (int) (width() * 87), (int) (height() * 52));
        if (chest1 != null) { //If the player intersects the chest's rectangle, they obtain the item and the chest is set to null. This pattern is used for obtaining SPECIFIC items in the later methods.
            Rectangle CharCollision = new Rectangle((int) (width() * 360), (int) (height() * 570), (int) (width() * 64), (int) (height() * 64));
            if (player.intersects(CharCollision)) {
                obtainItem(chest1, 0, 1);
            }
        } if (chest2 != null) {
            Rectangle ArmourCollision = new Rectangle((int) (width() * 800), (int) (height() * 800), (int) (width() * 64), (int) (height() * 64));
            if (player.intersects(ArmourCollision)) {
                obtainItem(chest2, 0, 2);
            }
        }
        handleCollisionArray(collisions, 6);
    }

    public void collision2() { //The second collision method. I have two methods to handle collisions, one for arrays and one for single collisions; they function the same otherwise.
        Rectangle sideWallCollision = new Rectangle(0, 0, (int) (width() * 250), getHeight());
        if (player.intersects(sideWallCollision) && !noCollisions) {
            handleCollisions(sideWallCollision);
        }
    }

    public void collision3() { //The third collision method. Collisions are handled as per normal, but the NPC rectangles are also drawn in, which is how the player can talk and accept quests from them if they intersect them. With regards to the bonfire and obelisk, if the player intersects those two rectangles, they can interact with the objects.
        Rectangle[] towerCollisions = new Rectangle[2];
        towerCollisions[0] = new Rectangle((int) (width() * 60), (int) (height() * 100), (int) (width() * 90), (int) (height() * 70));
        towerCollisions[1] = new Rectangle((int) (width() * 60), (int) (height() * 680), (int) (width() * 75), (int) (height() * 100));
        bonfire1 = new Rectangle((int)(width() * 730), (int)(height() * 365), (int)(width() * 75), (int)(height() * 75));
        obelisk = new Rectangle((int)(width() * 785), (int)(height() * 75), (int)(width() * 125), (int)(height() * 125));
        guard1 = new Rectangle((int)(width() * 60), (int)(height() * 100), (int)(width() * 90), (int)(height() * 120));
        guard2 = new Rectangle((int)(width() * 60), (int)(height() * 620), (int)(width() * 75), (int)(height() * 100));
        handleCollisionArray(towerCollisions, 2);
        Rectangle[] offPath = new Rectangle[7];
        offPath[0] = new Rectangle(0, (int) (height() * 20), getWidth(), (int) (height() * 115));
        offPath[1] = new Rectangle((int) (width() * 900), 0, getWidth(), (int) (height() * 355));
        offPath[2] = new Rectangle((int) (width() * 120), (int) (height() * 190), (int) (width() * 600), (int) (height() * 175));
        offPath[3] = new Rectangle((int) (width() * 160), (int) (height() * 440), (int) (width() * 60), (int) (height() * 60));
        offPath[4] = new Rectangle((int) (width() * 410), (int) (height() * 430), (int) (width() * 320), getHeight());
        offPath[5] = new Rectangle((int) (width() * 130), (int) (height() * 650), (int) (width() * 280), getHeight());
        offPath[6] = new Rectangle((int) (width() * 800), (int) (height() * 430), getWidth(), getHeight());
        handleCollisionArray(offPath, 7);
    }

    public void collision4() { //The fourth collision method, which contains the rectangles for the merchants and buying items from them/talking to them as well.
        Rectangle[] pathCollision = new Rectangle[4];
        pathCollision[0] = new Rectangle(0, (int) (height() * 780), getWidth(), getHeight());
        pathCollision[1] = new Rectangle(0, 0, (int) (width() * 730), (int) (height() * 720));
        pathCollision[2] = new Rectangle((int) (width() * 800), 0, getWidth(), (int) (height() * 730));
        pathCollision[3] = new Rectangle((int)(width() * 440), 0, (int)(width() * 10), getHeight());
        handleCollisionArray(pathCollision, 4);
        boostMerchant = new Rectangle((int)(width() * 440), (int)(height() * 720), (int)(width() * 50), (int)(height() * 60));
        magicMerchant = new Rectangle((int)(width() * 745), (int)(height() * 50), (int)(width() * 40), (int)(height() * 150));
        potionMerchant = new Rectangle((int)(width() * 745), (int)(height() * 200), (int)(width() * 40), (int)(height() * 150));
    }

    public void collision5() { //The fifth collision method.
        Rectangle[] campCollisions = new Rectangle[3];
        campCollisions[0] = new Rectangle(0, 0, (int) (width() * 1150), (int) (height() * 730));
        campCollisions[1] = new Rectangle((int) (width() * 1225), 0, getWidth(), getHeight());
        campCollisions[2] = new Rectangle(0, (int) (height() * 780), getWidth(), getHeight());
        handleCollisionArray(campCollisions, 3);
    }

    public void collision6() { //The 6th collision method, where the player can obtain an item (same code as above) and they can move out of or into different scenes by intersecting a rectangle around a ladder
        Rectangle[] collision = new Rectangle[2];
        collision[0] = new Rectangle(0, 0, getWidth(), (int)(height() * 75));
        collision[1] = new Rectangle ((int)(width() * 250), (int) (height() * 470), (int) (width() * 530), (int) (height() * 210));
        Rectangle ladderCollision = new Rectangle((int) (width() * 480), (int) (height() * 440), (int) (width() * 50), (int) (height() * 50));
        Rectangle itemCollision = new Rectangle((int)(width() * 1100), (int)(height() * 350), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(itemCollision) && chest3 != null) {
            obtainItem(chest3, rowNum, columnNum);
        } if (player.intersects(ladderCollision)) {
            scene = 11;
            x = (int)(width() * 676);
            y = (int)(height() * 40);
        } 
        handleCollisionArray(collision, 2); //endIndex is always equal to the length of the collision array.
    }

    public void collision7() { //The seventh collision method; sometimes the player's x and y coordinates are set for different scenes here as well, because it's easiest to manage them here after the player intersects a given rectangle than in the corresponding area method.
        Rectangle ladderCollisionBelow = new Rectangle((int) (width() * 675), (int) (height() * 1), (int) (width() * 50), (int) (height() * 35));
        Rectangle speedPotionChest = new Rectangle((int)(width() * 500), (int)(height() * 450), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(ladderCollisionBelow)) {
            scene = 10;
            x = (int) (width() * 450);
            y = (int) (height() * 300);
        } if (player.intersects(speedPotionChest) && chest4 != null) {
            obtainItem(chest4, 0, 5);
        }
    }
    
    public void collision8() {  //One of the largest collision methods. The measurements here also scale for every type of computer that this code runs on and every size of JPanel. 
        Rectangle forestPortalChest = new Rectangle((int)(width() * 450), (int)(height() * 400), (int)(width() * 64), (int)(height() * 64));
        Rectangle someRandomChest = new Rectangle((int)(width() * 260), (int)(height() * 575), (int)(width() * 64), (int)(height() * 64));
        Rectangle anotherRandomChest = new Rectangle((int)(width() * 1215), (int)(height() * 270), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(forestPortalChest) && chest5 != null) {
            obtainItem(chest5, 1, 3);
        } if (player.intersects(someRandomChest) && chest6 != null) {
            obtainItem(chest6, rowNum, columnNum);
        } if (player.intersects(anotherRandomChest) && chest8 != null) {
            obtainItem(chest8, rowNum, columnNum);
        }
        Rectangle[] collisionTrees = new Rectangle[25];
        collisionTrees[0] = new Rectangle((int)(width() * 180), (int)(height() * 300), (int)(width() * 400), (int)(height() * 100));
        collisionTrees[1] = new Rectangle((int)(width() * 140), (int)(height() * 100), (int)(width() * 75), (int)(height() * 300));
        collisionTrees[2] = new Rectangle((int)(width() * 215), (int)(height() * 100), (int)(width() * 130), (int)(height() * 115));
        collisionTrees[3] = new Rectangle((int)(width() * 425), 0, (int)(width() * 75), (int)(height() * 300));
        collisionTrees[4] = new Rectangle((int)(width() * 525), (int)(height() * 300), (int)(width() * 60), (int)(height() * 225));
        collisionTrees[5] = new Rectangle(0, (int)(height() * 485), (int)(width() * 590), (int)(height() * 75));
        collisionTrees[6] = new Rectangle(0, (int)(height() * 75), (int)(width() * 55), (int)(height() * 130));
        collisionTrees[7] = new Rectangle((int)(width() * 210), (int)(height() * 570), (int)(width() * 55), (int)(height() * 85));
        collisionTrees[8] = new Rectangle((int)(width() * 710), 0, (int)(width() * 75), (int)(height() * 245));
        collisionTrees[9] = new Rectangle((int)(width() * 710), (int)(height() * 245), (int)(width() * 130), (int)(height() * 55));
        collisionTrees[10] = new Rectangle((int)(width() * 785), (int)(height() * 295), (int)(width() * 75), (int)(height() * 175));
        collisionTrees[11] = new Rectangle((int)(width() * 850), (int)(height() * 380), (int)(width() * 75), (int)(height() * 250));
        collisionTrees[12] = new Rectangle((int)(width() * 915), (int)(height() * 525), (int)(width() * 325), (int)(height() * 75));
        collisionTrees[13] = new Rectangle((int)(width() * 1200), (int)(height() * 580), (int)(width() * 270), (int)(height() * 75));
        collisionTrees[14] = new Rectangle((int)(width() * 1420), (int)(height() * 630), (int)(width() * 200), (int)(height() * 75));
        collisionTrees[15] = new Rectangle((int)(width() * 950), 0, (int)(width() * 50), (int)(height() * 260));
        collisionTrees[16] = new Rectangle((int)(width() * 1000), (int)(height() * 90), (int)(width() * 365), (int)(height() * 65));
        collisionTrees[17] = new Rectangle((int)(width() * 1065), (int)(height() * 360), (int)(width() * 500), (int)(height() * 60));
        collisionTrees[18] = new Rectangle((int)(width() * 1300), (int)(height() * 75), (int)(width() * 75), (int)(height() * 300));
        collisionTrees[19] = new Rectangle((int)(width() * 1375), (int)(height() * 225), (int)(width() * 60), (int)(height() * 150));
        collisionTrees[20] = new Rectangle((int)(width() * 1490), (int)(height() * 60), (int)(width() * 55), (int)(height() * 240));
        collisionTrees[21] = new Rectangle((int)(width() * 400), (int)(height() * 650), (int)(width() * 75), (int)(height() * 230));
        collisionTrees[22] = new Rectangle((int)(width() * 660), (int)(height() * 530), (int)(width() * 105), (int)(height() * 400));
        collisionTrees[23] = new Rectangle((int)(width() * 330), (int)(height() * 630), (int)(width() * 75), (int)(height() * 105));
        collisionTrees[24] = new Rectangle((int)(width() * 75), (int)(height() * 695), (int)(width() * 100), (int)(height() * 100));
        handleCollisionArray(collisionTrees, 25);
    }
    
    public void collision9() { //9th collision method
        Rectangle[] eastForestCollisions = new Rectangle[9];
        eastForestCollisions[0] = new Rectangle((int)(width() * 200), 0, getWidth(), (int)(height() * 75));
        eastForestCollisions[1] = new Rectangle((int)(width() * 1500), 0, (int)(width() * 75), (int)(height() * 695));
        eastForestCollisions[2] = new Rectangle((int)(width() * 520), (int)(height() * 775), (int)(width() * 1030), (int)(height() * 150));
        eastForestCollisions[3] = new Rectangle(0, (int)(height() * 695), (int)(width() * 315), (int)(height() * 100));
        eastForestCollisions[4] = new Rectangle((int)(width() * 445), (int)(height() * 450), (int)(width() * 75), (int)(height() * 200));
        eastForestCollisions[5] = new Rectangle((int)(width() * 205), (int)(height() * 625), (int)(width() * 240), (int)(height() * 65));
        eastForestCollisions[6] = new Rectangle(0, (int)(height() * 560), (int)(width() * 60), (int)(height() * 175));
        eastForestCollisions[7] = new Rectangle(0, (int)(height() * 100), (int)(width() * 60), (int)(height() * 175));
        eastForestCollisions[8] = new Rectangle(0, (int)(height() * 350), (int)(width() * 250), (int)(height() * 60));
        Rectangle silverKeyChest = new Rectangle((int)(width() * 900), (int)(height() * 400), (int)(width() * 64), (int)(height() * 64));
        handleCollisionArray(eastForestCollisions, 9);
        if (player.intersects(silverKeyChest) && chest7 != null) {
            obtainItem(chest7, 1, 1);
        }
    }
    
    public void collision10() { //10th Collision method, and the code remains the same, occasionally deviating. 
        Rectangle[] southTreeCollisions = new Rectangle[5];
        southTreeCollisions[0] = new Rectangle((int)(width() * 375), 0, (int)(width() * 75), (int)(height() * 160));
        southTreeCollisions[1] = new Rectangle((int)(width() * 700), 0, (int)(width() * 85), (int)(height() * 260));
        southTreeCollisions[2] = new Rectangle(0, 0, (int)(width() * 60), getHeight());
        southTreeCollisions[3] = new Rectangle(0, (int)(height() * 740), getWidth(), (int)(height() * 75));
        southTreeCollisions[4] = new Rectangle((int)(width() * 1480), 0, (int)(width() * 60), getHeight());
        handleCollisionArray(southTreeCollisions, 5);
        Rectangle randChest = new Rectangle((int)(width() * 700), (int)(height() * 500), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(randChest) && chest9 != null) {
            obtainItem(chest9, rowNum, columnNum);
        }
    }
    
    public void collision11() { //Collision method for an area in the Mistwood Forest.
        Rectangle[] leftTreeCollisions = new Rectangle[17];
        leftTreeCollisions[0] = new Rectangle((int)(width() * 80), 0, (int)(width() * 10), getHeight());
        leftTreeCollisions[1] = new Rectangle(0, (int)(height() * 750), (int)(width() * 885), (int)(height() * 100));
        leftTreeCollisions[2] = new Rectangle(0, 0, (int)(width() * 675), (int)(height() * 25));
        leftTreeCollisions[3] = new Rectangle((int)(width() * 800), (int)(height() * 680), (int)(width() * 800), (int)(height() * 100));
        leftTreeCollisions[4] = new Rectangle((int)(width() * 1050), (int)(height() * 695), (int)(width() * 60), (int)(height() * 180));
        leftTreeCollisions[5] = new Rectangle(0, (int)(height() * 200), (int)(width() * 200), (int)(height() * 260));
        leftTreeCollisions[6] = new Rectangle((int)(width() * 1100), 0, (int)(width() * 300), (int)(height() * 65));
        leftTreeCollisions[7] = new Rectangle((int)(width() * 200), (int)(height() * 450), (int)(width() * 500), (int)(height() * 75));
        leftTreeCollisions[8] = new Rectangle((int)(width() * 600), (int)(height() * 235), (int)(width() * 100), (int)(height() * 375));
        leftTreeCollisions[9] = new Rectangle((int)(width() * 700), (int)(height() * 260), (int)(width() * 700), (int)(height() * 115));
        leftTreeCollisions[10] = new Rectangle((int)(width() * 1075), 0, (int)(width() * 75), (int)(height() * 140));
        leftTreeCollisions[11] = new Rectangle((int)(width() * 185), (int)(height() * 130), (int)(width() * 400), (int)(height() * 65));
        leftTreeCollisions[12] = new Rectangle((int)(width() * 830), 0, (int)(width() * 60), (int)(height() * 100));
        leftTreeCollisions[13] = new Rectangle((int)(width() * 690), (int)(height() * 75), (int)(width() * 150), (int)(height() * 80));
        leftTreeCollisions[14] = new Rectangle((int)(width() * 1380), (int)(height() * 430), (int)(width() * 200), (int)(height() * 75));
        leftTreeCollisions[15] = new Rectangle((int)(width() * 1325), (int)(height() * 380), (int)(width() * 60), (int)(height() * 160));
        leftTreeCollisions[16] = new Rectangle((int)(width() * 1510), (int)(height() * 60), (int)(width() * 60), (int)(height() * 100));
        handleCollisionArray(leftTreeCollisions, 17);
        Rectangle randomChest1 = new Rectangle((int)(width() * 100), (int)(height() * 650), (int)(width() * 64), (int)(height() * 64));
        Rectangle randomChest2 = new Rectangle((int)(width() * 200), (int)(height() * 300), (int)(width() * 64), (int)(height() * 64));
        Rectangle randomChest3 = new Rectangle((int)(width() * 75), (int)(height() * 100), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(randomChest1) && chest10 != null) {
            obtainItem(chest10, rowNum, columnNum);
        } if (player.intersects(randomChest2) && chest11 != null) {
            obtainItem(chest11, rowNum, columnNum);
        } if (player.intersects(randomChest3) && chest12 != null) {
            obtainItem(chest12, rowNum, columnNum);
        }
    }
    
    public void collision12() { //Collision method with a portal that the player can intersect with. The player's EXP and gold are increased, they're teleported back to the start of the forest, and their kill count is increased despite them not having killed any enemies (so the final boss will be slightly tougher)
        Rectangle[] northEastTreeCollisions = new Rectangle[7];
        northEastTreeCollisions[0] = new Rectangle(0, (int)(height() * 790), (int)(width() * 650), (int)(height() * 65));
        northEastTreeCollisions[1] = new Rectangle(0, 0, (int)(width() * 75), (int)(height() * 900));
        northEastTreeCollisions[2] = new Rectangle(0, 0, (int)(width() * 1570), (int)(height() * 65));
        northEastTreeCollisions[3] = new Rectangle((int)(width() * 1490), 0, (int)(width() * 75), (int)(height() * 650));
        northEastTreeCollisions[4] = new Rectangle((int)(width() * 1490), (int)(height() * 720), (int)(width() * 100), (int)(height() * 140));
        northEastTreeCollisions[5] = new Rectangle((int)(width() * 775), (int)(height() * 650), (int)(width() * 75), (int)(height() * 220));
        northEastTreeCollisions[6] = new Rectangle((int)(width() * 1065), (int)(height() * 650), (int)(width() * 70), (int)(height() * 220));
        handleCollisionArray(northEastTreeCollisions, 7);
        Rectangle portalCollision = new Rectangle((int)(width() * 700), (int)(height() * 100), (int)(width() * 150), (int)(height() * 150));
        if (player.intersects(portalCollision)) {
            if (EXP < 5 && (level == 1 || level == 2)) {
                EXP += 4;
                gold += 5000;
                x = (int)(width() * 750);
                y = (int)(height() * 75);
                scene = 12;
                killCount += 2;
            }
        }
    }
    
    public void collision13() { //Collision for the Grasslands entrance
        Rectangle[] grasslandEntranceCollisions = new Rectangle[7];
        grasslandEntranceCollisions[0] = new Rectangle(0, 0, (int)(width() * 60), (int)(height() * 640));
        grasslandEntranceCollisions[1] = new Rectangle(0, (int)(height() * 750), (int)(width() * 60), (int)(height() * 100));
        grasslandEntranceCollisions[2] = new Rectangle(0, 0, (int)(width() * 850), (int)(height() * 70));
        grasslandEntranceCollisions[3] = new Rectangle((int)(width() * 1170), (int)(height() * 25), (int)(width() * 400), (int)(height() * 70));
        grasslandEntranceCollisions[4] = new Rectangle(0, (int)(height() * 815), (int)(width() * 850), (int)(height() * 70));
        grasslandEntranceCollisions[5] = new Rectangle((int)(width() * 1100), (int)(height() * 810), (int)(width() * 400), (int)(height() * 70));
        grasslandEntranceCollisions[6] = new Rectangle((int)(width() * 1490), 0, (int)(width() * 70), (int)(height() * 870));
        handleCollisionArray(grasslandEntranceCollisions, 7);
    }
    
    public void collision14() { //Collision for the Clearing
        Rectangle clearingChest = new Rectangle((int)(width() * 750), (int)(height() * 650), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(clearingChest) && chest13 != null) {
            obtainItem(chest13, 0, 7);
        }
        Rectangle[] clearingCollisions = new Rectangle[5];
        clearingCollisions[0] = new Rectangle(0, 0, (int)(width() * 60), getHeight());
        clearingCollisions[1] = new Rectangle(0, (int)(height() * 180), (int)(width() * 1600), (int)(height() * 70));
        clearingCollisions[2] = new Rectangle((int)(width() * 1200), (int)(height() * 800), (int)(width() * 600), (int)(height() * 70));
        clearingCollisions[3] = new Rectangle((int)(width() * 1500), 0, (int)(width() * 70), getHeight());
        clearingCollisions[4] = new Rectangle(0, (int)(height() * 800), (int)(width() * 795), (int)(height() * 75));
        handleCollisionArray(clearingCollisions, 5);
    }
    
    public void collision15() { //Collision for the Refuge, which is where players can talk to and accept quests from NPCs
        Rectangle[] refugeCollisions = new Rectangle[7];
        refugeCollisions[0] = new Rectangle(0, 0, (int)(width() * 60), getHeight());
        refugeCollisions[1] = new Rectangle((int)(width() * 1495), 0, (int)(width() * 60), getHeight());
        refugeCollisions[2] = new Rectangle(0, (int)(height() * 830), getWidth(), (int)(height() * 40));
        refugeCollisions[3] = new Rectangle(0, 0, (int)(width() * 850), (int)(height() * 70));
        refugeCollisions[4] = new Rectangle((int)(width() * 1270), 0, (int)(width() * 300), (int)(height() * 60));
        refugeCollisions[5] = new Rectangle((int)(width() * 665), (int)(height() * 350), (int)(width() * 200), (int)(height() * 90));
        refugeCollisions[6] = new Rectangle((int)(width() * 1320), (int)(height() * 600), (int)(width() * 100), (int)(height() * 200));
        handleCollisionArray(refugeCollisions, 7);
        cleric = new Rectangle((int)(width() * 70), (int)(height() * 500), (int)(width() * 100), (int)(height() * 100));
        axeDwarf = new Rectangle((int)(width() * 310), (int)(height() * 50), (int)(width() * 100), (int)(height() * 100));
        wizard = new Rectangle((int)(width() * 1295), (int)(height() * 50), (int)(width() * 100), (int)(height() * 100));
        obelisk2 = new Rectangle((int)(width() * 1320), (int)(height() * 600), (int)(width() * 100), (int)(height() * 200));
        bonfire2 = new Rectangle((int)(width() * 665), (int)(height() * 350), (int)(width() * 200), (int)(height() * 90));
    }
    
    public void collision16() { //Collision for the left-centre grasslands/scene 20
        Rectangle itemChest = new Rectangle((int)(width() * 1200), (int)(height() * 400), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(itemChest) && chest14 != null) {
            obtainItem(chest14, rowNum, columnNum);
        }
    }
    
    public void collision17() { //Collision for the left-bottom grasslands/scene 21
        Rectangle[] southBoundaryWalls = new Rectangle[2];
        southBoundaryWalls[0] = new Rectangle(0, (int)(height() * 800), getWidth(), (int)(height() * 70));
        southBoundaryWalls[1] = new Rectangle(0, 0, (int)(width() * 50), getHeight());
        Rectangle anotherChest = new Rectangle((int)(width() * 180), (int)(height() * 700), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(anotherChest) && chest15 != null) {
            obtainItem(chest15, rowNum, columnNum);
        }
        handleCollisionArray(southBoundaryWalls, 2);
    }
    
    public void collision18() { //Collision for the left-top grasslands/scene 22
        Rectangle[] northBoundaryWalls = new Rectangle[2];
        northBoundaryWalls[0] = new Rectangle(0, 0, getWidth(), (int)(height() * 50));
        northBoundaryWalls[1] = new Rectangle(0, 0, (int)(width() * 50), getHeight());
        handleCollisionArray(northBoundaryWalls, 2);
    }
    
    public void collision19() { //Collision for the centre-bottom grasslands/scene 23
        Rectangle grasslandChest = new Rectangle((int)(width() * 1350), (int)(height() * 180), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(grasslandChest) && chest16 != null) {
            obtainItem(chest16, rowNum, columnNum);
        }
        Rectangle southCollision = new Rectangle(0, (int)(height() * 800), getWidth(), (int)(height() * 75));
        handleCollisions(southCollision);
    }
    
    public void collision20() { //Collision for the centre-centre grasslands/scene 24
        Rectangle centreGrassChest = new Rectangle((int)(width() * 600), (int)(height() * 250), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(centreGrassChest) && chest17 != null) {
            obtainItem(chest17, rowNum, columnNum);
        }
    }
    
    public void collision21() { //Collision for the centre-top grasslands/scene 25
        Rectangle northCollision = new Rectangle(0, 0, getWidth(), (int)(height() * 50));
        handleCollisions(northCollision);
        Rectangle grassChest = new Rectangle((int)(width() * 1050), (int)(height() * 300), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(grassChest) && chest18 != null) {
            obtainItem(chest18, rowNum, columnNum);
        }
    }
    
    public void collision22() { //Collision for the right-bottom grasslands/scene 26
        Rectangle[] southEastCollisions = new Rectangle[2];
        southEastCollisions[0] = new Rectangle(0, (int)(height() * 800), getWidth(), (int)(height() * 70));
        southEastCollisions[1] = new Rectangle((int)(width() * 1500), 0, (int)(width() * 70), getHeight());
        handleCollisionArray(southEastCollisions, 2);
        Rectangle southEastChest = new Rectangle((int)(width() * 565), (int)(height() * 700), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(southEastChest) && chest19 != null) {
            obtainItem(chest19, rowNum, columnNum);
        }
    }
    
    public void collision23() { //Collision for the right-centre grasslands/scene 27
        Rectangle eastCollision = new Rectangle((int)(width() * 1500), 0, (int)(width() * 70), getHeight());
        Rectangle rightChest = new Rectangle((int)(width() * 140), (int)(height() * 530), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(rightChest) && chest20 != null) {
            obtainItem(chest20, rowNum, columnNum);
        }
        handleCollisions(eastCollision);
    }
    
    public void collision24() { //Collision for the right-top grasslands/scene 28
        Rectangle[] northEastCollisions = new Rectangle[2];
        northEastCollisions[0] = new Rectangle((int)(width() * 1500), 0, (int)(width() * 70), getHeight());
        northEastCollisions[1] = new Rectangle(0, 0, getWidth(), (int)(height() * 250));
        handleCollisionArray(northEastCollisions, 2);
        dungeonDoor = new Rectangle ((int)(width() * 575), (int)(height() * 100), (int)(width() * 250), (int)(height() * 200));
    }
    
    public void collision25() { //Collision for an early area in the dungeon
        Rectangle firstDungeonChest = new Rectangle((int)(width() * 300), (int)(height() * 600), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(firstDungeonChest) && chest21 != null) {
            obtainItem(chest21, rowNum, columnNum);
        }
    }
    
    public void collision26() { //Collision for the second area in the dungeon
        Rectangle[] dungeon2Collisions = new Rectangle[3];
        dungeon2Collisions[0] = new Rectangle(0, 0, (int)(width() * 600), (int)(height() * 200));
        dungeon2Collisions[1] = new Rectangle(0, (int)(height() * 525), (int)(width() * 260), (int)(height() * 230));
        dungeon2Collisions[2] = new Rectangle((int)(width() * 1300), (int)(height() * 340), (int)(width() * 300), (int)(height() * 300));
        handleCollisionArray(dungeon2Collisions, 3);
    }
    
    public void collision27() { //Collision for the chest that gives the escapePortal out of the dungeon, hence why the chest is called escapeDungeon
        Rectangle escapeDungeon = new Rectangle((int)(width() * 700), (int)(height() * 200), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(escapeDungeon) && chest22 != null) {
            obtainItem(chest22, 1, 5);
        }
    }
    
    public void collision28() { //Collision for the only trapped chest in the game, which will kill the player (a fun trap included here to throw off expectations)
        Rectangle trappedChest = new Rectangle((int)(width() * 1300), (int)(height() * 100), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(trappedChest) && chest23 != null) {
            chest23 = null;
            scene = 999;
        }
    }  
    
    public void collision29() { //Collision for the cursed bonfire area, which, when interacted with, will reduce a player's health to 1 and set their EXP to 0.
        cursedBonfire = new Rectangle((int)(width() * 200), (int)(height() * 200), (int)(width() * 150), (int)(height() * 130));
        handleCollisions(cursedBonfire);
    }
    
    public void collision30() { //Collision for the area in the dungeon with the chest that gives the essential Gold Key item, that lets the player into the final boss room  
        Rectangle[] dungeon6Collisions = new Rectangle[4];
        dungeon6Collisions[0] = new Rectangle(0, 0, (int)(width() * 60), getHeight());
        dungeon6Collisions[1] = new Rectangle((int)(width() * 1500), 0, (int)(width() * 40), getHeight());
        dungeon6Collisions[2] = new Rectangle(0, (int)(height() * 820), (int)(width() * 670), (int)(height() * 40));
        dungeon6Collisions[3] = new Rectangle((int)(width() * 900), (int)(height() * 820), getWidth(), (int)(height() * 40));
        handleCollisionArray(dungeon6Collisions, 4);
        Rectangle keyChest = new Rectangle((int)(width() * 200), (int)(height() * 500), (int)(width() * 64), (int)(height() * 64)); 
        if (player.intersects(keyChest) && chest24 != null) {
            obtainItem(chest24, 1, 2);
        }
    }
    
    public void collision31() { //Collision for an area in the dungeon containing the doubleDamageBoost
        Rectangle[] dungeon7Collisions = new Rectangle[3];
        dungeon7Collisions[0] = new Rectangle((int)(width() * 50), (int)(height() * 350), (int)(width() * 150), (int)(height() * 75));
        dungeon7Collisions[1] = new Rectangle(0, 0, (int)(width() *  100), getHeight()); 
        dungeon7Collisions[2] = new Rectangle((int)(width() * 1490), 0, (int)(width() * 75), getHeight());
        handleCollisionArray(dungeon7Collisions, 3);
        bonfire3 = new Rectangle((int)(width() * 50), (int)(height() * 350), (int)(width() * 150), (int)(height() * 75));
        Rectangle doubleDamageBoost = new Rectangle((int)(width() * 650), (int)(height() * 700), (int)(width() * 64), (int)(height() * 64));
        if (player.intersects(doubleDamageBoost) && chest25 != null) {
            obtainItem(chest25, 2, 5);
        }
    }
    
    public void collision32() { //Collision for the final area before the boss fight in the dungeon
        Rectangle finalWall = new Rectangle(0, 0, getWidth(), (int)(height() * 75));
        handleCollisions(finalWall);
    }
    
    public void collision33() { //Collision for the boss room (that the player can't escape from until they die)
        Rectangle[] bossCollisions = new Rectangle[4];
        bossCollisions[0] = new Rectangle(0, 0, getWidth(), (int)(height() * 10));
        bossCollisions[1] = new Rectangle(0, 0, (int)(width() * 10), getHeight());
        bossCollisions[2] = new Rectangle(0, (int)(height() * 860), getWidth(), (int)(height() * 10));
        bossCollisions[3] = new Rectangle((int)(width() * 1520), 0, (int)(width() * 20), getHeight());
        handleCollisionArray(bossCollisions, 4);  
    }
    
    public void dungeonCollision() { //Collision for the walls in dungeon areas, which are mostly the same and so this method could be reused to cut down on some lines of code
        Rectangle[] baseCollisions = new Rectangle[4];
        baseCollisions[0] = new Rectangle(0, 0, (int)(width() * 60), getHeight());
        baseCollisions[1] = new Rectangle((int)(width() * 1500), 0, (int)(width() * 40), getHeight());
        baseCollisions[2] = new Rectangle(0, 0, (int)(width() * 670), (int)(height() * 30));
        baseCollisions[3] = new Rectangle((int)(width() * 860), 0, getWidth(), (int)(height() * 30));
        handleCollisionArray(baseCollisions, 4);
    }

    public void handleCollisions(Rectangle collisions) { //A method responsible for handling the different collision scenarios, as simply setting the player's velocity to 0 didn't seem to be working (would trap the player in the collision rectangle)
        if (player.x + player.width >= collisions.x && player.x <= collisions.x + collisions.width) { //If the RIGHT SIDE of the player's collision rectangle is to the RIGHT of the top-left of the collision rectangle, and if the LEFT SIDE of the player's collision rectangle is to the LEFT of the top-left of the collision rectangle, then it's a vertical collision
            if (player.y + player.height >= collisions.y && player.y + player.height <= collisions.y + collisions.height) { //If the bottom of the player's collision rectangle is beneath the top of the collision rectangle, and if the TOP of the player's collision rectangle is above the TOP of the collision rectangle, it's a collision from the top
                downVel = 0; //One velocity here (downVel) is set to 0 as a result of the direction of the collision. The player can move normally in all other directions, however.
            } else if (player.y <= collisions.y + collisions.height && player.y >= collisions.y) { //If the top of the player's collision rectangle is above the bottom of the collision rectangle, and if the bottom of the player's collision rectangle is below the bottom of the collision rectangle, it's a collision from the bottom
                upVel = 0; 
            }
        }
        if (player.y + player.height >= collisions.y && player.y <= collisions.y + collisions.height) { //Alternatively, if the bottom side of the player's rectangle is below the top side of the collision rectangle, and if the top corner of the player's rectangle is above the bottom side of the collision rectangle, it's a horizontal collision
            if (player.x + player.width >= collisions.x && player.x + player.width <= collisions.x + collisions.width) { //If the right side of the player's rectangle is to the right of the RIGHT side of the collision rectangle and if the right side of the player's rectangle is to the left of the right side of the collision rectangle, the player's colliding from the left. 
                rightVel = 0;
            } else if (player.x <= collisions.x + collisions.width && player.x >= collisions.x) { //Else, if the left side of the player's rectangle is to the left of the right side of the collision rectangle, and if the right side of the player's rectangle is to the right of the collision rectangle, it's a collision from the right side 
                leftVel = 0;
            }
        }
    }

    public void effects() { //Determines the effects of items when they're used in the inventory (when the cursor is positioned over them and "U" is pressed)
        if (inventory.isItemFound[currentRow - 1][currentColumn - 1]) { //If the item is found (is in the inventory), the row is then determined
            if (currentRow == 1) { //If the row is 1, depending on the column that the cursor is on, different methods are called (either the armour or potion methods)
                if (currentColumn >= 1 && currentColumn <= 3) {
                    changeArmour();
                } else if (currentColumn >= 4 && currentColumn <= 8) {
                    potionEffects();
                }
            } if (currentRow == 2) { //This row, depending on the column, can call the portalEffects and gold methods
                if (currentColumn >= 4 && currentColumn <= 6) {
                    portalEffects();
                } if (currentColumn >= 7 && currentColumn <= 8) {
                    obtainGold();
                }
            } if (currentRow == 3) { //This row (since currentRow and currentColumn are always one greater than their respective index) calls spellEnhancements or boosts 
                if (currentColumn >= 1 && currentColumn <= 5) {
                    spellEnhancements();
                } else if (currentColumn >= 6 && currentColumn <= 8) {
                    boosts();
                }
            }
        }
    }

    public void changeArmour() { //Responsible for changing the character's armour
        switch (currentColumn - 1) {
            case 0 -> character1 = BaseChar; //character1 is changed to the base character
            case 1 -> character1 = CharArmour; //character1 (the ImageIcon) is changed to the front-facing version of the medium-armoured character
            case 2 -> character1 = CharFull; //character1 is changed to the front-facing version of the heavily-armoured paladin
        }
        player1 = character1.getImage(); //player1 (the actual image) is obtained, and the character model is changed based on what column is selected 
        repaint();
    }

    public void potionEffects() { //Responsible for determining the permanent effects of potions on the character
        switch (currentColumn - 1) {
            case 3 -> health = level * 10; //Fills the character's health back to full
            case 4 -> extraDamage += 2; //Increases the character's damage for all attacks by 2
            case 5 -> extraSpeed++; //Increases the character's speed by 1 
            case 6 -> EXP += 10; //Increases the character's EXP by 10
            case 7 -> necromancyRevival = false; //Sets the necromancyRevival boolean value to false, making the final boss fight MUCH easier
        }
        updateInventory(); //See below for this method (essentially resets the item to a state of not being found)
    }
    
    public void portalEffects() { //Responsible for determining the effects of portals and what happens to the player character when they use them
        switch(currentColumn - 1) {
            case 3 -> { //If the forest portal is used, the scene is reset to 12 (the first portal area), x- and y-coordinate values are changed, and the music changes
                scene = 12;
                y = (int)(height() * 100);
                x = (int)(width() * 700);
                changeClip("The Mistwood.wav");
            } 
            case 4 -> { //The same logic as above holds true for the dungeon portal which takes players directly to the first area of the dungeon
                scene = 29;
                y = (int)(height() * 780);
                x = (int)(width() * 650);
                changeClip("Unease.wav");
            }
            case 5 -> { //The final portal, the escape portal, teleports players out of the dungeon, to the last area of the grasslands
                scene = 28;
                y = (int)(height() * 500);
                changeClip("The Sum of All Fears.wav");
            }
        }
        updateInventory();
    }
    
    public void obtainGold() { //A method responsible for increasing the player's total gold amount when they use the gold vault (bars) or the gold pouch
        switch (currentColumn - 1) {
            case 6 -> gold += 1000; //The pouch gives a slightly lower amount than the gold bars
            case 7 -> gold += 3000;
        }
        updateInventory();
    }
    
    public void spellEnhancements() { //A method that increases the damage of specific attacks that the player has, when they use the corresponding boosts
        switch (currentColumn - 1) {
            case 0 -> fireballDamage += 2; //These modifiers are added on to the respective attack later in the code 
            case 1 -> lightningDamage += 3;
            case 2 -> explosionDamage += 3;
            case 3 -> poisonDamage += 3;
            case 4 -> noCollisions = true; //This last item, phaseShift, disables collisions for one area, and then enables them again (see above)
        }
        updateInventory();
    }
    
    public void boosts() { //This method handles the effects of the boosts players can use.
        switch(currentColumn - 1) { //The logic is the same as above for the switch-case statements
            case 5 -> {
                if (damageModifier != 2) { //The damage modifier is what every damage total for each attack is multiplied by, effectively doubling the damage if this double-damage boost is activated, or doing nothing if it isn't
                    damageModifier = 2;
                }
            } case 6 -> { //This boost increases the character's level to 5, and is only able to be obtained if the character is level 4 and completes the dwarf's quest in the Grasslands
                if (level != 5) { 
                    level = 5;
                    EXP = 0;
                    health = level * 10;
                }
            } case 7 -> scene = 999; //The "Insta-Kill" boost instantly kills the player, contrary to what they might expect
        }
        updateInventory();
    }
    
    public void updateInventory() { //When the player uses/sells an item, the item is marked as unfound, which makes it disappear from the inventory. The inventory is then repainted to adjust for this. 
        inventory.isItemFound[currentRow - 1][currentColumn - 1] = false;
        inventory.repaint();
    }

    public void updatePlayerPosition() { //A method that modifies the player's position and changes the dimensions of their collision rectangle based on their x- and y-coordinates
        x += (-leftVel + rightVel); //The character's x velocity is equal to their negative left velocity (as left is -x), plus their right velocity
        y += (-upVel + downVel); //The character's y velocity is equal to their negative up velocity (as up is -y) plus their down velocity
        player = new Rectangle((int)(x + (width() * 40)), (int)(y + (height() * 105)), (int)(width() * 48), (int)(height() * 28));
    }
    
    public void updateCharacterUp() { //A method that updates the character's image if their up velocity is greater than zero (if they're moving up)
        if (upVel > 0) {
            if (character1 == BaseChar || character1 == BaseCharLeft || character1 == BaseCharRight) {
                character1 = BaseCharBack; //If the character1 image is equal to any BaseChar variation, it changes to BaseCharBack 
            } if (character1 == CharArmour || character1 == CharArmourLeft || character1 == CharArmourRight) {
                character1 = CharArmourBack; //Similarly, if the character1 image is equal to any CharArmour (medium armour) variation, it changes to CharArmourBack
            } if (character1 == CharFull || character1 == CharFullLeft || character1 == CharFullRight) {
                character1 = CharFullBack; //The same logic is used here
            }
        }
    }

    public void updateCharacterDown() { //This method does much of the same actions that the updateCharacterUp does
        if (downVel > 0) { //If downVel is greater than zero (the character is moving down), the character model is changed to the base version of the character with whatever armour they're wearing
            if (character1 == BaseCharBack || character1 == BaseCharLeft || character1 == BaseCharRight) {
                character1 = BaseChar;
            } if (character1 == CharArmourBack || character1 == CharArmourLeft || character1 == CharArmourRight) {
                character1 = CharArmour;
            } if (character1 == CharFullBack || character1 == CharFullLeft || character1 == CharFullRight) {
                character1 = CharFull;
            }
        }
    }

    public void updateCharacterLeft() { //Handles the character's image if the character moves left
        if (leftVel > 0) { //Character model is changed to the Left version of whatever armour the character is wearing
            if (character1 == BaseChar || character1 == BaseCharBack || character1 == BaseCharRight) {
                character1 = BaseCharLeft;
            } if (character1 == CharArmour || character1 == CharArmourBack || character1 == CharArmourRight) {
                character1 = CharArmourLeft;
            } if (character1 == CharFull || character1 == CharFullBack || character1 == CharFullRight) {
                character1 = CharFullLeft;
            }
        }
    }

    public void updateCharacterRight() { //Handles the character's image if the character moves right
        if (rightVel > 0) { //Character model changes to the Right version of the armour the character is wearing
            if (character1 == BaseChar || character1 == BaseCharBack || character1 == BaseCharLeft) {
                character1 = BaseCharRight;
            } if (character1 == CharArmour || character1 == CharArmourBack || character1 == CharArmourLeft) {
                character1 = CharArmourRight;
            } if (character1 == CharFull || character1 == CharFullBack || character1 == CharFullLeft) {
                character1 = CharFullRight;
            }
        }
    }

    public void determineVelocity() { //This method determines what defense level the character has and how fast the character moves, including any speed bonuses they might have. 
        if (character1 == BaseChar || character1 == BaseCharRight || character1 == BaseCharLeft || character1 == BaseCharBack) {
            chosenVel = 5 + extraSpeed; //Character moves significantly faster but has no defense, as they have no armour
            defense = 0;
        } else if (character1 == CharArmour || character1 == CharArmourRight || character1 == CharArmourLeft || character1 == CharArmourBack) {
            chosenVel = 3 + extraSpeed; //Character moves slightly slower but has some amount of defense as they have medium armour on
            defense = 2;
        } else if (character1 == CharFull || character1 == CharFullRight || character1 == CharFullLeft || character1 == CharFullBack) {
            chosenVel = 1 + extraSpeed; //Character moves extremely slow, but has the most defense
            defense = 3;
        }
    }

    public void drawAttack(Graphics g) { //Method responsible for drawing the player's attack image to the screen
        if (character1 == BaseChar || character1 == CharArmour || character1 == CharFull) { //The dimensions of each attackCollision rectangle and image change based on what character model is being used, and what direction the player is facing. This is done to make the combat slightly harder to master, and to introduce an element of skill to the game; the player has to align their x- and y-coordinates such as that the attack collides with the enemy's hitbox
            g.drawImage(attack, (int)(x + (width() * 32)), y + (int) (height() * 125), (int)(width() * 60), (int)(height() * 60), this);
            attackCollision1 = new Rectangle((int)(x + (width() * 32)), y + (int)(height() * 125), (int)(width() * 60), (int)(height() * 60));
        } else if (character1 == BaseCharLeft || character1 == CharArmourLeft || character1 == CharFullLeft) {
            g.drawImage(attack, x - (int)(width() * 100), (int)(y + (height() * 32)), (int)(width() * 60), (int)(height() * 60), this);
            attackCollision2 = new Rectangle(x - (int)(width() * 100), (int) (y + (height() * 32)), (int)(width() * 60), (int)(height() * 60));
        } else if (character1 == BaseCharRight || character1 == CharArmourRight || character1 == CharFullRight) {
            g.drawImage(attack, x + (int) (width() * 100), (int) (y + height() * 32), (int) (width() * 60), (int) (height() * 60), this);
            attackCollision3 = new Rectangle(x + (int)(width() * 100), (int)(y + height() * 32), (int)(width() * 60), (int)(height() * 60));
        } else if (character1 == BaseCharBack || character1 == CharArmourBack || character1 == CharFullBack) {
            g.drawImage(attack, (int)(x + (width() * 32)), y - (int)(height() * 75), (int)(width() * 60), (int)(height() * 60), this);
            attackCollision4 = new Rectangle(x + (int)(width() * 100), (int)(y + height() * 32), (int)(width() * 60), (int)(height() * 60));
        }
        isAttacking = true; //The boolean isAttacking is set to true, for damage calculation purposes in the explodingEnemy file
    }

    public void attackDamage() { //A method that calculates the base damage that the player deals, and the damage type that each attack is (as certain enemies have resistances and vulnerabilities)
        if (attack == attack1.getImage()) { //Attacks 1 through 4 deal small amounts of damage as they are the base level 1 attacks, while 2 through 8 deal a lot more as they are only obtained for certain armour types, and after levelling up
            damageType = "poison";
            damage = (2 + extraDamage + poisonDamage) * damageModifier; //The damageModifier is determined by the doubleDamage boost, and the extraDamage/poisonDamage, etc., is determined through whether the player consumed damagePotions or the respective poison spell to increase their damage output or not
        } if (attack == attack2.getImage()) {
            damageType = "radiant";
            damage = (2 + extraDamage) * damageModifier;
        } if (attack == attack3.getImage()) {
            damageType = "fire";
            damage = (3 + extraDamage + fireballDamage) * damageModifier;
        } if (attack == attack4.getImage()) {
            damageType = "piercing";
            damage = (4 + extraDamage) * damageModifier;
        } if (attack == attack5.getImage()) {
            damageType = "bludgeoning";
            damage = (10 + extraDamage) * damageModifier;
        } if (attack == attack6.getImage()) {
            damageType = "slashing";
            damage = (13 + extraDamage) * damageModifier;
        } if (attack == attack7.getImage()) {
            damageType = "lightning";
            damage = (10 + extraDamage + lightningDamage) * damageModifier;
        } if (attack == attack8.getImage()) {
            damageType = "force";
            damage = (15 + extraDamage + explosionDamage) * damageModifier;
        }
    }
    
    public void initializeExplodingEnemy(Graphics g, int index) { //A method that initializes the two-dimensional explodingEnemy array, using a for loop to iterate through the length of the array, and setting each enemy's initial position based on a random number within bounds of the width and height of a given area
        Random rand = new Random();
        for (int i = 0; i < explodingEnemies[index].length; i++) {
            if (explodingEnemies[index][i] != null && explodingEnemies[index][i].isEnemyAlive[index][i]) { //If the enemies are null, they aren't initialized again, so that their position isn't reset
                explodingEnemies[index][i].setInitialPosition(g, index, i, (int)(width() * rand.nextInt(1300) + 100), (int)(height() * rand.nextInt(700) + 50));
            }
        }
    }
    
    public void checkHealth() { //futureDeathCount is increased by 1 so that the 2nd obelisk in the grasslands can track player death count (without the deathCount increasing by more than 1 per death due to the timer between scenes being slower than the deathCount increments)
        if (health <= 0) {
            if (futureDeathCount == deathCount) {
                futureDeathCount++;
            }
            scene = 999; //The scene is set to the deathScreen
        }
    }
    
    public void deathScreen(Graphics g) { //Method responsible for handling player death
        clip.stop(); //All music is stopped, the base image is drawn, and text is displayed in the bottom-left of the screen prompting the player to press enter if they want to continue
        Image deathScreen = death.getImage();
        g.drawImage(deathScreen, 0, 0, getWidth(), getHeight(), this); 
        g.setFont(new Font("Garamond", Font.PLAIN, 50));
        g.setColor(Color.WHITE);
        if (scene == 999) {
            g.drawString("Press Enter to Begin Again.", 0, (int)(getHeight() - height() * 100));
        }
        if (deathCount != futureDeathCount) { //deathCount is set back to equal futureDeathCount
            deathCount = futureDeathCount;
        } if (gold >= 5000) { //Gold is lost upon death as a punishment  
            gold = 5000;
        } else if (gold >= 1000 && gold < 5000) {
            gold = 1000;
        } else {
            gold = 0;
        }
        health = level * 10; //Health is reset to full
    }

    public double height() { //This ratio is a double value that returns the height of the screen divided by the height of the computer I used to test a vast majority of the code; this is because I found that setting values any other way would make them change from computer to computer, so this was a workaround
        return getHeight()/864.00000000; 
    }

    public double width() { //This ratio is also a double value, that returns the width of the screen divided by the width of the computer I used to test most of the code 
        return getWidth()/1536.00000000;
    }

    public void openGuide() { //The method that controls what the player sees if they open the Guide screen at different times in their adventure. The guide is somewhat helpful, but also somewhat misleading as it's told from the perspective of a biased narrator, and gives additional lore details 
        switch (scene) { 
            case 5, 6 -> note = guide.getImage(); //If the player is in the Snow-Capped Mountains, they see the first version of the guide
            case 7, 8, 9, 10, 11 ->  note = guide2.getImage(); //Else, if the player is in the Town of Winterveil, they see the second version of the guide
            case 12, 13, 14, 15, 16, 17 -> note = guide3.getImage(); //Otherwise, the player sees the 3rd version of the guide if they're in the forest
            case 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 -> note = guide4.getImage(); //This guide is for the Grasslands
            case 29, 30, 31, 32, 33, 34, 35, 36 -> note = guide5.getImage(); //And this guide is for the Dungeon
        }
        guideOpened = true; //This boolean is triggered to track whether the guide is open or not
    }
    
    public void closeGuide() { //Method responsible for closing the guide, which sets the ImageIcon note (responsible for displaying the guide image) to null, sets the respective boolean value to false, and then repaints to adjust for that
        note = null;
        guideOpened = false;
        repaint();
    }
    
    public void controlsScreen() { //A method that works much like the open and closeGuide methods do, only this time it's with the controlsScreen
        controlScreen = controls.getImage();
        controlsOpened = true;
    }
    
    public void closeControls() { //Sets the controlScreen image to null, much like the closeGuide method, and then repaints.
        controlScreen = null;
        controlsOpened = false;
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) { //One of the necessary methods for the ISU3 file, responsible for keeping track of the alpha value during the transition scenes and also adjusting the vertical position of the text in the intro cutscene
        if (scene == 2 || scene == 4) {
            alpha += 1; //Alpha increments by 1, meaning the image becomes increasingly more opaque
            if (alpha >= 255) {
                transitionTimer.stop();
                scene++; //Increments the scene to either 3 or 5, and starts the timer (the timer with a delay of 1 ms) as opposed to the transitionTimer 
                timer.start();
            }
        } else if (scene == 3) { //If the scene is the intro cutscene, the text's downward velocity is set to 5, the text's y position is dropped by 5, and if the text's y position is less than 0, the text's y position is reverted to width() * 450
            if (textY < 0) {
                textY = (int)(width() * 450);
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { //An unused method, necessary to include if I wanted keyPressed
        e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) { //A method handling every instance where a key is pressed in the game
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_ENTER && (scene == 1 || scene == 3 || scene == 38 || scene == 999)) { //VK_ENTER generally skips cutscenes, whereafter the music changes and occasionally the transition between scenes occurs
            if (scene == 1 || scene == 3) {
                clip.stop();
                if (scene == 1) {
                    changeClip("Start.wav");
                } else if (scene == 3) {
                    changeClip("Mountaintops.wav");
                }
                scene++;
                transitionTimer.start();
            } if (scene == 999) { //If the scene is equal to the death scene when enter is pressed, the player's position is reset to the scene with the last (most-recently discovered) checkpoint in it. The player's y- and x-coordinate values are also changed here as opposed to in the other methods 
                if (!townDiscovered && !refugeFound) {
                    x = (int)(width() * 350);
                    y = (int)(height() * 650);
                    scene = 3;
                } else if (townDiscovered && !refugeFound) {
                    x = (int)(getWidth() * 0.03);
                    y = (int)(getHeight() * 0.33);
                    scene = 7;
                } else if (refugeFound) {
                    x = (int)(width() * 600);
                    y = (int)(height() * 450);
                    scene = 19;
                } else if (dungeonDelved) {
                    x = (int)(width() * 250);
                    y = (int)(height() * 400);
                    scene = 35;
                }
            } if (scene == 38) { //If the scene is 38 (the one where the necromancer "crashes" the game), the scene reverts back to just before the boss room
                timer.setDelay(3);
                y = (int)(height() * 600);
                scene = 36;
            }
        } if (scene > 4) { //This if statement is here so that the player can't alter their initial position and do other things they shouldn't, before they're drawn to the screen
            try {
                determineVelocity(); //The player's velocity is determined, and the player is then moved in the given direction when they press the arrow keys. Their other velocities are also set to 0. 
                if (c == KeyEvent.VK_UP) { 
                    upVel = chosenVel;
                    downVel = 0;
                    leftVel = 0;
                    rightVel = 0;
                    updateCharacterUp();
                } if (c == KeyEvent.VK_DOWN) {
                    downVel = chosenVel;
                    upVel = 0;
                    leftVel = 0;
                    rightVel = 0;
                    updateCharacterDown();
                } if (c == KeyEvent.VK_LEFT) {
                    leftVel = chosenVel;
                    rightVel = 0;
                    downVel = 0;
                    upVel = 0;
                    updateCharacterLeft();
                } if (c == KeyEvent.VK_RIGHT) {
                    rightVel = chosenVel;
                    leftVel = 0;
                    downVel = 0;
                    upVel = 0;
                    updateCharacterRight();
                } if (c == KeyEvent.VK_1) { //KeyEvent.VK_1 and 2, etc., are all attack keys, that make the image "attack" equal to attack_.getImage(). Attacks 5, 6, 7, and 8 all have conditions on them for when they can be used, depending on the player's current armour type and the player's level
                    attack = attack1.getImage();
                } else if (c == KeyEvent.VK_2) {
                    attack = attack2.getImage();
                } else if (c == KeyEvent.VK_3) {
                    attack = attack3.getImage();
                } else if (c == KeyEvent.VK_4) {
                    attack = attack4.getImage();
                } else if (c == KeyEvent.VK_5 && level >= 2) {
                    attack = attack5.getImage();
                } else if (c == KeyEvent.VK_6 && level >= 3 && (character1 == CharFull || character1 == CharFullBack || character1 == CharFullRight || character1 == CharFullLeft)) {
                    attack = attack6.getImage();
                } else if (c == KeyEvent.VK_7 && level >= 4 && (character1 == BaseChar || character1 == BaseCharBack || character1 == BaseCharLeft || character1 == BaseCharRight)) {
                    attack = attack7.getImage();
                } else if (c == KeyEvent.VK_8 && level == 5 && (character1 == CharArmour || character1 == CharArmourBack || character1 == CharArmourLeft || character1 == CharArmourRight)) {
                    attack = attack8.getImage();
                } else if (c == KeyEvent.VK_B) {
                    Timer defenseTimer = new Timer(3000, this);
                    if (character1 == CharFull || character1 == CharFullBack || character1 == CharFullRight || character1 == CharFullLeft) { //In heavy armour, the character can also increase their defense by pressing B, and this ability lasts for 3 seconds
                        defense++;
                        defenseTimer.start();
                        defense--;
                        defenseTimer.stop();
                    }
                } if (c == KeyEvent.VK_H) { //At the bonfires, characters can heal if the two corresponding rectangles (player and bonfire) intersect, and if they press H
                    if ((player.intersects(bonfire1)) || (player.intersects(bonfire2)) || (player.intersects(bonfire3))) {
                        health = level * 10; //Health is restored, and there's a small EXP penalty so as to prevent players from going out into combat zones and then backtracking to heal, ad infinitum
                        if (EXP > 0) {
                            EXP -= 1;
                        }
                    } if (player.intersects(cursedBonfire)) { 
                        EXP = 0;
                        health = level;
                    }
                } if (c == KeyEvent.VK_O) { //Used for obelisk interactions, calling the obelisk method above.
                    if (player.intersects(obelisk) || player.intersects(obelisk2)) {
                        obelisk();
                    }
                } if (c == KeyEvent.VK_X) { //VK_X is used to purchase items from the merchants, whereupon the JDialog boxes are shown and players can select items, etc.
                    if (player.intersects(magicMerchant)) {
                        magicShop();
                    } if (player.intersects(boostMerchant)) {
                        boostShop();
                    } if (player.intersects(potionMerchant)) {
                        potionShop();
                    }
                } if (c == KeyEvent.VK_S && inventoryOpen) { //The player has to have their inventory open to sell items
                    sellItems();
                } if (c == KeyEvent.VK_Q) { //If the player intersects the right NPCs, the character will recieve quest offerings or dialogue on how they're doing with the quest, which is tracked using multiple boolean values and is based on the current scene the player is in.
                    if (player.intersects(guard1) || player.intersects(axeDwarf) || player.intersects(wizard)) { 
                        if ((!questCompleted && !questAccepted && player.intersects(guard1)) && scene == 7 || (!quest2Completed && !quest2Accepted && player.intersects(axeDwarf)) && scene == 19 || (!quest3Completed && !quest3Accepted && player.intersects(wizard)) && scene == 19) {
                            quest();
                        } else if ((killCount >= 10 && questAccepted && !rewardGiven) && player.intersects(guard1) && scene == 7) {
                            questCompleted = true;
                        } else if (level == 4 && quest2Accepted == true && !reward2Given && player.intersects(axeDwarf) && scene == 19) {
                            quest2Completed = true;
                        } else if (doubleDamageExchanged && quest3Accepted == true && !reward3Given && player.intersects(wizard) && scene == 19) {
                            quest3Completed = true;
                        } if ((questCompleted && !rewardGiven && scene == 7) || (quest2Completed && !reward2Given && scene == 19) || (quest3Completed && !reward3Given && scene == 19)) {
                            questCompleted();
                        } else if ((questAccepted && !questCompleted && scene == 7) || (quest2Accepted && !quest2Completed && scene == 19) || (quest3Accepted && !quest3Completed && scene == 19)) {
                            questNotComplete();
                        } else if (rewardGiven && scene == 5 || reward2Given && scene == 19 || reward3Given && scene == 19) {
                            rewardAlreadyGiven();
                        }
                    }
                } if (c == KeyEvent.VK_T) { //If the player chooses to talk to an NPC, it calls their respective dialogue methods.
                    Random rand = new Random();
                    int randomInt = rand.nextInt(9) + 1;
                    String dialogue = "null";
                    if (scene == 7 && player.intersects(guard1) || player.intersects(guard2)) {
                        guardDialogue(dialogue, randomInt);
                    } else if (scene == 8) {
                        if (player.intersects(boostMerchant)) {
                            boostMerchantDialogue(dialogue, randomInt);
                        } else if (player.intersects(potionMerchant)) {
                            potionMerchantDialogue(dialogue, randomInt);
                        } else if (player.intersects(magicMerchant)) {
                            magicMerchantDialogue(dialogue, randomInt);
                        }
                    } else if (scene == 19) {
                        if (player.intersects(axeDwarf)) {
                            dwarfDialogue(dialogue, randomInt);
                        } else if (player.intersects(cleric)) {
                            clericDialogue(dialogue, randomInt);
                        } else if (player.intersects(wizard)) {
                            wizardDialogue(dialogue, randomInt);
                        }
                    }
                } if (c == KeyEvent.VK_K) { 
                    controlsScreen();
                } if (c == KeyEvent.VK_G) {
                    openGuide();
                } if (c == KeyEvent.VK_C) { //Closes all open screens, including the inventory, controls screen, and guide
                    if (inventoryOpen) {
                        inventory.requestFocus();
                        inventory.closeWindow();
                        inventoryOpen = false;
                    } if (guide != null) {
                        closeGuide();
                    } if (controls != null) {
                        closeControls();
                    }
                } if (c == KeyEvent.VK_I) { //Opens the inventory
                    inventory.requestFocus();
                    if (inventoryOpen == false) {
                        inventory.openWindow();
                        inventoryOpen = true;
                    }
                } if (c == KeyEvent.VK_U) { //Logic for using items
                    if (inventoryOpen || scene == 17 && !player.intersects(grasslandGate)) { //If the scene is anything normal and the player doesn't intersect a gate, the item is consumed and itemEffects is called
                        effects();
                    } if (currentColumn == 1 && currentRow == 2 && player.intersects(grasslandGate)) { //If the player intersects a gate (in this case, grasslandGate, and uses the correct key, the gate opens and the player has access to the next scene
                        keyInserted = true;
                        inventory.isItemFound[1][0] = false;
                    } if (currentColumn == 2 && currentRow == 2 && player.intersects(dungeonDoor)) {
                        silverKeyInserted = true;
                        inventory.isItemFound[1][1] = false;
                    } if (currentColumn == 3 && currentRow == 2 && player.intersects(bossDoor)) {
                        goldKeyInserted = true;
                        inventory.isItemFound[1][2] = false;
                        if (player.intersects(bossDoor) && battleUndecided == true && !requestShown) { //If the player intersects the boss door and hasn't been given the option to pick an ending yet, the JDialog box is shown after the gold key is inserted
                            showRequest();
                            requestShown = true;
                        }
                    }
                } if (c == KeyEvent.VK_E && currentColumn == 3 && currentRow == 5 && player.intersects(wizard) && scene == 19) { //E is only used once, to exchange the double-damage boost with the wizard and complete his quest (only doable when the player rectangle and wizard are intersecting)
                    doubleDamageExchanged = true;
                    updateInventory();       
                }
            } catch (Exception h) {
                System.out.println();
            }
        }
        switch (scene) { //Collision is determined here, and I couldn't figure out how to do a for loop for it in time, so I just used a switch-case statement instead.  
            case 5 -> collision1(); //The scene is always 4 less than the corresponding collision method.
            case 6 -> collision2();
            case 7 -> collision3();
            case 8 -> collision4();
            case 9 -> collision5();
            case 10 -> collision6();
            case 11 -> collision7();
            case 12 -> collision8();
            case 13 -> collision9();
            case 14 -> collision10();
            case 15 -> collision11();
            case 16 -> collision12();
            case 17 -> collision13();
            case 18 -> collision14();
            case 19 -> collision15();
            case 20 -> collision16();
            case 21 -> collision17();
            case 22 -> collision18();
            case 23 -> collision19();
            case 24 -> collision20();
            case 25 -> collision21();
            case 26 -> collision22();
            case 27 -> collision23();
            case 28 -> collision24();
            case 29 -> collision25();
            case 30 -> collision26();
            case 31 -> collision27();
            case 32 -> collision28();
            case 33 -> collision29();
            case 34 -> collision30();
            case 35 -> collision31();
            case 36 -> collision32();
            case 37 -> collision33();
        }
        if (scene == 29 || scene == 30 || scene == 31 || scene == 32 || scene == 33 || scene == 36) { //dungeonCollision is called here as it's fairly predictable for most of the dungeon areas (they have similar layouts)
            dungeonCollision();
        }
        updatePlayerPosition(); //The player's position is updated in this method as well.
    }

    @Override
    public void keyReleased(KeyEvent e) { //Used for two purposes, where one tracks if the player is currently attacking (significant for the ExplodingEnemy file) while the other is to set velocities in a certain direction to 0 once the key is released (more realistic movement).
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_UP) {
            upVel = 0;
        } if (c == KeyEvent.VK_DOWN) {
            downVel = 0;
        } if (c == KeyEvent.VK_LEFT) {
            leftVel = 0;
        } if (c == KeyEvent.VK_RIGHT) {
            rightVel = 0;
        } if (c == KeyEvent.VK_1 || c == KeyEvent.VK_2 || c == KeyEvent.VK_3 || c == KeyEvent.VK_4 || c == KeyEvent.VK_5 || c == KeyEvent.VK_6 || c == KeyEvent.VK_7 || c == KeyEvent.VK_8) {
            isAttacking = false;
        }
    }
}