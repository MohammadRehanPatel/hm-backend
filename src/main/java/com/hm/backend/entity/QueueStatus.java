package com.hm.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "queue_statuses")
public class QueueStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    @Column(name = "wait_time")
    private int waitTime;

    @Column(name = "doctor_available")
    private boolean doctorAvailable;

    @Column(name = "queue_position")
    private int queuePosition;

    public QueueStatus(Queue queue, int waitTime, boolean doctorAvailable, int queuePosition) {
        this.queue = queue;
        this.waitTime = waitTime;
        this.doctorAvailable = doctorAvailable;
        this.queuePosition = queuePosition;
    }
}
