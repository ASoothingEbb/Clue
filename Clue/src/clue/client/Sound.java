/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author zemig
 */
public class Sound {
   private Clip clip;
   
    public static Sound sound1 = new Sound("/resources/music/backgroundMusic.wav");
    //public static Sound sound2 = new Sound("/sound1.wav");
    //public static Sound sound3 = new Sound("/sound1.wav");

    public Sound(String fileName) {
        try{
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
        clip = AudioSystem.getClip();
        clip.open(ais);
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
}
