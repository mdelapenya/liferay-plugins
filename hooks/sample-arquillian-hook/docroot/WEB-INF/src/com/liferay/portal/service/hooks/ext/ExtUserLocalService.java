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

import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceWrapper;
import com.liferay.portal.service.UserLocalService;

/**
 * @author Manuel de la Peña
 */
public class ExtUserLocalService extends UserLocalServiceWrapper {

	/* (non-Java-doc)
	 * @see com.liferay.portal.service.UserLocalServiceWrapper#UserLocalServiceWrapper(UserLocalService userLocalService)
	 */
	public ExtUserLocalService(UserLocalService userLocalService) {
		super(userLocalService);
	}

	@Override
	public User addUser(long creatorUserId,
			long companyId, boolean autoPassword, java.lang.String password1,
			java.lang.String password2, boolean autoScreenName,
			java.lang.String screenName, java.lang.String emailAddress,
			long facebookId, java.lang.String openId, java.util.Locale locale,
			java.lang.String firstName, java.lang.String middleName,
			java.lang.String lastName, int prefixId, int suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, long[] userGroupIds, boolean sendEmail,
			com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {

		return super.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, "First Name Changed", middleName, "Last Name Changed",
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
			sendEmail, serviceContext);
	}

}