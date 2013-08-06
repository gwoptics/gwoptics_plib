/** 
 * SliderDemo
 * Example file for the usage of the gwSlider objects.
 * This needs the library gui4processing to be installed, 
 * see http://www.lagers.org.uk/g4p/.
 * 
 * Throughout example note the output in the PDE console;
 * you should see an integer and float output, retrieved by
 * getValue() (for integer) and getValuef (for float). 
 */

//import org.gwoptics.gui.slider.*;
import guicomponents.G4P;
import guicomponents.GWSlider;
import guicomponents.GSlider;
import org.gwoptics.ValueType;

GWSlider sl,sl1,sl2,sl3,sl4,sl5;

void setup() {
  size(350, 430);  
  
  // Disable G4P warning messages.
  G4P.messagesEnabled(false);
  
  // Simple default slider,
  // constructor is `Parent applet', the x, y position and length
  sl = new GWSlider(this,20,20,300);
  
  // Slider with a custom skin, check the data folder to find the `blue18px'
  // folder which stores the used image files.
  sl1 = new GWSlider(this,"blue18px",20,80,300);
  // there are 3 types
  // ValueType.DECIMAL  eg 0.002
  // ValueType.EXPONENT eg 2E-3
  // ValueType.INTEGER
  sl1.setValueType(ValueType.DECIMAL);
  sl1.setLimits(0.5f, 0f, 1.0f);
  sl1.setRenderValueLabel(false); 

  // Slider with another custom skin
  sl2 = new gwSlider(this,"red_yellow18px",20,140,300);
  sl2.setRenderValueLabel(false); 

  // Slider with another custom skin
  sl3 = new gwSlider(this,"purple18px",20,200,300);
  
  // Standard slider with labels switched off
  sl4 = new gwSlider(this,20,260,300);
  sl4.setValueType(ValueType.DECIMAL);
  sl4.setLimits(0.5f, 0f, 1.0f);
  sl4.setTickCount(3); 
  sl4.setRenderMaxMinLabel(false); //hides labels
  sl4.setRenderValueLabel(false);  //hides value label
  sl4.setStickToTicks(true);       //false by default 
  // `Stick to ticks' enforces that the handle can only rest at a 
  // tick position.
  		
  // This example shows small float numbers used and settings
  // the accuracy of the display labels
  sl4 = new gwSlider(this,20,320,300);
  sl4.setValueType(ValueType.EXPONENT);
  sl4.setLimits(2E-10, 0, 3.5E-10);
  sl4.setTickCount(10); 
  sl4.setPrecision(1);
  
  // We can also add custom labels to ticks
  // Note: setTickCount() is overriden by
  // the length of the input array, and 
  // vice versa
  String[] l = new String[4];
  l[0] = "A";
  l[1] = "B";
  l[2] = "C";
  l[3] = "D";
  sl5 = new gwSlider(this,20,380,300);
  sl5.setTickLabels(l);
  sl5.setLimits(1, 0, 30);
  sl4.setTickCount(10); 
  sl5.setStickToTicks(true);
  sl5.setValue(56);
  // notice that we are setting a value that is not exactly a tick
  // when `stick to tick' is true, setValue will stick to nearest tick
  // value
}

void draw() {
  background(200);
}

void handleSliderEvents(gwSlider slider) {
	println("integer value:" + slider.getValue() + " float value:" + slider.getValuef());
}
