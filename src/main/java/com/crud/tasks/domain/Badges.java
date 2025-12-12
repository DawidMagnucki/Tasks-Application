package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Badges {

    private int votes;

    @JsonProperty("attachmentsByType")
    private AttachmentsByType attachments;
}
