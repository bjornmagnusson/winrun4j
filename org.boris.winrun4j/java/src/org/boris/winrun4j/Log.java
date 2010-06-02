/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.winrun4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Log to the launcher log.
 */
public class Log
{
    /**
     * Info log.
     * 
     * @param msg.
     */
    public static void info(String msg) {
        LogIt(Level.INFO.level, "[info]", msg);
    }

    /**
     * Warning log.
     * 
     * @param msg.
     */
    public static void warning(String msg) {
        LogIt(Level.WARN.level, "[warn]", msg);
    }

    /**
     * Error log.
     * 
     * @param msg.
     */
    public static void error(String msg) {
        LogIt(Level.ERROR.level, " [err]", msg);
    }

    /**
     * Error log.
     */
    public static void error(Throwable t) {
        error(t.getMessage());
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        error(sw.toString());
    }

    /**
     * Internal log.
     * 
     * @param level.
     * @param msg.
     */
    private static void LogIt(int level, String marker, String msg) {
        long markerPtr = NativeHelper.toNativeString(marker, false);
        long msgPtr = NativeHelper.toNativeString(msg, false);
        NativeHelper.call(0, "Log_LogIt", level, markerPtr, msgPtr);
        NativeHelper.free(markerPtr, msgPtr);
    }

    public static class Level
    {
        public static final Level INFO = new Level(0, "info");
        public static final Level WARN = new Level(1, "warning");
        public static final Level ERROR = new Level(2, "error");
        public static final Level NONE = new Level(3, "none");

        private int level;
        private String text;

        private Level(int level, String text) {
            this.level = level;
            this.text = text;
        }

        public int getLevel() {
            return level;
        }

        public String getText() {
            return text;
        }
    }
}
