#!/bin/bash

docker-compose down -v
rm -rf ./master/data/*
rm -rf ./slave/data/*
chmod 444 ./slave/conf/mysql.conf.cnf
chmod 444 ./master/conf/mysql.conf.cnf
docker-compose build
docker-compose up -d

until docker exec mysql_master sh -c 'export MYSQL_PWD=Password4; mysql -u root -e ";"'
do
    echo "Waiting for mysql_master database connection..."
    sleep 4
done

#priv_stmt='CREATE USER "app_user"@"%" IDENTIFIED BY "Password4"; GRANT REPLICATION SLAVE ON *.* TO "app_user"@"%"; FLUSH PRIVILEGES;'
#priv_stmt='GRANT REPLICATION SLAVE ON *.* TO "app_user"@"%"; FLUSH PRIVILEGES;'
#docker exec mysql_master sh -c "export MYSQL_PWD=Password4; mysql -u root -e '$priv_stmt'"
sql_slave_user='CREATE USER "mydb_slave_user"@"%" IDENTIFIED BY "mydb_slave_pwd"; GRANT REPLICATION SLAVE ON *.* TO "mydb_slave_user"@"%"; FLUSH PRIVILEGES;'
docker exec mysql_master sh -c "mysql -u root -pPassword4 -e '$sql_slave_user'"

until docker-compose exec mysql_slave sh -c 'export MYSQL_PWD=Password4; mysql -u root -e ";"'
do
    echo "Waiting for mysql_slave database connection..."
    sleep 4
done

MS_STATUS=`docker exec mysql_master sh -c 'export MYSQL_PWD=Password4; mysql -u root -e "SHOW BINARY LOG STATUS"'`
CURRENT_LOG=`echo $MS_STATUS | awk '{print $6}'`
CURRENT_POS=`echo $MS_STATUS | awk '{print $7}'`

start_slave_stmt="CHANGE REPLICATION SOURCE TO SOURCE_HOST='mysql_master',SOURCE_USER='mydb_slave_user',SOURCE_PASSWORD='mydb_slave_pwd', GET_SOURCE_PUBLIC_KEY=1, SOURCE_LOG_FILE='$CURRENT_LOG',SOURCE_LOG_POS=$CURRENT_POS; START REPLICA;"
start_slave_cmd='export MYSQL_PWD=Password4; mysql -u root -e "'
start_slave_cmd+="$start_slave_stmt"
start_slave_cmd+='"'
echo "$start_slave_cmd"
docker exec mysql_slave sh -c "$start_slave_cmd"

docker exec mysql_slave sh -c "export MYSQL_PWD=Password4; mysql -u root -e 'SHOW REPLICA STATUS \G'"