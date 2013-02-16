/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.util;

import ca.teamdave.schwimmer.control.HighStaticPWD;
import ca.teamdave.schwimmer.control.LinearPID;
import ca.teamdave.schwimmer.control.LinearPIDFF;
import com.sun.squawk.util.LineReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.io.Connector;

/**
 *
 * @author leighpauls
 */
public class Const {
    private final Hashtable mValueTable;
    private final Hashtable mEffectiveTable;
    private static Const sInstance = null;
    
    private Const() {
        mValueTable = new Hashtable();
        mEffectiveTable = new Hashtable();
    }
    
    public static Const getInstance() {
        if (sInstance == null) {
            sInstance = new Const();
        }
        return sInstance;
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
                curIdx++;
            }
            // don't care about comments
            if (curLine.startsWith("#") || curLine.length() == 0) {
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

    /**
     * Print the equivalent constants file to get the same functionality of the 
     * current Const object state
     */
    public void dumpEffectiveTable() {
        Enumeration keys = mEffectiveTable.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            System.out.println(key + " = " + mEffectiveTable.get(key));
        }
    }
    
    public int getInt(String name, int defaultVal) {
        int val = getIntImpl(name, defaultVal);
        mEffectiveTable.put(name, Integer.toString(val));
        return val;
    }
    
    public double getDouble(String name, double defaultVal) {
        double val = getDoubleImpl(name, defaultVal);
        mEffectiveTable.put(name, Double.toString(val));
        return val;
    }
    
    private int getIntImpl(String name, int defaultVal) {
        String value = (String)mValueTable.get(name);
        if (value == null) {
            System.err.println("No Value for " + name + " = " + defaultVal);
            return defaultVal;
        }
        int res;
        try {
            res = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid int at " + name);
            return defaultVal;
        }
        if (res != defaultVal) {
            System.err.println(name + ": default does not match real value");
        }
        return res;
    }
    
    private double getDoubleImpl(String name, double defaultVal) {
        String value = (String)mValueTable.get(name);
        if (value == null) {
            System.err.println("No Value for " + name + " = " + defaultVal);
            return defaultVal;
        }
        double res;
        try {
            res = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid int at " + name);
            return defaultVal;
        }
        if (res != defaultVal) {
            System.err.println(name + ": default does not match real value");
        }
        return res;
    }
    
    public HighStaticPWD pwdFromConst(
            String namePrefix, 
            double defaultP,
            double defaultW, 
            double defaultD,
            double defaultError) {
        return new HighStaticPWD(
                getDouble(namePrefix + "_p", defaultP),
                getDouble(namePrefix + "_w", defaultW),
                getDouble(namePrefix + "_d", defaultD),
                getDouble(namePrefix + "_error", defaultError));
    }
    
    public LinearPID pidFromConst(
            String namePrefix,
            double defaultP,
            double defaultI,
            double defaultD,
            double defaultError) {
        return new LinearPID(
                getDouble(namePrefix + "_p", defaultP),
                getDouble(namePrefix + "_i", defaultI),
                getDouble(namePrefix + "_d", defaultD),
                getDouble(namePrefix + "_error", defaultError));
    }
    
    public LinearPIDFF pidFFFromConst(
            String namePrefix,
            double defaultP,
            double defaultI,
            double defaultD,
            double defaultError,
            double defaultFF) {
        return new LinearPIDFF(
                getDouble(namePrefix + "_p", defaultP),
                getDouble(namePrefix + "_i", defaultI),
                getDouble(namePrefix + "_d", defaultD),
                getDouble(namePrefix + "_error", defaultError),
                getDouble(namePrefix + "_ff", defaultFF));
    }
    
    
}
