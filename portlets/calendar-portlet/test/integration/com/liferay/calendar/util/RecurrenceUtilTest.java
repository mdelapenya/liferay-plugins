/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.calendar.util;

import com.liferay.ant.arquilian.WebArchiveBuilder;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.impl.CalendarBookingImpl;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class RecurrenceUtilTest {

	@Deployment
	public static WebArchive createDeployment() {
		return WebArchiveBuilder.build();
	}

	@Test
	public void testExpandCalendarBooking() {
		long startTime = getTime(_YEAR, _MONTH, _DAY, _HOUR);
		long endTime = getTime(_YEAR, _MONTH, _DAY, _HOUR + 2);

		int instanceCount = 3;

		String recurrence = createDailyRecurrenceRule(instanceCount);

		CalendarBooking calendarBooking = createCalendarBooking(
			startTime, endTime, recurrence);

		long intervalStartTime = startTime;
		long intervalEndTime = getTime(_YEAR, _MONTH, _DAY + instanceCount, 23);

		List<CalendarBooking> calendarBookingInstances =
			RecurrenceUtil.expandCalendarBooking(
				calendarBooking, intervalStartTime, intervalEndTime
				, instanceCount);

		Assert.assertEquals(instanceCount, calendarBookingInstances.size());

		testCalendarBookingStartTime(
			getTime(_YEAR, _MONTH, _DAY, _HOUR),
			calendarBookingInstances.get(0));
		testCalendarBookingStartTime(
			getTime(_YEAR, _MONTH, _DAY + 1, _HOUR),
			calendarBookingInstances.get(1));
		testCalendarBookingStartTime(
			getTime(_YEAR, _MONTH, _DAY + 2, _HOUR),
			calendarBookingInstances.get(2));
	}

	@Test
	public void testExpandCalendarBookingSetInstanceIndex() {
		long startTime = getTime(_YEAR, _MONTH, _DAY, _HOUR);
		long endTime = getTime(_YEAR, _MONTH, _DAY, _HOUR + 2);

		int instanceCount = 5;

		String recurrence = createDailyRecurrenceRule(instanceCount);

		CalendarBooking calendarBooking = createCalendarBooking(
			startTime, endTime, recurrence);

		long intervalStartTime = startTime;
		long intervalEndTime = getTime(_YEAR, _MONTH, _DAY + instanceCount, 23);

		List<CalendarBooking> calendarBookingInstances =
			RecurrenceUtil.expandCalendarBooking(
				calendarBooking, intervalStartTime, intervalEndTime,
				instanceCount);

		Assert.assertEquals(instanceCount, calendarBookingInstances.size());

		testCalendarBookingInstanceIndex(0, calendarBookingInstances.get(0));
		testCalendarBookingInstanceIndex(1, calendarBookingInstances.get(1));
		testCalendarBookingInstanceIndex(2, calendarBookingInstances.get(2));
		testCalendarBookingInstanceIndex(3, calendarBookingInstances.get(3));
		testCalendarBookingInstanceIndex(4, calendarBookingInstances.get(4));
	}

	@Test
	public void testExpandCalendarBookingSetInstanceIndexWithoutFirst() {
		long startTime = getTime(_YEAR, _MONTH, _DAY, _HOUR);
		long endTime = getTime(_YEAR, _MONTH, _DAY, _HOUR + 2);

		int instanceCount = 5;

		String recurrence = createDailyRecurrenceRule(instanceCount);

		CalendarBooking calendarBooking = createCalendarBooking(
			startTime, endTime, recurrence);

		int intervalShift = 2;

		long intervalStartTime = getTime(
			_YEAR, _MONTH, _DAY + intervalShift, 0);
		long intervalEndTime = getTime(_YEAR, _MONTH, _DAY + instanceCount, 23);

		List<CalendarBooking> calendarBookingInstances =
			RecurrenceUtil.expandCalendarBooking(
				calendarBooking, intervalStartTime, intervalEndTime,
				instanceCount);

		Assert.assertEquals(
			instanceCount - intervalShift, calendarBookingInstances.size());

		testCalendarBookingInstanceIndex(2, calendarBookingInstances.get(0));
		testCalendarBookingInstanceIndex(3, calendarBookingInstances.get(1));
		testCalendarBookingInstanceIndex(4, calendarBookingInstances.get(2));
	}

	@Test
	public void testExpandCalendarBookingWithoutRecurrence() {
		long startTime = getTime(_YEAR, _MONTH, _DAY, _HOUR);
		long endTime = getTime(_YEAR, _MONTH, _DAY, _HOUR + 2);

		CalendarBooking calendarBooking = createCalendarBooking(
			startTime, endTime, null);

		int dummyCount = 5;

		long intervalStartTime = startTime;
		long intervalEndTime = getTime(_YEAR, _MONTH, _DAY + dummyCount, 23);

		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);

		List<CalendarBooking> calendarBookingInstances =
			RecurrenceUtil.expandCalendarBooking(
				calendarBooking, intervalStartTime, intervalEndTime,
				dummyCount);

		Assert.assertEquals(1, calendarBookingInstances.size());

		testCalendarBookingStartTime(
			getTime(_YEAR, _MONTH, _DAY, _HOUR),
			calendarBookingInstances.get(0));
	}

	@Test
	public void testGetCalendarBookingInstance() {
		long startTime = getTime(_YEAR, _MONTH, _DAY, _HOUR);
		long endTime = getTime(_YEAR, _MONTH, _DAY, _HOUR + 2);

		int instanceCount = 5;

		String recurrence = createDailyRecurrenceRule(instanceCount);

		CalendarBooking calendarBooking = createCalendarBooking(
			startTime, endTime, recurrence);

		int instanceIndex = 3;

		CalendarBooking calendarBookingInstance =
			RecurrenceUtil.getCalendarBookingInstance(
				calendarBooking, instanceIndex);

		long expectedStartTime = getTime(
			_YEAR, _MONTH, _DAY + instanceIndex, _HOUR);

		testCalendarBookingInstanceIndex(
			instanceIndex, calendarBookingInstance);
		testCalendarBookingStartTime(
			expectedStartTime, calendarBookingInstance);
	}

	protected CalendarBooking createCalendarBooking(
		long startTime, long endTime, String recurrence) {

		CalendarBooking calendarBooking = new CalendarBookingImpl();

		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);
		calendarBooking.setRecurrence(recurrence);

		return calendarBooking;
	}

	protected String createDailyRecurrenceRule(int instanceCount) {
		return "RRULE:FREQ=DAILY;COUNT=" + instanceCount + ";INTERVAL=1";
	}

	protected long getTime(int year, int month, int day, int hour) {
		Calendar calendar = CalendarFactoryUtil.getCalendar(
			year, month, day, hour, 0);

		return calendar.getTimeInMillis();
	}

	protected void testCalendarBookingInstanceIndex(
		int expectedIndex, CalendarBooking calendarBooking) {

		Assert.assertEquals(expectedIndex, calendarBooking.getInstanceIndex());
	}

	protected void testCalendarBookingStartTime(
		long expectedStartTime, CalendarBooking calendarBooking) {

		Assert.assertEquals(expectedStartTime, calendarBooking.getStartTime());
	}

	private static final int _DAY = 18;

	private static final int _HOUR = 10;

	private static final int _MONTH = 5;

	private static final int _YEAR = 2014;

}