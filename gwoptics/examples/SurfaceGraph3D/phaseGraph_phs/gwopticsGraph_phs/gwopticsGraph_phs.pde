import org.gwoptics.graphics.*;
import org.gwoptics.graphics.camera.*;
import org.gwoptics.gaussbeams.*;
import org.gwoptics.graphics.graph3D.*;
import org.gwoptics.graphics.colourmap.presets.*;
import processing.core.PApplet;
import processing.core.PConstants;

float pmax=-1.0e10;
float pmin=1.0e10;

Camera3D cam;
SurfaceGraph3D g3d;

class GuassEq implements IGraph3DCallback{
  GaussMode gauss = new GaussMode(1064e-9, 1.0e-3, 10.0, 1.0);
  int n = 0;
  int m = 0;
  
  public float computePoint(float X, float Y) {
    float temp=(float)gauss.u_nm_phs(n,m,X,Y);
    if (temp>pmax){
      pmax=temp;
      println("max "+pmax+ " min "+pmin);
    }
    if (temp<pmin){
      pmin=temp;
      println("max "+pmax+ " min "+pmin);
    }
    //println(String.valueOf(gauss.u_n_phs(n,gauss.get_qx(),X)));
    return (float) (gauss.u_nm_phs(n,m,X,Y));
  }
}

GuassEq gcb = new GuassEq();

void setup() {
  size(800, 800, P3D); //Use P3D for now, openGl seems to have some issues
  
  cam = new Camera3D(this);
  
  //constructor arguments are:
  //(PApplet parent, float xLength, float yLength, float zLength)
  g3d = new SurfaceGraph3D(this, 500, 500,200);		
  g3d.setXAxisMin(-0.5e-2);		
  g3d.setXAxisMax(0.5e-2);
  g3d.setZAxisMin(-5);
  g3d.setZAxisMax(5);		
  g3d.setYAxisMin(-0.5e-2);		
  g3d.setYAxisMax(0.5e-2);		
  		
  g3d.addSurfaceTrace(gcb, 200, 200, new GrayScaleColourmap(true));
  g3d.plotSurfaceTrace(0);
		
}

void draw() {
  lights();
  background(204);
  pushMatrix();
  //centre the graph for rotating
  translate(-250,-100,-250);
  g3d.draw();
  popMatrix();
}
