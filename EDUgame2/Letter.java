import greenfoot.*;

public class Letter extends Actor
{
    private char letter;
    private boolean correct;
    private boolean hit = false;
    private int age = 0; 

    public Letter(char c, boolean isCorrect)
    {
        letter = c;
        correct = isCorrect;

        GreenfootImage img = new GreenfootImage(40,40);
        img.setColor(greenfoot.Color.WHITE);
        img.fill();
        img.setColor(greenfoot.Color.BLACK);
        img.drawString("" + letter, 12, 28);
        setImage(img);
    }

    public void act()
    {
        if (!hit && isTouching(Tank.Shot.class))
        {
            hit = true;
            ((Terrain)getWorld()).letterHit(this);
            removeTouching(Tank.Shot.class);
            getWorld().removeObject(this);
            return;
        }

        
        age++;
        
        if (age >= 300 && !hit)
        {
            hit = true; 
            ((Terrain)getWorld()).letterMissed(this); 
            getWorld().removeObject(this);
        }
    }

    public boolean isCorrect()
    {
        return correct;
    }

    public char getLetter()
    {
        return letter;
    }
}
