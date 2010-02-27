package org.gwoptics.gui.slider;

import guicomponents.GFont;
import guicomponents.GSlider;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.gwoptics.ValueType;
import org.gwoptics.graphics.GWColour;
import org.gwoptics.graphics.IRenderable;
import org.gwoptics.gui.GUIException;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * <p>
 * gwSlider is a wrapper class that extends the functionality of the gui4processing(g4p) slider. It adds a lot of
 * functionality that is found in many other main stream sliders in other frameworks.
 * </p>
 * 
 * <p>
 * The main change to the g4p slider was to introduce a more flexible and graphical interface to render the slider.
 * The method used to render the slider is combining several images that relate to various segments of the slider
 * in the form of a skin. The slider is broken down into 4 segments, each having a specific image file that relates
 * to them:
 * </p>
 * 
 * <ul>
 * <li>Left end cap of the slider(end_left.png)</li>
 * <li>Right end cap of the slider(end_right.png)</li>
 * <li>An extendible centre segment(centre.png)</li>
 * <li>Draggable thumb(handle.png and handle_mouseover.png)</li>
 * </ul>
 * 
 * <p>
 * The five images stated above define the skin that is applied to slider. A default skin is included in the library
 * and applied when no other alternative is provided. To generate a skin all these images must be included into a 
 * folder in the sketches data directory, where the name of the folder is the name of the skin. When creating a new
 * slider, there is a constructor available that allows you to specify the skin to use. Eg, if you have a folder name
 * 'ShinyRedSkin' in your data directory that has the above images in, then pass a string with "ShinyRedSkin" to the
 * constructor.
 * </p>
 * 
 * <p>
 * The images need to related. The end_left, end_right and centre png's must all be the same height. The height can be
 * whatever is required, though values round 20 is recommended. The end segments can both be different lengths and the
 * length of the centre images must be 1 pixel. The centre image is stretched depending on the length of the slider to 
 * fill in the middle section. The thumb/handle can be any height and width but should be an odd number of pixels. An
 * odd number allows a perfect centre to be found as fractional pixels are not possible. Alpha channel use is recommended
 * to generate interesting skins.
 * </p>
 * 
 * <p>
 * Also added to the slider is tick marks. The number of ticks shown is customisable and there is the option to stick the 
 * thumb to each tick. This only allows the thumb to take on certain values and positions. The ticks by default have only
 * 2 labels, one showing the minimum and one the maximum. The other options are to have no tick labels or to specify a 
 * string array containing the labels.
 * </p>
 * 
 * <p>
 * One of the more trickier features of the slider to understand is setting the type of the slider. By default the slider
 * is set up to display and use integer values. On occasion it is useful to be able to specify a floating value range.
 * When the slider is set to ValueType.Integer, any limits or values passed to the slider will be rounded to the nearest
 * integer. If you later then switch to ValueType.Decimal or Exponential the limits and value will still be rounded.
 * This can cause issues as the initial setting is ValueType.Integer, if you first set your floating limits and value then 
 * specify the type of the slider to be decimal, the decimal parts of the inputs will be lost. <b>Always specify the 
 * ValueType of the slider as soon as it is created</b>, this will save many headaches. 
 * </p>
 * 
 * <p>
 * Another confusing feature to the slider is the methods to retrieve the value of the slider. Initially the g4p slider
 * only allowed an integer value. To get around this a new value member is defined as a float, where all the relevant
 * methods are overridden to use this new value but to also update the old integer value. Unfortunately the getValue method
 * is already predefined to return and integer, so a new getValuef() method is added to allow you to access the floating 
 * member. Either can be used but remember getValue() will always return a rounded value.
 * </p>
 * 
 * <p>
 * The slider can also be controlled via the arrow keys on the keyboard for a finer adjustment. The slider must first
 * have been given focus with the mouse before hand, before it will work. 
 * </p>
 * 
 * <p>
 * <b>NOTE: Handle/Thumb image should be an odd number of pixels wide so that it can correctly centre on tick marks.</b>
 * </p>
 * 
 * <p>
 * History<br /> 
 * Version 0.3.3 overhaul to how ticks are calculated and rendered, firstly calculating the difference in value
 * between tick and then relating that into a distance in pixels, rather than dividing length of slider by tick
 * number.
 * </p>
 * @author Daniel Brown 21/6/09
 * @since 0.3.0
 *
 */
