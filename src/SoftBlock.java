import processing.core.PApplet;

import java.util.Random;

public class SoftBlock extends Block {

    private int type;

    public SoftBlock(PApplet pApplet, float pPosX, float pPosY) {
        super(pApplet, pPosX, pPosY);
        parseImage = pImage.get(0, 0, 40, 40);
        decideItem();
    }

    private void decideItem() {
        Random random = new Random();
        type = random.nextInt(4);
    }

    @Override
    public int itemReveal(int num) {
        if (num == 0) {
            if (type != 0) {
                super.isItem = true;
                parseImage = resourceManager.getItemImage(type);
                draw();
            }
        }
        else if (num == 1) {
            return type;
        }

        return 0;

    }

}
