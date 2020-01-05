package pl.polsl.gawron.marcel.rplaceServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.polsl.gawron.marcel.rplaceData.models.Image;
import pl.polsl.gawron.marcel.rplaceServer.tcpServer.TCPServer;
import pl.polsl.gawron.marcel.rplaceServer.tcpServer.controllers.ProtocolController;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Configuration
public class TCPServerConfig {

    @Value("${TCPServer.port}")
    int port;

    @Bean
    public TCPServer makeTCPServer(ProtocolController protocolController, Image image){
        return new TCPServer(protocolController, image, port);
    }
}
