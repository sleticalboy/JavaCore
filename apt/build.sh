#!/bin/bash

base=$( cd "$( dirname "$0"  )" && pwd  )
echo "build.sh dir: $base"
cmd_home="$JAVA_HOME/bin"
#echo "command base: $cmd_home"
# 清除 out 目录
rm -rf $base/out && mkdir -p $base/out
# 开始编译源文件
#src_files=`find $base -type f -name "*.java"`
#echo -e "source files:\n$src_files"
$cmd_home/javac -d $base/out `find $base -type f -name "*.java"`
# 拷贝清单文件
# 清单文件必须以 Manifest-Version: 1.0 开头，且每行结束后需换行
cp -r src/META-INF $OU$base/out
# 创建 dist 目录
DIST="`pwd`/dist"
rm -rf $base/dist && mkdir -p $base/dist
# 打包
cd $base/out
$cmd_home/jar -cfm $base/dist/apt.jar META-INF/MANIFEST.mf META-INF/services/javax.annotation.processing.Processor \
`find $base/out -type f -name "*.class"`

cd $base

echo "`tree $base/out`"
echo "build finished `date`"