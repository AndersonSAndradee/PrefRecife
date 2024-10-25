package br.com.chamadosprefrec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long idImage;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false, referencedColumnName = "ticket_id")
    private Ticket ticket;
    
    @Column(length = 350 )
    private String Url;

    // Getters and setters
    
    public Long getIdImage() {
        return idImage;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
