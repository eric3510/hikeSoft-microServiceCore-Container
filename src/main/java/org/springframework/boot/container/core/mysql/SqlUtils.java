package org.springframework.boot.container.core.mysql;

public class SqlUtils{
    protected static String getOperatingFirst(String sql){
        String[] operatings = {"in", "IN"};
        sql = sql.trim();
        int min = sql.length();
        boolean bool = false;
        String returnOperating = operatings[0];
        for(String operating : operatings){
            int indexOf = sql.indexOf(operating);
            if(indexOf != -1 && indexOf < min){
                returnOperating = operating;
                bool = true;
            }
        }
        return bool ? returnOperating : "";
    }


    public static void main(String[] args){
    }
}
