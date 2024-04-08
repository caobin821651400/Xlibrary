#!/bin/bash
#
# Copyright (C) 2019 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# 1.配置看自己需求
COMMON_OPTIONS="
    --target-os=android
    --disable-static
    --enable-shared
    --disable-doc
    --disable-programs
    --disable-everything
    --disable-avdevice
    --disable-avformat
    --disable-swscale
    --disable-postproc
    --disable-avfilter
    --disable-symver
    --disable-avresample
    --enable-swresample
    --enable-avresample
    --enable-decoder=vorbis
    --enable-decoder=opus
    --enable-decoder=flac
    --enable-decoder=alac
    --enable-decoder=pcm_mulaw
    --enable-decoder=pcm_alaw
    --enable-decoder=mp3
    --enable-decoder=mp4
    --enable-decoder=amrnb
    --enable-decoder=amrwb
    --enable-decoder=aac
    --enable-decoder=ac3
    --enable-decoder=eac3
    --enable-decoder=dca
    --enable-decoder=mlp
    --enable-decoder=truehd
    --extra-ldexeflags=-pie
    "&& \

# 2.NDK路径，记得修改
NDK_PATH="/home/joker/exo/android-ndk-r21e-linux-x86_64/android-ndk-r21e"
HOST_PLATFORM="linux-x86_64"
TOOLCHAIN_PREFIX="${NDK_PATH}/toolchains/llvm/prebuilt/${HOST_PLATFORM}/bin"
# 3.FFMPEG路径，记得修改
cd "/home/joker/exo/ffmpeg"

./configure \
    --libdir=android-libs/armeabi-v7a \
    --arch=arm \
    --cpu=armv7-a \
    --cross-prefix="${TOOLCHAIN_PREFIX}/armv7a-linux-androideabi16-" \
    --nm="${TOOLCHAIN_PREFIX}/arm-linux-androideabi-nm" \
    --strip="${TOOLCHAIN_PREFIX}/arm-linux-androideabi-strip" \
    --extra-cflags="-march=armv7-a -mfloat-abi=softfp" \
    --extra-ldflags="-Wl,--fix-cortex-a8" \
    ${COMMON_OPTIONS}
make -j4
make install-libs
make clean
./configure \
    --libdir=android-libs/arm64-v8a \
    --arch=aarch64 \
    --cpu=armv8-a \
    --cross-prefix="${TOOLCHAIN_PREFIX}/aarch64-linux-android21-" \
    --nm="${TOOLCHAIN_PREFIX}/aarch64-linux-android-nm" \
    --strip="${TOOLCHAIN_PREFIX}/aarch64-linux-android-strip" \
    ${COMMON_OPTIONS}
make -j4
make install-libs
make clean
./configure \
    --libdir=android-libs/x86 \
    --arch=x86 \
    --cpu=i686 \
    --cross-prefix="${TOOLCHAIN_PREFIX}/i686-linux-android16-" \
    --nm="${TOOLCHAIN_PREFIX}/i686-linux-android-nm" \
    --strip="${TOOLCHAIN_PREFIX}/i686-linux-android-strip" \
    --disable-asm \
    ${COMMON_OPTIONS}
make -j4
make install-libs
make clean
./configure \
    --libdir=android-libs/x86_64 \
    --arch=x86_64 \
    --cpu=x86_64 \
    --cross-prefix="${TOOLCHAIN_PREFIX}/x86_64-linux-android21-" \
    --nm="${TOOLCHAIN_PREFIX}/x86_64-linux-android-nm" \
    --strip="${TOOLCHAIN_PREFIX}/x86_64-linux-android-strip" \
    --disable-asm \
    ${COMMON_OPTIONS}
make -j4
make install-libs
make clean

