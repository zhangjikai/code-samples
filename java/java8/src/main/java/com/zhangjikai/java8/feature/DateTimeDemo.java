package com.zhangjikai.java8.feature;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * @author Jikai Zhang
 * @date 2018-05-06
 */
public class DateTimeDemo {
    
    public static void basic() {
        // 日期
        LocalDate date = LocalDate.of(2018, 4, 22);
        
        // 时间
        LocalTime time = LocalTime.now();
        
        // 日期和时间, api 和日期和时间的类似
        LocalDateTime dateTime = LocalDateTime.now();
        
        // 获取年月日
        System.out.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth());
        
        // 获取星期信息
        DayOfWeek dow = date.getDayOfWeek();
        System.out.println(dow.name() + " " + dow.getValue());
        
        // 获取当月的天数以及是否是闰年
        System.out.println(date.lengthOfMonth() + " " + date.isLeapYear());
        
        // 获得当天日期
        LocalDate today = LocalDate.now();
        
        // 使用 TemporalField 字段获取值
        int year = date.get(ChronoField.YEAR);
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);
        
        // 获取时分秒
        System.out.println(time.getHour() + " " + time.getMinute() + " " + time.getSecond());
        
        // 将字符串转为日期
        date = LocalDate.parse("2018-03-18");
    }
    
    public static void advanced() {
        // Duration 时间差
        Duration threeMinutes = Duration.ofMinutes(3);
        System.out.println(threeMinutes.getSeconds());
        
        // 获取两个时间点的时间差
        Duration duration = Duration.between(LocalTime.now(), LocalTime.of(20, 12));
        System.out.println(duration.getSeconds());
        
        // Period 日期差
        Period tenDays = Period.ofDays(10);
        System.out.println(tenDays.getDays());
        
        // 获取两个日期的时间差
        Period period = Period.between(LocalDate.now(), LocalDate.parse("2018-05-03"));
        System.out.println(period.getDays());
        
        // 将当前 period 对象添加到指定的日期上
        LocalDate date = (LocalDate) period.addTo(LocalDate.now());
        System.out.println(date);
        
        // 修改 LocalDate 属性，绝对值
        date = LocalDate.now();
        date = date.withYear(2011);
        System.out.println(date);
        
        // 修改 LocalDate 属性，相对值
        date = LocalDate.now();
        date = date.plusWeeks(1);
        System.out.println(date);
        
        // 使用 TemporalAdjusters 进行复杂的日期操作，例如定位到当前月份的最后一天
        date = LocalDate.now();
        date = date.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(date);
        
        // 自定义 TemporalAdjusters, 下面代码功能是定位到下一个工作日
        date = LocalDate.now();
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);
        
        // 使用 ofDateAdjuster 创建自定义 TemporalAdjuster 对象
        // TemporalAdjuster nextWorkingDay=TemporalAdjusters.ofDateAdjuster();
    }
    
    public static void format() {
        LocalDate date = LocalDate.now();
        
        // 20180506
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));
        // 2018-05-06
        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        
        // 自定义格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(date.format(formatter));
        
        // 创建本地化的格式
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d.MMMMyyyy", Locale.ITALIAN);
        System.out.println(date.format(italianFormatter));
        
    }
    
    public static void zone() {
        // 使用不同的时区
        ZoneId americaZone = ZoneId.of("America/Los_Angeles");
        Instant time = Instant.now();
        ZonedDateTime americaTime = time.atZone(americaZone);
        System.out.println(americaTime);
        
        
        // 使用时区的时间差值
        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        OffsetDateTime dateTimeNewYork = OffsetDateTime.of(LocalDateTime.now(), newYorkOffset);
        System.out.println(dateTimeNewYork);
        
        ZoneId chinaId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime chinaDateTime = dateTimeNewYork.atZoneSameInstant(chinaId);
        System.out.println(chinaDateTime);
    }
    
    public static void main(String[] args) {
        // basic();
        // advanced();
        // format();
        zone();
    }
}
