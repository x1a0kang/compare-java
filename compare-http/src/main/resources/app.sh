#!/bin/bash

#链接文件
source /etc/profile
export LC_ALL=en_US.UTF-8
Xms="@Xms@"
Xmx="@Xmx@"

#JAR包路径
projName="@gradleProjName@"
projVersion="@gradleProjVersion@"
Jar_Name="${projName}-${projVersion}.jar"

local_ip=$(ifconfig -a | grep inet | grep -v 127.0.0.1 | grep -v inet6 | grep 10. | awk '{print $2}' | tr -d "addr:")
sed -i "s/LOCAL_IP=.*/LOCAL_IP=${local_ip}/g" $(
  cd $(dirname $0)
  pwd
)/config/application-*.properties
sed -i "s/\r//g" $(
  cd $(dirname $0)
  pwd
)/config/application-*.properties

WORKER_ID=$(grep "$local_ip" "config/workid_mapping.txt" | awk '{print $2}')
sed -i "s|workerId=.*|workerId=${WORKER_ID}|g" "$(cd $(dirname $0) && pwd)/config/application.properties"

# 查询程序运行pid,运行的程序名
get_run_state() {
  pid=$(jps -l | grep ${projName} | awk '{print $1}')
  if [ -z "$pid" ]; then
    return 0
  else
    run_jar_name=$(jps -l | grep ${projName} | awk '{print $2}')
    return 1
  fi
}


check_run_state_with_retry() {
  max_attempts=3
  attempt=1

  while [ $attempt -le $max_attempts ]; do
    #echo "get_run_state 第 $attempt 次尝试"
    sleep 2
    get_run_state
    if [ $? -eq 1 ]; then
      return 1
    fi
    attempt=$((attempt + 1))
  done

  return 0
}


# 启动程序
start() {
  get_run_state
  if [ -n "${pid}" ]; then
    echo "程序 ${run_jar_name} 已经运行, pid = ${pid} [Failed]"
    return 0
  fi

  # pid为空启动程序
  nohup java ${JAVA_OPTS} -server -Xms${Xms} -Xmx${Xmx} -Xbootclasspath/a:./config -jar ${Jar_Name} -Dfile.encoding=UTF-8 >/dev/null 2>&1 &
  check_run_state_with_retry

  if [ -n "$pid" ]; then
    echo "程序 ${Jar_Name} 启动成功, pid = $pid [OK]"
  else
    echo "程序 ${Jar_Name} 启动失败!请重新启动 [Failed]"
  fi
}

# 停止程序
stop() {
  get_run_state
  if [ -z "${pid}" ]; then
    echo "程序 ${Jar_Name} 未运行 [OK]"
    return 0
  fi

  kill -9 ${pid}
  sleep 2
  get_run_state

  if [ -z "$pid" ]; then
    echo "程序 ${run_jar_name} 停止成功 [OK]"
  else
    echo "程序 ${run_jar_name} 停止失败, pid = $pid [Failed]"
  fi
}

# 查询程序运行状态
status() {
  get_run_state
  if [ -n "$pid" ]; then
    echo "程序 ${run_jar_name} 正在运行, pid = $pid"
  else
    echo "程序 ${Jar_Name} 未运行"
  fi
}

# 重启程序
restart() {
  stop
  start
}

# 根据输入执行命令
case "$1" in
"start")
  start
  ;;
"stop")
  stop
  ;;
"status")
  status
  ;;
"restart")
  restart
  ;;
*)
  echo "$1 操作不支持，仅支持[start|stop|status|restart]"
  ;;
esac
