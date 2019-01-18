package com.epri.dlsc.sbs.calc.service

object SubmitTest {
  def main(args: Array[String]): Unit = {


    val start = System.currentTimeMillis()

    val ids = Array(
      "33786f06454898b13f57389387afbb1",
      "dc115c669ab7f83e18c8e83084ef9d70",
      "29f85eba1f0d3efcf2ac80eaf6b21766",
      "8741626feeb868dbb00f89ac9d0f9b6f",
      "107fa36778fbef446475b5e3b79126ce",
      "31de374be2a7f0e1c8ae5fc504aed3ca")
    val params = ScriptParams.add("datetime", "2018-01-15")
      .add("taskid", "dddddd")
      .add("tradeseqid", "20180115000001")
      .add("starttime", "2018-01-15 00:00:00")
      .add("endtime", "2018-01-15 23:45:00")

    SettleSession.getOrCreate
        .config("95518", SourceTarget("3", "3"),  params,  Array("107fa36778fbef446475b5e3b79126ce"))
        .submit()
//  SettleSession.getOrCreate
//        .config(marketId, SourceTarget(sourceFlag, resultFlag),  paramsObj, new SimpleDateFormat("yyyyMMdd").parse(calcDateTime), formulaTypeOrIds)
//        .submit()

    println((System.currentTimeMillis() - start)/1000 + "s")


  }
}

