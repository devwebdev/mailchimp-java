package com.mailchimp.jackson;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author stevensnoeijen
 */
public final class MailChimpZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public ZonedDateTime deserialize(final JsonParser parser, final DeserializationContext context)
            throws IOException, JsonProcessingException {
        final String stringDate = parser.getText();
        if (stringDate == null || stringDate.isEmpty()) {
            return null;
        }
        try {
            ZonedDateTime date = ZonedDateTime.parse(stringDate, dateTimeFormatter);
            return date;
        } catch (DateTimeParseException ex) {
            //when format is incorrect set to null
            return null;
        }

    }

}
