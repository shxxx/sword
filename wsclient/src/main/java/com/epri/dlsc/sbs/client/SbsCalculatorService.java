package com.epri.dlsc.sbs.client;

public interface SbsCalculatorService extends javax.xml.rpc.Service {
    public java.lang.String getSbsCalculatorServiceHttpSoap11EndpointAddress();

    public com.epri.dlsc.sbs.client.SbsCalculatorServicePortType getSbsCalculatorServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException;

    public com.epri.dlsc.sbs.client.SbsCalculatorServicePortType getSbsCalculatorServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
