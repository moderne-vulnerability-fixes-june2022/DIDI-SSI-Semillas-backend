package com.atixlabs.semillasmiddleware.app.model.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@ToString
@Table
public class ActionLog {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime executionDateTime;

    @Column(length = 50)
    private  String userName;

    private ActionLevelEnum level;

    @Column(nullable = false, length = 8 )
    @Enumerated(value = EnumType.STRING)
    private ActionLevelEnum levelDescription;

    private ActionTypeEnum actionType;

    @Column(nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    private ActionTypeEnum actionTypeDescription;

    @Column(columnDefinition="TEXT")
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExecutionDateTime() {
        return executionDateTime;
    }

    public void setExecutionDateTime(LocalDateTime executionDateTime) {
        this.executionDateTime = executionDateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ActionLevelEnum getLevel() {
        return level;
    }

    public void setLevel(ActionLevelEnum level) {
        this.level = level;
        this.levelDescription = level;
    }


    public ActionTypeEnum getActionType() {
        return actionType;
    }

    public void setActionType(ActionTypeEnum actionType) {
        this.actionType = actionType;
        this.actionTypeDescription = actionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
