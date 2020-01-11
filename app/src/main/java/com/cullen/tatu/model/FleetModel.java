package com.cullen.tatu.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

@Entity
public class FleetModel implements Serializable {

    static final long serialVersionUID  = 1L;

    @Id(autoincrement = false)
    @Property(nameInDb = "ID")
    private Long id;

    @Property(nameInDb = "DEPARTURE")
    private String departure;

    @Property(nameInDb = "DESTINATION")
    private String destination;

    @Property(nameInDb = "PHONE")
    private String phone;

    @Property(nameInDb = "CONTACT")
    private String contact;

    @Property(nameInDb = "COST")
    private Double cost;

    @Property(nameInDb = "DEPARTURETIME")
    private Long departureTime;

    @Property(nameInDb = "VACANCY")
    private Integer vacancy;

    @Property(nameInDb = "CREATEBY")
    private Long createBy;

    @Property(nameInDb = "CARNUM")
    private String carNum;

    @Property(nameInDb = "DESCRIPTION")
    private String description;


    @Generated(hash = 902441847)
    public FleetModel(Long id, String departure, String destination, String phone,
            String contact, Double cost, Long departureTime, Integer vacancy,
            Long createBy, String carNum, String description) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.phone = phone;
        this.contact = contact;
        this.cost = cost;
        this.departureTime = departureTime;
        this.vacancy = vacancy;
        this.createBy = createBy;
        this.carNum = carNum;
        this.description = description;
    }

    @Generated(hash = 1472456733)
    public FleetModel() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Long departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getVacancy() {
        return vacancy;
    }

    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
