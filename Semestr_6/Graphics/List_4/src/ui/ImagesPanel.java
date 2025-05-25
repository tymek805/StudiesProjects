package ui;

import controllers.Controller;
import elements.ImageElement;

public class ImagesPanel extends DraggablePosterElementsPanel<ImageElement> {

    public ImagesPanel(Controller controller) {
        super(controller);
    }

    public void addImage(String path) {
        ImageElement imageElement = new ImageElement(path);
        addElement(imageElement); 
    }
}