public class gwSlider extends GSlider implements IRenderable {

	public String unit;
	
	protected PImage _leftEnd;
	protected PImage _thumb;
	protected PImage _thumb_mouseover;
	protected PImage _rightEnd;
	protected PImage _centre;
	protected String _skin;
	protected int _numTicks;
	protected int _tickLength;
	protected int _tickOffset;	
	protected int _precision;
	protected boolean _renderMaxMinLabel;
	protected boolean _renderValueLabel;
	protected boolean _stickToTicks;
	protected ValueType _valueType;
	protected String[] _tickLabels;
	protected boolean _isMouseOverThumb;
	protected boolean _mousePressedOverThumb;
	protected int[] _tickPositions;
	protected float[] _tickValues;
	protected GWColour _tickColour;	
	protected GWColour _fontColour;	
	
	protected float init;
	protected float maxValue;
	protected float minValue;
	protected float value;
	
	//setters
	/**
	 * Sets the number of decimal places to print in the value labels
	 */
	public void setPrecision(int acc){_precision = PApplet.constrain(acc, 0, 6);}
	/**
	 * Sets the type of slider that this should be.
	 * @see ValueType
	 */
	public void setValueType(ValueType type){_valueType = type;}
	/**
	 * Sets the number of ticks shown on the slider
	 */
	public void setTickCount(int n){
		_numTicks = n +1;
		_tickLabels = null;
		_calcTickPositions();
	}
	/**
	 * Accepts an array of strings that then determines the number of ticks shown and the label underneath each of them.
	 * This overrides any previous setting ot the tick count and value label rendering.
	 * @param lbls
	 */
	public void setTickLabels(String[] lbls){
		_tickLabels = lbls;
		_numTicks = lbls.length - 1;
		_calcTickPositions();
	}
	/**
	 * Setting to true limits the thumb to only take values that each tick represents and no value in between them
	 */
	public void setStickToTicks(boolean b){
		_stickToTicks = b;
		if(b){
			_stickToTickByValue(PApplet.map(winApp.mouseX, thumbMin, thumbMax, minValue, maxValue));
		}
		_calcTickPositions();
	}
	/**
	 * Adjusts the length of the ticks
	 * @param l
	 */
	public void setTickLength(int l){
		_tickLength = PApplet.constrain(l, 1, 10);
		_calcControlWidthHeight();
	}
	/**
	 * set to false to not render the min/max labels for a more minamalistic look.
	 */
	public void setRenderMaxMinLabel(boolean value){
		_renderMaxMinLabel = value;
		_calcControlWidthHeight();
	}
	/**
	 * set to false to not render the value label for a more minamalistic look.
	 */
	public void setRenderValueLabel(boolean value){
		_renderValueLabel = value;
	}
	/**
	 * Accepts RGB values between 0 and 255 to state the colour of the ticks
	 */
	public void setTickColour(int R,int G, int B){
		setTickColour(new GWColour(R,G,B));
	}
	/**
	 * Accepts a Colour object to specify the colour of the ticks
	 * @see GWColour
	 */
	public void setTickColour(GWColour c){
		if(c == null)
			throw new NullPointerException("Colour argument cannot be null");
		
		_tickColour = c;
	}
	/**
	 * Accepts RGB values between 0 and 255 to state the colour of the label fonts
	 */
	public void setFontColour(int R,int G, int B){
		setFontColour(new GWColour(R,G,B));
	}

	/**
	 * Accepts a Colour object to specify the colour of the label fonts
	 * @see GWColour
	 */
	public void setFontColour(GWColour c){
		if(c == null)
			throw new NullPointerException("Colour argument cannot be null");
		
		_fontColour = c;
	}
	
	/**
	 * If somehow the slider is created with out being associated with a PApplet to
	 * render is this function allows you to set one. Throws an excetion if parent is
	 * already present
	 */
	public void setParent(PApplet parent){
		if(winApp != null)
			throw new RuntimeException("Parent object has already been set.");
		winApp = parent;
	}
	/**
	 * gets the current value of the slider as an integer. Use getValuef() for floating value.
	 */
	@Override
	public int getValue(){
		return Math.round(value);
	}
	
