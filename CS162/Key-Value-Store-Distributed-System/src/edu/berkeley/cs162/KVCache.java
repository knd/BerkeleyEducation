/**
 * Implementation of a set-associative cache.
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


import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.*;

/**
 * A set-associate cache which has a fixed maximum number of sets (numSets).
 * Each set has a maximum number of elements (MAX_ELEMS_PER_SET).
 * If a set is full and another entry is added, an entry is dropped based on the eviction policy.
 */
public class KVCache implements KeyValueInterface {
        private int numSets = 100;
        private int maxElemsPerSet = 10;
        private Hashtable<String,KVCache.entry> contents[];
        private LinkedList<KVCache.entry> evictionQArray[];
        private WriteLock lockArray[];
        
        private class entry{
        	private int useBit;
        	private String key;
        	private String value;    
        	public entry(String key, String value){
        		this.key = key;
        		this.value = value;
        		this.useBit = 0;
        		
        	}
        	public boolean isRef(){
        		if (useBit==1){
        			return true;
        		}else{
        			return false;
        		}
        	}
        	public String getKey(){
        		return key;
        	}
        	public String getValue(){
        		return value;
        	}
        	public void setBitOne(){
        		this.useBit = 1;
        	}
        	public void updateValue(String value){
        		this.value = value;
        	}
        	public String toString(){
        		String rString = "Key:"+key+" Value:"+value+" useBit:"+useBit;
        		return rString;
        	}
        }
                
        /**
         * Creates a new LRU cache.
         * @param cacheSize     the maximum number of entries that will be kept in this cache.
         */
        @SuppressWarnings("unchecked")
		public KVCache(int numSets, int maxElemsPerSet) {
        //public cache(int numSets, int maxElemsPerSet){
                this.numSets = numSets;
                this.maxElemsPerSet = maxElemsPerSet;
                this.contents = (Hashtable<String,KVCache.entry>[])new Hashtable<?,?>[numSets];
                this.evictionQArray =  (LinkedList<KVCache.entry>[])new LinkedList<?>[numSets];
                this.lockArray = new WriteLock[numSets];
                for (int i=0; i<numSets; i++){
                	contents[i] = new Hashtable<String,KVCache.entry>();
                	evictionQArray[i] = new LinkedList<KVCache.entry>();
                	lockArray[i] = new ReentrantReadWriteLock().writeLock();
                	
                }
                // TODO: Implement Me!
        }

        /**
         * Retrieves an entry from the cache.
         * Assumes the corresponding set has already been locked for writing.
         * @param key the key whose associated value is to be returned.
         * @return the value associated to this key, or null if no value with this key exists in the cache.
         */
        public String get(String key) {
                // Must be called before anything else
            AutoGrader.agCacheGetStarted(key);
            AutoGrader.agCacheGetDelay();
        
                // TODO: Implement Me!
        	int setID = getSetId(key);
        	Hashtable<String,KVCache.entry> setToCheck = contents[setID];
        	if (setToCheck.containsKey(key)){
        		KVCache.entry e = setToCheck.get(key);
        		e.useBit = 1;
        		String returnString = e.value;
        		AutoGrader.agCacheGetFinished(key);
        		return returnString;
        	}   
                // Must be called before returning
                AutoGrader.agCacheGetFinished(key);
                return null;
        }

        /**
         * Adds an entry to this cache.
         * If an entry with the specified key already exists in the cache, it is replaced by the new entry.
         * If the cache is full, an entry is removed from the cache based on the eviction policy
         * Assumes the corresponding set has already been locked for writing.
         * @param key   the key with which the specified value is to be associated.
         * @param value a value to be associated with the specified key.
         * @return true is something has been overwritten 
         */
        public void put(String key, String value) {
                // Must be called before anything else
                AutoGrader.agCachePutStarted(key, value);
                AutoGrader.agCachePutDelay();

                // TODO: Implement Me!
        	KVCache.entry newEntry = new KVCache.entry(key,value);
        	int setID = getSetId(key);
        	Hashtable<String,KVCache.entry> setToCheck = contents[setID];
        	LinkedList<KVCache.entry> evictionQ = evictionQArray[setID];
        	
        	if (setToCheck.containsKey(key)){
        		KVCache.entry entryToUpdate = setToCheck.get(key);
        		entryToUpdate.updateValue(value);
        		entryToUpdate.setBitOne();
        		AutoGrader.agCachePutFinished(key, value);
        		
        	}
        	if(setToCheck.size()<maxElemsPerSet){
        		setToCheck.put(key, newEntry);
        		evictionQ.add(newEntry);
        		AutoGrader.agCachePutFinished(key, value);
        		
        	} else {
        		while(true){
        			KVCache.entry potentialEvict = evictionQ.getFirst();
        			if (potentialEvict.useBit==1){
        				evictionQ.remove(potentialEvict);
        				evictionQ.add(potentialEvict);
        				potentialEvict.useBit = 0;
        			} else {
        				evictionQ.remove(potentialEvict);
        				setToCheck.remove(potentialEvict.key);
        				setToCheck.put(key, newEntry);
        				evictionQ.add(newEntry);
        				break;
        			}
        		}
        	}
        		// Must be called before returning
                AutoGrader.agCachePutFinished(key, value);
                
        }

        /**
         * Removes an entry from this cache.
         * Assumes the corresponding set has already been locked for writing.
         * @param key   the key with which the specified value is to be associated.
         */
        public void del (String key) {
                // Must be called before anything else
                AutoGrader.agCacheGetStarted(key);
                AutoGrader.agCacheDelDelay();
                
                // TODO: Implement Me!
                int setID = getSetId(key);
                Hashtable<String,KVCache.entry> setToCheck = contents[setID];
                LinkedList<KVCache.entry> qToCheck = evictionQArray[setID]; 
                
                KVCache.entry e = setToCheck.remove(key);
                qToCheck.remove(e);
                // Must be called before returning
                AutoGrader.agCacheDelFinished(key);
        }
        
        /**
         * @param key
         * @return      the write lock of the set that contains key.
         */
        public WriteLock getWriteLock(String key) {
            // TODO: Implement Me!
            int setID = getSetId(key);
            return lockArray[setID];
        }
        
        /**
         * 
         * @param key
         * @return      set of the key
         */
        private int getSetId(String key) {
                return Math.abs(key.hashCode()) % numSets;
        }
        
    public String toXML() {
        // TODO: Implement Me!
    	StringBuilder string = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	string.append("<KVCache>");
    	for (int i = 0; i<numSets; i++){
    		Hashtable<String,KVCache.entry> set = contents[i];
    		string.append("<Set Id=\""+i+"\">");
    		Enumeration<String> e = set.keys();
    		int j=0;
    		while(e.hasMoreElements()){
    			j++;
    			KVCache.entry entry = set.get(e.nextElement());
    			String isRef = "";
    			if (entry.isRef()){
    				isRef="true";
    			} else {
    				isRef="false";
    			}
    			string.append("<CacheEntry isReferenced=\""+isRef+"\" isValid=\""+"true"+"\">");
    			string.append("<Key>"+entry.getKey()+"</Key>");
    			string.append("<Value>"+entry.getValue()+"</Value>");
    			string.append("</CacheEntry>");
    		}
    		for (; j<maxElemsPerSet; j++){
    			string.append("<CacheEntry isReferenced=\"false\" isValid=\"false\"><Key></Key><Value></Value></CacheEntry>");
    		}
    		string.append("</Set>");
    	}
    	string.append("</KVCache>");
        return string.toString();
    }

}