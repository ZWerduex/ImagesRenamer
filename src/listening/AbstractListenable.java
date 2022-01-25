package listening;

import java.util.ArrayList;

public class AbstractListenable implements Listenable {
	
	private ArrayList<Listener> listeners = new ArrayList<>();

	@Override
	public void addListener(Listener l) {
		listeners.add(l);
	}

	@Override
	public void removeListener(Listener l) {
		listeners.remove(l);
	}

	@Override
	public void notifyListeners() {
		for (Listener l : listeners)
			l.onNotification(this);
	}

	@Override
	public void notifyListeners(Object source) {
		for (Listener l : listeners)
			l.onNotification(source);
	}

}