	/**
	 * Gets the current floating value of the slider.
	 */
	public float getValuef(){
		if(_valueType == ValueType.INTEGER)
			return Math.round(value);
		else
			return value;
	}

	/**
	 * basic constructor that applies the default library skin to the slider. Accepts the x and y position of the slider, the PApplet parent
	 * where the slider is rendered and the length of the slider.
	 * 
	 * @param parent
	 * @param x
	 * @param y
	 * @param length
	 */
	public gwSlider(PApplet parent, int x, int y, int length) {
		this(parent,"gwSlider",x,y,length);
	}
	/**
	 * Alternative constructor that applies a given skin to the slider. Accepts the x and y position of the slider, the PApplet parent
	 * where the slider is rendered and the length of the slider. Throws GUIException if the necessary skin images are not present.
	 * 
	 * @param parent
	 * @param x
	 * @param y
	 * @param length
	 * @see GUIException
	 */
	public gwSlider(PApplet parent,String skin, int x, int y, int length) {
		super(parent, x, y, length, 1); //we reset the height later when we ge the image heights
		
		if(length < 1){throw new RuntimeException("Length of slider must be greater than 0.");}
		//signs upto the g4p events system
		createEventHandler(winApp, "handleSliderEvents", new Class[]{ gwSlider.class });
		
		//Here we set a bunch of default values for everything
		if(skin == null)
			_skin = "gwSlider";
		else
			_skin = skin;
		
		_numTicks = 5;
		_tickLength = 5;
		_tickOffset = 3;
		_renderMaxMinLabel = true;
		_renderValueLabel = true;
		_stickToTicks = false;
		_precision = 2;
		_valueType = ValueType.INTEGER;
		_tickColour = new GWColour(0,0,0);
		_fontColour = new GWColour(0,0,0);
		
		unit = "";
		
		//Look for the skin images, if these dont exist the variable come out null
		// no excetpions are thrown
		_leftEnd = parent.loadImage(_skin + "/end_left.png");
		_rightEnd = parent.loadImage(_skin + "/end_right.png");
		_thumb = parent.loadImage(_skin +"/handle.png");
		_thumb_mouseover = parent.loadImage(_skin +"/handle_mouseover.png");
		//load the centre image up temporarily as we will generate the final stretched
		//image to use shortly
		PImage cTemp = parent.loadImage(_skin + "/centre.png");
		
		String files = "";
		
		//genearate a list of files that arent there
		if(_leftEnd == null)
			files += "end_left.png\n";
		if(_rightEnd == null)
			files += "end_right.png\n";
		if(_thumb == null)
			files += "handle.png\n";
		if(_thumb_mouseover == null)
			files += "handle_mouseover.png\n";
		if(cTemp == null)
			files += "centre.png\n";
		
		if(files != ""){	
			throw new GUIException("The following files could not be found for the skin " + _skin + ": \n" + files
					+ "\nCheck that these files are correctly placed in the data directory under a folder with"
					+ " the same name as the skin used.\n");
		}
		
		//do a bunch of check on the images
		if(cTemp.width != 1){throw new GUIException("The supplied centre image for this skin is not of width 1px.");}
		if(cTemp.height != _leftEnd.height || cTemp.height != _rightEnd.height){
			throw new GUIException("The image components of the slider are not all the same height.");
		}
		
		this.height = cTemp.height;
		int cWidth = length - _leftEnd.width - _rightEnd.width;
		if(cWidth < 0){cWidth = 0;}
		
		
		_centre = new PImage(cWidth,cTemp.height);
		
		//now copy over the data from cTemp to main centre image
		//the standard pimage stretch method is no good in this case 
		//appears better to do it manually.
		cTemp.loadPixels();
		_centre.loadPixels();
		
		for (int i = 0; i < _centre.height; i++) {
			for (int j = 0; j < _centre.width; j++) {
				_centre.pixels[i*_centre.width +j] = cTemp.pixels[i];
			}
		}
		
		//the thumb only moves along the centre section
		thumbMin = x + _leftEnd.width;
		thumbMax = x + _leftEnd.width + _centre.width;
		this.setLimits(50.0f, 0.0f, 100.0f);
		
		localFont = globalFont;
		winApp.textFont(localFont);
		winApp.textAlign(PConstants.CENTER);
		
		_calcControlWidthHeight();
		_calcTickPositions();
		winApp.registerKeyEvent(this);
	}
	
