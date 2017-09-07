package com.baifendian.swordfish.server.sparksql.service;

import com.baifendian.swordfish.rpc.AdhocResultInfo;
import com.baifendian.swordfish.rpc.RetInfo;
import com.baifendian.swordfish.rpc.SparkSqlService.Iface;
import com.baifendian.swordfish.rpc.UdfInfo;
import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkSqlServiceImpl implements Iface {

  private static Logger logger = LoggerFactory.getLogger(SparkSqlServiceImpl.class);

  private RunnerManager runnerManager = new RunnerManager();

  public List<String> createUdf(List<UdfInfo> udfs){
    List<String> udfCmds = new ArrayList<>();

    return udfCmds;
  }

  @Override
  public RetInfo execEtl(String jobId, List<UdfInfo> udfs, int remainTime) throws TException {
    long endTime = System.currentTimeMillis() + remainTime * 1000L;

    //return runnerManager.executeEtlSql(createUdf(udfs), );
    return null;
  }

  @Override
  public RetInfo execAdhoc(String jobId, List<UdfInfo> udfs, int queryLimit, int remainTime)
      throws TException {
    return null;
  }

  @Override
  public RetInfo cancelExecFlow(String jobId) throws TException {
    return null;
  }

  @Override
  public AdhocResultInfo getAdhocResult(String jobId) throws TException {
    return null;
  }
}