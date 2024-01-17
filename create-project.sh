#!/usr/bin/env bash

if [ $# -ge 2 ]; then
    echo "Usage: $0 [template]"
    exit 1
fi

if [ $# -ge 3 ]; then
    echo "Usage: $0 [namespace]"
    exit 1
fi

bb create-project.clj $1 $2