	/**
	 * the width of the control and height, is determined by the length and also 
	 * the various images used in the skins. This function deals with that. It is
	 * imporatant as the width and height values define the bounding box around the
	 * slider. This then helps determine if mouse clicks are relevant to this slider
	 * or not.
	 */
	protected void _calcControlWidthHeight(){
		//width is determined by image sizes
		width = _leftEnd.width + _centre.width + _rightEnd.width;
		height =  _centre.height + _tickLength + _tickOffset;
		
		if(_renderMaxMinLabel){height += localFont.size;}
	}
	
	/**
	 * The tick positions are stored in an array and referenced on each draw call.
	 * It is done this way to provide a more accurate way of lining up the ticks and
	 * the thumb when stick to ticks is enabled
	 */
	protected void _calcTickPositions(){
		Point p = new Point();
		calcAbsPosition(p);
		
		if(_tickLabels == null){
			float sliderRange = maxValue - minValue;
			float dTick = sliderRange / _numTicks; //distance in terms of value
			
			_tickPositions = new int[_numTicks + 1];
			_tickValues = new float[_numTicks + 1];
			
			for(int i = 0; i <= _numTicks;i++){
				_tickPositions[i] = Math.round(PApplet.map(minValue + i * dTick, minValue, maxValue, thumbMin, thumbMax));
				_tickValues[i] = minValue + i * dTick;
			}
		}else{			
			float dTick = _centre.width / _numTicks; //distance in terms of px
			
			_tickPositions = new int[_numTicks + 1];
			_tickValues = new float[_numTicks + 1];
			
			for(int i = 0; i <= _numTicks;i++){
				_tickPositions[i] = Math.round(p.x + _leftEnd.width + i * dTick);
				_tickValues[i] = PApplet.map(p.x + _leftEnd.width + i * dTick, thumbMin, thumbMax, minValue, maxValue);
			}
		}
	}
	
	/**
	 * This method takes a value that is to represented on the slider and determines which
	 * tick it is closet to and sets the thumb at the relevant position
	 * @param v
	 */
	protected void _stickToTickByValue(float v){
		float sliderRange = maxValue - minValue;
		float dTick = sliderRange / _numTicks; //distance in terms of value
		
		int index = Math.round(PApplet.constrain(v, minValue, maxValue) / dTick);
		index = PApplet.constrain(index, 0, _tickPositions.length-1);
		thumbTargetPos = _tickPositions[index];
	}
	
