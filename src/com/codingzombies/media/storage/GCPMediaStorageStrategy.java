package com.codingzombies.media.storage;

import com.codingzombies.service.GCPImageServerService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import de.hybris.platform.media.exceptions.MediaStoreException;
import de.hybris.platform.media.services.MediaHeadersRegistry;
import de.hybris.platform.media.services.MediaLocationHashService;
import de.hybris.platform.media.services.MimeService;
import de.hybris.platform.media.storage.MediaStorageConfigService;
import de.hybris.platform.media.storage.MediaStorageStrategy;
import de.hybris.platform.media.storage.impl.LocalFileMediaStorageStrategy;
import de.hybris.platform.media.storage.impl.StoredMediaData;
import de.hybris.platform.util.Config;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class GCPMediaStorageStrategy implements MediaStorageStrategy {

    private static final Logger logger = LoggerFactory.getLogger(GCPMediaStorageStrategy.class);

    private GCPImageServerService gcpImageServerService;
    private MediaLocationHashService mediaLocationHashService;
    private MediaHeadersRegistry mediaHeadersRegistry;
    private MimeService mimeService;

    @Override
    public StoredMediaData store(MediaStorageConfigService.MediaFolderConfig mediaFolderConfig, String mediaId, Map<String, Object> metaData, InputStream inputStream) {
        final String mime = (String)metaData.get("mime");
        try {
            final Bucket bucket = gcpImageServerService.getBucket(mediaFolderConfig);
            final int available = inputStream.available();
            final String filename = getFileName(mediaId, mime);

            Blob created = bucket.create(filename,
                            inputStream, mime, Bucket.BlobWriteOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ)
                        ).toBuilder()
                            .setMetadata(mediaHeadersRegistry.getHeaders())
                            .build().update();

            String hashForLocation =   mediaLocationHashService.createHashForLocation(mediaFolderConfig.getFolderQualifier(), mediaId);
            return new StoredMediaData(created.getName(), hashForLocation, available, mime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        throw new MediaStoreException("Media '"+ mediaId +"' cannot be stored in Google Cloud Storage.");
    }

    private String getFileName(String mediaId, String mime) {
        String fileExtension = this.mimeService.getBestExtensionFromMime(mime);
        String fileName = StringUtils.isNotBlank(fileExtension) ? mediaId + '.' + fileExtension : mediaId;
        return fileName;
    }

    @Override
    public void delete(MediaStorageConfigService.MediaFolderConfig mediaFolderConfig, String location) {
        Bucket bucket = gcpImageServerService.getBucket(mediaFolderConfig);
        Blob blob = bucket.get(location);
        if(blob == null) {
            logger.error("Cannot find blob with location of: " + location);
        }
        blob.delete();
    }

    @Override
    public InputStream getAsStream(MediaStorageConfigService.MediaFolderConfig mediaFolderConfig, String location) {
        throw new UnsupportedOperationException("getAsStream() not supported for GCP");
    }

    @Override
    public File getAsFile(MediaStorageConfigService.MediaFolderConfig mediaFolderConfig, String location) {
        throw new UnsupportedOperationException("getAsFile() not supported for GCP");
    }

    @Override
    public long getSize(MediaStorageConfigService.MediaFolderConfig mediaFolderConfig, String location) {
        return 0L;
    }

    public void setGcpImageServerService(GCPImageServerService gcpImageServerService) {
        this.gcpImageServerService = gcpImageServerService;
    }

    public void setMediaLocationHashService(MediaLocationHashService mediaLocationHashService) {
        this.mediaLocationHashService = mediaLocationHashService;
    }

    public void setMimeService(MimeService mimeService) {
        this.mimeService = mimeService;
    }

    public void setMediaHeadersRegistry(MediaHeadersRegistry mediaHeadersRegistry) {
        this.mediaHeadersRegistry = mediaHeadersRegistry;
    }
}
