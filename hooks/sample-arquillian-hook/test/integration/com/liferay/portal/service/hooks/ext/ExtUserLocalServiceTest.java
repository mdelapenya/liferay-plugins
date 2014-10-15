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

package com.liferay.portal.service.hooks.ext;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Calendar;
import java.util.Locale;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class ExtUserLocalServiceTest {

	@Test
	public void testContactHasBeenModified() throws Exception {
		User user = _givenThatAUserIsCreated();

		// then

		Contact contact = user.getContact();

		Assert.assertEquals("First Name Changed", contact.getFirstName());
		Assert.assertEquals("Last Name Changed", contact.getLastName());
	}

	private User _givenThatAUserIsCreated() throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "ExtUserLocalServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "ExtUserLocalServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		ServiceContext serviceContext = new ServiceContext();

		String emailAddress = "ExtUserLocalServiceTest@liferay.com";

		String companyName = "company-name";

		String virtualHostname = companyName + ".com";
		String shardDefaultName = GetterUtil.getString(
			PropsUtil.get(PropsKeys.SHARD_DEFAULT_NAME));

		Company company = CompanyLocalServiceUtil.addCompany(
			companyName, virtualHostname, virtualHostname, shardDefaultName,
			false, 0, true);

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			company.getCompanyId());

		UserLocalServiceUtil.addUser(
			defaultUser.getUserId(), company.getCompanyId(), autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			facebookId, openId, locale, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
			sendMail, serviceContext);

		return UserLocalServiceUtil.getUserByEmailAddress(
			company.getCompanyId(), emailAddress);
	}

}