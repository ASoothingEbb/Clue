/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author zemig
 */
public class Sound {
   private Clip clip;
   private FloatControl volume;
   private BooleanControl muteControl;
   private boolean muted;
   private float range;
   

    public Sound(String fileName) {
        try{
            File sound = new File(fileName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            clip = AudioSystem.getClip();
            clip.open(ais);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            range = volume.getMaximum() - volume.getMinimum();
            muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            muteControl.setValue(false);
            muted = false;
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
        }

    }
    
   public void play(){
       try{
           new Thread(){
            @Override
            public void run(){
                synchronized(clip){
                    clip.stop();
                    clip.setFramePosition(1000);
                    clip.start();
                }
            }
           }.start();
           
       } catch(Exception e){
           System.out.println("ASD");
            e.printStackTrace();
       }
   }
   
   public void stop(){
       if(clip == null) return;
       clip.stop();  
   }
   
   public void loop(){
        try{
            if(clip != null){
                new Thread(){
                    public void run(){
                        synchronized(clip){
                            clip.stop();
                            clip.setFramePosition(1000);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);

                        }
                    }
                }.start();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
   }
   
    public boolean isActive(){
       return clip.isActive();
    }
   
    public void setVolume(float f){
        if(f == 0.0){
            muteControl.setValue(true);
            muted = true;
            return;
        }
        muted = false;
        
        float gain = (range * f) + volume.getMinimum();
        muteControl.setValue(false);
        volume.setValue(gain);
    }
   
    public void toggleSound(){
        if(!muted) muteControl.setValue(!muteControl.getValue());
    }
    
    public void reset(){
        stop();
        clip.setFramePosition(1000);
    }
}
