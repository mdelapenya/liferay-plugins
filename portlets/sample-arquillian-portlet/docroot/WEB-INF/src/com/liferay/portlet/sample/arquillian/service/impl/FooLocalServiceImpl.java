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

package com.liferay.portlet.sample.arquillian.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.sample.arquillian.model.Foo;
import com.liferay.portlet.sample.arquillian.model.impl.FooImpl;
import com.liferay.portlet.sample.arquillian.service.base.FooLocalServiceBaseImpl;

import java.util.Date;

/**
 * The implementation of the foo local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.sample.arquillian.service.FooLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Manuel de la Peña
 * @see com.liferay.portlet.sample.arquillian.service.base.FooLocalServiceBaseImpl
 * @see com.liferay.portlet.sample.arquillian.service.FooLocalServiceUtil
 */
public class FooLocalServiceImpl extends FooLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.portlet.sample.arquillian.service.FooLocalServiceUtil} to access the foo local service.
	 */

	public Foo concat(long fooOneId, long fooTwoId) throws SystemException {
		Foo fooOne = fooLocalService.fetchFoo(fooOneId);
		Foo fooTwo = fooLocalService.fetchFoo(fooTwoId);

		return concat(fooOne, fooTwo);
	}

	public Foo concat(Foo one, Foo two) throws SystemException {
		Foo result = new FooImpl();

		result.setField1(one.getField1() + two.getField1());
		result.setField2(one.getField2() || two.getField2());
		result.setField3(one.getField3() + two.getField3());

		Date resultDate = new Date(
			one.getField4().getTime() + two.getField4().getTime());

		result.setField4(resultDate);
		result.setField5(one.getField5() + two.getField5());

		return result;
	}

}