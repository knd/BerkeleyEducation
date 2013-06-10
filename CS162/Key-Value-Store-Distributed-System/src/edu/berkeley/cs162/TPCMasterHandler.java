/**
 * Handle TPC connections over a socket interface
 * 
 * @author Mosharaf Chowdhury (http://www.mosharaf.com)
 * @author Prashanth Mohan (http://www.cs.berkeley.edu/~prmohan)
 * 
 * Copyright (c) 2012, University of California at Berkeley
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of University of California, Berkeley nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *    
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.berkeley.cs162;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Implements NetworkHandler to handle 2PC operation requests from the Master/
 * Coordinator Server
 *
 */
public class TPCMasterHandler implements NetworkHandler {
	private KVServer kvServer = null;
	private ThreadPool threadpool = null;
	private TPCLog tpcLog = null;
	
	private long slaveID = -1;
	
	// Used to handle the "ignoreNext" message
	private boolean ignoreNext = false;
	
	// States carried from the first to the second phase of a 2PC operation
	private KVMessage originalMessage = null;
	private boolean aborted = true;	

	public TPCMasterHandler(KVServer keyserver) {
		this(keyserver, 1);
	}

	public TPCMasterHandler(KVServer keyserver, long slaveID) {
		this.kvServer = keyserver;
		this.slaveID = slaveID;
		threadpool = new ThreadPool(1);
	}

	public TPCMasterHandler(KVServer kvServer, long slaveID, int connections) {
		this.kvServer = kvServer;
		this.slaveID = slaveID;
		threadpool = new ThreadPool(connections);
	}

	private class ClientHandler implements Runnable {
		private KVServer keyserver = null;
		private Socket client = null;
		
		private void closeConn() {
			try {
				client.close();
			} catch (IOException e) {
			}
		}
		
		@Override
		public void run() {
			
			try {
				// Receive message from client
				// Implement me
				KVMessage msg = new KVMessage(client.getInputStream());
	
				// Parse the message and do stuff 
				String key = msg.getKey();
				
				if (msg.getMsgType().equals("putreq")) {
					handlePut(msg, key);
				}
				else if (msg.getMsgType().equals("getreq")) {
					handleGet(msg, key);
				}
				else if (msg.getMsgType().equals("delreq")) {
					handleDel(msg, key);
				} 
				else if (msg.getMsgType().equals("ignoreNext")) {
					// Set ignoreNext to true. PUT and DEL handlers know what to do.
					// ignoreNext KVMessage/TPCMessage
					//
					// <?xml version="1.0" encoding="UTF-8"?>
					// <KVMessage type="ignoreNext"/>
			
					ignoreNext = true;
					
					// Send back an acknowledgment
					// ignoreNext ACK
					//
					// <?xml version="1.0" encoding="UTF-8"?>
					// <KVMessage type="resp">
					// <Message>Success</Message>
					// </KVMessage>
					
					try { 
						KVMessage outputMsg = new KVMessage("resp");
						outputMsg.setMessage("Success");
						
					} catch (KVException e) {
						try {
							KVMessage errorMsg = new KVMessage("resp", e.getMsg().getMessage());
							errorMsg.sendMessage(client);
						} catch (KVException e1) {
							System.err.println(e1.getMsg().getMessage());
						}
					}
				}
				else if (msg.getMsgType().equals("commit") || msg.getMsgType().equals("abort")) {
					// Check in TPCLog for the case when SlaveServer is restarted
					// Due to sequential execution of TPC operations (non-extra credit),
					// the interruptedTpcOperation (if exists) will be either tpc 
					// op in phase 1 or global decision in phase 2 of a single TPC operation
					
					KVMessage interruptedTpcOperation = null;
					
					if (tpcLog.hasInterruptedTpcOperation()) {
						interruptedTpcOperation = tpcLog.getInterruptedTpcOperation();	
						// parse the interrupted tpc operation message
						String msgType = interruptedTpcOperation.getMsgType();
						
						if ("putreq".equals(msgType) || "delreq".equals(msgType)) {
							performActualTpcOperation(msg); // and logging
							sendAckTpcOperation();
							
						} else if ("commit".equals(msgType) || "abort".equals(msgType)) {
							sendAckTpcOperation();
						}
						
					} else {
						performActualTpcOperation(msg); // and logging
						sendAckTpcOperation();
					}
									
					// Reset state
					originalMessage = null;
					ignoreNext = false;
					aborted = false;	
				}
			
			} catch (IOException e) {
				System.err.println("Network: I/O error occurs when getting inputstream from master");
			} catch (KVException e) {
				System.err.println(e.getMsg().getMessage());
			} finally {
				// Finally, close the connection
				closeConn();
			}
		}
		
