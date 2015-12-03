/**********************************************************************
Copyright (c) 2012-2014 Alexander Kerner. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ***********************************************************************/

package net.sf.jmgf.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MGFDialectProteinPilot {

    private final static Logger log = LoggerFactory.getLogger(MGFDialectProteinPilot.class);

    private String title;

    public final static String REGEX_SPECTRUM = ".*Locus:([^\\s]+)[\\s].*";

    public MGFDialectProteinPilot() {

    }

    public MGFDialectProteinPilot(final String title) {
        this.title = title;
    }

    public String getSpectrum() {
        final Pattern p;
        p = Pattern.compile(REGEX_SPECTRUM);
        final Matcher m = p.matcher(getTitle());
        final boolean b = m.matches();
        if (b) {
            return m.group(1).trim();
        } else {
            if (log.isInfoEnabled()) {
                log.info("no spectrum for " + title);
            }
            return null;
        }
    }

    public synchronized String getTitle() {
        return title;
    }

    public synchronized void setTitle(final String title) {
        this.title = title;
    }

}
