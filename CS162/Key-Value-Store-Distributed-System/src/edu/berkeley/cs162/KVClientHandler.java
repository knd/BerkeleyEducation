/**
 * Handle client connections over a socket interface
 * 
 * @author Mosharaf Chowdhury (http://www.mosharaf.com)
 * @author Prashanth Mohan (http://www.cs.berkeley.edu/~prmohan)
 *
 * Copyright (c) 2011, University of California at Berkeley
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
 *  DISCLAIMED. IN NO EVENT SHALL PRASHANTH MOHAN BE LIABLE FOR ANY
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

/**
 * This NetworkHandler will asynchronously handle the socket connections. 
 * It uses a threadpool to ensure that none of it's methods are blocking.
 *
 */
public class KVClientHandler implements NetworkHandler {
	private ThreadPool threadpool = null;
    private TPCMaster tpcMaster = null;
	
	public KVClientHandler(TPCMaster tpcMaster) {
		initialize(1, tpcMaster);
	}

	public KVClientHandler(int connections, TPCMaster tpcMaster) {
		initialize(connections, tpcMaster);
	}

	private void initialize(int connections, TPCMaster tpcMaster) {
		threadpool = new ThreadPool(connections);
        this.tpcMaster = tpcMaster; 
	}
	

	private class ClientHandler implements Runnable {
		private Socket client = null;
		private TPCMaster tpcMaster = null;
		
		@Override
		public void run() {
			
			try {
					KVMessage inputMsg = new KVMessage(client.getInputStream());
					KVMessage outputMsg = new KVMessage("resp");
				
					String msgType = inputMsg.getMsgType();
					
					if ("getreq".equals(msgType)) {
							String key = inputMsg.getKey();
							String value = tpcMaster.handleGet(inputMsg);
							outputMsg.setKey(key);
							outputMsg.setValue(value);
							outputMsg.sendMessage(client);
						
					} else if ("putreq".equals(msgType)) {
							boolean success = tpcMaster.performTPCOperation(inputMsg, true);
							if (success) {
									outputMsg.setMessage("Success");
							} else {
									// several errors have occurred with 2pc operation
									outputMsg.setMessage("Unknown Error: Put failed");							
							}
							outputMsg.sendMessage(client);
											
					} else if ("delreq".equals(msgType)) {
							boolean success = tpcMaster.performTPCOperation(inputMsg, false);
							if (success) {
									outputMsg.setMessage("Success");
							} else {
									// several errors have occurred with 2pc operation
									outputMsg.setMessage("Unknown Error: Del failed");
							}
							outputMsg.sendMessage(client);
						
					} else {
							outputMsg.setMessage("Unknown Error: this type of request does not exist");
							outputMsg.sendMessage(client);						
					}
					
			} catch (IOException e) {
					System.err.println("Network Error: I/O error occurs when creating input stream");
					
			} catch (KVException e) {
					try {
							e.getMsg().sendMessage(client);
					} catch (KVException e1) {
							System.err.println("Network Error: I/O error occurs when sending error message back to client");
					}
					
			} finally {
					try {
							client.close();
					} catch (IOException e) {
							System.err.println("Network Error: Could not close client socket on server");
					}
			}
		}
		
		public ClientHandler(TPCMaster tpcMaster, Socket client) {
			this.client = client;
			this.tpcMaster = tpcMaster;
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.berkeley.cs162.NetworkHandler#handle(java.net.Socket)
	 */
	@Override
	public void handle(Socket client) throws IOException {
		Runnable r = new ClientHandler(tpcMaster, client);
		try {
			threadpool.addToQueue(r);
		} catch (InterruptedException e) {
			// Ignore this error
			return;
		}
	}
}
