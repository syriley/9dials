#/bin/bash
if [ $# -ne 1 ]; then
        echo "usage: ./build.sh [fb|nine|tuna]"
        exit 1
fi


if [ $1 == "nine" ]; then
    echo 'Building NineDials flash recorder'
    mxmlc -source-path=src -library-path=lib -output=recorder.swf NineDials.mxml
fi
if [ $1 == "fb" ]; then
    echo 'Building Facebook flash recorder'
    mxmlc -source-path=src -library-path=lib -output=recorder.swf Facebook.mxml
fi
if [ $1 == "tuna" ]; then
    echo 'Building Tuna flash recorder'
    mxmlc -source-path=src -library-path=lib -output=recorder.swf Tuna.mxml
fi

