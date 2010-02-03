import processing.opengl.*;

import org.gwoptics.graphics.graph3D.SquareGridMesh;
import org.gwoptics.graphics.camera.Camera3D;

Camera3D cam;
SquareGridMesh sMesh;

void setup(){
    size(500,300,P3D);
    cam = new Camera3D(this);
    cam.setPosition(new PVector(1700,1200,0));
    sMesh = new SquareGridMesh(20,20,100,100,this);
    registerPre(this);
}

void pre(){
  for(int i = 0; i <= sMesh.sizeX();i++){
    for(int j = 0; j <= sMesh.sizeY();j++){
      sMesh.setZValue(i,j, (float)(100 * sin(8*i*j) * Math.random()));
    }
  }
}

void draw(){
  background(200);
  translate(-1000,0,-1000);
  sMesh.draw();
}
