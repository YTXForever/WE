#!/bin/bash
#

#Global Variables
HOME_DIR="/root/shell"
FILE_NAME="process.cfg"
this_pid=$$

    #if [ ! -e $HOME_DIR/$FILE_NAME ];then
    #    echo "$HOME_DIR/$FILE_NAME is not exist,Please 确认一下"
    #    exit 1
    #fi

#Func : Get All Groups
function get_all_group
{
    echo `sed -n '/\[GROUP_LIST\]/,/\[.*\]/p' $HOME_DIR/$FILE_NAME |grep -v -E '^$|\[.*\]'`
}

#Func : Get All process
function get_all_process
{
	for g in `get_all_group` 
	do
		echo `get_all_process_by_group $g`
	done
}

#Func : Get All Process By Group 
function get_all_process_by_group
{
	echo `sed -n "/\[$1\]/,/\[.*\]/p" $HOME_DIR/$FILE_NAME |grep -v -E '^$|\[.*\]'`
}

#Get pids by process Name
function get_pid_by_name
{
	if [ $# -ne 1 ];then
		return 1
	fi
	echo `ps -ef|grep $1|grep -v grep|grep -v $0|grep -v $this_pid|awk '{print $2}'`
}

#Func: get info by pid
function get_info_by_pid
{
	pro_status=""
	pro_cpu=""
	pro_mem=""
	pro_start_time=""
	
	if [ `ps -ef $1|awk -v pid=$1 '$2==pid{print }'|wc -l` -ge 0 ]; then
		pro_status="RUNNING"
	else
		pro_status="STOPPED"
	fi
	pro_cpu=`ps -aux $1|awk -v pid=$1 '$2==pid{print $3}'`
	pro_mem=`ps -aux $1|awk -v pid=$1 '$2==pid{print $4}'`
	pro_start_time=`ps -p $1 -o lstart|grep -v STARTED`
}

#Func: format print process info
function format_print
{
	ps -ef|grep $1|grep -v grep |grep -v $0|grep -v $this_pid >/dev/null 2>&1
	if [ $? -eq 0 ];then
		for pid in `get_pid_by_name $1`;do
			get_info_by_pid $pid
			 awk -v p_name=$1 -v g_name=$2 -v p_status=$pro_status -v p_pid=$pid -v p_cpu=$pro_cpu -v p_mem=$pro_mem -v p_start_time="$pro_start_time" 'BEGIN{printf "%-20s%-12s%-10s%-6s%-7s%-10s%-20s\n",p_name,g_name,p_status,p_pid,p_cpu,p_mem,p_start_time}'
		done
	else
		awk -v p_name=$1 -v g_name=$2 'BEGIN{printf "%-20s%-12s%-10s%-6s%-7s%-10s%-20s\n",p_name,g_name,"STOPPED","NULL","NULL","NULL","NULL"}'
	fi
}

#Func: Get group name by process name
function get_group_by_process
{
	for group_name in `get_all_group`;do
		for process_name in `get_all_process_by_group $group_name`;do
			if [ $process_name == $1 ];then
				echo $group_name
			fi
		done
	done
}
function process_in_cfg
{
	for process in `get_all_process`;do
		if [ $process == $1 ];then
			return
		fi
	done
	echo "Process $1 not exist..."
	return 1
}
function group_in_cfg
{
	for group in `get_all_group`;do
		if [ $group == $1 ];then
			return
		fi
	done
	echo "Group $1 not exist..."
	return 1
}

if [ $# -ne 0 ];then
	if [ $1 == "-g" ];then
		shift
		for group_name in $@;do
			group_in_cfg $group_name || continue
			for process_name in `get_all_process_by_group $group_name`;do
				format_print $process_name $group_name
			done
		done
	else
		for process_name in $@;do
			process_in_cfg $process_name || continue
			format_print $process_name `get_group_by_process $process_name`			
		done
	fi
else
	for process_name in `get_all_process`;do
		format_print $process_name `get_group_by_process $process_name`
	done
fi