	/**
	 * This method accepts an input value that states a screen position(usually from a mouse click) and 
	 * determines the nearest tick marks to stick the thumb to
	 * @param pos
	 */
	protected void _stickToTickByPosition(float pos){
		Point p = new Point();
		calcAbsPosition(p);
		
		float sliderRange = maxValue - minValue;
		float dTick = sliderRange / _numTicks; //distance in terms of value
		float v = PApplet.map(pos, thumbMin, thumbMax, minValue, maxValue);
		
		int index = Math.round((v - minValue) / dTick);
		index = PApplet.constrain(index, 0, _tickPositions.length-1);
		thumbTargetPos = _tickPositions[index];
	}
		
	
	/**
	 * handles all the mouse events that may effect the slider. depending on mouse position and action
	 * the focus is set to the slider and the relevant function and members set.
	 */
	@Override
	public void mouseEvent(MouseEvent event){
		float sliderRange = maxValue - minValue;
		
		if(isVisible() && sliderRange > 0.0f){
			boolean isMouseOver = this.isOver(event.getX(), event.getY());
			
			switch (event.getID()) {
			   case MouseEvent.MOUSE_PRESSED:
				   if(isMouseOver){
					   this.takeFocus();
					   if(isOverThumb(event.getX(), event.getY()))
						   _mousePressedOverThumb = true;
					   else
						   _mousePressedOverThumb = false;
				   }
			     break;
			     
			   case MouseEvent.MOUSE_RELEASED:
				   if(focusIsWith == this && isMouseOver || (_mousePressedOverThumb == true)){
					   if(_stickToTicks){
						   _stickToTickByPosition(winApp.mouseX);
					   }else{
						    thumbTargetPos  = PApplet.constrain(winApp.mouseX, thumbMin, thumbMax);
					   }
					   
					   if(isOverThumb(event.getX(), event.getY())){
						   _isMouseOverThumb = true;
					   }else{
						   _isMouseOverThumb = false;
					   }
					   
					   _mousePressedOverThumb = false;					  
					   
					   eventType = RELEASED;
					   fireEvent();
				   }else{
					   _isMouseOverThumb = false;
					   _mousePressedOverThumb = false;
				   }
			     break;	 
			     
			   case MouseEvent.MOUSE_DRAGGED:
				 if((focusIsWith == this) && _mousePressedOverThumb){
					 thumbTargetPos  = PApplet.constrain(winApp.mouseX , thumbMin, thumbMax);
				 }
			     break;
			     
			   case MouseEvent.MOUSE_MOVED:
				   if(isOverThumb(event.getX(), event.getY())){
					   _isMouseOverThumb = true;
				   }
				   else
					   _isMouseOverThumb = false;
			       break;
			 }
		}
	}
	
	/**
	 * Registered key event of parent object, checks if arrow keys are being pressed and has focus
	 * if so the thumb is moved one pixel.
	 */
	public void keyEvent(KeyEvent e){
		if(e.getID() == KeyEvent.KEY_PRESSED && this.hasFocus()){
			if(e.getKeyCode()== 37){ //left arrow
				thumbTargetPos = PApplet.constrain(thumbTargetPos - 1,thumbMin,thumbMax);
			}else if(e.getKeyCode()== 39){ //right arrow
				thumbTargetPos = PApplet.constrain(thumbTargetPos + 1,thumbMin,thumbMax);
			}
		}
	}
	
	/**
	 * returns whether the positions supplied is over the mouse or not
	 */
	@Override
	public boolean isOver(int ax, int ay){
		Point p = new Point(0,0);
		calcAbsPosition(p);
		float val = (float) (_centre.height * 0.5 - _thumb.height * 0.5); //takes into account if the thumbs height
																		  //is greater than the central bar image
		if(ax >= p.x && ax <= p.x + width && ay >= (p.y + val) && ay <= (p.y + height - val))
			return true;
		else 
			return false;
	}
	
	/**
	 * return whether input position is over the thumb, used to determine whether to show the
	 * handle_mouseover image
	 * @param ax
	 * @param ay
	 * @return
	 */
	public boolean isOverThumb(int ax, int ay){		
		Point p = new Point(0,0);
		calcAbsPosition(p);
		Rectangle r = new Rectangle((int)(thumbPos - 0.5*_thumb.width - 1),
									(int)(p.y + 0.5*_centre.height -  0.5*_thumb.height -1),
									(int)(_thumb.width + 1),
									(int)(_thumb.height + 1));
		
		if(r.contains(ax, ay))
			return true;
		else 
			return false;
	}
	
	/**
	 * Sets the limits of the slider as integer values. Converted to floats depending on slider type
	 * @see setValueType 
	 */
	@Override
	public void setLimits(int init, int min, int max){
		setLimits((float)init,(float)min,(float)max);
	}
	
