package com.hao.server;

import com.hao.dao.DB;
import com.hao.dao.Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client Worker handling request of a client.
 *
 * @author a.khettar
 */
public class ClientWorker implements Runnable {

    private final Socket socket;
    private String workingDir;
    private final Logger logger = Logger.getLogger(ClientWorker.class.getName());

    /**
     * @param socket
     */
    public ClientWorker(final Socket socket, String homeDir) {
        this.socket = socket;
        this.workingDir = homeDir;
    }

    public void run() {

        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            new Process(new DB(), reader, out).run();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to close the socket", e);

            }
        }
    }

}
