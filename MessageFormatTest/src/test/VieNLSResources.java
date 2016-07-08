/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2012. All Rights Reserved.
 *
 * U.S. Government Users Restricted Rights - 
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 *******************************************************************************/
package test;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * NLS Resource class that encapsulates the access to localised resources
 * obtaining the locale either from the thread local in the case of browser
 * requests or the server if no locale is available.
 */
public class VieNLSResources {

	private static final VieNLSResources instance = new VieNLSResources();

	private VieNLSResources() {
	}

	public static VieNLSResources getInstance() {
		return instance;
	}

	public ResourceBundle getBundle() {
		// Locale locale = LocaleThreadLocal.get();
		Locale locale = new Locale("fr");
		if (locale == null) {
			// failed to get the context local, fall back to the machine local
			locale = Locale.getDefault();
		}
		final ResourceBundle resource = ResourceBundle.getBundle(
				"test.vieMessages", locale);
		return resource;
	}

	public String get(final String key, final Object... messages) {
		return MessageFormat.format(getBundle().getString(key), messages);
	}
}