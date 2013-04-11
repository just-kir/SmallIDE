
public class Main {

	/**
	 * @param args
	 * @throws ConflictException 
	 */
	public static void main(String[] args) throws ConflictException{
		MainFrame mf = new MainFrame();
		RussianPost rp = new RussianPost(); //added Russian post
		Configuration conf = new Configuration();
		
		///// modules for testing
		AlphaModule am = new AlphaModule(mf, rp, conf);
		BetaModule bm = new BetaModule(mf,rp, conf);
		GammaModule gm = new GammaModule(mf, rp, conf);
		/////
		
		
		
		Module m = new Module(mf, rp, conf) {
			@Override
			public void menuClick(int index) {
				System.out.println("fuck "+index);
			}
			
			@Override
			public String toString() {
				
				return "fucking sample module";
			}
		};
		try{
		mf.addMenu("File/fucking.../fucking/....fuck file", m);
		mf.addMenu("Edit/copypast", m);
		mf.addMenu("File/fucking.../let this file fuck me",m);
		mf.addMenu("File/fucking.../let this file fuck me", m);
		}catch(Exception e){}
		System.out.println("[DONE] ");
	}

}
