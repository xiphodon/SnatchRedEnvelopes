package com.qiyun.cyt.snatchredenvelopes;

/**
 * Log日志工具类
 *
 * Created by GuoChang on 2016/8/9.
 */
public class Log {
	/** 日志输出级别NONE */
	public static final int LEVEL_NONE = 0;
	/** 日志输出级别E */
	public static final int LEVEL_ERROR =1;
	/** 日志输出级别W */
	public static final int LEVEL_WARN = 2;
	/** 日志输出级别I */
	public static final int LEVEL_INFO = 3;
	/** 日志输出级别D */
	public static final int LEVEL_DEBUG = 4;
	/** 日志输出级别V */
	public static final int LEVEL_VERBOSE = 5;

	/** 日志输出时的TAG */
	private static String mTag = "LogUtils";
	/** 是否允许输出log */
	private static int mDebuggable = LEVEL_VERBOSE;

	/** 以级别为 d 的形式输出LOG */
	public static void v(String tag, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			android.util.Log.v(tag, msg);
		}
	}

	/** 以级别为 d 的形式输出LOG */
	public static void d(String tag, String msg) {
		if (mDebuggable >= LEVEL_DEBUG) {
			android.util.Log.d(tag, msg);
		}
	}

	/** 以级别为 i 的形式输出LOG */
	public static void i(String tag, String msg) {
		if (mDebuggable >= LEVEL_INFO) {
			android.util.Log.i(tag, msg);
		}
	}

	/** 以级别为 w 的形式输出LOG */
	public static void w(String tag, String msg) {
		if (mDebuggable >= LEVEL_WARN) {
			android.util.Log.w(tag, msg);
		}
	}

	/** 以级别为 w 的形式输出Throwable */
	public static void w(String tag, Throwable tr) {
		if (mDebuggable >= LEVEL_WARN) {
			android.util.Log.w(tag, "", tr);
		}
	}

	/** 以级别为 w 的形式输出LOG信息和Throwable */
	public static void w(String tag, String msg, Throwable tr) {
		if (mDebuggable >= LEVEL_WARN && null != msg) {
			android.util.Log.w(tag, msg, tr);
		}
	}

	/** 以级别为 e 的形式输出LOG */
	public static void e(String tag, String msg) {
		if (mDebuggable >= LEVEL_ERROR) {
			android.util.Log.e(tag, msg);
		}
	}

	/** 以级别为 e 的形式输出Throwable */
	public static void e(String tag, Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR) {
			android.util.Log.e(tag, "", tr);
		}
	}

	/** 以级别为 e 的形式输出LOG信息和Throwable */
	public static void e(String tag, String msg, Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR && null != msg) {
			android.util.Log.e(tag, msg, tr);
		}
	}
}
