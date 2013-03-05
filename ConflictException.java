
public class ConflictException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String s;
	
	public ConflictException(Module m1, Module m2) {
		s = m2.toString() + " conflict with " + m2.toString(); 
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n" + s;
	}
}
