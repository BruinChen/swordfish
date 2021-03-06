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
package com.baifendian.swordfish.webserver.service;

import com.baifendian.swordfish.dao.mapper.ExecutionFlowMapper;
import com.baifendian.swordfish.dao.mapper.ProjectMapper;
import com.baifendian.swordfish.dao.model.ExecutionFlow;
import com.baifendian.swordfish.dao.model.ExecutionFlowError;
import com.baifendian.swordfish.dao.model.ExecutionState;
import com.baifendian.swordfish.dao.model.Project;
import com.baifendian.swordfish.dao.model.User;
import com.baifendian.swordfish.webserver.dto.StatDto;
import com.baifendian.swordfish.webserver.exception.NotFoundException;
import com.baifendian.swordfish.webserver.exception.ParameterException;
import com.baifendian.swordfish.webserver.exception.PermissionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatService {

  private static Logger logger = LoggerFactory.getLogger(StatService.class.getName());

  @Autowired
  private ProjectMapper projectMapper;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private ExecutionFlowMapper executionFlowMapper;

  /**
   * 查询状态信息
   *
   * @param operator
   * @param projectName
   * @param startTime
   * @param endTime
   */
  public List<StatDto> queryStates(User operator, String projectName, long startTime, long endTime) {

    long timeInt = (endTime - startTime) / 86400000;
    if (timeInt > 30) {
      logger.error("time interval > 30: {}", timeInt);
      throw new ParameterException("time \"{0}\" > 30", timeInt);
    }

    Date startDate = new Date(startTime);
    Date endDate = new Date(endTime);

    Project project = projectMapper.queryByName(projectName);
    if (project == null) {
      logger.error("Project does not exist: {}", projectName);
      throw new NotFoundException("Not found project \"{0}\"", projectName);
    }

    // 需要有 project 执行权限
    if (!projectService.hasExecPerm(operator.getId(), project)) {
      logger.error("User {} has no right permission for the project {}", operator.getName(), project.getName());
      throw new PermissionException("User \"{0}\" is not has project \"{1}\" exec permission", operator.getName(), project.getName());
    }

    List<ExecutionState> executionStateList = executionFlowMapper.selectStateByProject(project.getId(), startDate, endDate);

    List<StatDto> statResponseList = new ArrayList<>();

    for (ExecutionState executionState : executionStateList) {
      statResponseList.add(new StatDto(executionState));
    }

    return statResponseList;
  }

  /**
   * 小时维度的查询状态信息
   *
   * @param operator
   * @param projectName
   * @param day
   * @return
   */
  public List<StatDto> queryStatesHour(User operator, String projectName, long day) {
    Date date = new Date(day);

    // 查看是否对项目具备相应的权限
    Project project = projectMapper.queryByName(projectName);
    if (project == null) {
      logger.error("Project does not exist: {}", projectName);
      throw new NotFoundException("Not found project \"{0}\"", projectName);
    }

    if (!projectService.hasExecPerm(operator.getId(), project)) {
      logger.error("User {} has no right permission for the project {}", operator.getName(), project.getName());
      throw new PermissionException("User \"{0}\" is not has project \"{1}\" exec permission", operator.getName(), project.getName());
    }

    List<ExecutionState> executionStateList = executionFlowMapper.selectStateHourByProject(project.getId(), date);

    List<StatDto> statResponseList = new ArrayList<>();

    for (ExecutionState executionState : executionStateList) {
      statResponseList.add(new StatDto(executionState));
    }

    return statResponseList;
  }

  /**
   * 返回查询排行
   *
   * @param operator
   * @param projectName
   * @param date
   * @param num
   */
  public List<ExecutionFlow> queryConsumes(User operator, String projectName, long date, int num) {

    Date datetime = new Date(date);

    Project project = projectMapper.queryByName(projectName);
    if (project == null) {
      logger.error("Project does not exist: {}", projectName);
      throw new NotFoundException("Not found project \"{0}\"", projectName);
    }

    // 必须要有 project 执行权限
    if (!projectService.hasExecPerm(operator.getId(), project)) {
      logger.error("User {} has no right permission for the project {}", operator.getName(), project.getName());
      throw new PermissionException("User \"{0}\" is not has project \"{1}\" exec permission", operator.getName(), project.getName());
    }

    return executionFlowMapper.selectDurationsByProject(project.getId(), num, datetime);
  }

  /**
   * 返回错误的排行信息
   *
   * @param operator
   * @param projectName
   * @param date
   * @param num
   */
  public List<ExecutionFlowError> queryErrors(User operator, String projectName, long date, int num) {
    Date datetime = new Date(date);

    Project project = projectMapper.queryByName(projectName);
    if (project == null) {
      logger.error("Project does not exist: {}", projectName);
      throw new NotFoundException("Not found project \"{0}\"", projectName);
    }

    // 必须有 project 执行权限
    if (!projectService.hasExecPerm(operator.getId(), project)) {
      logger.error("User {} has no right permission for the project {}", operator.getName(), project.getName());
      throw new PermissionException("User \"{0}\" is not has project \"{1}\" exec permission", operator.getName(), project.getName());
    }

    return executionFlowMapper.selectErrorsByProject(project.getId(), num, datetime);
  }
}
