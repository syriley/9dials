package com.sjriley 
{//Package
	import flash.events.Event;
	import flash.events.DataEvent;
	import com.sjriley.events.UploadManagerEvent;
	import flash.events.EventDispatcher;
	import flash.net.FileFilter;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLLoader;
	import flash.utils.ByteArray;
	import mx.core.FlexGlobals;
	import ru.inspirit.net.MultipartURLLoader;
	
	public class UploadManager extends EventDispatcher
	{//FileManager Class
	
		private var urlLoader:MultipartURLLoader;
		private var urlRequest:URLRequest;
		
	
		public function upload($fileBytes:ByteArray, $title:String):void
		{
			urlLoader = new MultipartURLLoader();
			urlLoader.addEventListener(Event.COMPLETE, handleUploadComplete, false, 0, true);
			//urlRequest = new URLRequest();

			//urlRequest.url = FlexGlobals.topLevelApplication.parameters.uploadUrl;
			//urlRequest.method = URLRequestMethod.POST;

			//urlRequest.data = $fileBytes;
			//Logger.log("uploading file to " + urlRequest.url);
			Logger.log('starting upload');
			try {
				urlLoader.addVariable("name", $title);
				urlLoader.addFile($fileBytes, 'newSource.ogg', 'file')
				urlLoader.load(FlexGlobals.topLevelApplication.parameters.uploadUrl);
			}
			catch (err:Error)
			{
			 	Logger.log(err.message);
			}
		}

		//Event
		private function handleUploadComplete(e:Event):void {			
			try {
				dispatchEvent(new UploadManagerEvent(UploadManagerEvent.UPLOAD_COMPLETE, e.target.data));
				Logger.log("upload completed successfully");
    		}
			 catch (err:Error)
			{
				Logger.log(err.message);
				Logger.log(err.toString());
				
			}
		}
	}
}