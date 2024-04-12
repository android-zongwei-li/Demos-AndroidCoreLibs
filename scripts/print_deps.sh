# 输入依赖库
# 先进入到上一级目录，否则找不到 gradlew 文件
cd ..
# 如果没有权限执行 gradlew 脚本文件，adb 命令前面加上 sh
# ./gradlew 模块名称:dependencies > 文件输出路径
# 输入文件名称建议：目录/模块名称_deps.txt
sh ./gradlew app:dependencies > scripts/app_deps.txt
# sh ./gradlew core_apis:dependencies > scripts/core_apis_deps.txt