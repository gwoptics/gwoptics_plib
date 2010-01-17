package org.gwoptics.graphics.graph2D.traces;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import org.gwoptics.graphics.graph2D.IGraph2D;

import processing.core.PApplet;

/**
 * 
 * @author Daniel Brown 13/7/09
 * @since 0.4.0 
 */
public class RollingLine2DTrace extends Line2DTrace{

	protected int _drawPoint;
	protected ReentrantLock _lock;
	protected boolean _doDraw;
	protected Timer _timer;
	protected long _refreshRate;
	protected boolean _isMaster;
	protected RollingLine2DTrace[] _slaveTraces;
	
	protected class RollingTick extends TimerTask{
		@Override
		public void run() {
			if(_isMaster){
				try {
					_lock.lock();
				
					_doDraw = true;
					_timer.schedule(new RollingTick(), _refreshRate);
				} finally {
					_lock.unlock();
				}				
			}
		}		
	}
	
	public long getRefreshRate(){return _refreshRate;}
	protected boolean isMaster(){return _isMaster;}
	
	public RollingLine2DTrace(ILine2DEquation eq, long RefreshRate) {
		super(eq);
		_refreshRate = RefreshRate;
		_timer = new Timer();
		_isMaster = true; //true unless otherwise found it isnt in preCheck
		_lock = new ReentrantLock();
		_drawPoint = (int) Float.NaN;
		_drawPoint = Integer.MAX_VALUE;
		_slaveTraces = new RollingLine2DTrace[0];
	}		

	@Override
	public void setParent(PApplet parent){
		super.setParent(parent);
		parent.registerPre(this);
	}
	
	@Override
	public void setGraph(IGraph2D grph) {
		super.setGraph(grph);
		//pointdata is recreated depending on the x-axis length so the 
		//draw point also needs to be checked to see if its in range.
		_drawPoint = PApplet.constrain(_drawPoint, 0, _pointData.length - 1);		
	}
	
	/**
	 * Here we override the onAddTrace method to see if any Rolling2DTraces
	 * have been previously added. Then check to see if our refresh rate
	 * is the same as the others
	 */
	@Override
	public void onAddTrace(Object traces[]){		
		if(traces != null){
			if(traces.length > 0){
				for (Object t : traces) {
					if(t instanceof RollingLine2DTrace){
						_isMaster = false;
						RollingLine2DTrace rt = (RollingLine2DTrace)t;
						
						if(rt.isMaster()){
							rt._addTraceToMaster(this);
						}
						
						//check to see if our refresh rates match
						if(rt.getRefreshRate() != _refreshRate)
							throw new Trace2DException("The refresh rate of this trace must be the same " +
																"as the refresh rate of the Rolling2DTraces already " +
																"present in the Graph2D object.");
						
					}else
						throw new Trace2DException("There are other types of traces that are not Rolling2DTraces, remove before using a Rolling2DTrace.");
				}
			}
		}		
		
		if(_isMaster){
			_slaveTraces = new RollingLine2DTrace[0];
			_timer.schedule(new RollingTick(), _refreshRate);
		}else{
			if(_timer != null){
				_timer.cancel();
				_timer = null;
			}
		}
	}
	
	@Override
	public void onRemoveTrace(){
		if(_isMaster){
			if(_slaveTraces != null){
				if(_slaveTraces.length > 0){
					_changeMasterTrace(this, _slaveTraces[0]);
				}
			}
		}
	}
	
	protected static void _changeMasterTrace(RollingLine2DTrace prev, RollingLine2DTrace next){
		if(prev.isMaster() && !next.isMaster()){
			try {
				prev._lock.lock();
				next._lock.lock();
				
				prev._removeTraceFromMaster(next);
				
				prev._isMaster = false;
				next._isMaster = true;
				
				next._slaveTraces = prev._slaveTraces;
				next._timer = new Timer();
				next._timer.schedule(next.new RollingTick(), next._refreshRate);
			} finally {
				prev._lock.unlock();
				next._lock.unlock();
			}
		}
	}
	
	protected void _addTraceToMaster(RollingLine2DTrace rolling2DTrace){
		if(_isMaster && rolling2DTrace != null){
			synchronized(_slaveTraces){
				RollingLine2DTrace tmp[] = new RollingLine2DTrace[_slaveTraces.length + 1];
				System.arraycopy(_slaveTraces, 0, tmp, 0, _slaveTraces.length);
				tmp[tmp.length - 1] = rolling2DTrace; 
				
				_slaveTraces = new RollingLine2DTrace[tmp.length];
				System.arraycopy(tmp, 0, _slaveTraces, 0, tmp.length);
			}
		}
	}
	
	protected void _removeTraceFromMaster(RollingLine2DTrace t){
		if(_isMaster && t != null){
			synchronized(_slaveTraces){
				RollingLine2DTrace[] tmp = new RollingLine2DTrace[_slaveTraces.length];
				int ix = 0;
				
				for (RollingLine2DTrace rt : _slaveTraces) {
					if(!t.equals(rt)){
						tmp[ix] = rt;
						ix++;
					}
				}
				
				if(tmp[_slaveTraces.length - 1] == null){
					_slaveTraces = new RollingLine2DTrace[tmp.length - 1];
					
					for (int i = 0; i < tmp.length - 1; i++) {
						_slaveTraces[i] = tmp[i];
					}
				}
			}
		}
	}
	
	@Override
	public void generate() {
		if(_ax == null || _ay == null)
			throw new RuntimeException("One of the axis objects are null, set them using setAxes().");
		
		for (int i = 0; i < _pointData.length; i++) {
			if(i < _drawPoint){
				_eqData[i] = _eqData[i+1];
				_pointData[i] = _pointData[i+1];
			}else if(i == _drawPoint){
				_eqData[i] =  _cb.computePoint(_drawPoint, i);
				_pointData[i] = _ay.valueToPosition((float) _eqData[i]);
			}else if(i > _drawPoint){
				_eqData[i] = Float.NaN;
				_pointData[i] = Float.NaN;
			}				
		}
	}
	
	public void pre(){
		try {
			_lock.lock();
			
			if(_isMaster && _doDraw){
				generate();
				
				for (RollingLine2DTrace t : this._slaveTraces ) {
					if(t != null)
						t.generate();
				}
				
				_doDraw = false;
			}
		} finally {
			_lock.unlock();
		}		
	}
	
	@Override
	public void draw() {		
		if(_ax != null){
			_ax.setDrawTickLabels(false);
			_ax.setDrawTicks(false);
			_ax.setAxisLabel("Time");
		}
		
		super.draw();	
	}
}
