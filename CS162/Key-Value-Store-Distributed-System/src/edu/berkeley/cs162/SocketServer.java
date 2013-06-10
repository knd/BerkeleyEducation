/**
 * Socket Server manages network connections 
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
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * This is an generic class that should handle all TCP network connections 
 * arriving on a given unique (host, port) tuple. Ensure that this class 
 * remains generic by providing the connection handling logic in a NetworkHandler
 */
public class SocketServer {
	String hostname;
	int port;
	NetworkHandler handler;
	ServerSocket server;
	boolean isStopped;
	
	public SocketServer(String hostname) {
		this.hostname = hostname;
		this.port = -1;
	}

	public SocketServer(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	/**
	 * Creates a ServerSocket. 
	 * Uses 'port' if it is greater than zero, otherwise, selects a random port. 
	 * @throws IOException
	 */
	public void connect() throws IOException {
		int port = (this.port < 0) ? 0 : this.port;
		
		// when the server socket is not bound yet
		// retry to acquire a free random port
		do {
			try {
				this.server = new ServerSocket(port); // port of 0 will let server listen on any free random port
				this.isStopped = false;
			} catch (IOException e) {
				System.err.println("Network Error: Could not open socket.");
				System.exit(1);
			}
		} while (this.server.getLocalPort() == -1);
		
		this.port = this.server.getLocalPort();
	}
	
	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	/**
	 * Accept requests and service them asynchronously. 
	 * @throws IOException if there is a network error (for instance if the socket is inadvertently closed) 
	 */
	public void run() throws IOException {
	      while (!isStopped) {
	    	  
	    	  Socket clientSocket = null;
	    	  
	    	  try {
	    		  clientSocket = this.server.accept();
	    		  this.handler.handle(clientSocket);
	    	  } catch (IOException e) {
	    		  if (isStopped) {
	    			  System.out.println("Server stopped");
	    			  closeSocket();
	    			  System.exit(0);
	    		  } else {
	    			  System.err.println("Interrupted I/O. Server stopped");
	    			  closeSocket();
	    			  System.exit(1);
	    		  }
	    	  }
	      }
	}
	
	/** 
	 * Add the network handler for the current socket server
	 * @param handler is logic for servicing a network connection
	 */
	public void addHandler(NetworkHandler handler) {
		this.handler = handler;
	}

	/**
	 * Stop the ServerSocket
	 */
	public void stop() {
		closeSocket();
	}
	
	private void closeSocket() {
		this.isStopped = true;
	    try {
	    	this.server.close();
	    } catch (IOException e) {
	    	System.err.println("Network error: Could not close the socket");
	    	System.exit(1);
	    }
	}
	
	protected void finalize(){
		closeSocket();
	}
}
