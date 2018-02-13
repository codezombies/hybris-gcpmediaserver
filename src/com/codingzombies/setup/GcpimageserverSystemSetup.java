/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.codingzombies.setup;

import com.codingzombies.constants.GcpimageserverConstants;
import com.codingzombies.service.GCPImageServerService;
import de.hybris.platform.core.initialization.SystemSetup;


@SystemSetup(extension = GcpimageserverConstants.EXTENSIONNAME)
public class GcpimageserverSystemSetup
{
	private final GCPImageServerService gcpimageserverService;

	public GcpimageserverSystemSetup(final GCPImageServerService gcpimageserverService)
	{
		this.gcpimageserverService = gcpimageserverService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		// ignore
	}

}
