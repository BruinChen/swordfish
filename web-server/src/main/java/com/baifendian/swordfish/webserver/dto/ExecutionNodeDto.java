/*
 * Copyright (C) 2017 Baifendian Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baifendian.swordfish.webserver.dto;

import com.baifendian.swordfish.common.hadoop.ConfigurationUtil;
import com.baifendian.swordfish.common.json.JsonOrdinalSerializer;
import com.baifendian.swordfish.dao.enums.FlowStatus;
import com.baifendian.swordfish.dao.model.ExecutionNode;
import com.baifendian.swordfish.dao.utils.json.JsonObjectDeserializer;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 * 工作流调度节点返回结果
 */
public class ExecutionNodeDto {

  private String name;
  private String desc;
  private String type;
  private Date startTime;
  private Date endTime;
  @JsonRawValue
  @JsonDeserialize(using = JsonObjectDeserializer.class)
  private String parameter;
  private int duration;
  @JsonSerialize(using = JsonOrdinalSerializer.class)
  private FlowStatus status;
  private List<String> appLinks;
  private String jobId;
  private List<String> dep;
  @JsonRawValue
  @JsonDeserialize(using = JsonObjectDeserializer.class)
  private String extras;

  public ExecutionNodeDto() {
  }

  /**
   * 与 executionNode 实体的信息合并
   */
  public void mergeExecutionNode(ExecutionNode executionNode) {
    if (executionNode != null) {
      this.startTime = executionNode.getStartTime();
      this.endTime = executionNode.getEndTime();

      this.duration = (startTime == null) ? 0 :
          Math.toIntExact((
              (endTime == null) ? System.currentTimeMillis() - startTime
                  .getTime() : endTime.getTime() - startTime.getTime()) / 1000);

      this.status = executionNode.getStatus();

      // link 需要添加前缀
      List<String> appIds = executionNode.getAppLinkList();

      if (CollectionUtils.isNotEmpty(appIds)) {
        this.appLinks = new ArrayList<>();

        for (String appId : appIds) {
          this.appLinks.add(ConfigurationUtil.getWebappAddress(appId));
        }
      }

      this.jobId = executionNode.getJobId();
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public FlowStatus getStatus() {
    return status;
  }

  public void setStatus(FlowStatus status) {
    this.status = status;
  }

  public List<String> getAppLinks() {
    return appLinks;
  }

  public void setAppLinks(List<String> appLinks) {
    this.appLinks = appLinks;
  }

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public List<String> getDep() {
    return dep;
  }

  public void setDep(List<String> dep) {
    this.dep = dep;
  }

  public String getExtras() {
    return extras;
  }

  public void setExtras(String extras) {
    this.extras = extras;
  }
}
