package com.sjriley.events 
{//Packge
	import flash.events.Event;
	
	/**
	 * ...
	 * @author Jake Callery
	 */
	public class UploadManagerEvent extends Event 
	{//MicManagerEvent Class

		static public const UPLOAD_COMPLETE:String = "uploadComplete";
	
		private var _data:String;
	
		public function UploadManagerEvent(type:String, $data:String=null, bubbles:Boolean=false, cancelable:Boolean=false) 
		{//MicManagerEvent 
			super(type, bubbles, cancelable);
			_data = $data;
		}//MicManagerEvent
		
		public override function clone():Event 
		{//clone
			return new UploadManagerEvent(type, _data, bubbles, cancelable);
		}//clone 
		
		public override function toString():String 
		{//toString
			return formatToString("UploadManagerEvent", "type", "data", "bubbles", "cancelable", "eventPhase"); 
		}//toString
		
		public function get data():String { return _data; }
		
	}//UploadManagerEvent Class
	
}//Package