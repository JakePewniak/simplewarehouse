package com.jacek.net.simplewarehouse.services.initialization;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Jacek Niepsuj
 */
@Service
public class FileDownloaderImpl implements FileDownloader {

    @Value("${data.file.url}")
    private String FILE_URL;

    private String DOWNLOAD_ERROR = "Failed to download an init data file";

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloaderImpl.class);

    @Override
    public byte[] downloadDataFile() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
             ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bos.write(dataBuffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            LOGGER.error(DOWNLOAD_ERROR, e);
            throw new RuntimeException(DOWNLOAD_ERROR);
        }
    }
}
