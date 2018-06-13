package com.ac.scada.collectd.dtu;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by boris feng on 2016/5/7.
 */
public abstract class AbstractListener implements CollectorListener {
    private Logger logger = LoggerFactory.getLogger(AbstractListener.class);

    @Autowired
    protected CollectorFactory collectorFactory;

    protected ServerSocket server;

    // run in listen thread
    protected abstract void execute(Socket client);

    protected abstract int getPort();

    @Override
    public boolean start() {
        logger.debug("start");

        try {
            Thread job = new Thread(new ListenJob(this), "listen-" + getPort());
            job.setDaemon(true);
            job.start();
            logger.debug("listener task run, {}",job.getName());

            return true;
        } catch (Exception e) {
            logger.error("init other err:" + e);
        }

        return false;
    }

    @Override
    public boolean close() {
        try {
            if (server != null && !server.isClosed())
                server.close();

            return true;
        } catch (IOException e) {
            logger.error("setting close server socket {}", e.getMessage());
        } catch (Exception e) {
            logger.error("setting close exception {}", e.getMessage());
        }

        return false;
    }

    protected void run() {
        try {
            server = new ServerSocket(getPort());
            logger.debug("setting server init ok port:{}", getPort());
            do {
                try {
                    Socket client = server.accept();
                    logger.debug("begin to connecting:{}", client.getRemoteSocketAddress().toString());
                    logger.debug("getLocalPort:{}", client.getLocalPort());
                    logger.debug("getPort:{}", client.getPort());
                    logger.debug("getInetAddress:{}", client.getInetAddress());
                    logger.debug("getLocalAddress:{}", client.getLocalAddress());
                    logger.debug("getLocalSocketAddress:{}", client.getLocalSocketAddress());
                    execute(client);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } while (!server.isClosed());
        } catch (IOException e) {
            logger.error("can not create correct Socket:{}", e.getMessage());
        } catch (Exception e) {
            logger.error("other err:" + e);
        }
    }

    class ListenJob implements Runnable {
        private AbstractListener handler;

        @Override
        public void run() {
            if (handler != null)
                handler.run();
        }

        public ListenJob(AbstractListener handler) {
            this.handler = handler;
        }
    }
}
