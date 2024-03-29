package com.db.dbhackathonapi.Tables;


import com.db.dbhackathonapi.interfaces.Activity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;


@ToString
@Setter
@Getter
@Entity // This tells Hibernate to make a table out of this class
public class ElectricityConsumption extends Activity {
    @Id
    private int id;

    private String userEmail;
    private String applianceType;
    private int durationMinutes;
    private int contributors;
    private int ghgFootprint;
    private Timestamp timestamp;


}