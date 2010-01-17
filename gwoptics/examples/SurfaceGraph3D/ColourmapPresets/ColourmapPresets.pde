
import org.gwoptics.graphics.graph3D.*;
import org.gwoptics.graphics.*;
import org.gwoptics.graphics.colourmap.presets.*;

import org.gwoptics.graphics.*;
import org.gwoptics.graphics.camera.*;
import org.gwoptics.graphics.colourmap.*;
import org.gwoptics.graphics.graph3D.*;

Camera3D cam;
SurfaceGraph3D g3d[];

// The following is used to print fps rate
PGraphics3D g3; // Used to remember PGraphics3D transformation matrix
float[] rotations = new float[3]; // store camera angles

IGraph3DCallback gcb = new IGraph3DCallback(){
  public float computePoint(float X, float Y) {
    return (float) (Math.sin(X)*Math.sin(Y));
  }
};

void setup() {
  size(800, 800, P3D); //Use P3D for now, openGl seems to have some issues

  cam = new Camera3D(this);
  g3 = (PGraphics3D)g; // for printing fps rate
  PFont myFont;
  
  myFont = createFont("FFScala", 32);
  textFont(myFont);

  g3d = new SurfaceGraph3D[4];

  for(int i = 0; i < 4;i++){
    g3d[i] = new SurfaceGraph3D(this, 400, 400,200);		
    g3d[i].setXAxisMin(-5);		
    g3d[i].setXAxisMax(5);	
    g3d[i].setYAxisMin(-5);		
    g3d[i].setYAxisMax(5);
    g3d[i].setZAxisMin(-1);
    g3d[i].setZAxisMax(1);

    switch(i){
    case 0:
      g3d[i].addSurfaceTrace(gcb, 100, 100, PresetColourmaps.getColourmap(Presets.FLIP));
      break;
    case 1:
      g3d[i].addSurfaceTrace(gcb, 100, 100, PresetColourmaps.getColourmap(Presets.COOL));
      break;
    case 2:
      g3d[i].addSurfaceTrace(gcb, 100, 100, PresetColourmaps.getColourmap(Presets.HOT));
      break;
    case 3:
      g3d[i].addSurfaceTrace(gcb, 100, 100, PresetColourmaps.getColourmap(Presets.WARM));
      break;
    default:
      break;
    }

    g3d[i].plotSurfaceTrace(0);
  }
}

void draw() {
  background(204);

  pushMatrix();
  translate(-250,0,-250);
  g3d[0].draw();
  popMatrix();

  pushMatrix();
  translate(250,0,250);
  g3d[1].draw();
  popMatrix();

  pushMatrix();
  translate(-250,0,250);
  g3d[2].draw();
  popMatrix();

  pushMatrix();
  translate(250,0,-250);
  g3d[3].draw();
  popMatrix();
  printFPS();
}


void printFPS()
{
  PMatrix3D currCameraMatrix = new PMatrix3D(g3.camera);
  camera();
  text("fps = "+(float)round(10000.0*frameCount/millis())/10.0, 70, 30);
  g3.camera = currCameraMatrix;
}

