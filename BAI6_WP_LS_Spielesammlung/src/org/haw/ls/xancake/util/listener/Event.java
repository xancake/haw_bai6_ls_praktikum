package org.haw.ls.xancake.util.listener;

@FunctionalInterface
public interface Event<L> {
	
	void fireEvent(L l);
}
