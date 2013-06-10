/**
 * Client component for generating load for the KeyValue store. 
 * This is also used by the Master server to reach the slave nodes.
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
 * This class is used to communicate with (appropriately marshalling and unmarshalling) 
 * objects implementing the {@link KeyValueInterface}.
 *
 */
public class KVClient implements KeyValueInterface {

	private String server = null;
	private int port = 0;
	
	/**
	 * @param server is the DNS reference to the Key-Value server
	 * @param port is the port on which the Key-Value server is listening
	 */
	public KVClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	/**
	 * Return socket for connection with master server.
	 * 
	 * @return socket to connect server
	 * @throws KVException
	 */
	public Socket connectHost() throws KVException {
		Socket socket = null;
		try {
			socket = new Socket(this.server, this.port);
		} catch (UnknownHostException e){
			KVMessage networkErrorMsg = new KVMessage("resp", "Network Error: Could not connect");
			throw new KVException(networkErrorMsg);
		} catch (IOException e) {
			KVMessage networkErrorMsg = new KVMessage("resp", "Network Error: Could not create socket");
			throw new KVException(networkErrorMsg);
		}
		return socket;
	}
	
	/**
	 * Close socket after receiving response from master server. (or connection error)
	 * 
	 * @param sock
	 * @throws KVException
	 */
	public void closeHost(Socket sock) throws KVException {
		try {
			if (!sock.isClosed()) {
				sock.close();
			}
		} catch (IOException e) {
			KVMessage networkErrorMsg = new KVMessage("resp", "Network Error: Could not close socket");
			throw new KVException(networkErrorMsg);
		} finally {
			if (sock != null) {
				sock = null;
			}
		}
	}
	
	/**
	 * Send put(key, value) to master server.
	 * 
	 * @param key
	 * @param value
	 * @throws KVException
	 */
	public void put(String key, String value) throws KVException {
		Socket sock = connectHost();
		
		KVMessage outMsg = new KVMessage("putreq");
		outMsg.setKey(key);
		outMsg.setValue(value);
		outMsg.sendMessage(sock);
		
		try {
			KVMessage inMsg = new KVMessage(sock.getInputStream());
			if (!"Success".equals(inMsg.getMessage())) {
				throw new KVException(inMsg);
			}
		} catch (IOException e) {
			KVMessage networkErrorMsg = new KVMessage("resp", "Network Error: Could not receive data");
			throw new KVException(networkErrorMsg);
		} catch (KVException e) {
			throw new KVException(e.getMsg());
		} finally {
			closeHost(sock);
		}
		
		return;
	}
	
	public void put(String key, String value, String tpcOpId, int timeout) throws KVException {
		// TODO: Implement Me for Project 4
		return;		
	}

	/**
	 * Send get(key) request to master server.
	 * 
	 * @param key
	 * @return value of key-value pair
	 * @throws KVException
	 */
	public String get(String key) throws KVException {
	    Socket sock = connectHost();
	    
	    KVMessage outMsg = new KVMessage("getreq");
	    outMsg.setKey(key);
	    outMsg.sendMessage(sock);
	    KVMessage inMsg = null;
	    
	    try {
	    	inMsg = new KVMessage(sock.getInputStream());
	    	if ((inMsg.getValue() == null || inMsg.getValue().equals(""))) {
	    		throw new KVException(inMsg);
	    	}
	    } catch (IOException e) {
	    	KVMessage networkErrorMsg = new KVMessage("resp", "Network Error: Could not receive data");
	    	throw new KVException(networkErrorMsg);
	    } catch (KVException e) {
	    	throw new KVException(e.getMsg());
	    }
	    finally {
	    	closeHost(sock);
	    }
	    
	    return inMsg.getValue();
	}
	
	public String get(String key, int timeout) throws KVException {
		// TODO: Implement Me for Project 4
		return null;
	}
	
	/**
	 * Send del(key) request to master server.
	 * 
	 * @param key
	 * @throws KVException
	 */
	public void del(String key) throws KVException {
	    Socket sock = connectHost();
	    
	    KVMessage outMsg = new KVMessage("delreq");
	    outMsg.setKey(key);
	    outMsg.sendMessage(sock);
	    KVMessage inMsg = null;
	    
	    try {
	    	inMsg = new KVMessage(sock.getInputStream());
	    	if (!"Success".equals(inMsg.getMessage())) {
	    		throw new KVException(inMsg);
	    	}
	    } catch (IOException e) {
	    	KVMessage networkErrorMsg = new KVMessage("resp", "Network Error: Could not receive data");
	    	throw new KVException(networkErrorMsg);
	    } catch (KVException e) {
	    	throw new KVException(e.getMsg());
	    }
	    finally {
	    	closeHost(sock);
	    }
	    
	    return;
	}	
	
	public void del(String key, String tpcOpId, int timeout) throws KVException {
		// TODO: Implement Me for Project 4
		return;
	}
	
	public void ignoreNext() throws KVException {
	    // TODO: Implement Me!
	}
}
