#!/usr/bin/env sh

msg=`which javac`
if [ -z $msg ]; then
  echo "please make sure javac command in your \$PATH"
  exit 1
fi
echo "start compile source files"
javac com/binlee/net/EchoServer.java

msg=`which java`
if [ -z $msg ]; then
  echo "please make sure java command in your \$PATH"
  exit 1
fi
echo "start exec..."
java com.binlee.net.EchoServer