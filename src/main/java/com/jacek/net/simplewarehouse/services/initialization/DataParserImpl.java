package com.jacek.net.simplewarehouse.services.initialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jacek.net.simplewarehouse.dtos.initialization.ParsedDataDto;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * @author Jacek Niepsuj
 */
@Service
public class DataParserImpl implements DataParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataParserImpl.class);
    private static final String PARSE_ERROR = "Can not parse data from file. Please check this file.";
    private static final String IO_ERROR = "Can not parse data - problem with reading file content.";
    private static final String DATE_PARSE_ERROR = "Can not parse date field from. Please check this file.";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH);

    private static int DATASOURCE = 0;
    private static int CAMPAIGN = 1;
    private static int DAILY = 2;
    private static int CLICKS = 3;
    private static int IMPRESSIONS = 4;

    @Override
    public List<ParsedDataDto> parse(byte[] csvContent) {
        List<ParsedDataDto> result = new ArrayList<>();
        String[] singleLineValues;
        InputStream in = new ByteArrayInputStream(csvContent);
        InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
        try (CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build()) {
            while ((singleLineValues = csvReader.readNext()) != null) {
                result.add(getSingleLineData(singleLineValues));
            }
            LOGGER.info("Parsed: " + result.size() + " entries.");
            return result;
        }  catch (IOException ex) {
            LOGGER.error(IO_ERROR);
            throw new RuntimeException(IO_ERROR);
        }
    }

    private ParsedDataDto getSingleLineData(String[] data) {
        try {
            return ParsedDataDto.builder()
                    .datasource(data[DATASOURCE])
                    .campaign(data[CAMPAIGN])
                    .daily(parseDate(data[DAILY]))
                    .clicks(Long.parseLong(data[CLICKS]))
                    .impressions(Long.parseLong(data[IMPRESSIONS]))
                    .build();
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            LOGGER.error(PARSE_ERROR, e);
            throw new RuntimeException(PARSE_ERROR);
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            LOGGER.error(DATE_PARSE_ERROR, e);
            throw new RuntimeException(DATE_PARSE_ERROR);
        }
    }

}
