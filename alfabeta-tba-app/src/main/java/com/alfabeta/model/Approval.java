package com.alfabeta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "approvals")
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "claim_id")
    private Claim claim;

    @Size(max = 120)
    @Column(name = "approver_name", length = 120)
    private String approverName;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "approval_date")
    private Instant approvalDate;

    @Size(max = 50)
    @ColumnDefault("'Under Review'")
    @Column(name = "approval_status", length = 50)
    private String approvalStatus;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;

}