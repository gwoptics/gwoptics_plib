import org.gwoptics.graphics.*;
import org.gwoptics.graphics.camera.*;
import org.gwoptics.graphics.graph3D.*;
import org.gwoptics.graphics.colourmap.presets.*;

Camera3D cam;
SurfaceGraph3D g3d;

class standingWave implements IGraph3DCallback{
  public float computePoint(float X, float Y) {
    return (float) 1/(1+(X*X+Y*Y));
  }
}

standingWave gcb = new standingWave();

void setup() {
  size(800, 800, P3D); //Use P3D for now, openGl seems to have some issues

  cam = new Camera3D(this);
  PVector cam_pos = new PVector(0f,540f,-10f);
  cam.setPosition(cam_pos);

  // constructor arguments are:
  // (PApplet parent, float xLength, float yLength, float zLength)
  // (z axis will be switched off if length is given as 0)
  g3d = new SurfaceGraph3D(this, 250, 250,0);		
  g3d.setXAxisMin(-2);		
  g3d.setXAxisMax(2);
  g3d.setZAxisMin(-1);
  g3d.setZAxisMax(1);		
  g3d.setYAxisMin(-2);		
  g3d.setYAxisMax(2);	

  //There are several colourmap presets to try such as: HotColourmap, WarmColourmap or
  // GrayScaleColourmap
  g3d.addSurfaceTrace(gcb, 100, 100, new FlipColourmap(true));
}

void draw() {

  g3d.plotSurfaceTrace(0);
  background(204);
  pushMatrix();
  //centre the graph for rotating
  translate(-125,0,-125);
  g3d.draw();
  popMatrix();
}



