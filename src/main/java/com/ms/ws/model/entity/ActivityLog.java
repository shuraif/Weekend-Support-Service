package com.ms.ws.model.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ActivityLog")
@Getter @Setter
public class ActivityLog {

  @Id
  private String id; // Unique identifier for the history record
  private String userId; // User who performed the action
  private String action; // e.g., "add", "update", "delete"
  private LocalDateTime timestamp; // Timestamp of the action
  private String details; // JSON string or description of the action performed

}
