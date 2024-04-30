#!/bin/bash

echo $PWD
echo $HOME
echo $USER
echo $HOSTNAME
echo $OSTYPE
( set -o posix; set ) | less | grep 'hello'
env | grep 'world'
