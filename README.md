运行说明

Step 1. 修改Maven setting, 使用阿里云Nexus仓库
`<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror>`

Step 2. 运行数据库脚本test/resources/schema.sql, 创建store数据库，不需要创建表

Step 3. 在BaseTest.java中右击运行All Tests 就可以创建表格和插入默认数据