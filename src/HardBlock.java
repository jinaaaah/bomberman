import processing.core.PApplet;

public class HardBlock extends Block {
    public HardBlock(PApplet pApplet, float pPosX, float pPosY) {
        super(pApplet, pPosX, pPosY);
        parseImage = pImage.get(0,120,40,40);
    }
}
