/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.util;

import com.sun.squawk.util.LineReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import javax.microedition.io.Connector;

/**
 *
 * @author leighpauls
 */
public class DaveConstants {
    private final Hashtable mValueTable;
    
    public DaveConstants() {
        mValueTable = new Hashtable();
        
    }
    
    public void reloadConstants() {
        mValueTable.clear();
        InputStream is = null;
        
        try {
            is = Connector.openInputStream("file:///dave_consts.conf");
            LineReader r = new LineReader(new InputStreamReader(is));
            readFile(r);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();           
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void readFile(LineReader r) throws IOException {
        String curLine;
        int curLineNum = 0;
        
        while ((curLine = r.readLine()) != null) {
            int curIdx = 0;
            while (curIdx < curLine.length()) {
                // discard whitespace
                char curChar = curLine.charAt(curIdx);
                if (curChar == ' ' 
                        || curChar == '\t'
                        || curChar == '\n'
                        || curChar == '\r') {
                    curLine = curLine.substring(0, curIdx) + curLine.substring(curIdx+1);
                }
            }
            // don't care about comments
            if (curLine.startsWith("#")) {
                continue;
            }
            int equalBreak = curLine.indexOf("=");
            if (equalBreak < 0) {
                System.err.print("Bad line format at line " + curLineNum);
                continue;
            }
            String name = curLine.substring(0, equalBreak);
            String value = curLine.substring(equalBreak+1);
            mValueTable.put(name, value);
            
            curLineNum++;
        }
    }
    
    public int getInt(String name, int default_val) {
        String value = (String)mValueTable.get(name);
        if (value == null) {
            System.err.println("No Value for " + name);
            return default_val;
        }
        int res;
        try {
            res = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid int at " + name);
            return default_val;
        }
        if (res != default_val) {
            System.err.println(name + ": default does not match real value");
        }
        return res;
    }
    
    public double getDouble(String name, double default_val) {
        String value = (String)mValueTable.get(name);
        if (value == null) {
            System.err.println("No Value for " + name);
            return default_val;
        }
        double res;
        try {
            res = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid int at " + name);
            return default_val;
        }
        if (res != default_val) {
            System.err.println(name + ": default does not match real value");
        }
        return res;
    }
}
