package com.epri.dlsc.sbs.client;

public class SbsCalculatorServiceLocator extends org.apache.axis.client.Service implements com.epri.dlsc.sbs.client.SbsCalculatorService {

    public SbsCalculatorServiceLocator() {
    }


    public SbsCalculatorServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SbsCalculatorServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SbsCalculatorServiceHttpSoap11Endpoint
    private java.lang.String SbsCalculatorServiceHttpSoap11Endpoint_address = "http://node1:9000/axis2/services/SbsCalculatorService.SbsCalculatorServiceHttpSoap11Endpoint/";

    public java.lang.String getSbsCalculatorServiceHttpSoap11EndpointAddress() {
        return SbsCalculatorServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SbsCalculatorServiceHttpSoap11EndpointWSDDServiceName = "SbsCalculatorServiceHttpSoap11Endpoint";

    public java.lang.String getSbsCalculatorServiceHttpSoap11EndpointWSDDServiceName() {
        return SbsCalculatorServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setSbsCalculatorServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        SbsCalculatorServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.epri.dlsc.sbs.client.SbsCalculatorServicePortType getSbsCalculatorServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SbsCalculatorServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSbsCalculatorServiceHttpSoap11Endpoint(endpoint);
    }

    public com.epri.dlsc.sbs.client.SbsCalculatorServicePortType getSbsCalculatorServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.epri.dlsc.sbs.client.SbsCalculatorServiceSoap11BindingStub _stub = new com.epri.dlsc.sbs.client.SbsCalculatorServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getSbsCalculatorServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSbsCalculatorServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        SbsCalculatorServiceHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.epri.dlsc.sbs.client.SbsCalculatorServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.epri.dlsc.sbs.client.SbsCalculatorServiceSoap11BindingStub _stub = new com.epri.dlsc.sbs.client.SbsCalculatorServiceSoap11BindingStub(new java.net.URL(SbsCalculatorServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getSbsCalculatorServiceHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SbsCalculatorServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getSbsCalculatorServiceHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.sbs.dlsc.epri.com", "SbsCalculatorService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.sbs.dlsc.epri.com", "SbsCalculatorServiceHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("SbsCalculatorServiceHttpSoap11Endpoint".equals(portName)) {
            setSbsCalculatorServiceHttpSoap11EndpointEndpointAddress(address);
        }
        else
        { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
