package com.alviss.hadoop;

import com.alviss.util.CharPosUtil;
import com.kumkee.userAgent.UserAgent;
import com.kumkee.userAgent.UserAgentParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserAgentTest {

    @Test
    public void testCharPosUtil() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("weblog/100_access.log")));
        String line = reader.readLine();
        if (line != null && !line.equals("")) {
            int index = CharPosUtil.getCharPos(line, "\"", 7) + 1;
            String subStr = line.substring(index);
            System.out.println(subStr);
            UserAgentParser userAgentParser = new UserAgentParser();
            UserAgent userAgent = userAgentParser.parse(subStr);
            System.out.println(userAgent.getBrowser());
        }
    }

    @Test
    public void testUserAgentParser() {
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
