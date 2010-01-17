import org.gwoptics.gui.slider.*;
import guicomponents.GSlider;
import org.gwoptics.ValueType;

gwSlider sl,sl1,sl2,sl3,sl4;

void setup() {
  size(400, 300); //Use P3D for now, openGl seems to have some issues
  
  //Throughout example note the output in the PDE console
  //you should see an integer and float output, retrieved by
  //getValue() (for integer) and getValuef (for float). The reason
  //behind this is to do with inheriting from the g4p library 
  //certain functions which can't be overriden.
  
  //simple default slider
  //constructor is Parent applet, x, y, length
  sl = new gwSlider(this,20,20,300);
  
  //slider with custom skin, check the data folder to find the testSkin
  //folder, skin basically defines which folder in the data directory to
  //look for certain image files.
  sl1 = new gwSlider(this,"testSkin",20,80,300);
  //there are 3 types
  // ValueType.DECIMAL eg 0.002
  // ValueType.EXPONENT eg 2E-3
  // ValueType.INTEGER
  sl1.setValueType(ValueType.DECIMAL);
  sl1.setLimits(0.5f, 0f, 1.0f);
  
  sl2 = new gwSlider(this,20,140,300);
  sl2.setValueType(ValueType.DECIMAL);
  sl2.setLimits(0.5f, 0f, 1.0f);
  sl2.setTickCount(3); 
  sl2.setRenderMaxMinLabel(false); //hides labels
  sl2.setRenderValueLabel(false);	//hides value label
  sl2.setStickToTicks(true);//false by default this sticks
  							//the handle to ticks so no inbetween
  							//values are possible
  
  //Last example shows small float numbers used and settings
  //the accuracy of the display labels
  sl3 = new gwSlider(this,20,190,300);
  sl3.setValueType(ValueType.EXPONENT);
  sl3.setLimits(2E-10, 0, 3.5E-10);
  sl3.setTickCount(10); 
  sl3.setPrecision(1);
  
  //Can also add custom labels to ticks
  //Note: setTickCount() is overriden by
  //the length of the input array, and 
  //vice versa
  String[] l = new String[4];
  l[0] = "A";
  l[1] = "B";
  l[2] = "C";
  l[3] = "D";
  sl4 = new gwSlider(this,20,240,300);
  sl4.setTickLabels(l);
  sl4.setStickToTicks(true);
  sl4.setValue(56);//notice that we are setting a value that isnt exactly a tick
  				   //when stick to tick is true, setValue will stick to nearest tick
  				   //value
}

void draw() {
  background(200);
}

void handleSliderEvents(gwSlider slider) {
	println("integer value:" + slider.getValue() + " float value:" + slider.getValuef());
}
