package org.example.back.demos.model;

/**
 * @author ljn
 * @Description: 用户角色
 * @date 2024/4/4/17:15
 */

public enum Role {
    TransCompany, //运输公司
    PerChaseCompany;// 采购公司

    public static final class permission {
        public static final int TRANS = 1;
        public static final int PER_CHASE = 2;
    }

    public static int getRole(Role role) {
        switch (role){
            case TransCompany:
                return 1;
            case PerChaseCompany:
            default:
                return 2;
        }
    }
    
    public static Role getRole(int role) {
        switch (role){
            case 1:
                return TransCompany;
            case 2:
            default:
                return PerChaseCompany;
        }
    }
    
    public boolean isTransCompany() {
        return this == Role.TransCompany;
    }
    
    public boolean isPerChaseCompany() {
        return this == Role.PerChaseCompany;
    }
}
