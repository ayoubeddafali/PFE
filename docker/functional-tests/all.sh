#!/usr/bin/env bash

#
#./wait-for-it.sh 46.101.62.85:8030 -t 120
#./wait-for-it.sh 46.101.62.85:8020 -t 120
#./wait-for-it.sh 46.101.62.85:8010 -t 120
./wait-for-it.sh 46.101.62.85:80 -t 120

exec $@