package com.sb.cb.record;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ProductRecord(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("amount") int amount,
        @JsonProperty("active") boolean active
) {

}
