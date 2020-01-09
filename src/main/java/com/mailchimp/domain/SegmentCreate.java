package com.mailchimp.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author eamoralesl
 */
//TODO Implement dynamic segment creation  http://developer.mailchimp.com/documentation/mailchimp/reference/lists/segments/#%20
@Data
public class SegmentCreate {
    @JsonProperty("name")
    private String name;
    @JsonProperty("static_segment")
    private List<String> staticSegment = new ArrayList<>();

}