	/**
	 * Sets the limits of the slider as float values. Converted to floats or integer depending
	 * on the type of the slider.
	 * @see setValueType 
	 */
	public void setLimits(float init, float min, float max){
		if(_valueType != ValueType.INTEGER){
			minValue = Math.min(min, max);
			maxValue = Math.max(min, max);
			this.init = PApplet.constrain(init, minValue, maxValue);
		}else{
			minValue = Math.round(Math.min(min, max));
			maxValue = Math.round(Math.max(min, max));
			this.init = Math.round(PApplet.constrain(init, minValue, maxValue));
		}
		
		/*if(thumbMax - thumbMin < maxValue - minValue && G4P.messages){
			System.out.println(this.getClass().getSimpleName()+".setLimits");
			System.out.println("  not all values in the range "+min+" - "+max+" can be returned");
			System.out.print("  either reduce the range or make the slider ");
			if(this.getClass().getSimpleName().equals("gwSlider")) 
				System.out.print("width");
			else
				System.out.print("height");
			System.out.println(" at least " + (max-min+thumbSize));
		}*/
		
		thumbTargetPos = thumbPos;
		// Set the value immediately ignoring inertia
		setValue(init, true);
	}
	
	/**
	 * sets the current value of the slider
	 */
	@Override
	public void setValue(int newValue){
		setValue((float) newValue);
	}
	
	/**
	 * sets the current value of the slider. Value is rounded to integer depending on type of slider
	 */
	public void setValue(float newValue){
		if(_valueType == ValueType.INTEGER)
			value = Math.round(PApplet.constrain(newValue, minValue, maxValue));
		else
			value = PApplet.constrain(newValue, minValue, maxValue);
			
		if(_stickToTicks){
			_stickToTickByValue(newValue);
		}else{
			thumbTargetPos = Math.round(PApplet.map(value, minValue, maxValue, thumbMin, thumbMax));
		}
	}
	
	/**
	 * sets the current value of the slider, and also switches on inertia for smoother motions
	 */
	@Override
	public void setValue(int newValue,  boolean ignoreInteria){
		setValue((float) newValue, ignoreInteria);
	}
	/**
	 * sets the current value of the slider, and also switches on inertia for smoother motions
	 */
	public void setValue(float newValue,  boolean ignoreInteria){
		setValue(newValue);
		if(ignoreInteria)
			thumbPos = thumbTargetPos;
	}
	/**
	 * Sets font of labels
	 */
	@Override
	public void setFont(String fontname, int fontsize){
		localFont = GFont.getFont(winApp, fontname, fontsize);
	}
	/**
	 * Pre is called before each draw call and should not be used by the end user.
	 * It checks to see if the thumb and value positions match up correctly.
	 */
	@Override
	public void pre(){
		int change, inertia = thumbInertia;
		if(thumbPos == thumbTargetPos){
			isValueChanging = false;
		}
		else {
			// Make sure we get a change value by repeatedly decreasing the inertia value
			do {
				change = (thumbTargetPos - thumbPos)/inertia;
				inertia--;
			} while (change == 0 && inertia > 0);
			// If there is a change update the current value and generate an event
			if(change != 0){
				thumbPos += change;
				float newValue = 0.0f;
				float upperVal, lowerVal;
				
				//Here we need to do opposite of what _stickToTickByValue() does and
				//relate a position to a value to make sure the value does change
				//gradually but in steps
				if(_stickToTicks){
					float tickDist = (_centre.width)/(float)_numTicks;
					float relPos = (float) (thumbPos - x - _thumb.width*0.5);
					int num = Math.round(relPos/tickDist);
					newValue = _tickValues[num];
				}else{
					//To deal with the situation when a user sets a value that cant
					//be fully represented on the slider due to there not being enough
					//pixels(not long enough). I have taken the next highest and lowest
					//pixels and found the values they represent on the slider.
					upperVal = PApplet.map(thumbPos + 1, thumbMin, thumbMax, minValue, maxValue);
					newValue = PApplet.map(thumbPos, thumbMin, thumbMax, minValue, maxValue);
					lowerVal = PApplet.map(thumbPos -1, thumbMin, thumbMax, minValue, maxValue);
					
					//now if our value is between these bounds then we want to keep the value that
					//the user set but not to change the position of the slider and its true position
					//is in fractions of pixels. if that makes sense.
					if(value > lowerVal && value < upperVal){
						//set the new value to the old so that we preserve user set value.
						newValue = value;
					}
				}
				
				boolean valueChanged = (newValue != value);
				
				value = newValue;
				if(valueChanged){
					eventType = CHANGED;
					fireEvent();
				}
			}else{
				isValueChanging = false;
			}
		}			
	}
	
