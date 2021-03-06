/*
 * // $Id$ //
 *
 * tGSF -- teikoku Grid Scheduling Framework
 *
 * Copyright (c) 2006-2009 by the
 *   Robotics Research Institute (Section Information Technology)
 *   at TU Dortmund University, Germany
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the
 *
 *   Free Software Foundation, Inc.,
 *   51 Franklin St, Fifth Floor,
 *   Boston, MA 02110, USA
 */
package de.irf.it.rmg.core.teikoku.kernel.events;


import de.irf.it.rmg.core.teikoku.job.Job;
import de.irf.it.rmg.core.teikoku.site.Site;
import de.irf.it.rmg.core.util.time.Instant;
import de.irf.it.rmg.sim.kuiga.Event;


/**
 * TODO: not yet commented
 * 
 * @author <a href="mailto:alexander.papaspyrou@udo.edu">Alexander
 *         Papaspyrou</a>
 * @since
 * @version $Revision$ (as of $Date$ by $Author$)
 * 
 */
public class JobStartedEvent extends Event {

	/**
	 * TODO: not yet commented
	 * 
	 */
	private Job startedJob;

	/**
	 * TODO: not yet commented
	 * 
	 * @param timestamp
	 * @param startedJob
	 * @param location
	 */
	public JobStartedEvent(final Instant timestamp, final Job startedJob,
			final Site location) {
		super(timestamp, location.getUUID().toString(), location);
		this.startedJob = startedJob;
	}

	/**
	 * TODO: not yet commented
	 * 
	 * @return
	 */
	final public Job getStartedJob() {
		return this.startedJob;
	}

	/**
	 * TODO: not yet commented
	 * 
	 * @param startedJob
	 */
	final public void setStartedJob(final Job startedJob) {
		this.startedJob = startedJob;
	}

	// -------------------------------------------------------------------------
	// Implementation/Overrides for de.irf.it.rmg.sim.kuiga.Event
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.irf.it.rmg.core.teikoku.kernel.events.Event#getType()
	 */
	@Override
	final public int getOrdinal() {
		return EventType.JOB_STARTED.ordinal();
	}

	// -------------------------------------------------------------------------
	// Implementation/Overrides for java.lang.Comparable
	// -------------------------------------------------------------------------

	/**
	 * Overrides the default comparison behavior for events (based on time,
	 * type, and id) for the following special case:
	 * 
	 * If a JobStartedEvent is compared to a {@link JobQueuedEvent}, and
	 * <ol>
	 * <li>both occur at the same time (i.e. JobStartedEvent.
	 * {@link #getTimestamp()} equals {@link JobQueuedEvent#getTimestamp()}) and
	 * </li>
	 * <li>both refer to the same job (i.e. JobStartedEvent.
	 * {@link #getStartedJob()} == {@link JobQueuedEvent#getQueuedJob()},
	 * <li>
	 * </ol>
	 * then the queueing must occur before the starting of the job, that is
	 * <code>jobQueuedEvent < jobStartedEvent</code> in order to ensure correct
	 * event handling.
	 * 
	 * @see de.irf.it.rmg.sim.kuiga.Event#compareTo(de.irf.it.rmg.sim.kuiga.Event)
	 * @see de.irf.it.rmg.core.teikoku.kernel.events.EventType
	 */
	@Override
	final public int compareTo(final Event other) {
		int result = 0;

		if (this.getTimestamp().equals(other.getTimestamp())
				&& other.getClass().equals(JobQueuedEvent.class)
				&& this.getStartedJob() == (( JobQueuedEvent )other)
						.getQueuedJob()) {
			result = 1;
		} // if
		else {
			result = super.compareTo(other);
		} // else

		return result;
	}
}
