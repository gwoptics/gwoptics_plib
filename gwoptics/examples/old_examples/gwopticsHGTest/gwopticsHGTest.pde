
import gwoptics.*;

// Mode index
int n = 1;

GaussMode gauss = new GaussMode(1064e-9, 1.0e-3, 0.0, 1.0);

float[] x;   
float[] y; 
float xscale;
float yscale;

void setup() {
  size(400,400);
  x = new float[width];
  y = new float[width];

  xscale=10.0 * 1.0e-3/width;
  float ymax=0;
  float ymin=0;
  for (int i = 0; i<width; i++)
  {
    x[i]=(i-width/2)*xscale;
    y[i]=(float)gauss.u_n_amp(n,gauss.get_qx(),x[i]);
    if (y[i]>ymax) {
      ymax=y[i];
    }
    if (y[i]<ymin){
      ymin=y[i];
    }
  }
  yscale=height/2/(ymax+1.0);
}

void draw() {
  background(0);
  for (int i = 1; i < width; i++) {
    float my1=round(0.5*height-yscale*y[i-1]);
    float my2=round(0.5*height-yscale*y[i]);
    strokeWeight(2);  
    stroke(250);
    line(i-1, my1, i, my2);
  }
}

