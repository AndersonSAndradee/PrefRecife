package br.com.chamadosprefrec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TicketStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    private Long StatsId;

    @Column(nullable = false)
    private String state;


    //Get and setters 
    public Long getStatsId() {
        return StatsId;
    }

    public void setStatsId(Long statsId) {
        StatsId = statsId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