		/**
		 * Send ack message to master after performing actual 
		 * actual operation and logging
		 */
		private void sendAckTpcOperation() throws KVException {
			// send ack to master
			KVMessage outputMsg = new KVMessage("ack");
			outputMsg.setTpcOpId(originalMessage.getTpcOpId());
			outputMsg.sendMessage(client);
		}
		
		/**
		 * Perform actual tpc operation and the log to disk.
		 * 
		 * @param msg The global decision from master
		 */
		private void performActualTpcOperation(KVMessage msg) {
			// perform actual operation
			aborted = "abort".equals(msg.getMsgType());
			handleMasterResponse(msg, originalMessage, aborted);
			// logging global decision 
			tpcLog.appendAndFlush(msg);
		}

		/**
		 * Phase 1 of TPC operation:
		 * - received msg:
		 * 		<?xml version="1.0" encoding="UTF-8"?>
		 * 		<KVMessage type="putreq">
		 * 		<Key>key</Key>
		 * 		<Value>value</Value>
		 * 		<TPCOpId>2PC Operation ID</TPCOpId>
		 * 		</KVMessage>	
		 * 
		 * - logging [ put(k, v) ]
		 * - send back ready/abort to master
		 * 
		 * @param msg
		 * @param key
		 */
		private void handlePut(KVMessage msg, String key) {
			AutoGrader.agTPCPutStarted(slaveID, msg, key);
			
			// Store for use in the second phase
			originalMessage = new KVMessage(msg);
			// logging
			tpcLog.appendAndFlush(msg);
			
			// send ready/abort decision to master
			String msgType = ignoreNext ? "abort" : "ready";
			String tpcOpId = msg.getTpcOpId();
			
			try {
				KVMessage outputMsg = new KVMessage(msgType);
				outputMsg.setTpcOpId(tpcOpId);
				if (ignoreNext) {
					outputMsg.setMessage("IgnoreNext Error: SlaveServer " + slaveID + " has ignored this 2PC request during the first phase");
				}
				outputMsg.sendMessage(client);
				
			} catch (KVException e) {
				try {
					KVMessage errorMsg = new KVMessage("abort", e.getMsg().getMessage());
					errorMsg.sendMessage(client);
				} catch (KVException e1) {
					// possible errors due to incorrect msg format/ network error
					System.err.println(e1.getMsg().getMessage());
				}
			}

			AutoGrader.agTPCPutFinished(slaveID, msg, key);
		}
		
		/**
		 * Perform GET operation on KVServer and send back response to 
		 * client/socket of master server.
		 * 
		 * @param msg - sent by master client socket to this TPCMasterHandler
		 * @param key - of the GET request msg
		 */
 		private void handleGet(KVMessage msg, String key) {
 			AutoGrader.agGetStarted(slaveID);
			
 			try {
 				// get value from KVServer 
 				// and send successful GET response to master
				String value = keyserver.get(key);
				KVMessage outputMsg = new KVMessage("resp");
				outputMsg.setKey(key);
				outputMsg.setValue(value);
				outputMsg.sendMessage(client);
				
			} catch (KVException e) {
				try {
					// get error message thrown by KVServer
					// and send back to master
					e.getMsg().sendMessage(client);
				} catch (KVException e1) {
					System.err.println("Network Error: I/O error occurs when sending error message back to master");
				}
			}
 			
 			AutoGrader.agGetFinished(slaveID);
		}
		
