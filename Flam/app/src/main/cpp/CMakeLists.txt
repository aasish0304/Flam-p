# Minimum CMake version
cmake_minimum_required(VERSION 3.22.1)

# Project name
project("flam")

# Add include path for OpenCV headers
include_directories(${CMAKE_SOURCE_DIR}/include)

# Create shared library from native-lib.cpp
add_library(${CMAKE_PROJECT_NAME} SHARED
        native-lib.cpp)

# Find system log library
find_library(log-lib log)

# Link libraries
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        ${log-lib})
