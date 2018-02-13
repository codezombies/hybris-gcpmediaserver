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
package com.codingzombies.service;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import de.hybris.platform.media.storage.MediaStorageConfigService;
import de.hybris.platform.util.Config;

public interface GCPImageServerService
{

    default Storage getStorage() {
        return StorageOptions.getDefaultInstance().getService();
    }

    default Bucket getBucket(MediaStorageConfigService.MediaFolderConfig mediaFolderConfig) {
        Storage storage = getStorage();
        return storage.get(Config.getString("gcp.folder." + mediaFolderConfig.getFolderQualifier() + ".bucket.name", "images"));
    }

}