 		/**
		 * Phase 1 of TPC operation:
		 * - received msg:
		 * 		<?xml version="1.0" encoding="UTF-8"?>
		 * 		<KVMessage type="delreq">
		 * 		<Key>key</Key>
		 * 		<TPCOpId>2PC Operation ID</TPCOpId>
		 * 		</KVMessage>	
		 * 
		 * - logging [ del(k) ]
		 * - send back ready/abort to master
		 * 
 		 * @param msg
 		 * @param key
 		 */
		private void handleDel(KVMessage msg, String key) {
			AutoGrader.agTPCDelStarted(slaveID, msg, key);

			// Store for use in the second phase
			originalMessage = new KVMessage(msg);
			// logging
			tpcLog.appendAndFlush(msg);
			
			// send ready/abort to master
			String msgType = ignoreNext ? "abort" : "ready";
			String tpcOpId = msg.getTpcOpId();
			
			try {
				KVMessage outputMsg = new KVMessage(msgType);
				outputMsg.setTpcOpId(tpcOpId);
				if (ignoreNext) {
					outputMsg.setMessage("IgnoreNext Error: SlaveServer " + slaveID + " has ignored this 2PC request during the first phase");
				}
				outputMsg.sendMessage(client);
				
			} catch (KVException e) {
				try {
					KVMessage errorMsg = new KVMessage("abort", e.getMsg().getMessage());
					errorMsg.sendMessage(client);
				} catch (KVException e1) {
					// possible errors due to incorrect msg format/ network error
					System.err.println(e1.getMsg().getMessage());
				}
			}
			
			AutoGrader.agTPCDelFinished(slaveID, msg, key);
		}

		/**
		 * Second phase of 2PC
		 * 
		 * @param masterResp Global decision taken by the master
		 * @param origMsg Message from the actual client (received via the coordinator/master)
		 * @param origAborted Did this slave server abort it in the first phase 
		 */
		private void handleMasterResponse(KVMessage masterResp, KVMessage origMsg, boolean origAborted) {
			AutoGrader.agSecondPhaseStarted(slaveID, origMsg, origAborted);
			
			// perform actual operation if global decision is "commit"
			if (!origAborted) {
				String origMsgType = origMsg.getMsgType();
				String key = origMsg.getKey();
				
				try {
					if ("putreq".equals(origMsgType)) {
						keyserver.put(key, origMsg.getValue());
					} else if ("delreq".equals(origMsgType)) {
						keyserver.del(key); 
					}
					
				} catch (KVException e) {
					System.err.println(e.getMsg().getMessage());
				}
			}
			
			AutoGrader.agSecondPhaseFinished(slaveID, origMsg, origAborted);
		}

		public ClientHandler(KVServer keyserver, Socket client) {
			this.keyserver = keyserver;
			this.client = client;
		}
	}

	@Override
	public void handle(Socket client) throws IOException {
		AutoGrader.agReceivedTPCRequest(slaveID);
		Runnable r = new ClientHandler(kvServer, client);
		try {
			threadpool.addToQueue(r);
		} catch (InterruptedException e) {
			// TODO: HANDLE ERROR
			// ignore this according to piazza @724
			return;
		}		
		AutoGrader.agFinishedTPCRequest(slaveID);
	}

	/**
	 * Set TPCLog after it has been rebuilt
	 * @param tpcLog
	 */
	public void setTPCLog(TPCLog tpcLog) {
		this.tpcLog  = tpcLog;
	}

	/**
	 * Registers the slave server with the coordinator
	 * 
	 * @param masterHostName
	 * @param server KVServer used by this slave server (contains the hostName and a random port)
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws KVException
	 */
	public void registerWithMaster(String masterHostName, SocketServer server) throws UnknownHostException, IOException, KVException {
		AutoGrader.agRegistrationStarted(slaveID);
		
		Socket master = new Socket(masterHostName, 9090);
		KVMessage regMessage = new KVMessage("register", slaveID + "@" + server.getHostname() + ":" + server.getPort());
		regMessage.sendMessage(master);
		
		// Receive master response. 
		// Response should always be success, except for Exceptions. Throw away.
		new KVMessage(master.getInputStream());
		
		master.close();
		AutoGrader.agRegistrationFinished(slaveID);
	}
}
