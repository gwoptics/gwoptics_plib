package org.gwoptics.graphics.graph2D.traces;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;


import processing.core.PApplet;

public class UpdatingLine2DTrace extends Line2DTrace {

	protected ReentrantLock _lock;
	protected boolean _doDraw;
	protected Timer _timer;
	protected long _refreshRate;
	protected boolean _isMaster;
	protected UpdatingLine2DTrace[] _slaveTraces;
	protected int _drawPoint;
	
	protected boolean isMaster(){return _isMaster;}
	public long getRefreshRate(){return _refreshRate;}
	
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
	
	public UpdatingLine2DTrace(ILine2DEquation eq, long RefreshRate) {
		super(eq);
		
		_refreshRate = RefreshRate;
		_timer = new Timer();
		_isMaster = true; //true unless otherwise found it isnt in preCheck
		_lock = new ReentrantLock();
		_slaveTraces = new UpdatingLine2DTrace[0];
		_drawPoint = 0;
	}
	
	@Override
	public void setParent(PApplet parent){
		super.setParent(parent);
		parent.registerPre(this);
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
					if(t instanceof UpdatingLine2DTrace){
						_isMaster = false;
						UpdatingLine2DTrace rt = (UpdatingLine2DTrace)t;
						
						if(rt.isMaster()){
							rt._addTraceToMaster(this);
						}
						
						//check to see if our refresh rates match
						if(rt.getRefreshRate() != _refreshRate)
							throw new Trace2DException("The refresh rate of this trace must be the same " +
																"as the refresh rate of the Updating2DTraces already " +
																"present in the Graph2D object.");
						
					}else
						throw new Trace2DException("There are other types of traces that are not Updating2DTraces, remove before using a Updating2DTrace.");
				}
			}
		}		
		
		if(_isMaster){
			_slaveTraces = new UpdatingLine2DTrace[0];
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
	
	protected static void _changeMasterTrace(UpdatingLine2DTrace updating2DTrace, UpdatingLine2DTrace next){
		if(updating2DTrace.isMaster() && !next.isMaster()){
			try {
				updating2DTrace._lock.lock();
				next._lock.lock();
				
				updating2DTrace._removeTraceFromMaster(next);
				
				updating2DTrace._isMaster = false;
				next._isMaster = true;
				
				next._slaveTraces = updating2DTrace._slaveTraces;
				next._timer = new Timer();
				next._timer.schedule(next.new RollingTick(), next._refreshRate);
			} finally {
				updating2DTrace._lock.unlock();
				next._lock.unlock();
			}
		}
	}
	
	protected void _addTraceToMaster(UpdatingLine2DTrace t){
		if(_isMaster && t != null){
			synchronized(_slaveTraces){
				UpdatingLine2DTrace tmp[] = new UpdatingLine2DTrace[_slaveTraces.length + 1];
				System.arraycopy(_slaveTraces, 0, tmp, 0, _slaveTraces.length);
				tmp[tmp.length - 1] = t; 
				
				_slaveTraces = new UpdatingLine2DTrace[tmp.length];
				System.arraycopy(tmp, 0, _slaveTraces, 0, tmp.length);
			}
		}
	}
	
	protected void _removeTraceFromMaster(UpdatingLine2DTrace t){
		if(_isMaster && t != null){
			synchronized(_slaveTraces){
				UpdatingLine2DTrace[] tmp = new UpdatingLine2DTrace[_slaveTraces.length];
				int ix = 0;
				
				for (UpdatingLine2DTrace rt : _slaveTraces) {
					if(!t.equals(rt)){
						tmp[ix] = rt;
						ix++;
					}
				}
				
				if(tmp[_slaveTraces.length - 1] == null){
					_slaveTraces = new UpdatingLine2DTrace[tmp.length - 1];
					
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
			if(i == _drawPoint){
				_eqData[i] =  _cb.computePoint(_drawPoint,i);
				_pointData[i] = _ay.valueToPosition((float) _eqData[i]);
			}else if(i > _drawPoint){
				_eqData[i] = Float.NaN;
				_pointData[i] = Float.NaN;
			}				
		}
		
		_drawPoint ++;
		
		if(_drawPoint > _pointData.length - 1)
			_drawPoint = 0;
	}
	
	public void pre(){
		try {
			_lock.lock();
			
			if(_isMaster && _doDraw){
				generate();
				
				for (UpdatingLine2DTrace t : this._slaveTraces ) {
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
		super.draw();
		
		if(_ax != null){
			_ax.setDrawTickLabels(false);
			_ax.setDrawTicks(false);
			_ax.setAxisLabel("Time");
		}
	}
}
