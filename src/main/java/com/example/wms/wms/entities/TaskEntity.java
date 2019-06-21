package com.example.wms.wms.entities;

import com.example.wms.wms.base.BaseType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "task")
    String task;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    BaseType.PriorityOfExecution priority;

    @Column(name = "created")
    Date created;

    @Column(name = "done")
    Date done;
}
