
package com.sjriley
{//Package
	import com.sjriley.events.MicManagerEvent;
	import com.sjriley.Logger;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.SampleDataEvent;
	import flash.media.Sound;
	import flash.media.SoundChannel;
	import flash.utils.ByteArray;
	

	public class PlaybackManager extends EventDispatcher
	{//PlaybackManager Class
	
		private const BYTES_PER_SAMPLE:Number = 8; 	//8 for stereo
		private const NUM_SAMPLES:int = 2048;
		
		private var _micManager:MicManager;
		private var _newMicData:Boolean;
		private var _newBytes:Boolean;
		private var _playBackBytes:ByteArray;
		private var _isPlaying:Boolean;
		private var _sound:Sound;
		private var _soundChannel:SoundChannel;
		
		
		public function PlaybackManager($micManager:MicManager) 
		{//PlaybackManager
			Logger.log('new PlaybackManager')
			_playBackBytes = new ByteArray;
			_newMicData = false;
			_isPlaying = false;
			_newBytes = false;
			
			_sound = new Sound();
			_sound.addEventListener(SampleDataEvent.SAMPLE_DATA, handleSampleData, false, 0, true);
			
			_micManager = $micManager;
			_micManager.addEventListener(MicManagerEvent.NEW_DATA, handleNewMicData, false, 0, true);
		}//PlaybackManager
		
		private function handleSampleData(e:SampleDataEvent):void 
		{//handleSampleData
			
			for (var i:int = 0; i < NUM_SAMPLES; i++)
			{//feed sound
				if (_playBackBytes.bytesAvailable < 8)
				{//loop
					_playBackBytes.position = 0;
				}//loop
				
				//feed data
				e.data.writeFloat(_playBackBytes.readFloat());		//Left Channel
				e.data.writeFloat(_playBackBytes.readFloat());		//Right Channel
			}//feed sound
			
		}//handleSampleData
		
		public function togglePlay():void
		{//togglePlay
			Logger.log('play has been toggled')
			if (_isPlaying)
			{//stop
				stopPlay();
			}//stop
			else
			{//play
				startPlay();
			}//play
		}//togglePlay
		
		public function startPlay():void
		{//startPlay
			_isPlaying = true;
			_soundChannel = _sound.play();
			
			dispatchEvent(new Event(Event.CHANGE));
		}//startPlay
		
		public function stopPlay():void
		{//stopPlay
			_isPlaying = false;
			_soundChannel.stop();
			
			dispatchEvent(new Event(Event.CHANGE));
		}//stopPlay
		
		public function loadBytes($bytes:ByteArray):void
		{//loadBytes
			_newBytes = true;
			_playBackBytes.length = 0;
			_playBackBytes.writeBytes($bytes);
			trace("Loaded New Bytes");
			dispatchEvent(new Event(Event.CHANGE));
			_newBytes = false;
		}//loadBytes
		
		public function forceDataUpdate():void
		{//forceDataUpdate
			updateDataFromMic();
		}//forceDataUpdate
		
		private function updateDataFromMic():void
		{//updateDataFromMic
			reset();
			_playBackBytes.writeBytes(_micManager.recData);
			_newMicData = false;
			dispatchEvent(new Event(Event.CHANGE));
		}//updateDataFromMic
		
		private function handleNewMicData(e:MicManagerEvent):void 
		{//handleNewMicData
			trace("New Mic Data: " + ByteArray(e.data).length);
			_newMicData = true;
			updateDataFromMic();
		}//handleNewMicData
		
		private function reset():void
		{//reset
			_playBackBytes.length = 0;
		}//reset
		
		public function get isPlaying():Boolean { return _isPlaying; }
		public function get playBackBytes():ByteArray { return _playBackBytes; }
		public function get newBytes():Boolean { return _newBytes; }
		
	}//PlaybackManager Class

}//Package