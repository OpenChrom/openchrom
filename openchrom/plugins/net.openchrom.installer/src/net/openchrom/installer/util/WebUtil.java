/*******************************************************************************
 * Copyright (c) 2009, 2024 Tasktop Technologies, Polarion Software and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author i.burilo
 */
public class WebUtil {

	private static final int STATUS_OK = 200;
	protected static Authenticator authenticator;
	protected static boolean isProxyInitialized;

	/**
	 * Download an HTTP-based resource
	 * 
	 * @param target
	 *            the target file to which the content is saved
	 * @param sourceUrl
	 *            the web location of the content
	 * @param monitor
	 *            the monitor
	 * @throws IOException
	 *             if a network or IO problem occurs
	 */
	public static void downloadResource(File target, URL sourceUrl, IProgressMonitor monitor) throws IOException {

		monitor.beginTask("retrievingUrl", IProgressMonitor.UNKNOWN);
		try {
			HttpURLConnection con = (HttpURLConnection)sourceUrl.openConnection();
			int result = con.getResponseCode();
			if(result == WebUtil.STATUS_OK) {
				InputStream in = con.getInputStream();
				try {
					in = new BufferedInputStream(in);
					OutputStream out = new BufferedOutputStream(new FileOutputStream(target));
					try {
						int i;
						while((i = in.read()) != -1) {
							out.write(i);
						}
					} catch(IOException e) {
						// avoid partial content
						out.close();
						target.delete();
						throw e;
					} finally {
						out.close();
					}
				} finally {
					if(in != null) {
						try {
							in.close();
						} catch(IOException ie) {
							// ignore
						}
					}
				}
			} else {
				throw new IOException("cannotDownload");
			}
		} finally {
			monitor.done();
		}
	}

	public static void setAuthenticator(Authenticator authenticator) {

		WebUtil.authenticator = authenticator;
	}
}
