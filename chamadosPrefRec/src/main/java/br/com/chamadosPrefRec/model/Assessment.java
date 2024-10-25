package br.com.chamadosprefrec.model;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Long assessmentId;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false, referencedColumnName = "ticket_id")
    private Ticket ticket;

    @Column(nullable = false)
    private String averageResolutionTime;

    @Column(nullable = false)
    private String qualityService;
    
    @Column(length = 250)
    private String comment;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    

    // Getters and Setters

    public Long getAssessmentId() {
        return assessmentId;
    }


    public Ticket getTicket() {
        return ticket;
    }


    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


    public String getAverageResolutionTime() {
        return averageResolutionTime;
    }


    public void setAverageResolutionTime(String averageResolutionTime) {
        this.averageResolutionTime = averageResolutionTime;
    }


    public String getQualityService() {
        return qualityService;
    }


    public void setQualityService(String qualityService) {
        this.qualityService = qualityService;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public LocalDate getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    // Builder

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
    }
}
