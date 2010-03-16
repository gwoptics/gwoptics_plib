import guicomponents.*;
import org.gwoptics.gui.button.*;

gwButton btn;

void setup(){
  size(300,300);
    
  btn = new gwButton(this,"test",50,50,200,200);
  
  PImage imgNorm = loadImage("norm.png");
  PImage imgOver = loadImage("over.png");
  PImage imgPressed = loadImage("pressed.png");
  
  btn.setUseImages(true);
  btn.setImages(imgNorm,imgOver,imgPressed);
}

public void draw(){}
