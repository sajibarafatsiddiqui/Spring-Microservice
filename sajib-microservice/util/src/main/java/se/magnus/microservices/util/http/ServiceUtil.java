package se.magnus.microservices.util.http;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {
    private static final Logger log = LoggerFactory.getLogger(ServiceUtil.class);
    private final String port;
    private String serviceAddress=null;
    
    @Autowired
    public ServiceUtil(@Value("${server.port}") String port) {
        this.port = port;
    }

    public String getServiceAddress(){
        return serviceAddress=findServerAddress()+"/"+findServerName()+":"+ port;

    }

    private String findServerAddress(){
        try {
           
                return InetAddress.getLocalHost().getHostAddress();
            
        } catch (UnknownHostException e) {
            return "Unknown IP Address";
        }
        
    } 
    private String findServerName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Unknown Hostname";
        }
    }   
    
}
