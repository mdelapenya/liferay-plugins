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
	public void testExpandCalendarBookingSetInstanceIndex() {
		CalendarBooking calendarBooking = new CalendarBookingImpl();

		int instanceCount = 5;

		long startTime = getTime(_YEAR, _MONTH, _DAY, _HOUR);
		long endTime = getTime(_YEAR, _MONTH, _DAY, _HOUR+2);
		long intervalStartTime = startTime;
		long intervalEndTime = getTime(_YEAR, _MONTH, _DAY + instanceCount, 23);

		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);
		calendarBooking.setRecurrence(
			"RRULE:FREQ=DAILY;COUNT=" + instanceCount + ";INTERVAL=1");

		List<CalendarBooking> instances = RecurrenceUtil.expandCalendarBooking(
			calendarBooking, intervalStartTime, intervalEndTime, instanceCount);

		Assert.assertEquals(instanceCount, instances.size());

		CalendarBooking instance = instances.get(0);
		Assert.assertEquals(0, instance.getInstanceIndex());

		instance = instances.get(1);
		Assert.assertEquals(1, instance.getInstanceIndex());

		instance = instances.get(2);
		Assert.assertEquals(2, instance.getInstanceIndex());

		instance = instances.get(3);
		Assert.assertEquals(3, instance.getInstanceIndex());

		instance = instances.get(4);
		Assert.assertEquals(4, instance.getInstanceIndex());
	}

	@Test
	public void testExpandCalendarBookingSetInstanceIndexWithoutFirst() {
		CalendarBooking calendarBooking = new CalendarBookingImpl();

		int instanceCount = 5;
		int intervalShift = 2;

		long startTime = getTime(_YEAR, _MONTH, _DAY, _HOUR);
		long endTime = getTime(_YEAR, _MONTH, _DAY, _HOUR + 2);

		long intervalStartTime = getTime(
			_YEAR, _MONTH, _DAY + intervalShift, 0);
		long intervalEndTime = getTime(_YEAR, _MONTH, _DAY + instanceCount, 23);

		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);
		calendarBooking.setRecurrence(
			"RRULE:FREQ=DAILY;COUNT=" + instanceCount + ";INTERVAL=1");

		List<CalendarBooking> instances = RecurrenceUtil.expandCalendarBooking(
			calendarBooking, intervalStartTime, intervalEndTime, instanceCount);

		Assert.assertEquals(instanceCount - intervalShift, instances.size());

		CalendarBooking instance = instances.get(0);
		Assert.assertEquals(2, instance.getInstanceIndex());

		instance = instances.get(1);
		Assert.assertEquals(3, instance.getInstanceIndex());

		instance = instances.get(2);
		Assert.assertEquals(4, instance.getInstanceIndex());
	}

	private static long getTime(int year, int month, int day, int hour) {
		Calendar calendar = CalendarFactoryUtil.getCalendar(
			year, month, day, hour, 0);

		return calendar.getTimeInMillis();
	}

	private static final int _DAY = 18;

	private static final int _HOUR = 10;

	private static final int _MONTH = 5;

	private static final int _YEAR = 2014;

}