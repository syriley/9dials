#/bin/bash
echo 'Building flash recorder'
mxmlc -source-path=src -library-path=lib -output=recorder.swf App.mxml
