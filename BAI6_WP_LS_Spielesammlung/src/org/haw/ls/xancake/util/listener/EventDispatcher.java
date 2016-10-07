package org.haw.ls.xancake.util.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventDispatcher<L> {
	private List<L> myListeners;
	
	public EventDispatcher() {
		this(new ArrayList<L>());
	}
	
	protected EventDispatcher(List<L> listenerList) {
		myListeners = listenerList;
	}
	
	public void addListener(L l) {
		myListeners.add(Objects.requireNonNull(l));
	}
	
	public void removeListener(L l) {
		myListeners.remove(l);
	}
	
	public List<L> getListeners() {
		return new ArrayList<L>(myListeners);
	}
	
	public void fireEvent(Event<? super L> event) {
		for(L l : myListeners) {
			event.fireEvent(l);
		}
	}
}
