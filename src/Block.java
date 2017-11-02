import processing.core.PApplet;

public class Block extends RenderObject {

    protected boolean isItem;

    public Block(PApplet pApplet, float pPosX, float pPosY) {
        super(pApplet, pPosX, pPosY);
        pImage = resourceManager.loadBlockImage();
        isItem = false;
    }

    public int itemReveal(int num) {
        return 0;
    }

}
