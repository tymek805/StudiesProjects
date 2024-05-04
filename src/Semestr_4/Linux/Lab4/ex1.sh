#!/bin/bash
echo "Script name:" $0
echo "Number of params:" $#
for param in "$@"; do
    echo "- $param"
done
