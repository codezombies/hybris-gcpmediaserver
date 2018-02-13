package com.codingzombies.media.url.impl;

import com.codingzombies.service.GCPImageServerService;
import de.hybris.platform.media.MediaSource;
import de.hybris.platform.media.storage.MediaStorageConfigService;
import de.hybris.platform.media.url.MediaURLStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GCPMediaWebURLStrategy implements MediaURLStrategy {

    private static final Logger logger = LoggerFactory.getLogger(GCPMediaWebURLStrategy.class);

    private GCPImageServerService gcpImageServerService;

    @Override
    public String getUrlForMedia(MediaStorageConfigService.MediaFolderConfig mediaFolderConfig, MediaSource mediaSource) {
        if(mediaFolderConfig.getStorageStrategyId().equals("gcpMediaStorageStrategy")) {
            return gcpImageServerService.getBucket(mediaFolderConfig).get(mediaSource.getLocation()).getMediaLink();
        }

        logger.error("MediaSource: {} is not configured to use gcpMediaStorageStrategy", mediaSource.getLocation());
        return "";
    }


    public void setGcpImageServerService(GCPImageServerService gcpImageServerService) {
        this.gcpImageServerService = gcpImageServerService;
    }
}
