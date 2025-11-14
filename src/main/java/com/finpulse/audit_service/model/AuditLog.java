package com.finpulse.audit_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "audit_logs",
        indexes = {
                @Index(name = "ix_audit_user_id", columnList = "user_id"),
                @Index(name = "ix_audit_action", columnList = "action"),
                @Index(name = "ix_audit_entity_type", columnList = "entity_type"),
                @Index(name = "ix_audit_created_at", columnList = "created_at DESC")
        }
)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "user_id")
    private Long userId;  // reference to Auth Service (no FK)

    @Column(name = "action", nullable = false, length = 150)
    private String action;  // e.g., "TRANSACTION_CREATED"

    @Column(name = "entity_type", length = 100)
    private String entityType;  // e.g., "ACCOUNT", "KYC_DOCUMENT"

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    public enum AuditStatus { SUCCESS, FAILED }
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AuditStatus status;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details; // can store JSON or long text

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
