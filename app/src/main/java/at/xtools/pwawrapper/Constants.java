package at.xtools.pwawrapper;

public class Constants {
    public Constants(){}
    // Root page
    public static final String WEBAPP_URL = "https://www.leasingrechnen.at/";
    public static final String WEBAPP_HOST = "leasingrechnen.at"; // used for checking Intent-URLs

	// User Agent tweaks
    public static final boolean POSTFIX_USER_AGENT = true; // set to true to append USER_AGENT_POSTFIX to user agent
    public static final boolean OVERRIDE_USER_AGENT = false; // set to true to use USER_AGENT instead of default one
    public static final String USER_AGENT_POSTFIX = "AndroidApp"; // useful for identifying traffic, e.g. in Google Analytics
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36";
	
	// Constants
    // window transition duration in ms
    public static final int SLIDE_EFFECT = 2200;
    // show your app when the page is loaded XX %.
    // lower it, if you've got server-side rendering (e.g. to 35),
    // bump it up to ~98 if you don't have SSR or a loading screen in your web app
    public static final int PROGRESS_THRESHOLD = 65;
    // turn on/off mixed content (both https+http within one page) for API >= 21
    public static final boolean ENABLE_MIXED_CONTENT = true;
}
