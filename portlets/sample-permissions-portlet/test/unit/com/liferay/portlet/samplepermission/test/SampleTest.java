/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.samplepermission.test;

import com.liferay.portal.kernel.servlet.ServletContextPool;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class SampleTest {

	public static TemporaryFolder testFolder = new TemporaryFolder();

	@Deployment
	public static Archive<?> getGetDeployment() throws IOException {

		try {
			testFolder.create();

			File buildFile = new File("build.xml");

			Project project = new Project();

			project.setUserProperty("ant.file", buildFile.getAbsolutePath());

			project.init();

			ProjectHelper helper = ProjectHelper.getProjectHelper();

			project.addReference("ant.projectHelper", helper);

			helper.parse(project, buildFile);

			project.setProperty("app.server.deploy.dir",
				testFolder.getRoot().getAbsolutePath());
			project.setProperty("auto.deploy.unpack.war", "false");

			project.executeTarget("direct-deploy");

			File tempFile = new File(
				testFolder.getRoot().getAbsolutePath(),
				project.getProperty("plugin.name") + ".war");

			return ShrinkWrap.createFromZipFile(
				WebArchive.class, tempFile);
		}
		finally {
			testFolder.delete();
		}

	}

	@Test
	public void test1() {
		System.out.println(ServletContextPool.keySet());
	}
}
