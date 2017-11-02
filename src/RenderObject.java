import processing.core.PApplet;
import processing.core.PImage;

public abstract class RenderObject {

    protected PApplet pApplet;
    protected PImage pImage;
    protected PImage parseImage;
    protected ResourceManager resourceManager;
    protected float pPosX, pPosY;
    protected int tick = 0;

    public RenderObject(PApplet pApplet, float pPosX, float pPosY) {
        this.pApplet = pApplet;
        this.pPosX = pPosX;
        this.pPosY = pPosY;
        resourceManager = new ResourceManager(this.pApplet);
    }

    public void draw() {
        pApplet.image(parseImage, pPosX, pPosY);
    }

    public float getpPosX() {
        return pPosX;
    }

    public float getpPosY() {
        return pPosY;
    }
}
