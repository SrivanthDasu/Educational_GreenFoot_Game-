import greenfoot.*;

public class Tank extends Actor
{
    private Actor turret;
    private int targetX, targetY;
    private int trackTimer;

    public Tank()
    {
        turret = new Actor(){};
        GreenfootImage turretImg = new GreenfootImage("turret.png");
        turret.setImage(turretImg);
    }

    protected void addedToWorld(World world)
    {
        world.addObject(turret, getX(), getY());
        targetX = world.getWidth() / 2;
        targetY = world.getHeight() / 2;
        turnTowards(targetX, targetY);
        turret.turnTowards(targetX, targetY);
    }

    public void act()
    {
        handleMovement();
        handleTurretAiming();
        handleShooting();
        leaveTrack();
    }

    private void handleMovement()
    {
        int dr = 0;
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) dr--;
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) dr++;
        setRotation(getRotation() + dr * 5);
        turret.setRotation(getRotation());

        int d = 0;
        if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) d++;
        if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) d--;
        move(d * 4);
        turret.setLocation(getX(), getY());
    }

    private void handleTurretAiming()
    {
        if (Greenfoot.mouseMoved(null) || Greenfoot.mouseDragged(null))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            targetX = mouse.getX();
            targetY = mouse.getY();
        }
        int angleToTarget = (int)Math.toDegrees(Math.atan2(targetY - getY(), targetX - getX()));
        int rotationDiff = (angleToTarget - turret.getRotation() + 360) % 360;
        if (rotationDiff > 180) rotationDiff -= 360;
        turret.setRotation(turret.getRotation() + (rotationDiff > 0 ? 3 : -3));
    }

    private void handleShooting()
    {
        if (Greenfoot.mouseClicked(null))
        {
            Shot shot = new Shot();
            getWorld().addObject(shot, getX(), getY());
        }
    }

    private void leaveTrack()
    {
        trackTimer = (trackTimer + 1) % 4;
        if (trackTimer == 0)
        {
            getWorld().addObject(new Track(), getX(), getY());
        }
    }

    
    public class Shot extends Actor
    {
        private int speed = 10;
        public Shot()
        {
            GreenfootImage img = new GreenfootImage(5,5);
            img.setColor(greenfoot.Color.BLACK);
            img.fillOval(0,0,5,5);
            setImage(img);
            setRotation(turret.getRotation());
        }
        public void act()
        {
            move(speed);
            if (getX() <= 0 || getX() >= getWorld().getWidth() - 1 ||
                getY() <= 0 || getY() >= getWorld().getHeight() - 1)
                getWorld().removeObject(this);
        }
    }

    
    public class Track extends Actor
    {
        private int age = 0;
        public Track()
        {
            setRotation(getRotation());
            GreenfootImage img = new GreenfootImage(10,10);
            img.setColor(greenfoot.Color.DARK_GRAY);
            img.fillRect(2,0,2,10);
            img.fillRect(6,0,2,10);
            setImage(img);
        }
        public void act()
        {
            age++;
            getImage().setTransparency(Math.max(255 - age * 2,0));
            if (age >= 127) getWorld().removeObject(this);
        }
    }
}
