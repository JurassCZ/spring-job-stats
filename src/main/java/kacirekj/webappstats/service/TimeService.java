package kacirekj.webappstats.service;

import org.apache.commons.net.time.TimeTCPClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TimeService implements CommandLineRunner {

    public long internetTimeMillis() throws IOException {
            TimeTCPClient client = new TimeTCPClient();
            try {
                // Set timeout of 60 seconds
                client.setDefaultTimeout(60000);
                // Connecting to time server
                // Other time servers can be found at : http://tf.nist.gov/tf-cgi/servers.cgi#
                // Make sure that your program NEVER queries a server more frequently than once every 4 seconds
                client.connect("time.nist.gov");

                return client.getDate().getTime();
            } finally {
                client.disconnect();
            }
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println(internetTimeMillis());
    }
}


