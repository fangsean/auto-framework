package com.auto.common.struct.common.schedule.model;

import lombok.Data;

import java.util.Date;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * @Description: <p></p>
 */
@Data
public class ScheduleJob {
    private static final Long serialVersionUID = 1435515995276255188L;

    private Long id;

    private String className;

    private String cronExpression;

    private String jobName;

    private String jobGroup;

    private String triggerName;

    private String triggerGroup;

    private Boolean pause;

    private Boolean enable;

    private String description;

    private Date createTime;

    private Date lastUpdateTime;
}
