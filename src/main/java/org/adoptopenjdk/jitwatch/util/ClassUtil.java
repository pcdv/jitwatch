/*
 * Copyright (c) 2013, 2014 Chris Newland.
 * Licensed under https://github.com/AdoptOpenJDK/jitwatch/blob/master/LICENSE-BSD
 * Instructions: https://github.com/AdoptOpenJDK/jitwatch/wiki
 */
package org.adoptopenjdk.jitwatch.util;

import org.adoptopenjdk.jitwatch.core.JITWatchConstants;
import org.adoptopenjdk.jitwatch.loader.DisposableURLClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class ClassUtil
{
	private static DisposableURLClassLoader disposableClassLoader = new DisposableURLClassLoader(new ArrayList<URL>());

	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	private ClassUtil()
	{
	}

	public static void initialise(final List<URL> urls)
	{
		if (JITWatchConstants.DEBUG_LOGGING)
		{
			for (URL url : urls)
			{
				logger.debug("Adding classpath to DisposableURLClassLoader {}", url);
			}
		}
		
		disposableClassLoader = new DisposableURLClassLoader(urls);
	}

	public static Class<?> loadClassWithoutInitialising(String fqClassName) throws ClassNotFoundException
	{
		return Class.forName(fqClassName, false, disposableClassLoader);
	}
	
	public static Class<?> loadClassWithoutInitialising(String fqClassName, ClassLoader classLoader) throws ClassNotFoundException
	{
		return Class.forName(fqClassName, false, classLoader);
	}
}