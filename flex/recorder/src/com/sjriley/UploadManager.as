package com.sjriley 
{//Package
	import flash.events.Event;
	import flash.events.DataEvent;
	import flash.events.EventDispatcher;
	import flash.net.FileFilter;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLLoader;
	import flash.utils.ByteArray;
	import mx.core.FlexGlobals;
	
	public class UploadManager extends EventDispatcher
	{//FileManager Class
	
		private var urlLoader:URLLoader;
		private var urlRequest:URLRequest;
		
	
		public function upload($fileBytes:ByteArray):void
		{
			urlLoader = new URLLoader();
			urlLoader.addEventListener(Event.COMPLETE, handleUploadComplete, false, 0, true);
			urlRequest = new URLRequest();

			urlRequest.url = FlexGlobals.topLevelApplication.parameters.uploadUrl;
			urlRequest.method = URLRequestMethod.POST;
			urlRequest.contentType = "application/octet-stream";
			urlRequest.data = $fileBytes;
			Logger.log("uploading file to " + urlRequest.url);
			urlLoader.load(urlRequest);
		}

		//Event
		private function handleUploadComplete(e:Event):void {
			Logger.log("upload completed successfully");
		}
	}
}