/**
 *
 */
package com.acs.biz.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.acs.core.common.utils.SpringCommonTest;

/**
 * @author Eric
 */
public class EchoServerTest extends SpringCommonTest {

	@Async
	public void startServer() {
		try {
			int port = 5600;
			ServerSocket server = new ServerSocket(port);
			logger.debug("EchoServer started on port " + port + "...");
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

	private double targetTemperature = 25;
	private double targetHumidity = 50;

	EchoHandler(Socket client) {
		this.client = client;
		myClientNum = ++clientNum;
	}

	@Override
	public void run() {
		try {
			// logger.debug("client (" + myClientNum + ") connected.");
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
			// writer.println("[type 'bye' to disconnect]");

			while (true) {
				String line = reader.readLine();
				// logger.debug("client (" + myClientNum + "):" + line);
				if (line.trim().equals("bye")) {
					writer.println("bye!");
					break;
				}
				int sleep = rand.nextInt(3);
				// int sleep = 2;
				// logger.debug("client (" + myClientNum + ") sleep" + sleep);
				Thread.sleep(sleep * 1000);

				String response = processCommand(line);
				writer.println(response);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				;
			}
		}
	}

	private String processCommand(String cmd) {
		try {
			String response = null;
			if (cmd.startsWith("SET TEMPERATURE")) {
				String[] arg = cmd.split("\t");
				String strTemperature = arg[1].split(":")[1];
				this.targetTemperature = Double.parseDouble(strTemperature);
				String strHumidity = arg[2].split(":")[1];
				this.targetHumidity = Double.parseDouble(strHumidity);

				response = "[OK] " + cmd;
			} else if (cmd.startsWith("STATUS")) {
				double temperature, humidity;
				if (rand.nextDouble() > 0.5) {
					temperature = targetTemperature + rand.nextDouble();
				} else {
					temperature = targetTemperature - rand.nextDouble();
				}
				if (rand.nextDouble() > 0.5) {
					humidity = targetHumidity + rand.nextDouble() + rand.nextDouble();
				} else {
					humidity = targetHumidity - rand.nextDouble() - rand.nextDouble();
				}
				temperature = Math.round(temperature * 100.0) / 100.0;
				humidity = Math.round(humidity * 100.0) / 100.0;

				response = "[OK] " + cmd + "\tTemperature:" + temperature + "\tHumidity:" + humidity;

			} else {
				response = "[UNKNOWN] " + cmd;
			}

			return response;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "[ERR] " + e.getMessage();
		}

	}
}
