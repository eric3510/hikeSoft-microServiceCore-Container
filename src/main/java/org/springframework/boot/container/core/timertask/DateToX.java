package org.springframework.boot.container.core.timertask;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/***
 * @author 王强 Email : eric3510@foxmail.com
 * @version 创建时间：2017/12/5
 * DateToX
 */
@Data
public class DateToX implements Serializable{
    private long milliSecond;

    public DateToX(TimeUnit timeUnit, long duration){
        this.milliSecond = timeUnit.toMillis(duration);
    }

    public static void main(String[] args){
        DateToX dateToX = new DateToX(TimeUnit.DAYS, 1);
    }
}
