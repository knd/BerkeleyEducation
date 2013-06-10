/**
 * Persistent Key-Value storage layer. Current implementation is transient, 
 * but assume to be backed on disk when you do your project.
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
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.*;


/**
 * This is a dummy KeyValue Store. Ideally this would go to disk, 
 * or some other backing store. For this project, we simulate the disk like 
 * system using a manual delay.
 *
 */
public class KVStore implements KeyValueInterface {
	private Dictionary<String, String> store 	= null;
	
	public KVStore() {
		resetStore();
	}

	private void resetStore() {
		store = new Hashtable<String, String>();
	}
	
	public void put(String key, String value) throws KVException {
		AutoGrader.agStorePutStarted(key, value);
		
		try {
			putDelay();
			store.put(key, value);
		} finally {
			AutoGrader.agStorePutFinished(key, value);
		}
	}
	
	public String get(String key) throws KVException {
		AutoGrader.agStoreGetStarted(key);
		
		try {
			getDelay();
			String retVal = this.store.get(key);
			if (retVal == null) {
			    KVMessage msg = new KVMessage("resp", "key \"" + key + "\" does not exist in store");
			    throw new KVException(msg);
			}
			return retVal;
		} finally {
			AutoGrader.agStoreGetFinished(key);
		}
	}
	
	public void del(String key) throws KVException {
		AutoGrader.agStoreDelStarted(key);

		try {
			delDelay();
			if(key != null)
				this.store.remove(key);
		    else {
                KVMessage msg = new KVMessage("resp", "key \"" + key + "\" does not exist in store");
                throw new KVException(msg);
            }
		} finally {
			AutoGrader.agStoreDelFinished(key);
		}
	}
	
	private void getDelay() {
		AutoGrader.agStoreDelay();
	}
	
	private void putDelay() {
		AutoGrader.agStoreDelay();
	}
	
	private void delDelay() {
		AutoGrader.agStoreDelay();
	}
	
    public String toXML() throws KVException {
    	StringBuilder string = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	string.append("<KVStore>");
    	Enumeration<String> keys = store.keys();
    	String key;
    	while (keys.hasMoreElements()) {
    		string.append("<KVPair>");
    		key = keys.nextElement();
			string.append("<Key>").append(key).append("</Key>");
			string.append("<Value>").append(store.get(key)).append("</Value>");
			string.append("</KVPair>");
    	}
    	string.append("</KVStore>");
    	
    	return string.toString();
    }        

    public void dumpToFile(String fileName) throws KVException {
    	File file = new File(fileName);
    	try {
			file.createNewFile();
			Writer output = new BufferedWriter(new FileWriter(file));
			output.write(toXML());
			output.close();
			
		} catch (IOException e) {
			throw new KVException(new KVMessage("resp","IO Error"));
		}
    	resetStore();
    }

    public void restoreFromFile(String fileName) throws KVException{
    	File file = new File(fileName);
    	if (!file.exists()) {
    	    throw new KVException(new KVMessage("resp","IO Error"));
    	}
    	else {
    		DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
    	    DocumentBuilder docBuilder;
			try {
				docBuilder = docFac.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new KVException(new KVMessage("resp","IO Error"));
			}
    	    Document doc;
			try {
				doc = docBuilder.parse(file);
			} catch (SAXException e) {
				throw new KVException(new KVMessage("resp","IO Error"));
			} catch (IOException e) {
				throw new KVException(new KVMessage("resp","IO Error"));
			}
    	    NodeList keys = doc.getElementsByTagName("Key");
    	    Element keyNode;
    	    Element valueNode;
    	    String key;
    	    String value;
    	    for (int i=0; i < keys.getLength(); i++) {
    	        keyNode = (Element) keys.item(i);
    	        if (!keyNode.hasChildNodes()) {
    	            throw new KVException(new KVMessage("resp","IO Error"));
    	        }
    	        else {
    	            key = ((Element)keyNode).getFirstChild().getNodeValue();
    	            valueNode = (Element) keyNode.getNextSibling();
    	            if (valueNode == null || !(valueNode.getTagName().equals("Value")) || !valueNode.hasChildNodes()) {
    	                throw new KVException(new KVMessage("resp","IO Error"));
    	            }
    	            else {
    	                value = ((Element)valueNode).getFirstChild().getNodeValue();
    	                put(key,value);
    	            }
    	        }
    	    }
    	}
    }
}