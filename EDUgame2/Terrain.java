import greenfoot.*;

public class Terrain extends World
{
    private Tank tank;

    private String[] words = {"GREENFOOT", "TANK", "EDUCATION", "PROGRAM"};
    private int currentWordIndex = 0;
    private String currentWord;
    private int currentLetterIndex = 0;

    private int score = 0;
    private int misses = 0;

    private Actor scoreDisp, missDisp, wordDisp;

    public Terrain()
    {
        super(800, 600, 1);
        setPaintOrder(Actor.class);

        tank = new Tank();
        addObject(tank, 60, 300);

        
        scoreDisp = new Actor(){};
        missDisp = new Actor(){};
        wordDisp = new Actor(){};
        addObject(scoreDisp, 80, 20);
        addObject(missDisp, 80, 50);
        addObject(wordDisp, 400, 20);

        startNextWord();
        updateDisplays();
    }

    public void act()
    {
        
        if (misses >= 5)
        {
            Greenfoot.stop();
        }

        
        if (getObjects(Letter.class).isEmpty())
        {
            spawnNextLetter();
        }
    }

    private void startNextWord()
    {
        if (currentWordIndex >= words.length)
        {
            Greenfoot.stop(); 
            return;
        }
        currentWord = words[currentWordIndex];
        currentLetterIndex = 0;
        spawnNextLetter();
    }

    private void spawnNextLetter()
    {
        if (currentLetterIndex >= currentWord.length()) return;

        char nextChar = currentWord.charAt(currentLetterIndex);
        Letter letter = new Letter(nextChar, true);

       
        int x = 50 + Greenfoot.getRandomNumber(getWidth() - 100);
        int y = 50 + Greenfoot.getRandomNumber(getHeight() - 100);
        addObject(letter, x, y);
    }

    public void letterHit(Letter letter)
    {
        if (letter.isCorrect())
        {
            score++;
            currentLetterIndex++;
            if (currentLetterIndex >= currentWord.length())
            {
                currentWordIndex++;
                startNextWord();
            }
        }
        else
        {
            misses++;
        }
        updateDisplays();
    }
    // Called when a letter disappears without being shot
    public void letterMissed(Letter letter)
    {
        misses++;
        updateDisplays();
        
        currentLetterIndex++;
        if (currentLetterIndex >= currentWord.length())
        {
            currentWordIndex++;
            startNextWord(); 
        }
        else
        {
            spawnNextLetter();
        }
    }   


    private void updateDisplays()
    {
        scoreDisp.setImage(new GreenfootImage("Score: " + score, 24, greenfoot.Color.BLACK, new greenfoot.Color(0,0,0,0)));
        missDisp.setImage(new GreenfootImage("Misses: " + misses, 24, greenfoot.Color.BLACK, new greenfoot.Color(0,0,0,0)));

        
        if (currentWordIndex < words.length)
        {
            StringBuilder progress = new StringBuilder();
            for (int i=0; i<currentWord.length(); i++)
            {
                if (i < currentLetterIndex) progress.append(currentWord.charAt(i));
                else progress.append("_");
            }
            wordDisp.setImage(new GreenfootImage("Word: " + progress.toString(), 24, greenfoot.Color.BLACK, new greenfoot.Color(0,0,0,0)));
        }
        else
        {
            wordDisp.setImage(new GreenfootImage("All words completed!", 24, greenfoot.Color.BLACK, new greenfoot.Color(0,0,0,0)));
        }
    }
}
