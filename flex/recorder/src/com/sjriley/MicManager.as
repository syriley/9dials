package com.sjriley 

{//Package
	import com.sjriley.Logger;
	import com.sjriley.events.MicManagerEvent;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.system.Security;
	import flash.system.SecurityPanel;
	import flash.events.SampleDataEvent;
	import flash.events.StatusEvent;
	import flash.media.Microphone;
	import flash.utils.ByteArray;

	public class MicManager extends EventDispatcher
	{//MicManager Class
	
		private var _recData:ByteArray;
		private var _isRecording:Boolean;
		private var _mic:Microphone;
		private var _micDenied:Boolean;
		
		public function MicManager() 
		{//MicManager
			_isRecording = false;
			_micDenied = false;
			_recData = new ByteArray();
		}//MicManager
		
		public function initialise():void
		{
			//Setup mic
			_mic = Microphone.getMicrophone();
			if (_mic != null)
			{//wait for ready
				_mic.setLoopBack(false);
				_mic.setUseEchoSuppression(false);
				_mic.rate = 44;
				_mic.addEventListener(StatusEvent.STATUS, handleMicStatus, false, 0, true);
				if(_mic.muted){
					Logger.log("the mic is muted");
					dispatchEvent(new MicManagerEvent(MicManagerEvent.MIC_MUTED));
					Security.showSettings(SecurityPanel.PRIVACY);
				}
			}//wait for ready
			else
			{//no mic
				Logger.log("no mic");
			}//no mic
		}
		public function toggleRecording():void
		{//toggleRecording
			if (_isRecording)
			{//stop
				stopRecording();
			}//stop
			else
			{//start
				startRecording();
			}//start
		}//toggleRecording
		
		public function startRecording():void
		{//startRecording
			_recData.length = 0;
			_mic.setSilenceLevel(0);
			_mic.addEventListener(SampleDataEvent.SAMPLE_DATA, handleSampleData, false, 0, true);
			_isRecording = true;
			dispatchEvent(new Event(Event.CHANGE));
		}//startRecording
		
		public function stopRecording():void
		{//stopRecording
			_isRecording = false;
			_mic.setSilenceLevel(100);
			_mic.removeEventListener(SampleDataEvent.SAMPLE_DATA, handleSampleData, false);
			dispatchEvent(new Event(Event.CHANGE));
			dispatchEvent(new MicManagerEvent(MicManagerEvent.NEW_DATA, _recData));
		}//stopRecording
		
		private function handleMicStatus(e:StatusEvent):void 
		{//handleMicStatus
			Logger.log("Got Mic Status: " + e.code);
			
			if (e.code == "Microphone.Muted")
			{//denied
				Logger.log("Mic permission denied");
				stopRecording();
			}//denied
			
		}//handleMicStatus
		
		private function handleSampleData(e:SampleDataEvent):void 
		{//handleSampleData
			while (e.data.bytesAvailable)
			Logger.log("actually recording");
			{//save data
				//Grab bytes
				var samp:Number = e.data.readFloat();
				
				//convert to stereo
				_recData.writeFloat(samp);	//Left Channel
				_recData.writeFloat(samp);	//Right Channel
				
			}//save data
		}//handleSampleData
		
		public function reset():void
		{//reset
			_recData.length = 0;
		}//reset
		
		public function get recData():ByteArray { return _recData; }
		public function get isRecording():Boolean { return _isRecording; }
		public function get micDenied():Boolean { return _micDenied; }
		
	}//MicManager Class

}//Package