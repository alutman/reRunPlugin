package rerun.extension;

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

}
