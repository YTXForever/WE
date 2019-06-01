#!/bin/bash
#

if [ "$1" == "-v" ];then
	VERBOSE=1
	shift
fi
if [ "$1" == "" ];then
	echo "commit message is blank"
	exit 1
fi
[ $VERBOSE == 1 ] && git pull || git pull > /dev/null 2>&1
if [ $? -ne 0 ]; then
	echo "pull error"
	exit 1
fi
echo "pull complete"
git add . > /dev/null 2>&1
git commit -m $1 > /dev/null 2>&1
git push > /dev/null 2>&1
if [ $? -ne 0 ]; then
	echo "push error"
	exit 1
fi
echo "push complete"
exit 0
