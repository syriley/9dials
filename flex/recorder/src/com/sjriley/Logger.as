package com.sjriley
{
    import flash.external.ExternalInterface

	public class Logger {
		public static function log (message:String):void {
	        ExternalInterface.call( 'console.log', message);
	    }
	}
}