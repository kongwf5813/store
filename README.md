#运行说明

Step 1. 修改Maven setting, 使用阿里云Nexus仓库
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror>
    
Step 2. 运行数据库脚本sql/store.sql, 创建数据库

Step 3. 可以通过设置spring.jpa.hibernate.ddl-auto=update 进行更新表操作，每次当修改entity，hibernate会自动创建表或者更新表结构