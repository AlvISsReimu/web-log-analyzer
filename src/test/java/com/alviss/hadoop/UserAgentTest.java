package com.alviss.hadoop;

import com.kumkee.userAgent.UserAgent;
import com.kumkee.userAgent.UserAgentParser;

public class UserAgentTest {

    public static void main(String[] args) {
        String source = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C)";
        UserAgentParser userAgentParser = new UserAgentParser();
        UserAgent userAgent = userAgentParser.parse(source);

        String browser = userAgent.getBrowser();
        String engine = userAgent.getEngine();
        String engineVersion = userAgent.getEngineVersion();
        String os = userAgent.getOs();
        String platform = userAgent.getPlatform();
        String version = userAgent.getVersion();
        boolean isMobile = userAgent.isMobile();

        System.out.println(browser +  ", " + engine +  ", " + engineVersion +  ", " + os +  ", " + platform +  ", " + version +  ", " + isMobile);
    }

}
