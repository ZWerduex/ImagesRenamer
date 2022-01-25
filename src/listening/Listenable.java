package listening;

public interface Listenable {
	
	public void addListener(Listener l);
	
	public void removeListener(Listener l);
	
	public void notifyListeners();
	
	public void notifyListeners(Object source);

}
