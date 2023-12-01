package com.acc.group4.Navigators.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class ValidationOfURL {

    // ** Validate the given URL
    public static boolean validate(String navigators_urlgiles) throws UnknownHostException {
        try {
            // ** Check for protocol (http or https)
            String navigators_protocolgiles = "http://";
            if (navigators_urlgiles.startsWith(navigators_protocolgiles) || navigators_urlgiles.startsWith("https://")) {
                // ** Create URL object and check for proper syntax
                URL navigators_urlObjgiles = new URL(navigators_urlgiles);

                // ** Check for valid host name
                if (navigators_urlObjgiles.getHost() != null) {
                    // ** Check for valid TLD (top level domain)
                    String navigators_tldgiles = navigators_urlObjgiles.getHost().substring(navigators_urlObjgiles.getHost().lastIndexOf(".") + 1);
                    if (navigators_tldgiles.matches("[a-zA-Z]{2,}")) {
                        // ** Check for file extension (if any)
                        String navigators_pathgiles = navigators_urlObjgiles.getPath();
                        if (navigators_pathgiles != null && !navigators_pathgiles.equals("")) {
                            String[] navigators_partsgiles = navigators_pathgiles.split("\\.");
                            if (navigators_partsgiles.length > 0) {
                                String navigators_extensiongiles = navigators_partsgiles[navigators_partsgiles.length - 1];
                                if (navigators_extensiongiles.matches("[a-zA-Z0-9]{2,}")) {
                                    // ** If all checks pass, return true
                                    return true;
                                }
                            }
                        } else {
                            // ** If all checks pass, return true
                            return true;
                        }
                    }
                }
            }
            // ** Return false if any check fails
            return false;
        } catch (MalformedURLException e) {
            // ** Handle MalformedURLException and print error message
            System.err.println("Malformed URL: " + navigators_urlgiles);
            return false;
        }
    }
}
