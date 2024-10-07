package com.scaffolding.optimization;

public class QuickDropConstants {
    public static final String QUICK_DROP_REASON_CODE_UNEXPECTED_EXCEPTION = "there was an unexpected exception";
    public enum QuickDropErrorCodes {

        USER_ALREADY_EXISTS("The user already exists"),
        USER_NOT_FOUND("The user was not found"),

        EMAIL_NOT_SENT("The registration email was not sent"),

        USER_NOT_CREATED("The user was not created due to invalid data sent"),

        USER_NOT_UPDATED("The user was not updated"),

        USER_NOT_DELETED("The user was not deleted"),

        USER_NOT_AUTHENTICATED("The user was not authenticated"),
        ACTION_UNSUPPORTED("The action is not supported"),
        INVALID_DATA("The data sent is invalid"),
        EMAIL_ALREADY_EXISTS("The email already exists" +
                " please use another email address "+ " or contact the administrator for more details");

        String description;

        public String getDescription() {
            return this.description;
        }

        QuickDropErrorCodes(String description) {
            this.description = description;
        }
    }

    public enum QuickDropRoles {
        ADMIN("ADMIN"),
        CUSTOMER("CUSTOMER"),
        DRIVER("DRIVER");
        private String role;

        QuickDropRoles(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }

    }

    public enum QuickDropStatus{
        CREATED("CREADO"),
        DELIVERED("ENTREGADO"),

        IN_TRANSIT("EN_TRANSITO"),

        CANCELLED("CANCELADO"),

        COLLECTED("EN_BODEGA");

        private String status;

        QuickDropStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }


    }

    public enum QuickDropUserTypes {
        CUSTOMER("externo"),
        EMPLOYEE("interno");
        private String userType;
        QuickDropUserTypes(String userType) {
            this.userType = userType;
        }

        public String getUserType() {
            return userType;
        }

    }

    public enum operationTypes {
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        READ("READ");
        private String operation;
        operationTypes(String operation) {
            this.operation = operation;
        }
        public String getOperationType() {
            return operation;
        }

    }

    public enum statusTypes {
        CREATED("Creado");

        String status;

        statusTypes(String status) {
            this.status = status;
        }

        public String getStatusType() {
            return status;
        }

    }
}
