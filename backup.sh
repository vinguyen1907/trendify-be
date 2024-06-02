#!/bin/bash

DB_CONTAINER_NAME=trendify-be-postgres-1
DB_USER=user
DB_NAME=trendify
#BACKUP_PATH=/Users/tienvi/Development/UIT/backup
BACKUP_PATH=/var/lib/postgresql/data/backup
#LOG_PATH=/var/lib/postgresql/data/log

# Tạo thư mục backup và log nếu chưa tồn tại
#docker exec -t $DB_CONTAINER_NAME mkdir -p $BACKUP_PATH
#docker exec -t $DB_CONTAINER_NAME mkdir -p $LOG_PATH

# Define name of backup file
BACKUP_FILE=$BACKUP_PATH/db_backup$(date +\%Y-\%m-\%d).backup
# Tên file log
#LOG_FILE=$LOG_PATH/backup_$(date +\%Y-\%m-\%d).log
#ERROR_LOG_FILE=$LOG_PATH/backup_error_$(date +\%Y-\%m-\%d).log

# Execute pg_dump command in the Docker container
docker exec -t $DB_CONTAINER_NAME pg_dump -U $DB_USER -F c -b -v -f $BACKUP_FILE $DB_NAME
#> $LOG_FILE 2> $ERROR_LOG_FILE

# Check and remove old backup files (older than 7 days)
find $BACKUP_PATH -type f -name "*.backup" -mtime +7 -exec rm {} \;
