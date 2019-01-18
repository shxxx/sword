package com.epri.dlsc.sbs.client;

public class SbsCalculatorServicePortTypeProxy implements com.epri.dlsc.sbs.client.SbsCalculatorServicePortType {
    private String _endpoint = null;
    private com.epri.dlsc.sbs.client.SbsCalculatorServicePortType sbsCalculatorServicePortType = null;

    public SbsCalculatorServicePortTypeProxy() {
        _initSbsCalculatorServicePortTypeProxy();
    }

    public SbsCalculatorServicePortTypeProxy(String endpoint) {
        _endpoint = endpoint;
        _initSbsCalculatorServicePortTypeProxy();
    }

    private void _initSbsCalculatorServicePortTypeProxy() {
        try {
            sbsCalculatorServicePortType = (new com.epri.dlsc.sbs.client.SbsCalculatorServiceLocator()).getSbsCalculatorServiceHttpSoap11Endpoint();
            if (sbsCalculatorServicePortType != null) {
                if (_endpoint != null)
                    ((javax.xml.rpc.Stub)sbsCalculatorServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                else
                    _endpoint = (String)((javax.xml.rpc.Stub)sbsCalculatorServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
            }

        }
        catch (javax.xml.rpc.ServiceException serviceException) {}
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (sbsCalculatorServicePortType != null)
            ((javax.xml.rpc.Stub)sbsCalculatorServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

    }

    public com.epri.dlsc.sbs.client.SbsCalculatorServicePortType getSbsCalculatorServicePortType() {
        if (sbsCalculatorServicePortType == null)
            _initSbsCalculatorServicePortTypeProxy();
        return sbsCalculatorServicePortType;
    }

    public java.lang.String test() throws java.rmi.RemoteException{
        if (sbsCalculatorServicePortType == null)
            _initSbsCalculatorServicePortTypeProxy();
        return sbsCalculatorServicePortType.test();
    }

    public java.lang.String logPrint() throws java.rmi.RemoteException{
        if (sbsCalculatorServicePortType == null)
            _initSbsCalculatorServicePortTypeProxy();
        return sbsCalculatorServicePortType.logPrint();
    }

    public java.lang.String requestCalculatorByFormulaType(java.lang.String marketId, java.lang.String formulaType, java.util.Date dateTime, java.lang.String source, java.lang.String result, com.epri.dlsc.sbs.client.Entry1[] scriptParams) throws java.rmi.RemoteException{
        if (sbsCalculatorServicePortType == null)
            _initSbsCalculatorServicePortTypeProxy();
        return sbsCalculatorServicePortType.requestCalculatorByFormulaType(marketId, formulaType, dateTime, source, result, scriptParams);
    }

    public java.lang.String requestCalculatorByFormulaIds(java.lang.String marketId, java.lang.String[] formulaIds, java.util.Date dateTime, java.lang.String source, java.lang.String result, com.epri.dlsc.sbs.client.Entry2[] scriptParams) throws java.rmi.RemoteException{
        if (sbsCalculatorServicePortType == null)
            _initSbsCalculatorServicePortTypeProxy();
        return sbsCalculatorServicePortType.requestCalculatorByFormulaIds(marketId, formulaIds, dateTime, source, result, scriptParams);
    }


}
