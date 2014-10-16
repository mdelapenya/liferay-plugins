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

import com.liferay.portlet.sample.arquillian.model.Foo;
import com.liferay.portlet.sample.arquillian.model.impl.FooImpl;
import com.liferay.portlet.sample.arquillian.service.FooLocalServiceUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class FooLocalServiceImplTest {

	@Test
	public void testConcat() {
		Foo one = _createFoo("1", true, 1, 1, "1");
		Foo two = _createFoo("2", false, 2, 2, "2");

		Foo result = FooLocalServiceUtil.concat(one, two);

		Assert.assertEquals("12", result.getField1());
		Assert.assertEquals(true, result.getField2());
		Assert.assertEquals(3, result.getField3());
		Assert.assertEquals(new Date(3), result.getField4());
		Assert.assertEquals("12", result.getField5());
	}

	@Test
	public void testConcatByFooId() {
		Foo one = FooLocalServiceUtil.addFoo(
			_createFoo("1", true, 1, 1, "1"));

		Foo two = FooLocalServiceUtil.addFoo(
			_createFoo("2", false, 2, 2, "2"));

		Foo result = FooLocalServiceUtil.concat(one.getFooId(), two.getFooId());

		Assert.assertEquals("12", result.getField1());
		Assert.assertEquals(true, result.getField2());
		Assert.assertEquals(3, result.getField3());
		Assert.assertEquals(new Date(3), result.getField4());
		Assert.assertEquals("12", result.getField5());
	}

	private Foo _createFoo(
		String field1, boolean field2, int field3, long time, String field5) {

		Foo foo = new FooImpl();

		foo.setField1(field1);
		foo.setField2(field2);
		foo.setField3(field3);
		foo.setField4(new Date(time));
		foo.setField5(field5);

		return foo;
	}

}