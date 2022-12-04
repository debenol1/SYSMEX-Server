/*******************************************************************************
 * Copyright (c) 2020-2022,  Olivier Debenath
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Olivier Debenath <olivier@debenath.ch> - initial implementation
 *    
 *******************************************************************************/
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

	private final static String INIT_MESSAGE = "Sysmex/HL7 Server booted";
	private final static String HOST = "Running on host: ";
	private final static String ADDRESS = "Using address: ";
	private final static String PORT = "Using Port: ";
	private final static String WAITING_FOR_MSG = "Waiting for client message...";
	private final static String RECEIVED_CLIENT_MSG_1 = "Received client message [";
	private final static String RECEIVED_CLIENT_MSG_2 = "] from ";

	private Properties properties;
	private ServerSocket serverSocket;
	private static Logger logger = Logger.getLogger(CommonSocketServer.class);

	public CommonSocketServer(int portNumber, Properties properties) {
		logger.info(INIT_MESSAGE);
		this.properties = properties;
		try {
			serverSocket = new ServerSocket(portNumber);
			logger.info(HOST + InetAddress.getLocalHost().getHostName());
			logger.info(ADDRESS + InetAddress.getLocalHost().getHostAddress());
			logger.info(PORT + serverSocket.getLocalPort());
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
				logger.info(WAITING_FOR_MSG);
				Socket socket = serverSocket.accept();
				logger.info(RECEIVED_CLIENT_MSG_1 + i + RECEIVED_CLIENT_MSG_2 + socket.getLocalSocketAddress());
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

	private final static String NEW_SECTION = "-------------------------------------------------";
	private final static String WAITING_FOR_MSG = "Waiting for client message...";
	private final static String RECEIVING_MSG_FROM_SYSMEX = "Step [1/4] - Reiceiving raw message from Sysmex";
	private final static String SLEEP_DURATION = "sleep.duration";
	private final static String SLEEP_1 = "Sleep (";
	private final static String SLEEP_2 = "ms)...";
	private final static String WAKE_UP = "Wake up...";
	private final static String RAW_MSG = "Raw Message: ";
	private final static String SLEEP_3 = "Sleep 2...";
	private final static String BUILD_XPM = "Step [2/4] - Build XPMessage from raw message";
	private final static String XPM_BUILT = "XPMessage built";
	private final static String BUILD_HL7 = "Step [3/4] - Build HL7 Message from XPMessage";
	private final static String HL7_BUILT = "HL7Message built";
	private final static String HL7_SAVED = "Step [4/4] - Save HL7 Message";
	private final static String WRITE_TO_FILE = "write.to.file";
	private final static String TRUE = "true";
	private final static String MSG_IDENTIFIED = "Message identified. Will be pushed to regular persistence process";
	private final static String PREFIX = "smx-";
	private final static String DASH = "-";
	private final static String EXT = "extension";
	private final static String OUT_DIR = "output.dir";
	private final static String MSG_TO_FILE = "Written message to file ";
	private final static String MSG_UNIDENTIFIED = "Message unidentified. Will be pushed to unresolved directory";
	private final static String U_PREFIX = "u-smx-";
	private final static String U_DIR = "unresolved.dir";
	private final static String DIS_CLOSED = "DataInputStream closed";
	private final static String DOS_CLOSED = "DataOutputStream closed";
	private final static String OS_CLOSED = "OutputStream closed";
	private final static String SOCKET_CLOSED = "Socket closed";
	
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
			logger.info(NEW_SECTION);
			logger.info(RECEIVING_MSG_FROM_SYSMEX);
			DataInputStream fromAnalyzer = new DataInputStream(socket.getInputStream());
			OutputStream out = socket.getOutputStream();
			DataOutputStream toAnalyzer = new DataOutputStream(out);
			long sleepDuration = Long.parseLong(properties.getProperty(SLEEP_DURATION));
			logger.info(SLEEP_1 + sleepDuration + SLEEP_2);
			Thread.sleep(sleepDuration);
			logger.info(WAKE_UP);

			StringBuilder textBuilder = new StringBuilder();
			Reader reader = new BufferedReader(
					new InputStreamReader(fromAnalyzer, Charset.forName(StandardCharsets.UTF_8.name())));
			int c = 0;

			while ((c = reader.read()) != 3) {
				if (c != 0 && c != 65533 && c != 2 && c != 3) {
					textBuilder.append((char) c);
				}
			}

			logger.info(RAW_MSG + textBuilder.toString());
			toAnalyzer.writeUTF(generateACK());
			logger.info(SLEEP_3);
			// Thread.sleep(10000);
			logger.info(WAKE_UP);
			logger.info(NEW_SECTION);
			logger.info(BUILD_XPM);
			XPMessage xpMessage = new XPMessage(textBuilder.toString());
			xpMessage.build();
			logger.info(XPM_BUILT);
			logger.info(NEW_SECTION);
			logger.info(BUILD_HL7);
			HL7Message hl7Message = new HL7Message(properties, xpMessage);
			hl7Message.build();
			logger.info(HL7_BUILT);
			logger.info(NEW_SECTION);
			logger.info(HL7_SAVED);
			if (properties.getProperty(WRITE_TO_FILE).equalsIgnoreCase(TRUE) && hl7Message.isResolved()) {
				logger.info(MSG_IDENTIFIED);
				String filename = PREFIX + hl7Message.getId() + DASH
						+ Long.toString(new java.util.Date().getTime()) + properties.getProperty(EXT);
				String filePath = properties.getProperty(OUT_DIR) + FileSystems.getDefault().getSeparator()
						+ filename;
				Files.write(Paths.get(filePath), hl7Message.toString().getBytes());
				logger.info(MSG_TO_FILE + filePath);

			} else {
				logger.info(MSG_UNIDENTIFIED);
				String filename = U_PREFIX + hl7Message.getId() + DASH
						+ Long.toString(new java.util.Date().getTime()) + properties.getProperty(EXT);
				String filePath = properties.getProperty(U_DIR) + FileSystems.getDefault().getSeparator()
						+ filename;
				Files.write(Paths.get(filePath), hl7Message.toString().getBytes());
				logger.info(MSG_TO_FILE + filePath);
			}
			logger.info(NEW_SECTION);
			fromAnalyzer.close();
			logger.info(DIS_CLOSED);
			toAnalyzer.close();
			logger.info(DOS_CLOSED);
			out.close();
			logger.info(OS_CLOSED);
			socket.close();
			logger.info(SOCKET_CLOSED);
			logger.info(NEW_SECTION);
			logger.info(WAITING_FOR_MSG);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String generateACK() {
		return "\u00004";
	}
}
