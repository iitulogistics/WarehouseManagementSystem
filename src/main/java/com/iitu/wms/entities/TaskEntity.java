package com.iitu.wms.entities;

import com.iitu.wms.base.BaseType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "task")
    private
    String task;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private
    BaseType.PriorityOfExecution priority;

    @Column(name = "created")
    private Date created;

    @Column(name = "done")
    private Date done;

    @JoinColumn(name = "usr_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public BaseType.PriorityOfExecution getPriority() {
        return priority;
    }

    public void setPriority(BaseType.PriorityOfExecution priority) {
        this.priority = priority;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDone() {
        return done;
    }

    public void setDone(Date done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
