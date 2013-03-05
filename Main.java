
public class Main {

	/**
	 * @param args
	 * @throws ConflictException 
	 */
	public static void main(String[] args) throws ConflictException{
		MainFrame mf = new MainFrame();
		Module m = new Module(mf) {
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
