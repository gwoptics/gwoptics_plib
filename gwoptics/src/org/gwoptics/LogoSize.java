package org.gwoptics;
/**
 * Used in conjunction with the Logo object, this defines the various sizes that the gwOptics
 * logo can come in. SizeXX with the XX part representing the height in pixels of the logo.
 * 
 * @author Daniel Brown
 */
public enum LogoSize {
	Size20{
		@Override
		public int getSize(){
			return 20;
		}
	},
	Size25{
		@Override
		public int getSize(){
			return 25;
		}
	},
	Size30{
		@Override
		public int getSize(){
			return 30;
		}
	},
	Size35{
		@Override
		public int getSize(){
			return 35;
		}
	},
	Size40{
		@Override
		public int getSize(){
			return 40;
		}
	},
	Size50{
		@Override
		public int getSize(){
			return 50;
		}
	},
	Size60{
		@Override
		public int getSize(){
			return 60;
		}
	},
	Size80{
		@Override
		public int getSize(){
			return 80;
		}
	};
	
	public int getSize(){
		return 35;
	}
}
