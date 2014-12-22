/**
 *
 */
package com.acs.biz.device.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acs.core.common.utils.SpringCommonTest;

/**
 * @author Eric
 */
public class EchoServerTest extends SpringCommonTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void startServer() {
		try {
			ServerSocket server = new ServerSocket(5600);
			logger.debug("EchoServer started...");
			while (true) {
				Socket client = server.accept();
				EchoHandler handler = new EchoHandler(client);
				handler.start();
			}
		} catch (Exception e) {
			System.err.println("Exception caught:" + e);
		}
	}
}

class EchoHandler extends Thread {
	private static Random rand = new Random(System.currentTimeMillis());
	private static int clientNum = 0;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Socket client;
	private int myClientNum;

	EchoHandler(Socket client) {
		this.client = client;
		myClientNum = ++clientNum;
	}

	@Override
	public void run() {
		try {
			logger.debug("client (" + myClientNum + ") connected.");
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
			// writer.println("[type 'bye' to disconnect]");

			while (true) {
				String line = reader.readLine();
				logger.debug("client (" + myClientNum + "):" + line);
				if (line.trim().equals("bye")) {
					writer.println("bye!");
					break;
				}
				int sleep = rand.nextInt(3);
				// int sleep = 2;
				logger.debug("client (" + myClientNum + ") sleep" + sleep);
				Thread.sleep(sleep * 1000);

				writer.println("[echo] " + line);
			}
		} catch (Exception e) {
			System.err.println("Exception caught: client (" + myClientNum + ") disconnected.");
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				;
			}
		}
	}
}
