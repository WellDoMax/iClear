package com.laowulao.noads.db;

public class Table {

    public static final String NAME = "record";

    public static final String PACKAGE_NAME = "package_name";

    public static final String APP_NAME = "app_name";

    public static final String TIMES = "times";

    public static final String MODIFY_TIME = "modify_time";

    public static final int ID_PACKAGE_NAME = 0;
    public static final int ID_APP_NAME = 1;
    public static final int ID_TIMES = 3;
    public static final int ID_MODIFY_TIME = 2;

    public static final String CREATE_TABLE = "create table if not exists " + NAME + "(" +
            PACKAGE_NAME + " text primery key, " +
            APP_NAME + " text, " +
            TIMES + " integer, " +
            MODIFY_TIME + " text)";

}
