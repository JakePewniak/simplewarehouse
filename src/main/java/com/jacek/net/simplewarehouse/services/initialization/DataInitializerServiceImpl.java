package com.jacek.net.simplewarehouse.services.initialization;

import javax.transaction.Transactional;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jacek.net.simplewarehouse.dtos.initialization.ParsedDataDto;

/**
 * @author Jacek Niepsuj
 */
@Component
public class DataInitializerServiceImpl implements DataInitializerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializerServiceImpl.class);
    private FileDownloader fileDownloader;
    private DataParser dataParser;
    private DataBaseLoader dataBaseLoader;
    private boolean databaseLoaded = false;

    @Autowired
    public DataInitializerServiceImpl(FileDownloader fileDownloader, DataParser dataParser, DataBaseLoader dataBaseLoader) {
        this.fileDownloader = fileDownloader;
        this.dataParser = dataParser;
        this.dataBaseLoader = dataBaseLoader;
    }

    @Override
    @Transactional
    public void init() {
        LOGGER.info("Starting DataInitializer...");
        if (databaseLoaded) {
            LOGGER.info("Database loading skipped because data are already initialized.");
            return;
        }
        byte[] csvContent = fileDownloader.downloadDataFile();
        List<ParsedDataDto> parsedDatas = dataParser.parse(csvContent);
        dataBaseLoader.loadDataToDB(parsedDatas);
        databaseLoaded = true;
    }
}
