package com.mcmoddev.htwtweaks.util;

import com.google.common.collect.Lists;
import com.mcmoddev.htwtweaks.transport.containers.LaserTransportContainer;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

public class ListenableHolder {
	private final List<Reference<? extends LaserTransportContainer>> listeners = Lists.newArrayList();
	private final ReferenceQueue<LaserTransportContainer> pendingRemovals = new ReferenceQueue<>();

	public void addWeakListener(LaserTransportContainer e)
	{
		listeners.add(new WeakReference<>(e, pendingRemovals));
	}

	public void doCallbacks()
	{
		for (Reference<? extends LaserTransportContainer>
			 ref = pendingRemovals.poll();
			 ref != null;
			 ref = pendingRemovals.poll())
		{
			listeners.remove(ref);
		}

		for (Iterator<Reference<? extends LaserTransportContainer>> iterator = listeners.iterator(); iterator.hasNext(); )
		{
			Reference<? extends LaserTransportContainer> reference = iterator.next();
			LaserTransportContainer listener = reference.get();
			if (listener == null)
				iterator.remove();
			else
				listener.onInventoryChanged();
		}
	}
}