	@Override
	public void draw(){		
		if(!visible) return;
		String format = null;
		
		//depending on slider type we want to format the 
		//labels as necessary
		switch(_valueType){
		case INTEGER:
			format = "%d%s";
			break;
		case DECIMAL:
			format = "%." + _precision + "f%s";
			break;
		case EXPONENT:
			format = "%." + _precision + "E%s";
			break;
		}
		
		//calculates the absolute position, this is 
		//for when the slider is embedded in other panels and controls
		Point p = new Point(); 
		calcAbsPosition(p);
		
		//draw each of the slider skin images
		winApp.image(_leftEnd, p.x, p.y);
		winApp.image(_centre, p.x + _leftEnd.width, p.y);
		winApp.image(_rightEnd, p.x + _leftEnd.width + _centre.width, p.y);
		
		winApp.pushStyle();

		winApp.textFont(localFont);
		winApp.textAlign(PConstants.CENTER);
		
		//calc a few tick variables for drawing
		float tickDist =  (_centre.width )/(float)_numTicks;
		winApp.stroke(_tickColour.toInt());
		winApp.strokeWeight(1);
		winApp.fill(_fontColour.toInt());
		//draw ticks
		float tickYPos = p.y + _centre.height + _tickOffset + _tickLength + localFont.size;
		
		for(int i = 0;i < _tickPositions.length;i++){
			if(_tickLabels != null){
				Point pos = new Point(_tickPositions[i],(int) tickYPos);
				winApp.text(_tickLabels[i],pos.x,pos.y);
			}else if(i == 0 && _renderMaxMinLabel){
				//Draw in the min value
				Point pos = new Point(p.x + _leftEnd.width + Math.round(i * tickDist),p.y + _centre.height + _tickOffset + _tickLength + localFont.size) ;
				if(_valueType == ValueType.INTEGER)
					winApp.text(String.format(format,Math.round(minValue),unit),pos.x,pos.y);
				else
					winApp.text(String.format(format,minValue,unit),pos.x,pos.y);
				
			}else if(i == _numTicks && _renderMaxMinLabel){	
				//Draw in the max value			
				Point pos = new Point(p.x + _leftEnd.width + Math.round(i * tickDist),p.y + _centre.height + _tickOffset + _tickLength + localFont.size) ;
				if(_valueType == ValueType.INTEGER)
					winApp.text(String.format(format,Math.round(maxValue),unit),pos.x,pos.y);
				else
					winApp.text(String.format(format,maxValue,unit),pos.x,pos.y);
			}
			
			winApp.beginShape(LINE);
			winApp.vertex(_tickPositions[i], p.y + _centre.height + _tickOffset);
			winApp.vertex(_tickPositions[i], p.y + _centre.height + _tickOffset + _tickLength);
			winApp.endShape();

			//draws a highlight line next to the black line, gives a more
			//3d feel to the control
			winApp.pushStyle();
			winApp.stroke(230);
			winApp.beginShape(LINE);
			winApp.vertex(_tickPositions[i] + 1, p.y + _centre.height + _tickOffset);
			winApp.vertex(_tickPositions[i] + 1, p.y + _centre.height + _tickOffset + _tickLength);
			winApp.endShape();
			winApp.popStyle();
			
			if(!_isMouseOverThumb)
				winApp.image(_thumb, thumbPos - Math.round(_thumb.width*0.5) + 1, (float) (p.y + 0.5*_centre.height - 0.5*_thumb.height));
			else
				winApp.image(_thumb_mouseover, thumbPos - Math.round(_thumb_mouseover.width*0.5) + 1, (float) (p.y + 0.5*_centre.height - 0.5*_thumb_mouseover.height));
			
			if(_renderValueLabel){
				if(_valueType == ValueType.INTEGER)
					winApp.text(String.format(format,Math.round(value),unit),thumbPos, p.y - _thumb.height*0.5f + 0.5f*_centre.height - 4 );
				else
					winApp.text(String.format(format,value,unit),thumbPos, p.y - _thumb.height*0.5f + 0.5f*_centre.height - 4 );
			}			
		}			
		
		winApp.popStyle();
	}
}
