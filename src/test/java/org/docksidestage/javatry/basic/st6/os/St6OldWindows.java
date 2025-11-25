package org.docksidestage.javatry.basic.st6.os;

public class St6OldWindows extends St6OperationSystem {
    public St6OldWindows(String loginId) {
        super(loginId);
    }
    
    public String getOsName() {
        return "OldWindows";
    }

    @Override
    protected String getFileSeparator() {
        return "\\";
    }

    @Override
    protected String getUserDirectory(String loginId) {
        return "/Documents and Settings/" + loginId;
    }
}
