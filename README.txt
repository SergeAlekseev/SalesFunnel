Первый запуск

1) установить, если не установлено https://www.oracle.com/java/technologies/javase-jdk14-downloads.html#license-lightbox
2) установить, если не установлено https://apache-mirror.rbc.ru/pub/apache/tomcat/tomcat-9/v9.0.37/bin/apache-tomcat-9.0.37.exe
3) создать базу в postgreSQL с названием "SalesFunnel" и восстановить бекап из папки "SalesFunnel_PostgreSQL_dump_standartData"
4) импортировать проект в idea
5) в классе "FunnelController" установить в значенни костанты "PASSWORD" свой пароль от postgreSQL
6) собрать проект
7) запустить задачу maven war:exploded
8) скопировать папку SalesFunnel-1.0-SNAPSHOT из target
9) вставить в tomcat\webapps
10) удалить ROOT папку, переименовать SalesFunnel-1.0-SNAPSHOT в ROOT
11) запустить tomcat (tomcat\bin\Tomcat9.exe)

Всё должно работать (http://localhost:8080/settings, http://localhost:8080/stats...)
Для обновления версии, начинать с 6 пункта
