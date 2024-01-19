#!/usr/bin/env bash

if [ $# -lt 1 ]; then
    echo "Usage: $0 give an argument as [template]"
    exit 1
fi

if [ $# -lt 2 ]; then
    echo "Usage: $0 give an argument as [namespace]"
    exit 1
fi

bb create-project.clj $1 $2
