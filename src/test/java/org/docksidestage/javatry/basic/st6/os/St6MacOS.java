package org.docksidestage.javatry.basic.st6.os;

public class St6MacOS extends St6OperationSystem {
    public St6MacOS(String loginId) {
        super(loginId);
    }
    
    public String getOsName() {
        return "Mac";
    }

    @Override
    protected String getFileSeparator() {
        return "/";
    }

    @Override
    protected String getUserDirectory(String loginId) {
        return "/Users/" + loginId;
    }
}
