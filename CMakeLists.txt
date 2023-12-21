cmake_minimum_required(VERSION 3.26)
project(TEP)

set(CMAKE_CXX_STANDARD 17)

#List_3
#add_executable(TEP
#        List_3/main.cpp
#        List_3/Interface/Command.cpp
#        List_3/Interface/Command.h
#        List_3/Interface/UserInput.cpp
#        List_3/Interface/UserInput.h
#        List_3/Tree/Node.cpp
#        List_3/Tree/Node.h
#        List_3/Tree/Tree.cpp
#        List_3/Tree/Tree.h
#)
#add_executable(TEP
#        Lab4/main.cpp
#        Lab4/Interface.h
#        Lab4/Tree.h
#        Lab4/utilities/Node.h
#        Lab4/utilities/Operators.h
#        Lab4/utilities/Tools.h
#        Lab4/utilities/Type.h
#        Lab4/main.h
#        Lab4/main.h
#)
# List 2 executable config
add_executable(TEP List_2/main.cpp List_2/Number.cpp List_2/Number.h)
#add_executable(TEP Lab5/main.cpp Lab5/MySmartPointer.h Lab5/main.cpp Lab5/RefCounter.h Lab5/Tree.h Lab4/Interface.h Lab4/utilities/Tools.h Lab5/utilities/Node.h Lab5/utilities/Operators.h Lab5/utilities/Tools.h Lab5/utilities/Type.h Lab5/Interface.h)