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
package com.baifendian.swordfish.common.datasource.hbase;

public class HBaseParam {
  private String zkQuorum;

  private String zkZnodeParent;

  private int zkPort;

  public String getZkQuorum() {
    return zkQuorum;
  }

  public void setZkQuorum(String zkQuorum) {
    this.zkQuorum = zkQuorum;
  }

  public String getZkZnodeParent() {
    return zkZnodeParent;
  }

  public void setZkZnodeParent(String zkZnodeParent) {
    this.zkZnodeParent = zkZnodeParent;
  }

  public int getZkPort() {
    return zkPort;
  }

  public void setZkPort(int zkPort) {
    this.zkPort = zkPort;
  }
}
