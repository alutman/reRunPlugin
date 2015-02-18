package rerun.extension;

import org.springframework.util.StringUtils;

import java.util.StringTokenizer;

/**
 * Created by alutman on 10-Feb-15.
 */
public class ReRunParameter {
    private String key;
    private String value;

    public ReRunParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int height() {
        int newLines = StringUtils.countOccurrencesOf(value, "\n");
        int plusOne = 1;

        int wraps = 0;
        StringTokenizer st = new StringTokenizer(value, "\n");
        while(st.hasMoreTokens()) {
            String next = st.nextToken();
            while(next.length() > 100) {
                wraps++;
                next = next.substring(100, next.length());
            }
        }
        return newLines + wraps + plusOne;
    }
}
