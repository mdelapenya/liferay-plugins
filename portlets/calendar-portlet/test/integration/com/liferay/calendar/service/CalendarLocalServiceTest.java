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

package com.liferay.calendar.service;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResourceModel;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Miguel Pastor
 */
@RunWith(Arquillian.class)
public class CalendarLocalServiceTest {

	@Test
	public void testAddCalendarResourceCreatesCalendar() throws Exception {
		User user = UserTestUtil.addUser();
		Group group = GroupTestUtil.addGroup();

		long userId = user.getUserId();
		long groupId = group.getGroupId();

		Map<Locale, String> nameMap = new HashMap<>();
		nameMap.put(LocaleUtil.getDefault(), user.getFullName());

		CalendarResourceModel calendarResource =
			CalendarResourceLocalServiceUtil.addCalendarResource(
				userId, groupId, PortalUtil.getClassNameId(Group.class),
				groupId, PortalUUIDUtil.generate(),
				RandomTestUtil.randomString(), nameMap,
				new HashMap<Locale, String>(), true, new ServiceContext());

		List<Calendar> calendars =
			CalendarLocalServiceUtil.getCalendarResourceCalendars(
				groupId, calendarResource.getCalendarResourceId());

		Assert.assertEquals(1, calendars.size());
	}

}