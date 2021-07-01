package dev.peertosir.difftoolui.model;

public class Diff {
    private String service;
    private String host1;
    private String host2;
    private String excludeRegExps;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHost1() {
        return host1;
    }

    public void setHost1(String host1) {
        this.host1 = host1;
    }

    public String getHost2() {
        return host2;
    }

    public void setHost2(String host2) {
        this.host2 = host2;
    }

    public String getExcludeRegExps() {
        return excludeRegExps;
    }

    public void setExcludeRegExps(String excludeRegExps) {
        this.excludeRegExps = excludeRegExps;
    }
}
