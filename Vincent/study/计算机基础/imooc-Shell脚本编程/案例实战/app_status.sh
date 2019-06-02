# !/bin/bash

# 全局变量
HOME_DIR="/root/xshell_script_1"
CONFIG_FILE="process.cfg"
THIS_PID=$$

# 获取所有组
function get_all_group
{
        G_LIST=`sed -n '/\[GROUP_LIST\]/,/\[.*\]/p' $HOME_DIR/$CONFIG_FILE | egrep -v "(^$|\[.*\])"`
        echo $G_LIST
}

# 判断group是否在配置中
function group_is_in_config
{
        for group in `get_all_group`
        do
                if [ $1 == $group ];then
                        return
                fi
        done
        return 1
}

# 获取某个组的所有进程
function get_all_process_by_group
{
        group_is_in_config $1
        if [ $? -eq 0 ];then
                P_LIST=`sed -n "/\[$1\]/,/\[.*\]/p" $HOME_DIR/$CONFIG_FILE | egrep -v "(^$|\[.*\])"`
                echo $P_LIST
        else
                echo "$1 not in config"
                # return 1
        fi
}

# 获取所有进程
function get_all_process
{
        for g in `get_all_group`
        do
                # echo $g
                echo `get_all_process_by_group $g`
        done
}

# 通过name获取pid
function get_pid_by_name
{
        if [ $# -ne 1 ];then
                echo "参数超过一个了"
                return 1
        else
                pids=`ps -ef | grep $1 | egrep -v "( grep | $0 )" | awk '{print $2}'`
                echo $pids
        fi
}

# 通过pid获取进程详细信息
function get_process_info_by_pid
{
        if [ `ps -ef | awk -v pid=$1 '$2==pid{print}' | wc -l` -eq 1 ];then
                pro_state=RUNNING
        else
                pro_state=STOPPED
        fi
        pro_cpu=`ps -aux | awk -v pid=$1 '$2==pid{print $3}'`
        pro_mem=`ps -aux | awk -v pid=$1 '$2==pid{print $4}'`
        pro_start_time=` ps -p $1 -o lstart | grep -v "STARTED"`
        # echo $pro_state $pro_cpu $pro_mem $pro_start_time
}

# 通过进程名获取组名
function get_group_by_process
{
        for group in `get_all_group`
        do
                for process in `get_all_process_by_group $group`
                do
                        if [ $1 == $process ];then
                                echo $group
                        fi
                done
        done
}

# 格式化打印
function format_print
{
        ps -ef | grep $1 | egrep -v "( grep | $THIS_PID )" &> /dev/null
        if [ $? -eq 0 ];then
                pids=`get_pid_by_name $1`
                for pid in $pids
                do
                        get_process_info_by_pid $pid
                        awk \
                                -v p_name=$1 \
                                -v g_name=$2 \
                                -v p_pid=$pid \
                                -v p_status=$pro_state \
                                -v p_cpu=$pro_cpu \
                                -v p_mem=$pro_mem \
                                -v p_start_time="$pro_start_time" \
                                'BEGIN{printf "%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",p_name,g_name,p_status,p_pid,p_cpu,p_mem,p_start_time}'
                done
        else
                awk \
                        -v p_name=$1 \
                        -v g_name=$2 \
                        'BEGIN{printf "%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",p_name,g_name,"STOPPED","NULL","NULL","NULL","NULL"}'
        fi
}

# 校验进程是否存在
function process_is_in_config
{
        for process in `get_all_process`
        do
                if [ $1 == $process ];then
                        return
                fi
        done
        echo "$1 进程不存在"
        return 1
}

# 主函数
if [ ! -e $HOME_DIR/$CONFIG_FILE ];then
        echo "$HOME_DIR/$CONFIG_FILE is not exist.. Pleasr Check.."
        exit 1
fi

awk 'BEGIN{printf "%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n","p_name","g_name","p_status","p_pid","p_cpu","p_mem","p_start_time"}'
if [ $# -gt 0 ];then
        if [ $1 == "-g" ];then
                shift
                for gn in $@
                do
                        for pn in `get_all_process_by_group $gn`
                        do
                                process_is_in_config $pn && format_print $pn $gn
                        done
                done
        else
                for pn in $@
                do
                        process_is_in_config $pn && format_print $pn `get_group_by_process $pn`
                done
        fi
else
        for pn in `get_all_process`
        do
                process_is_in_config $pn && format_print $pn `get_group_by_process $pn`
        done
fi