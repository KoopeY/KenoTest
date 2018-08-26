package ru.koopey.test_keno.entity;

import lombok.Data;
import ru.koopey.test_keno.enums.RateStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "rates")
@Data
public class Rate implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "calculated_date")
    private Date calculatedDate;

    @Column(name = "status")
    private RateStatus status;

    @Column(name = "balls")
    private String balls;

    @Column(name = "playerId")
    private String playerId;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "win")
    private Integer win;

    @Column(name = "round")
    private Long round;

    @Column(name = "uuid")
    private String uuid;
}
