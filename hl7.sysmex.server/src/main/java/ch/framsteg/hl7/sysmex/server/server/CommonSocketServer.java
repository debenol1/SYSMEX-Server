package ch.framsteg.hl7.sysmex.server.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;

import ch.framsteg.hl7.sysmex.server.messages.HL7Message;
import ch.framsteg.hl7.sysmex.server.messages.XPMessage;

public class CommonSocketServer extends Thread {
	private Properties properties;
	private ServerSocket serverSocket;
	private static Logger logger = Logger.getLogger(CommonSocketServer.class);

	public CommonSocketServer(int portNumber, Properties properties) {
		logger.info("Sysmex/HL7 Server booted");
		this.properties = properties;
		try {
			serverSocket = new ServerSocket(portNumber);
			logger.info("Running on host: "+InetAddress.getLocalHost().getHostName());
			logger.info("Using address: "+InetAddress.getLocalHost().getHostAddress());
			logger.info("Using Port: "+serverSocket.getLocalPort());
			handleConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleConnection() {
		
		// The server do a loop here to accept all connection initiated by the
		// client application.
		int i = 1;
		while (true) {
			try {
				logger.info("Waiting for client message...");
				Socket socket = serverSocket.accept();
				logger.info("Received client message ["+i+"] from "+socket.getLocalSocketAddress());
				ConnectionHandler connectionHandler = new ConnectionHandler(socket, i, properties);
				connectionHandler.init();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}

class ConnectionHandler implements Runnable {
	private final Socket socket;
	private Properties properties;

	private static Logger logger = Logger.getLogger(ConnectionHandler.class);

	ConnectionHandler(Socket socket, int requestNumber, Properties properties) {
		this.socket = socket;
		this.properties = properties;
	}
	
	public void init() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		try {
			logger.info("-------------------------------------------------");
			logger.info("Step [1/4] - Reiceiving raw message from Sysmex");
			DataInputStream fromAnalyzer = new DataInputStream(socket.getInputStream());
			OutputStream out = socket.getOutputStream();
			DataOutputStream toAnalyzer = new DataOutputStream(out);
			long sleepDuration = new Long(properties.getProperty("sleep.duration")).longValue();
			logger.info("Sleep ("+sleepDuration+"ms)...");
			Thread.sleep(sleepDuration);
			logger.info("Wake up...");
			
			StringBuilder textBuilder = new StringBuilder();
			Reader reader = new BufferedReader(
					new InputStreamReader(fromAnalyzer, Charset.forName(StandardCharsets.UTF_8.name())));
			int c = 0;
					
			while ((c = reader.read()) != 3) {
				if (c != 0 && c != 65533 && c != 2 && c != 3) {
					textBuilder.append((char) c);
				}
			}
			
			logger.info("Raw Message: " + textBuilder.toString());
			toAnalyzer.writeUTF(generateACK());
			logger.info("Sleep 2...");
			//Thread.sleep(10000);
			logger.info("Wake up...");
			logger.info("-------------------------------------------------");
			logger.info("Step [2/4] - Build XPMessage from raw message");
			XPMessage xpMessage = new XPMessage(textBuilder.toString());
			xpMessage.build();
			logger.info("XPMessage built");
			logger.info("-------------------------------------------------");
			logger.info("Step [3/4] - Build HL7 Message from XPMessage");
			HL7Message hl7Message = new HL7Message(properties, xpMessage);
			hl7Message.build();
			logger.info("HL7Message built");
			logger.info("-------------------------------------------------");
			logger.info("Step [4/4] - Save HL7 Message");
			if (properties.getProperty("write.to.file").equalsIgnoreCase("true") && hl7Message.isResolved()) {
				logger.info("Message identified. Will be pushed to regular persistence process");
				String filename = "smx-" + hl7Message.getId() + "-"
						+ new Long(new java.util.Date().getTime()).toString() + properties.getProperty("extension");
				String filePath = properties.getProperty("output.dir") + FileSystems.getDefault().getSeparator()
						+ filename;
				Files.write(Paths.get(filePath), hl7Message.toString().getBytes());
				logger.info("Written message to file " + filePath);

			} else {
				logger.info("Message unidentified. Will be pushed to unresolved directory");
				String filename = "u-smx-" + hl7Message.getId() + "-"
						+ new Long(new java.util.Date().getTime()).toString() + properties.getProperty("extension");
				String filePath = properties.getProperty("unresolved.dir") + FileSystems.getDefault().getSeparator()
						+ filename;
				Files.write(Paths.get(filePath), hl7Message.toString().getBytes());
				logger.info("Written message to file " + filePath);
			}
			logger.info("-------------------------------------------------");
			fromAnalyzer.close();
			logger.info("DataInputStream closed");
			toAnalyzer.close();
			logger.info("DataOutputStream closed");
			out.close();
			logger.info("OutputStream closed");
			socket.close();
			logger.info("Socket closed");
			logger.info("-------------------------------------------------");
			logger.info("Waiting for client message...");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String generateACK() {
		return "\u00004";
	}
}
