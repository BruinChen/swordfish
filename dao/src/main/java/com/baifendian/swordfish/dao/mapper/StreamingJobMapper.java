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
package com.baifendian.swordfish.dao.mapper;

import com.baifendian.swordfish.dao.model.StreamingJob;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.sql.Timestamp;

public interface StreamingJobMapper {

  /**
   * 插入记录并获取记录 id <p>
   *
   * @param job
   * @return 插入记录数
   */
  @InsertProvider(type = StreamingJobProvider.class, method = "insert")
  @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", keyProperty = "flow.id", resultType = int.class, before = false)
  int insertAndGetId(@Param("job") StreamingJob job);

  /**
   * 根据项目名称和
   *
   * @param projectName
   * @param name
   * @return
   */
  @Results(value = {@Result(property = "id", column = "id", id = true, javaType = int.class, jdbcType = JdbcType.INTEGER),
      @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "desc", column = "desc", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "projectId", column = "project_id", javaType = int.class, jdbcType = JdbcType.INTEGER),
      @Result(property = "projectName", column = "project_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.DATE),
      @Result(property = "modifyTime", column = "modify_time", javaType = Timestamp.class, jdbcType = JdbcType.DATE),
      @Result(property = "ownerId", column = "owner_id", javaType = int.class, jdbcType = JdbcType.INTEGER),
      @Result(property = "owner", column = "owner_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "parameter", column = "parameter", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "userDefinedParams", column = "user_defined_params", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "extras", column = "extras", javaType = String.class, jdbcType = JdbcType.VARCHAR),
  })
  @SelectProvider(type = StreamingJobProvider.class, method = "findByProjectNameAndName")
  StreamingJob findByProjectNameAndName(@Param("projectName") String projectName, @Param("name") String name);

  /**
   * 更新流任务的 job
   *
   * @param job
   * @return
   */
  @UpdateProvider(type = StreamingJobProvider.class, method = "updateStreamingJob")
  StreamingJob updateStreamingJob(@Param("job") StreamingJob job);

  /**
   * 删除一个流任务 <p>
   *
   * @param id
   * @return 删除记录数
   */
  @DeleteProvider(type = StreamingJobProvider.class, method = "deleteById")
  int deleteById(@Param("id") int id);

  /**
   * 查询项目中流任务数
   *
   * @param projectId
   * @return
   */
  @SelectProvider(type = StreamingJobProvider.class, method = "selectProjectStreamingCount")
  int selectProjectStreamingCount(@Param("projectId") int projectId);
}
