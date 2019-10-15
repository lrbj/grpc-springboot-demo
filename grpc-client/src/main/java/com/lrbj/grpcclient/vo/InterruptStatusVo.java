package com.lrbj.grpcclient.vo;


import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class InterruptStatusVo {
    //申请单号
    private String applicationId;
    // 申请人用户
    private String applyUser;
    // 中断开始时间
    private Long interruptStartTime;
    // 中断截止时间
    private Long interruptEndTime;
    // 延期开始时间
    private Long extensionStartTime;
    // 延期截止时间
    private Long extensionEndTime;
    // 状态是否处于中断过程中
    private boolean isIntrrupted ;
    // 施工地点-园区
    private String locationPark;
    // 施工地点-区域
    private String locationRegion;
    // 施工地点-栋
    private String locationBuilding;

    //TODO 中断项目
    private List<InterruptProjectVo> interruptProjects;

}
