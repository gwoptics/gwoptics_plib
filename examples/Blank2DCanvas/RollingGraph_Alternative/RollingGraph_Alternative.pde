 import org.gwoptics.graphics.graph2D.Graph2D;
  import org.gwoptics.graphics.graph2D.traces.Blank2DTrace;

  float t = 0f;
  float xmin = -5f;
  float xmax = 1f;
  float xinc = 1;

  class Point2D{
    public float X,Y;
    public Point2D(float x, float y){X=x;Y=y;}
  }

  class ScatterTrace extends Blank2DTrace{
    private ArrayList _data;
    private float pSize = 0.0000008f;
    
    public ScatterTrace(){
      _data = new ArrayList();
    }
    
    public void addPoint(float x, float y){_data.add(new Point2D(x,y));}
  
    private void drawPoint(Point2D p, PGraphics canvas){
      canvas.pushStyle();
      canvas.stroke(255,0,0);
      canvas.line(p.X-pSize,p.Y,p.X+pSize,p.Y);
      canvas.line(p.X,p.Y-pSize,p.X,p.Y+pSize);      
      canvas.popStyle();
    }
    
    public void TraceDraw(PGraphics canvas) {
      if(_data != null){            
        for (int i = 0;i < _data.size(); i++) {
          drawPoint((Point2D)_data.get(i),canvas);            
        }
      }
    }
  }
  
  ScatterTrace sTrace;
  Graph2D g;
    
  void setup(){
    size(600,500);
        
    sTrace  = new ScatterTrace();
    
    g = new Graph2D(this, 400,400, true);
    g.setAxisColour(220, 220, 220);
    g.setFontColour(255, 255, 255);
        
    g.position.y = 50;
    g.position.x = 100;
        
    g.setYAxisTickSpacing(1f);
    g.setXAxisTickSpacing(1f);
    
    g.setXAxisMinorTicks(1);
    g.setYAxisMinorTicks(1);
    
    g.setYAxisMin(0f);
    g.setYAxisMax(10f);
        
    g.setXAxisLabelAccuracy(0);
    
    g.addTrace(sTrace);
    
  }
  
  void draw(){
    background(0);
    
    t += 1.0f/(float)frameRate;
    
    g.setXAxisMin(xmin + t*xinc);
    g.setXAxisMax(xmax + t*xinc);
    
    sTrace.addPoint(t, 5 + random(-0.5,0.5) + 3f*sin(t));
    sTrace.generate(); // regenerate the trace for plotting
    
    g.draw();
  }