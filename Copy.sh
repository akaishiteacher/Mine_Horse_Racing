#!/bin/sh
rm /home/mozipi/ドキュメント/mcserver/debugserver/plugins/*.jar
rm /home/mozipi/ドキュメント/mcserver/debugserver/plugins/MineHorseRacingPlugin/lang/*.lang
cd target/
cp *.jar /home/mozipi/ドキュメント/mcserver/debugserver/plugins
