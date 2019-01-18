package com.epri.dlsc.sbs.client;

public interface SbsCalculatorServicePortType extends java.rmi.Remote {
    public java.lang.String test() throws java.rmi.RemoteException;
    public java.lang.String logPrint() throws java.rmi.RemoteException;
    public java.lang.String requestCalculatorByFormulaType(java.lang.String marketId, java.lang.String formulaType, java.util.Date dateTime, java.lang.String source, java.lang.String result, com.epri.dlsc.sbs.client.Entry1[] scriptParams) throws java.rmi.RemoteException;
    public java.lang.String requestCalculatorByFormulaIds(java.lang.String marketId, java.lang.String[] formulaIds, java.util.Date dateTime, java.lang.String source, java.lang.String result, com.epri.dlsc.sbs.client.Entry2[] scriptParams) throws java.rmi.RemoteException;
}

